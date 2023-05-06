package AirFight_LeoShi;
import java.awt.*;
import javax.swing.*;
/*
 * the player class contains the jet that the user will control
 */
public class Player extends movingThing {
    private ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/player.png"); // set the image for player's jet
    public Player(){
        img = icon.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        width = 50;
        height = 50;
        x=175; // the spawn location
        y=500;
        
    }
    /**
     * this contains how the jet shoot a bullet
     * @return a bullet[]
     */
    public Bullet[] shoot(){
        int xStep = width/4;
        int yStep = 20 ;
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(x+2*xStep-8, y-yStep);
        return bullets;
    }
    @Override
    public void move() {
        // TODO Auto-generated method stub
    }

    public boolean outOfBounds(){
        return false;
    }
    /**
     * reset the jet to the orginal spawn when it got hit
     */
    public void reset(){
        x=200;
        y=500;
    }
    /**
     * when the jet got hit by other object
     * @param f
     * @return
     */
    public boolean crush(movingThing f){
        // the hit box of the object
        int minx = f.x - this.width/2;
        int maxx = f.x + this.width/2 + f.width;
        int miny = f.y - this.height/2;
        int maxy = f.y + this.height/2 + f.height;
        // the hit box of the jet
        int x = this.x + this.width/2;
        int y = this.y + this.height/2;
        
        return x>minx && x<maxx && y>miny && y<maxy;

    }

}
