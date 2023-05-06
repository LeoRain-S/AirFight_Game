package AirFight_LeoShi;
import java.awt.*;
import javax.swing.*;
import java.util.*;
/**
 * this is the enemy jet class
 */
public class Enemy extends movingThing {
    private int speed = 3;
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/Enemy_Image.png"); // set the image
    public Enemy(){
        this.img = icon.getImage(); 
        img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        width = 50;
        height = 50;
        Random RNG = new Random(); // random spawn location
        y = 10-height;
        x = RNG.nextInt(400 - width);
    }

    public int destroyed(){ // the reward
        return 10;
    }

    public boolean outOfBounds(){
        return y>600;
    }

    public void move(){ 
        y += speed; // the speed
    }
}
