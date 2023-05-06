package AirFight_LeoShi;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * this is a kind of obstacle, the normal rock
 */
public class Obstacle extends movingThing {
    
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/rock.png"); // set the iamge
    
    public Obstacle(){
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
        Random rng = new Random(); // the speed is random 
        Random rng2 = new Random();
        if(rng2.nextInt(2)==0){ // move down right
            y += rng.nextInt(5);
            x += rng.nextInt(3);
        }
        else{ // move down left
            y += rng.nextInt(5);
            x -= rng.nextInt(3);
        }
    
        
            
        
        
    }
}
