import greenfoot.GreenfootImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JOptionPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
/**
 * Starter code for Processor - the class that processes images.
 * <p>
 * This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. 
 * 
 * The function of each text button in each tab is controlled in this class along with the saving option.
 * 
 * @author Jordan Cohen/Tishko Araz
 * @version 2017/05/08
 */
public class Processor  
{
    // variables that store the ARGB values at a given pixel
    private static int alpha;
    private static int red;
    private static int green; 
    private static int blue; 

    private static ArrayList<BufferedImage> images=new ArrayList<BufferedImage>();  // an array list to store all images that have been manipulated (used for the undo feature) - image
    private static ArrayList <BufferedImage> redoImages= new ArrayList <BufferedImage>();  // an array list to save all the images that have experienced "undo" (for the redo feature) - image
    
    /**
     * Blurs the image every time the blur button is pressed. The blur effect is known as Gaussian Blur.
     * @author http://www.jhlabs.com/ip/blurring.html
     * @param bi - the buffered image to be blurred
     * 
     */
    public static void blur (BufferedImage bi){

        float[] matrix = {
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
            };

        BufferedImage biCopy = deepCopy(bi);
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
        op.filter(biCopy, bi);

        addImage (bi);  // add the image to the undo array list for images
    }

    /**
     * Adds a brownish layer over the image. Can only be used once.
     * Equates all RGB values, then increases the red and green values by a certain sepia depth (brown degree).
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi - buffered image the brownish layer will be add on
     * 
     */
    public static void sepia(BufferedImage bi)
    {

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        int sepiaDepth = 20;  // the degree of the brown layer

        // loop through every single pixel using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                //Equalizes all RGB values, and then uses Sepia depth to increase Red and Green amounts by a certain value.
                int equalizer = (red + green + blue) / 3;

                red = equalizer + (sepiaDepth * 2); 
                blue = equalizer;
                green = equalizer + sepiaDepth;                

                //Restriction for values of RGB to avoid color saturation
                if (red>255) red=255;
                if (green>255) green=255;
                if (blue>255) blue=255;

                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used
                bi.setRGB (x, y, newColour);  // draws the pixels in the buffered image
            }
        }
        // if the type is image 
        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Reflects the right side of the image and draws it on the left side producing a concaving (mirror) effect.
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi - buffered image that will undergo the effect.
     * 
     * 
     */
    public static void mirrorHorizontally (BufferedImage bi)
    {

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        //For loop used similar to in ArrayList. This allows for the swapping of pixels at certain locations with other pixels. Effectively reversing half of the 2-D array.
        //This produces the results seen with button press. 
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {

                setRGBValues(bi, xSize-x-1,y);  // store the RGB values in that pixel in the red,blue, green variables

                // packs pixels to be placed into the buffered image that was initially used
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }      

        addImage (bi);  // add the image to the undo array list for images
    }
    /**
     * Reflects the bottom side of the image and draws it on the upper side producing a concaving (mirror) effect.
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi - buffered image that will undergo the effect.
     * 
     * 
     */
    public static void mirrorVertically (BufferedImage bi)
    {

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        //For loop used similar to in ArrayList. This allows for the swapping of pixels at certain locations with other pixels. Effectively reversing half of the 2-D array.
        //This produces the results seen with button press. 
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {

                setRGBValues(bi, x,ySize-y-1);   // store the RGB values in that pixel in the red,blue, green variables
                // packs pixels to be placed into the buffered image that was initially used
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }      

        addImage (newBi);  // add the image to the undo array list for images
    }
    /**
     * Acts as a color filter that manipulates different colors at the same time. 6 effects in total. It is recommended to use this method in a colorful image
     * to best display its function.
     * Effect 1- Has 5 effects. Applies different effect every time this method is called. The user presses the "Effect 1" button 5 times to loop through each effect.
     * Each rgb value gets assigned the other 2 rgb values and their negative values (separatly- 4 effects). It also gets assigned its own negative value which will 
     * give the user the negative effect (5th effect).
     * Effect 2- Has 3 effects. Applies different effect every time this method is called. The user presses the "Effect 2" button 3 times to loop through each effect.
     * Each rgb value gets assigned the other 2 rgb values (switches between the rgb values). Similar to effect 1, but without the negative effect feature.
     * Effect 3- Has 2 effects. Applies different effect every time this method is called. The user presses the "Effect 3" button 2 times to loop through each effect.
     * First effect is a negative effect except that the blue values get the negative green instead of negative blue. The second effect is switching back to the original
     * image but all the blue values get converted to the green values.
     * 
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi - the buffered image to be manipulated
     * @effect - the type of effect (e.g. "effect1" for the first effect, "effect2" for the second effect, etc.)
     * 
     */
    public static void advancedFilter (BufferedImage bi, String effect)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {

                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                // assign temporary variables to store the original rgb colors for each pixel
                int tempRed=red;  
                int tempBlue=blue;
                int tempGreen=green;

                if (effect== "effect1"){  // if user chooses effect 1, then

                    red=255-blue;   // red gets the neg value of blue
                    blue =255-green;  // blue gets the neg value of green
                    green=255-tempRed;  // green gets the neg value of (original) red

                    // this also works as a loop
                    // every time this method runs, each rgb value will be assigned a different value 
                    // eventually each rgb value will be assigned the original and neg values of each rgb
                    // this will produce 5 effects and the sixth one will be the original image (when each rgb value gets assigned back its original rgb value)
                }

                if (effect== "effect2"){  // if user chooses effect 2, then

                    red= tempGreen;   // red gets green value
                    blue =tempRed;   // blue gets red value
                    green=tempBlue;  // green gets blue value

                    // this also works as a loop
                    // every time this method runs, each rgb value will be assigned a different value 
                    // eventually each rgb value will be assigned the each rgb value
                    // similar to effect 1 but without the neg effect
                }

                if (effect== "effect3"){  // if user chooses effect 3, then

                    red=255-red;   // red gets its neg value 
                    blue =255-tempGreen;  // blue gets the neg value of green
                    green=255-green;   // green gets its neg value

                    // this also works as loop
                    // every time this method runs, each rgb value will be assigned a different value 
                    // one time will be neg value and the other time will be the original value of that neg
                    // blue will either be assigned the neg value of green or the actual value of green
                }

                

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);

            }
        }

        addImage (bi);  // add the image to the undo array list for images
    }
    /**
     * Acts as a color filter. Changes the color of the image to the specified color.
     * Enables the user to keep darkening the color by constantly calling the method (keep clicking on the specific color filter button).
     * Utilizes pixel packaging and unpackaging methods. 
     * @param bi- the buffered image that will change color
     * @param color- the color the image will change to (e.g. "green", "red", "darkBlue", "yellow", "purple", "lightBlue")
     * 
     */
    public static void filter (BufferedImage bi, String color)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                if (color=="green"){
                    // make the image green by reducing the other rgb values to 0 except green
                    blue=0;
                    red=0;

                    // when the user press the green button, the green color should become darker
                    if (green >150){  // if green did not reach "saturation poing", then
                        green-=5;  // decrease the green value
                    }
                }

                if (color=="red"){
                    // make the image red by reducing the other rgb values to 0 except red
                    blue=0;
                    green=0;

                    // when the user press the red button, the red color should become darker
                    if (red >150){  // if red did not reach "saturation poing", then
                        red-=5;  // decrease the red value
                    }
                }

                if (color=="darkBlue"){
                    // make the image darkBlue by reducing the other rgb values to 0 except blue
                    green=0;
                    red=0;

                    // when the user press the darkBlue button, the darkBlue color should become darker
                    if (blue >150){  // if darkBlue did not reach "saturation poing", then
                        blue-=5;  // decrease the blue value
                    }
                }

                if (color=="yellow"){
                    // make the image yellow by reducing the blue value to 0 (yellow made out of red and green values only)
                    blue=0;

                    // when the user press the yellow button, the yellow color should become darker
                    if (green >150 && red >150){  // if yellow did not reach "saturation poing", then
                        green-=5;  // decrease the green value
                        red-=5;  // decrease the red value
                    }
                }

                if (color=="purple"){ 
                    // make the image purple by reducing the green value to 0 (purple made out of red and blue values only)
                    green=0;

                    // when the user press the purple button, the purple color should become darker
                    if (blue >150 && red >150){  // if purple did not reach "saturation poing", then
                        blue-=5;  // decrease the blue value
                        red-=5;  // decrease the red value
                    }
                }

                if (color=="lightBlue"){
                    // make the image lightBlue by reducing the red value to 0 (lightBlue made out of blue and green values only)
                    red=0;

                    // when the user press the lightBlue button, the lightBlue color should become darker
                    if (blue >150 && green >150){  // if ligthBlue did not reach "saturation poing", then
                        blue-=5;  // decrease the blue value
                        green-=5; // decrease the green value
                    }
                }

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images
    }
    /**
     * Decreases the transparency of the image by 10 values every time.
     * Can be used more than once.
     * 
     * @param bi - buffered image that will undergo the effect.
     *
     */
    public static void transparency (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                if (alpha > 50){
                    alpha-=10;
                }

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images
    }
    /**
     * Swaps the RGB values with their opposite values closer to 255. Produces an x-ray effect.
     * Can be used to switch back to the original image.
     * Utilizes pixel packaging and unpackaging methods. 
     * @param bi - buffered image to apply the effect to
     *
     */
    public static void negative (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                // apply the opposite end of the rgb spectrum for each rgb color
                red = 255-red;
                green = 255-green;
                blue = 255-blue;

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Increases the red color. Makes the image appear as if it is having a warm atmosphere.
     * Can be used more than once. Gets warmer every time it is called (to a certain extent-until saturation)
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi- buffered image to apply the effect to
     * 
     */
    public static void warm (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                // every time the user calls the method
                // the image should get warmer by increasing the red color
                if (red+20 < 255){ // if the red color does not go beyond 255 after applying the effect (hence +20),
                    red +=5;  // increase the red color to make it look warmer
                }

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Decreases the red color. Makes the image appear as if it is having a cold atmosphere.
     * Can be used more than once. Gets colder every time it is called (to a certain extent-until saturation)
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi- buffered image to apply the effect to
     * 
     */
    public static void cold (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                // every time the user calls the method
                // the image should get warmer by increasing the red color
                if (red-20 > 0){ // if the red color does not go below 0 after applying the effect (hence -20),
                    red -=5;  // increase the red color to make it look warmer
                }

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Makes the image appear brighter by increasing all the RGB values. Makes it appear as if it is exposed to more lighting.
     * Can be used more than once. Gets colder every time it is called (to a certain extent-until saturation)
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi- buffered image to apply the effect to
     * 
     */
    public static void bright (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                // every time the user calls the method
                // the image should get brighter by increasing all the RGB colors
                if (red+125<= 255){  // if the red color does not go beyond 255 after applying the effect (hence +125)
                    red+=5;  // increase the red value

                }
                if (green+125 <= 255){  // if the green color does not go beyond 255 after applying the effect (hence +125)
                    green +=5;  // increase the green value

                }
                if (blue+125<= 255){  // if the blue color does not go beyond 255 after applying the effect (hence +125)
                    blue +=5;  // increase the blue value

                } 

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Changes the image to a black and white image by equating all the RGB values to have the same value in each pixel.
     * Utilizes pixel packaging and unpackaging methods. 
     * 
     * @param bi- the image to apply the effect to
     *
     */
    public static void greyScale (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // loop through every pixel in the given buffered image using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                setRGBValues(bi, x,y);   // store the RGB values in that pixel in the red,blue, green variables

                int average = (red+green+blue)/3;  // calculating the average of the RGB values in each pixel

                // assign the average value to each of the RGB values
                red = average;
                green = average;
                blue = average;

                // packs pixels to be placed into the buffered image that was initially used.
                int newColour = packagePixel (red, green, blue, alpha);

                // draws the pixels in the buffered image
                bi.setRGB (x, y, newColour);
            }
        }

        addImage (bi);  // add the image to the undo array list for images

    }

    /**
     * Flips the image horizontally. Can be called more than once (flips every time the user clicks the flipHorizontal button).
     * Utilizes pixel packaging and unpackaging methods. 
     * @param bi- buffered image to be horizontally flipped
     *
     */
    public static void flipHorizontal (BufferedImage bi)
    {

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        /**
         * INSERT TWO LOOPS HERE:
         * - FIRST LOOP MOVES DATA INTO A SECOND, TEMPORARY IMAGE WITH PIXELS FLIPPED
         *   HORIZONTALLY
         * - SECOND LOOP MOVES DATA BACK INTO ORIGINAL IMAGE
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 

        // loop through every pixel in the original pic using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               
                newBi.setRGB (xSize-x-1,y, bi.getRGB (x,y));  // gets all the colors of the pixels and saves them in a new temp variable (pixels are flipped along the y-axis)

            }
        }
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               

                bi.setRGB (x,y, newBi.getRGB (x,y));  // gets all the colors of the pixels from the temp variable and saves back to the orginal
            }
        }

        addImage (newBi);  // add the image to the undo array list for images
    }
    /**
     * Flips the image vertically. Can be called more than once (flips every time the user clicks the flipVertical button).
     * Utilizes pixel packaging and unpackaging methods. 
     * @param bi- buffered image to be vertically flipped
     *
     */
    public static void flipVertical (BufferedImage bi)
    {

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        // loop through every pixel in the original pic using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               
                newBi.setRGB (x,ySize-y-1, bi.getRGB (x,y));  // gets all the colors of the pixels and saves them in a new temp variable (pixels are reversed upside down)

            }
        }
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               

                bi.setRGB (x,y, newBi.getRGB (x,y));
            }
        }

        // loop through every pixel in the original pic using size as limit

        addImage (newBi);  // add the image to the undo array list for images
    }
    /**
     * Returns a buffered image of the original image rotated clockwise. The image must be converted to a greenfoot image later if it was to be set as an image for an actor.
     * 
     * @param bi - image to be rotated clockwise
     * @return BufferedImage - the rotated image
     *
     */
    public static BufferedImage rotateCW (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // New image, to store pixels as we reverse everything. Can't use old image due to different dimension as a result of the rotation
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);

        // loop through every pixel in the original pic using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               
                newBi.setRGB (ySize-y-1,x, bi.getRGB (x,y));  // save colors of the pixels of the original image in the new image but flip the x and y coordintes and 
                // perform a reflection on the y coordinates (which are now the x-coordinates)

            }
        }

        addImage (newBi);  // add the image to the undo array list for images

        return newBi;  // return this new image to be converted to a greenfoot image
    }
    /**
     * Returns a buffered image of the original image rotated counter-clockwise. The image must be converted to a greenfoot image later if it was to be set as an image for 
     * an actor.
     * 
     * @param bi - image to be rotated counter-clockwise
     * @return BufferedImage - the rotated image
     * 
     */
    public static BufferedImage rotateCCW (BufferedImage bi){

        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // New image, to store pixels as we reverse everything. Can't use old image due to different dimension as a result of the rotation
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);

        // loop through every pixel in the original pic using size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {               
                newBi.setRGB (y,xSize-x-1, bi.getRGB (x,y)); // save colors of the pixels of the original image in the new image but flip the x and y coordintes and 
                // perform a reflection on the x coordinates (which are now the y-coordinates)

            }
        }

        addImage (newBi);  // add the image to the undo array list for images

        return newBi;  // return this new image to be converted to a greenfoot image
    }

    private static BufferedImage previousImage;  // last image that was manipulated right before the current image

    /**
     * Undo the user's last step. Brings back the picture right before the current picture on the screen.
     * @return BufferedImage - the picture right before the current picture
     */
    public static BufferedImage undo (){

        try{
            if (images.size()>0){  // if there are images saved (in the undo list)
                previousImage = images.get(images.size()-2);  // the previous image is the last image that was manipulated right before the current image
                redoImages.add(deepCopy(images.get(images.size()-1))); // add the current image to the redo list in case the user wants to bring it back using the redo button
                images.remove(images.size()-1);  // remove the current image from the saved images list (undo list)
               
            }
        }
        catch(ArrayIndexOutOfBoundsException e){  // nothing more to undo
            previousImage = images.get(images.size()-1);

        } 
         return previousImage; // return the (buffered) image the user wishes to bring back
    }

    /**
     * Redo the user's last step. Brings back the last picture the user removed using the undo feature.
     * @param current- current buffered image
     * @return BufferedImage - the picture right after the current picture (that was undo-ed)
     */
    public static BufferedImage redo (BufferedImage current){

        try{
            //if (redoImages.size()>0){  // if there are images saved (in the redo list)
            previousImage = redoImages.get(redoImages.size()-1); // previous image is the last image in the redo list (the image that got removed from the screen due to the undo button)
            addImage(redoImages.get(redoImages.size()-1));  // save the image you are about to remove (in the undo list) in case the user wants to bring it back using the undo button
            redoImages.remove(redoImages.size()-1);  // remove that last image from the redo list
            //}
        }
        // if the user presses the redo button before the undo
        catch(ArrayIndexOutOfBoundsException e){return current;}
        catch (NullPointerException k){
            return current;}// nothing has been undo-ed yet, therefore, there is no images in the array list

        return previousImage; // return the (buffered) image the user wishes to bring back
    }


    /**
     * Saves the image that was just manipulated using any of the methods in the Processor class. Can also save the original picture.
     * Mainly used to access previous images for the undo feature.
     * Utilizes deepCopy method.
     * 
     * @param bi - the image wished to be saved
     */
    public static void addImage (BufferedImage bi){
        
        images.add(deepCopy(bi));
        if(images.size()-1>200){//memory allocation see me to look at calculation for the number 200
            images.remove(0);
        }
    }


    /**
     * Returns a new unrelated buffered image of the buffered image that was passed by reference.
     * Called by the addImage method.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * @return  BufferedImage Returns the BufferedImage that is used for undoing.
     */
    public static BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }

    /** 
     * Stores the RGB values in RGB variables based on the given pixel.
     * 
     * @param bi - buffered image the brownish layer will be add on
     * @param x - x-coordinate of the pixel
     * @param y - y-coordinate of the pixel
     */
    private static void setRGBValues(BufferedImage bi, int x, int y){
        // Calls method in BufferedImage that returns R G B and alpha values
        // encoded together in an integer
        int rgb = bi.getRGB(x, y);

        // Call the unpackPixel method to retrieve the four integers for
        // R, G, B and alpha and assign them each to their own integer
        int[] rgbValues = unpackPixel (rgb);
        alpha = rgbValues[0];
        red = rgbValues[1];
        green = rgbValues[2];
        blue = rgbValues[3];    

    }

    /** 
     * 
     * Saves as PNG. Image located in the same location as the .java files. Combines the layer with the image to produce a final image.
     * 
     * @param image- image to be combined and saved
     *
     * 
     */
    public static void saveAsPNG(GreenfootImage image){

        // save it as a final image
        GreenfootImage  finalImage = image;
        //Asks for user input.
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)");

        fileName += ".png";  // saves the file extension (type of image)
        File f = new File (fileName);  // type of file to be saved in
        try{
            ImageIO.write(finalImage.getAwtImage(), "png", f);  // saves the image
        } catch (IOException e)//Catches an input output exception if necessary.
        {
            System.out.println ("Found Exception");
        }

        JOptionPane.showMessageDialog (null, "Successfully saved as " + fileName + "!");//Text to indicate success in saving.
    }

    /** 
     * 
     * Saves as JPG. Image located in the same location as the .java files. Combines the layer with the image to produce a final image.
     * @author http://www.javamex.com/tutorials/graphics/bufferedimage_save_png_jpeg.shtml
     * @param image- image to be combined and saved
     * @param layer- layer to be combined and saved
     * 
     */
    public static void saveAsJPG(GreenfootImage image){

        // save it as a final image
        GreenfootImage  finalImage = image;
        //Asks for user input.
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)");

        fileName += ".jpg";  // saves the file extension (type of image)
        File f = new File (fileName);  // type of file to be saved in
        try{
            ImageIO.write(finalImage.getAwtImage(), "jpg", f);  // saves the image
        } catch (IOException e)//Catches an input output exception if necessary.
        {
            System.out.println ("Found Exception");
        }

        JOptionPane.showMessageDialog (null, "Successfully saved as " + fileName + "!");//Text to indicate success in saving.
    }
    

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * @param newBi The BufferedImage to convert.
     * @return GreenfootImage A GreenfootImage built from the BufferedImage provided.
     */

    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);
        return returnImage;
    }
}
