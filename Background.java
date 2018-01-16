import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;

import java.awt.*;
import javax.swing.DebugGraphics;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
/**
 * Starter code for Image Manipulation Array Assignment.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * Add to it and make it your own!
 * 
 * This class spawns the drop down menu and sets the paint order of the world.
 * 
 * @author Jordan Cohen/Tishko Araz
 * @version April 23, 2017
 */
public class Background extends World
{

    /**
     * Constructor the world and the drop down menu.
     * The drop down menu is responsible for displaying all the actors on the screen.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1000, 600, 1); 

        // declaring and intializing the drop down menu
        DropDownMenu menu= new DropDownMenu();
        // adding the drop down menu on the screen
        addObject (menu,getWidth()/2,19);
        // setting the paint order of world
        setPaintOrder (TextButton.class,  DropDownMenu.class, ImageHolder.class);
    }



}
