import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import java.lang.Object;
import java.awt.*;
import javax.swing.DebugGraphics;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import java.awt.image.BufferedImage;

/**
 * Drop down menu controls and spawns all the actors on the world. It controls how the drop down menu acts. If the mouse hovers over any tab, a list of options appear and 
 * the user can click on any text button to perform the feature the text button says. The methods of the text buttons are called from the Processor class.
 * 
 * @author Ramy Elbakari 
 * @version April 23, 2015
 */
public class DropDownMenu extends Actor
{
    // Constants:
    private final String STARTING_FILE = "rainbow.jpg";  // name of the starting file

    // Objects and Variables:
    private ImageHolder image;  // object that will store image

    // objects that will store the undo and redo buttons
    private ImageHolder2 undo;
    private ImageHolder2 redo;

    // text button tabs
    private TextButton translation;
    private TextButton filter;
    private TextButton advancedFilter;
    private TextButton effects;
    private TextButton draw1;
    private TextButton file;

    // All the text buttons in all of the tabs
    // file buttons
    private TextButton openButton;
    private TextButton savePNGButton;  

    // filter tab
    private TextButton greenButton;
    private TextButton redButton;
    private TextButton darkBlueButton;
    private TextButton yellowButton;
    private TextButton purpleButton;
    private TextButton lightBlueButton;

    // advancedFilter tab
    private TextButton effect1;
    private TextButton effect2;
    private TextButton effect3;
    private TextButton effect4;
    private TextButton effect5;
    private TextButton effect6;  

    // translation tab
    private TextButton CWRButton;
    private TextButton CCWRButton;
    private TextButton hRevButton;
    private TextButton vRevButton;
    private TextButton mirrorVerticallyButton;
    private TextButton mirrorHorizontallyButton;

    // draw tab
    private TextButton drawButton;
    private TextButton eraseButton;
    private TextButton chooseColor;
    private TextButton paletteButton;

    // effects tab
    private TextButton transButton;
    private TextButton negButton;
    private TextButton warmButton;
    private TextButton coldButton;
    private TextButton brightButton;
    private TextButton greyScaleButton;    
    private TextButton sepiaButton;
    private TextButton blurButton;    

    private Palette palette;  // object that will store the palette

    private Draw layer;  // layer that the brush/eraser will work on

    private boolean once=true;  // intiate only once(built the initial tabs, images and other components of the starting screen)
    private boolean draw =false;  // variable that indicates the brush option has been selected
    private boolean erase =false;  // variable that indicates the erase option has been selected

    private boolean enableDraw =false;  // variable that enables the brush to draw
    private boolean enableErase =false;  // variable that enables the eraser to erase

    private static int red, blue, green;  // variables used in the java panel that enables the user to choose a specific color by inputting all 3 RGB values
    private static int color;  // variable used to convert the three RBG values to a specific color

    // variables used to check if the mouse is over each specific tab  (true-mouse is over the tab, false-is not)
    private boolean translationCheck=false;
    private boolean filterCheck=false;
    private boolean advancedFilterCheck=false;
    private boolean effectsCheck=false;
    private boolean draw1Check=false;
    private boolean fileCheck=false;

    private int [] x = {50, 125, 237, 370, 470, 560};  // x-coordinates of the tabs
    private int y= 19;  // y-coordinate of the tab

    private boolean viewPalette=false;  // check whether the palette is displayed on the screen (true-is diplayed, false- is not)

    private GreenfootImage fileNameLabel;  // label that will show the file name
    private ImageHolder2 fileName;  // the object that will act as the holder of that label

    private GreenfootImage indicatorLabel;     // label that will show whether the brush or eraser is in use (won't show anything if nothing is being used)
    private ImageHolder2 indicator;    // the object that will act as the holder of that label
    
