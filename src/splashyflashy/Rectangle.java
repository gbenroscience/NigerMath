/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package splashyflashy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author GBENRO
 */
public class Rectangle {

    /**
     * the x location of the top left corner of the rectangle
     */
    private int x;
    /**
     * the y location of the top left corner of the rectangle
     */
    private int y;
    /**
     * the width of the rectangle
     */
    private int width;
    /**
     * the height of the rectangle
     */
    private int height;

    /**
     * constructor that creates a Rectangle object with top left corner at
     * x=0,y=0 with width and height equal to the specified values
     *
     * @param width the width of the Rectangle object to be created.
     * @param height the height of the Rectangle object to be created
     */
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * creates a Rectangle object at Point P(x,y) and assigns it a width value
     * of width and a height value of height
     *
     * @param x the x coordinate of the top left corner of the Rectangle.
     * @param y the y coordinate of the top left corner of the Rectangle.
     * @param width the width of the Rectangle
     * @param height the height of the Rectangle
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @return the x coordinate of the top left corner of the Rectangle.
     */
    public int getX() {
        return x;
    }

    /**
     * sets the x coordinate of the top left corner of the Rectangle to....x
     *
     * @param x the value to set the x coordinate of the top left corner of the
     * Rectangle to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return the y cooordinate of the top left corner of the Rectangle.
     */
    public int getY() {
        return y;
    }

    /**
     * sets the y coordinate of the top left corner of the Rectangle to....y
     *
     * @param y the value to set the y coordinate of the top left corner of the
     * Rectangle to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return the height of the Rectangle.
     */
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return width of the Rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * sets the width of the Rectangle to....y
     *
     * @param y the value to set the y coordinate of the top left corner of the
     * Rectangle to.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @param g the Graphics object used to load the bar.
     * @param x the x position of the top left corner loading bar
     * @param y the y position of the top left corner of the loading bar
     * @param width the width or length of the loading bar
     * @param height the height of the loading bar
     */
    public void draw(Graphics g, int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        g.setColor(Color.red);
        g.fill3DRect(x, y, width, height, true);
    }

    /**
     * This method draws Rectangle objects with the properties listed below
     *
     * @param x the x coordinate of the upper left corner of the enclosing
     * rectangle of the oval
     * @param y the y coordinate of the upper left corner of the enclosing
     * rectangle of the oval
     * @param width the width of the oval
     * @param height the height of the oval
     * @param col the color of the oval
     */
    public void draw(Graphics g, int x, int y, int width, int height, Color col) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        g.setColor(col);
        g.fill3DRect(x, y, width, height, true);
    }

}//end class Rectangle
