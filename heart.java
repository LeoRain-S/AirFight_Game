package AirFight_LeoShi;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * the heart class is used to heal the player
 */
public class heart extends movingThing{
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/heart.png"); // set the image for heart
    public heart(){
        this.img = icon.getImage(); 
        img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        width = 50;
        height = 50;
        Random RNG = new Random();
        y = 10-height;
        x = RNG.nextInt(400 - width);
    }
    @Override
    public boolean outOfBounds() {
        return y>600;
    }
    @Override
    public void move() {
        y += 6; // the move speed
    
        
            
        
        
    }
}
