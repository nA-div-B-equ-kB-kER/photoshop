import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing.
 * 
 * @author Jordan Cohen
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;
    private boolean header=false;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text, boolean header)
    {
        this(text, 20);
        this.header=header;
    }

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, 20);
        this.header=header;
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        GreenfootImage tempTextImage;
        if (header==true){
            tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, new Color (50, 50,235));
        } else {
            tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, new Color (100, 120,235));

        }
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        if (header==true){
            myImage.setColor (new Color (50, 50,235));
        } else{
            myImage.setColor (new Color (100, 120,235));
        }
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update (String text)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.WHITE, new Color (0, 0,255));
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (new Color (255, 255,0));
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update ()
    {
        GreenfootImage tempTextImage;
        if (header==true){
             tempTextImage = new GreenfootImage (buttonText, 20, Color.WHITE, new Color (50, 50,235));
        } else {
            tempTextImage = new GreenfootImage (buttonText, 20, Color.WHITE, new Color (100, 120,235));
        }
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        if (header==true){
            myImage.setColor (new Color (50, 50,235));
        } else {
            myImage.setColor (new Color (100, 120,235));
        }
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    public void act(){
        if (Greenfoot.mouseMoved (this)){
            update (buttonText);

        } else if (Greenfoot.mouseMoved (null)){
            update();

        }

    }
}
