package AirFight_LeoShi;
import java.awt.*;
/**
 * this class is related to every class in this game, the contains the basic character of a moving object
 */
public abstract class movingThing {
    
    protected int x; // the x position
    protected int y; // the y position
    protected int width;
    protected int height;
    protected Image img;
    // getter and setter
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImg() {
        return this.img;
    }

    public abstract boolean outOfBounds();
    
    public abstract void move();
    // when the object got hit by a bullet
    public boolean gotKilled(Bullet bullet){
        int x = bullet.x;
        int y = bullet.y;
        return (this.x<x) && (x<this.x+width) && (this.y<y) && (y<this.y+height);
    }

}
