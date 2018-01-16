import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * Simple class that serves to be an Actor to display the image. It also contains an important method that converts a buffered image to a greenfoot image.
 * 
 * (Revised 11/14 to avoid crashing if user cancels import operation).
 * 
 * @author Jordan Cohen
 * @version November 2013, revised
 */
public class ImageHolder extends Actor
{
    private GreenfootImage imageToDisplay; 
    boolean doOnce=false;

    /**
     * Construct an ImageHolder with a file name. If there is an error, 
     * show a blank GreenfootImage.
     * 
     * @param fileName  Name of image file to be displayed.
     */
    public ImageHolder (String fileName)
    {
        openFile (fileName);
    }

    /**
     * Construct an ImageHolder with a greenfoot image.
     * 
     * @param image - greenfoot Image to be displayed
     */
    public ImageHolder (GreenfootImage image){
        setImage (image);
    }

    /**
     * Save the starting image in the processing class for the undo/redo features
     */
    public void act(){
        if (doOnce ==false){  // run once
            Processor.addImage(this.getBufferedImage());  // save the starting image 
            doOnce =true;  // do not run again
        }

    }

    /**
     * Attempt to open a file and assign it as this Actor's image
     * 
     * @param fileName  Name of the image file to open (must be in this directory)
     * @return boolean  True if operation successful, otherwise false
     */
    public boolean openFile (String fileName)
    {
        try {
            if (fileName != null)
            {
                imageToDisplay = new GreenfootImage (fileName);
                setImage(imageToDisplay);
            }
            else
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Allows access to my awtImage - the backing data underneath the GreenfootImage class.
     * 
     * @return BufferedImage returns the backing image for this Actor as an AwtImage
     */
    public BufferedImage getBufferedImage ()
    {
        return this.getImage().getAwtImage();
    }

    /**
     * Converts a buffered image to a greenfoot image.
     * 
     * @param newBi- buffered image to be converted
     * @return GreenfootImage - a greenfoot image of the buffered image
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
