package AirFight_LeoShi;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * a kind of obstacle in this game, the fast rock
 */
public class Obstacle2 extends movingThing {
    
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/hardRock.png"); // set the image
    
    public Obstacle2(){
        this.img = icon.getImage(); 
        img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        width = 50;
        height = 50;
        Random RNG = new Random(); // random spawn location
        y = 10-height;
        x = RNG.nextInt(400 - width);
    }
    @Override
    public boolean outOfBounds() {
        return y>600;
        
    }
    @Override
    public void move() {
        y += 10; // the speed of it
    
        
            
        
        
    }
}