    /**
     * Initializes all the actors in the start up screen.
     * Assigns the labes of each tab and text button.
     * Assigns the image.
     */
    public DropDownMenu(){
        // Initializing the tabs - true changes the color of an original textbutton to a color that indicates it is acting as a tab
        translation = new TextButton(" [ Translation ] ", true);
        filter = new TextButton(" [ Filter ] ", true);
        advancedFilter = new TextButton(" [ Advanced Filter ] ", true);
        effects = new TextButton(" [ Effects ] ", true);
        draw1 = new TextButton(" [ Draw ] ", true);
        file = new TextButton(" [ File ] ", true);

        // Initializng the text buttons in all of the tabs
        // file tab
        openButton = new TextButton(" [ Open File ] ");
        savePNGButton= new TextButton(" [ Save PNG ] ");

        // filter tab
        greenButton = new TextButton(" [ Green ] ");
        redButton = new TextButton(" [ Red ] ");
        darkBlueButton = new TextButton(" [ Dark Blue ] ");
        yellowButton = new TextButton(" [ Yellow ] ");
        purpleButton = new TextButton(" [ Purple ] ");
        lightBlueButton = new TextButton(" [ Light Blue ] ");

        // advancedFilter tab
        effect1 = new TextButton(" [ Effect 1 ] ");
        effect2 = new TextButton(" [ Effect 2 ] ");
        effect3 = new TextButton(" [ Effect 3 ] ");
        effect4 = new TextButton(" [ Effect 4 ] ");
        effect5 = new TextButton(" [ Effect 5 ] ");
        effect6 = new TextButton(" [ Effect 6 ] ");

        // translation tab
        CWRButton = new TextButton(" [ CW Rotate ] ");
        CCWRButton = new TextButton(" [ CCW Rotate ] ");
        hRevButton = new TextButton(" [ Flip Horizontal ] ");
        vRevButton = new TextButton(" [ Flip Vertical ] ");        
        mirrorVerticallyButton = new TextButton(" [ Mirror Horizontal] ");
        mirrorHorizontallyButton = new TextButton(" [ Mirror Vertical] ");

        // draw tab
        drawButton= new TextButton(" [ Brush ] ");
        eraseButton= new TextButton(" [ Erase ] ");
        chooseColor= new TextButton(" [ Choose Color ] ");
        paletteButton= new TextButton(" [ View Palette ] ");

        // effects tab
        transButton = new TextButton(" [ Transparency ] ");
        negButton = new TextButton(" [ Negative ] ");
        warmButton = new TextButton(" [ Warm ] ");
        coldButton = new TextButton(" [ Cold ] ");
        brightButton = new TextButton(" [ Bright ] ");
        greyScaleButton = new TextButton(" [ Grey Scale ] ");       
        sepiaButton = new TextButton(" [ Sepia ] ");
        blurButton = new TextButton(" [ Blur ] ");
        brightButton = new TextButton(" [ Bright ] ");        

        image = new ImageHolder(STARTING_FILE);  // initializing the object that will display the image

        // initializing the label that will display the file name along with the object that will display that label
        //fileNameLabel = new GreenfootImage (" [ Open File: " + STARTING_FILE + " ] ", 20, , null);
        fileName = new ImageHolder2 (fileNameLabel);

        // initializing the label that will display the whether the brush or eraser is in use along with the object that will display that label
        //indicatorLabel = new GreenfootImage ("", 20, Color.YELLOW, null);
        indicator = new ImageHolder2 (indicatorLabel);

        // initialzing the undo and redo buttons
        undo = new ImageHolder2("undo.png");
        redo = new ImageHolder2("redo.png");

        palette =new Palette();  // intializing palette

        layer= new Draw(image.getImage().getWidth(), image.getImage().getHeight());  // initializing the layer

    }
    private int thickness=6;  // how much should the text buttons of each tab be spaced apart when they are dropped
    /**
     * Displays the drop down menu when the user hovers over any of the tabs.
     * Performs the function of each text button. The method of each text button is found in the Processor class.
     */
    public void act() 
    {
        // Initiate the starter screen -will only execute once (more on that in the method itself)
        initiate();

        // Checks if the mouse moved over any of the tabs
        // If yes, displays the specific text buttons of each tab under each other like a drop down menu.
        if (Greenfoot.mouseMoved(translation)){
            getWorld().addObject (CWRButton,x[3],40+thickness);
            getWorld().addObject (CCWRButton,x[3],67+thickness);
            getWorld().addObject (hRevButton,x[3],94+thickness);
            getWorld().addObject (vRevButton,x[3],121+thickness);
            getWorld().addObject (mirrorHorizontallyButton,x[3],148+thickness);
            getWorld().addObject (mirrorVerticallyButton,x[3],175+thickness);

            translationCheck=true;  // the mouse hovered over the translation tab - (variable will be used to remove all the text buttons when the mouse is no longer on
            // the tab
        }
        else if (Greenfoot.mouseMoved(filter)){
            getWorld().addObject (greenButton,x[1],40+thickness);
            getWorld().addObject (redButton,x[1],67+thickness);
            getWorld().addObject (darkBlueButton,x[1],94+thickness);
            getWorld().addObject (yellowButton,x[1],121+thickness);
            getWorld().addObject (purpleButton,x[1],148+thickness);
            getWorld().addObject (lightBlueButton,x[1],175+thickness);

            filterCheck=true;
        }
        else if (Greenfoot.mouseMoved(advancedFilter)){
            getWorld().addObject (effect1,x[2],40+thickness);
            getWorld().addObject (effect2,x[2],67+thickness);
            getWorld().addObject (effect3,x[2],94+thickness);
            getWorld().addObject (effect4,x[2],121+thickness);
            getWorld().addObject (effect5,x[2],148+thickness);
            getWorld().addObject (effect6,x[2],175+thickness);

            advancedFilterCheck=true;
        }
        else if (Greenfoot.mouseMoved(effects)){

            getWorld().addObject (warmButton,x[5],40+thickness);
            getWorld().addObject (coldButton,x[5],67+thickness);
            getWorld().addObject (brightButton,x[5],94+thickness);
            getWorld().addObject (blurButton,x[5],121+thickness);
            getWorld().addObject (negButton,x[5],148+thickness);
            getWorld().addObject (sepiaButton,x[5],175+thickness);
            getWorld().addObject (greyScaleButton,x[5],202+thickness);
            getWorld().addObject (transButton,x[5],229+thickness);

            effectsCheck=true;
        }
        else if (Greenfoot.mouseMoved(draw1)){
            getWorld().addObject (drawButton,x[4],40+thickness);
            getWorld().addObject (eraseButton,x[4],67+thickness);
            getWorld().addObject (chooseColor,x[4],94+thickness);
            getWorld().addObject (paletteButton,x[4],121+thickness);

            draw1Check=true;
        }
        else if (Greenfoot.mouseMoved(file)){
            getWorld().addObject (openButton,x[0],40+thickness);
            getWorld().addObject (savePNGButton,x[0],67+thickness);

            fileCheck=true;
        }

        // if the mouse is over anything except the tabs (over the layer or the black border of the menu or the world, the tab will be closed)
        // Looking at which tab was open, the text buttons of that tab will be removed (imitating the nature of the drop down menu)
        if (Greenfoot.mouseMoved(layer)  || Greenfoot.mouseMoved(getWorld()) || Greenfoot.mouseMoved(this)){
            if (translationCheck==true){
                getWorld().removeObject (CWRButton);
                getWorld().removeObject (CCWRButton);
                getWorld().removeObject (hRevButton);
                getWorld().removeObject (vRevButton);
                getWorld().removeObject (mirrorHorizontallyButton);
                getWorld().removeObject (mirrorVerticallyButton);
                translationCheck=false;  // mouse is no longer on the translation tab, its text buttons will no longer be shown on the screen 
            }
            else if (filterCheck==true){
                getWorld().removeObject (greenButton);
                getWorld().removeObject (redButton);
                getWorld().removeObject (darkBlueButton);
                getWorld().removeObject (yellowButton);
                getWorld().removeObject (purpleButton);
                getWorld().removeObject (lightBlueButton);
                filterCheck=false;
            }
            else if (advancedFilterCheck==true){
                getWorld().removeObject (effect1);
                getWorld().removeObject (effect2);
                getWorld().removeObject (effect3);
                getWorld().removeObject (effect4);
                getWorld().removeObject (effect5);
                getWorld().removeObject (effect6);
                advancedFilterCheck=false;
            }
            else if (effectsCheck==true){
                getWorld().removeObject (sepiaButton);
                getWorld().removeObject (greyScaleButton);
                getWorld().removeObject (blurButton);
                getWorld().removeObject (negButton);
                getWorld().removeObject (warmButton);
                getWorld().removeObject (coldButton);
                getWorld().removeObject (brightButton);
                getWorld().removeObject (transButton);
                effectsCheck=false;
            }
            else if (draw1Check==true){
                getWorld().removeObject (drawButton);
                getWorld().removeObject (eraseButton);
                getWorld().removeObject (chooseColor);
                getWorld().removeObject (paletteButton);
                draw1Check=false;
            }
            else if (fileCheck==true){
                getWorld().removeObject (openButton);
                getWorld().removeObject (savePNGButton);
                fileCheck=false;
            }
        }

        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        // If the user clicks on any text buttons (belonging to any of the tabs)
        // Each button will perform its specific funtion (methods in detail will be found in the processor class)
        // Each proccesser method will require to provide the buffered image of the current image to modify and it will automatically change on the screen
        // Normal filters can be clicked more than once and its color will get darker
        // Normal and Advanced filters should not be clicked together (black image might appear).
        // The effect is applied to both the layer and the actual image (true- image, false, layer)        
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(hRevButton)){
                Processor.flipHorizontal(image.getBufferedImage(), true);
                Processor.flipHorizontal(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(vRevButton)){
                Processor.flipVertical(image.getBufferedImage(), true);
                Processor.flipVertical(layer.getImage().getAwtImage(), false);
            }
            // Rotations will return buffered images and therefore a method from the ImageHolder1 class called createGreenfootImageFromBI converts them to 
            // greenfoot images. The images will be set as the new image on the screen
            else if (Greenfoot.mouseClicked(CWRButton)){
                image.setImage (image.createGreenfootImageFromBI (Processor.rotateCW(image.getBufferedImage(), true)));
                layer.update (ImageHolder.createGreenfootImageFromBI (Processor.rotateCW(layer.getImage().getAwtImage(), false)));
            }
            else if (Greenfoot.mouseClicked(CCWRButton)){
                image.setImage (image.createGreenfootImageFromBI (Processor.rotateCCW(image.getBufferedImage(), true)));
                layer.update (ImageHolder.createGreenfootImageFromBI (Processor.rotateCCW(layer.getImage().getAwtImage(), false)));
            }
            else if (Greenfoot.mouseClicked(transButton)){
                Processor.transparency(image.getBufferedImage(), true);
                Processor.transparency(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(negButton)){
                Processor.negative(image.getBufferedImage(), true);
                Processor.negative(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(warmButton)){
                Processor.warm(image.getBufferedImage(), true);
                Processor.warm(layer.getImage().getAwtImage(), false);
            } 
            else if (Greenfoot.mouseClicked(coldButton)){
                Processor.cold(image.getBufferedImage(), true);
                Processor.cold(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(brightButton)){
                Processor.bright(image.getBufferedImage(), true);
                Processor.bright(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(greyScaleButton)){
                Processor.greyScale(image.getBufferedImage(), true);
                Processor.greyScale(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(greenButton)){
                Processor.filter(image.getBufferedImage(), "green", true);
                Processor.filter(layer.getImage().getAwtImage(), "green", false);
            }
            else if (Greenfoot.mouseClicked(redButton)){
                Processor.filter(image.getBufferedImage(), "red", true);
                Processor.filter(layer.getImage().getAwtImage(), "red", false);
            }
            else if (Greenfoot.mouseClicked(darkBlueButton)){
                Processor.filter(image.getBufferedImage(), "darkBlue", true);
                Processor.filter(layer.getImage().getAwtImage(), "darkBlue", false);
            }
            else if (Greenfoot.mouseClicked(yellowButton)){
                Processor.filter(image.getBufferedImage(), "yellow", true);  
                Processor.filter(layer.getImage().getAwtImage(), "yellow", false);
            }
            else if (Greenfoot.mouseClicked(purpleButton)){
                Processor.filter(image.getBufferedImage(), "purple", true); 
                Processor.filter(layer.getImage().getAwtImage(), "purple", false);
            }
            else if (Greenfoot.mouseClicked(lightBlueButton)){
                Processor.filter(image.getBufferedImage(), "lightBlue", true); 
                Processor.filter(layer.getImage().getAwtImage(), "lightBlue", false);
            }
            else if (Greenfoot.mouseClicked(blurButton)){
                Processor.blur(image.getBufferedImage(), true);   
                Processor.blur(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(sepiaButton)){
                Processor.sepia(image.getBufferedImage(), true); 
                Processor.sepia(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(mirrorVerticallyButton)){
                Processor.mirrorVertically(image.getBufferedImage(), true);  
                Processor.mirrorVertically(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(mirrorHorizontallyButton)){
                Processor.mirrorHorizontally(image.getBufferedImage(), true);     
                Processor.mirrorHorizontally(layer.getImage().getAwtImage(), false);
            }
            else if (Greenfoot.mouseClicked(chooseColor)){  // if the user clicks on the choose color button
                jOptionPane();  // show a pop-up box that will ask the user to input RGB values for the color he/she wishes to draw with
            }
            else if (Greenfoot.mouseClicked(savePNGButton)){
                Processor.saveAsPNG(image.getImage(), layer.getImage());
            }            
            // undo method returns the previous image that was on the screen. This image will be set as the current image. Multiple undo-es is possible
            else if (Greenfoot.mouseClicked(undo)){
                image.setImage (image.createGreenfootImageFromBI (Processor.undo()));
                layer.update(ImageHolder.createGreenfootImageFromBI (Processor.undoLayer()));                
            }
            // redo method returns the image that was undo-ed in case the user changes his/her mind. This image will be set as the current image. Multiple redo-es is possible
            else if (Greenfoot.mouseClicked(redo)){
                image.setImage (image.createGreenfootImageFromBI (Processor.redo(image.getBufferedImage())));
                layer.update(ImageHolder.createGreenfootImageFromBI (Processor.redoLayer()));                
            }
            // one method for all the advancedFilter effects (specify which effect to apply when user clicks a specific button)
            else if (Greenfoot.mouseClicked(effect1)){
                Processor.advancedFilter(image.getBufferedImage(), "effect1", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect1", false);
            }
            else if (Greenfoot.mouseClicked(effect2)){
                Processor.advancedFilter(image.getBufferedImage(), "effect2", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect2", false);
            }
            else if (Greenfoot.mouseClicked(effect3)){
                Processor.advancedFilter(image.getBufferedImage(), "effect3", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect3", false);
            }
            else if (Greenfoot.mouseClicked(effect4)){
                Processor.advancedFilter(image.getBufferedImage(), "effect4", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect4", false);
            }
            else if (Greenfoot.mouseClicked(effect5)){
                Processor.advancedFilter(image.getBufferedImage(), "effect5", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect5", false);
            }
            else if (Greenfoot.mouseClicked(effect6)){
                Processor.advancedFilter(image.getBufferedImage(), "effect6", true);
                Processor.advancedFilter(layer.getImage().getAwtImage(), "effect6", false);
            }
            else if (Greenfoot.mouseClicked(paletteButton)){  // if the user clicks on the view palette button,
                if (viewPalette==true){  // if it was already shown
                    palette.removePalette();  // remove it
                    viewPalette=false;  // indicate that it is not shown
                }else {  // else (if it was not shown)
                    getWorld().addObject (palette, 127,498);  // add the palette on the screen
                    viewPalette=true;  // indicate that it is shown
                }
            }
            else if (Greenfoot.mouseClicked(openButton)){  // if the user clicks on the open button
                openFile();  // show a pop-up box that will ask the user to input the name of the file wish to be imported
            }
        }

        // if user presses the draw button
        if (Greenfoot.mousePressed(drawButton)){
            enableDraw=true;  // enable the user to draw
            enableErase=false;  // do not enable the user to erase
            indicator.update ("Brush In Use!");  // updates the label
        }

        // if user presses the draw button
        if (Greenfoot.mousePressed(eraseButton)){
            enableErase=true;  // enable the user to erase
            enableDraw=false;  // do not enable the user to draw
            indicator.update ("Eraser In Use!");  // updates the label
        }

        // if the user presses the mouse in any part of the layer and brush is on
        if (Greenfoot.mousePressed(layer) && enableDraw==true){
            draw=true;  // draw

        }
        // if the user clicks (finishes pressing) the layer and was drawing before that
        if (Greenfoot.mouseClicked(layer) && draw ==true){
            // save the changes to both the layer and the image
            Processor.addImage(image.getBufferedImage());
            Processor.addImageLayer(layer.getImage().getAwtImage());

            draw=false;  // stop drawing
        }      
        if (draw==true){  // if the user draws,
            // apply the drawing on the screen where the mouse is curently at (and with the current color)
            // there is an x and y factor. The mouse coordinates on the screen and the mouse coordinates on the layer is different (different prespective when looking
            // at mouse locations - 0 starts at the begining of the pic not the begining of the screen)
            Draw.draw(image.getBufferedImage(), color, (layer.getX() - layer.getImage().getWidth()/2), (layer.getY() - layer.getImage().getHeight()/2));
        }

        // same concept applies as the brush but this time instead of drawing, you are setting the pixels to have no value and thus the layer becomes
        // transparent
        if (Greenfoot.mousePressed(layer) && enableErase==true){
            erase=true;

        }
        if (Greenfoot.mouseClicked(layer) && erase ==true){

            Processor.addImage(image.getBufferedImage());
            Processor.addImageLayer(layer.getImage().getAwtImage());

            erase=false;
        }      
        if (erase==true){
            Draw.erase((layer.getX() - layer.getImage().getWidth()/2), (layer.getY() - layer.getImage().getHeight()/2));
        }

    }

    //Box b;  // box will be used to create space between the text fields in the option pane
    /**
     * Creates an option pane for the user to input the three RGB values.
     * The option pane appears when the user clicks on the choose color button from the draw tab.
     * Sets the color of the brush based on the three RGB values.
     */
    private void jOptionPane() {
        // three text fields that will store the 3 RGB values
        JTextField redField = new JTextField(5);
        JTextField greenField = new JTextField(5);
        JTextField blueField = new JTextField(5);

        JPanel myPanel = new JPanel();  // panel that will display the option pane
        // add the labels and textfields and boxes(act as spacers) in that panel
        myPanel.add(new JLabel("red:"));
        myPanel.add(redField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("green:"));
        myPanel.add(greenField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("blue:"));
        myPanel.add(blueField);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer

        // showes the panel on the screen
        // stores what the user chose when the panel appeared (either ok or cancel)
        int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Red, Green and Blue Values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {  // if user chose ok,
            try{
                // store the 3 RGB values that the user entered
                red= Integer.parseInt (redField.getText());
                blue= Integer.parseInt (greenField.getText());
                green= Integer.parseInt (blueField.getText());
                // convert it to a color
                color = Processor.packagePixel (red, blue, green, 255);
            }
            catch (NumberFormatException e){}  // user did not input anything

        }

    }

    /**
     * Initiates the start-up screen. Includes the drop down menu with all the tabs displayed. Includes the image, undo, redo and label displaying
     * file names and whether the brush or erase is in use.
     * Called by the main method only once.
     */
    private void initiate(){
        if (once ==true){  // only run this method once when called from the act method

            GreenfootImage border = new GreenfootImage (getWorld().getWidth(), 38);  // image that will act as a black border
            //border.setColor (Color.BLACK);  // set the color to black
            border.fill();  // fill the image (rectangle shape) with black color

            GreenfootImage backGround = new GreenfootImage (getWorld().getWidth()-10, 38-10);  // image will act as the background for the tabs 
            //backGround.setColor (new Color (200,20,40));  // set the color to red
            backGround.fill();  // fill the image (rectangle shape) with red color

            border.drawImage (backGround, 5,5);  // draw the red bar on the black bar (black bar appears as a border)

            setImage (border);  // set the image of the drop down menu as the red and black bar

            // add all the tabs on the screen
            getWorld().addObject (file, x[0],y);
            getWorld().addObject (filter, x[1],y);
            getWorld().addObject (advancedFilter, x[2],y);
            getWorld().addObject (translation,x[3],y);
            getWorld().addObject (draw1, x[4],y);
            getWorld().addObject (effects, x[5],y);

            // add the undo and redo buttons
            getWorld().addObject (undo, 640, y);
            getWorld().addObject (redo, 675, y); 

            // adds the image on the screen
            getWorld().addObject (image, 485, 341);

            // add the fileName and (brush/eraser)indicater labels on the screen
            getWorld().addObject (fileName, 900, 19);
            getWorld().addObject (indicator, 745, 19);

            // add the layer in which the brush and eraser will act on on the screen
            getWorld().addObject (layer,485,341);

            once =false; // never run this code again
        }    
    }

    /**
     * Allows the user to open a new image file.
     * Displays the specified image.
     * Displays a JOptionPane that asks the user the file name.
     * User has to input the file name with extension.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String name = JOptionPane.showInputDialog("Please input a value");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (name))
        {
            String display = " [ Open File: " + name + " ] ";

            fileName.update(display);

        }

        // saves the image of the new file
        Processor.addImage(image.getBufferedImage());
        Processor.addImageLayer(layer.getImage().getAwtImage());
    }

    /**
     * Sets the color of the brush. Called by the palette when the user clicks on a specific color.
     * 
     * @param newColor - the new color of the brush
     */
    public static void setColor(int newColor){
        color=newColor;
    }

}