package AirFight_LeoShi;
import java.awt.*;
import javax.swing.*;
/**
 * this is the bullet class
 */
public class Bullet extends movingThing {
    private int speed = 5; // the flying speed
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/Bullet_Image.png"); // image
    public Bullet(int x, int y){
        this.x = x;
        this.y = y;
        img = icon.getImage();
        img = img.getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        width = 15;
        height = 15;
    }
    public boolean outOfBounds(){
        return y<-height;
    }
    @Override
    public void move() {
        y-=speed;
        
    }
}
