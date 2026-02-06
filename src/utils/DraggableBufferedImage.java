/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import utils.ObjectDragger;

/**
 *
 * @author MRSJIBOYE
 */
public class DraggableBufferedImage extends BufferedImage{

    private Point location;
    public static Component component;

    private boolean selected = true;
    private Color selectionColor = Color.blue;
    private boolean needsRepaint;

    public DraggableBufferedImage(Point location, int width,int height, int imageType,Component component ) {
        super(width, height, imageType);
        this.location = location;
        DraggableBufferedImage.component = component;
new ObjectDragger(ObjectDragger.IMAGE_OBJECT, this, false);
    }
    public DraggableBufferedImage(Point location, Dimension size, int imageType,Component component ) {
        this(location,size.width,size.height,imageType,component);
new ObjectDragger(ObjectDragger.IMAGE_OBJECT, this, false);
    }


    public DraggableBufferedImage(ColorModel cm,WritableRaster raster,boolean isRasterPreMultipled,Hashtable properties,Component component) {
        super(cm, raster, isRasterPreMultipled, properties);
        DraggableBufferedImage.component = component;
new ObjectDragger(ObjectDragger.IMAGE_OBJECT, this, false);
    }

    public DraggableBufferedImage( int width,int height, int imageType,IndexColorModel cm,Component component) {
        super(width, height, imageType, cm);
        DraggableBufferedImage.component = component;
new ObjectDragger(ObjectDragger.IMAGE_OBJECT, this, false);
    }


    public void setLocation(Point location) {
        this.location = location;
    }
    public void setLocation(int x, int y) {
        this.location = new Point(x, y);
    }



    public Point getLocation() {
        return location;
    }

    public static void setComponent(Component component) {
        DraggableBufferedImage.component = component;
    }

    public static Component getComponent() {
        return component;
    }


    public void draw( Graphics g ){
        if(needsRepaint){
            component.repaint();
            setNeedsRepaint(false);
        }
        if(selected){
        g.setColor(selectionColor);
        g.draw3DRect(location.x-2, location.y-2, getWidth()+4, getHeight()+4, true);
        g.draw3DRect(location.x-1, location.y-1, getWidth()+2, getHeight()+2, true);
        g.draw3DRect(location.x, location.y, getWidth(), getHeight(), true);
        }
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(this, null, location.x, location.y);


    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setNeedsRepaint(true);
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
        setNeedsRepaint(true);
    }

    public void setNeedsRepaint(boolean needsRepaint) {
        this.needsRepaint = needsRepaint;
    }

    public boolean isNeedsRepaint() {
        return needsRepaint;
    }


/**
 *
 * @param p The Point to check. The Point is specified in an
 * absolute sense..and that is relative to the screen coordinates.
 * @return true if the point lies within the image.
 */
public boolean contains(Point p){
    Point pt = component.getLocationOnScreen();
    Rectangle r = new Rectangle( new Point(location.x+pt.x,location.y+pt.y),new Dimension(getWidth(),getHeight()));
    return r.contains(p);
}
/**
 *
 * @return the absolute location of the image relative to the
 * screen coordinates.
 */
public Point getLocationOnScreen(){
    Point pt = component.getLocationOnScreen();
    return new Point(location.x+pt.x,location.y+pt.y);

}
/**
 *
 * @return a Rectangle that encloses this object
 * relative to its immediate parent component
 * on which it is displayed.
 */
public Rectangle getBounds(){
    Point pt = component.getLocationOnScreen();
    return new Rectangle(location,new Dimension(getWidth(),getHeight()));
}
/**
 *
 * @return a Rectangle that encloses this object
 * relative to the screen coordinates.
 * on which it is displayed.
 */
public Rectangle getAbsoluteBounds(){
    Point pt = component.getLocationOnScreen();
    return new Rectangle(new Point(location.x+pt.x,location.y+pt.y),new Dimension(getWidth(),getHeight()));
}

/**
 * Loads an image.
 * @param imgPath The path to the image.
 */
        public void  loadImage(String imgPath) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read( new File(imgPath) );
            setData(bimg.getData());
            setNeedsRepaint(true);
        } catch (Exception e) {

        }

    }//end method
/**
 *
 * @return the area of the image.
 */
        public double area(){
            return getWidth()*getHeight();
        }

/**
 *
 * @return the x coordinate of the image
 * relative to its displaying component
 */
public int getX(){
    return location.x;
}
/**
 *
 * @return the y coordinate of the image
 * relative to its displaying component
 */
public int getY(){
    return location.y;
}
/**
 *
 * @return the x coordinate of the image
 * relative to the screen.
 */
public int getXOnScreen(){
    return location.x+component.getLocationOnScreen().x;
}
/**
 *
 * @return the y coordinate of the image
 * relative to the screen.
 */
public int getYOnScreen(){
    return location.y+component.getLocationOnScreen().y;
}



/**
 *
 * @param images An array of images that need to be merged into
 * a single one.
 * @return A single image that encompasses all images in the specified array.
 */
public static final DraggableBufferedImage merge( DraggableBufferedImage...images ){
    DraggableBufferedImage img = images[0];
    int xMin = img.getX();
    int xMax = xMin+img.getWidth();

int yMin = img.getY();
int yMax = yMin + img.getHeight();
int len = images.length;

for( int i=1;i<len;i++){
int x = images[i].getX();
int y = images[i].getY();

int w = images[i].getWidth();
int h = images[i].getHeight();


    if( xMin > x ){
        xMin = x;
    }
    if( xMax < x+w ){
        xMax = x+w;
    }
    if( yMin > y ){
        yMin = y;
    }
    if( yMax < y+h ){
        yMax = y+h;
    }
}//end for loop

Rectangle rect = new Rectangle(xMin,yMin,xMax-xMin,yMax-yMin);

DraggableBufferedImage dbi = new DraggableBufferedImage(rect.getLocation(),rect.getSize(),images[0].getType(),component);


Graphics2D g = (Graphics2D)  dbi.createGraphics();
g.setBackground(Color.white);
g.fillRect(rect.x, rect.y, rect.width, rect.height);
for(int i=0;i<len;i++){
    images[i].setLocation( images[i].getX()-rect.x,images[i].getY()-rect.y );
    images[i].draw(g);
}

return dbi;
}//end method


public boolean isNEBorder(){
return getMousePosition().y == getLocation().y && getMousePosition().x == getLocation().x+getWidth();
}
public boolean isSEBorder(){
return getMousePosition().y == getLocation().y +getHeight() && getMousePosition().x == getLocation().x+getWidth();
}
public boolean isNWBorder(){
return getMousePosition().y == getLocation().y && getMousePosition().x == getLocation().x;
}
public boolean isSWBorder(){
return getMousePosition().y == getLocation().y +getHeight() && getMousePosition().x == getLocation().x;
}

public boolean isNBorder(){
return getMousePosition().y == getLocation().y;
}
public boolean isSBorder(){
return getMousePosition().y == getLocation().y+getHeight();
}
public boolean isEBorder(){
return getMousePosition().x == getLocation().x+getWidth();
}
public boolean isWBorder(){
return getMousePosition().x == getLocation().x;
}

/**
 *
 * @return true if the mouse is at one of the N,S,W,E,NE,NW,SE,SW borders of the object.
 */
public boolean isBorder(){
    return isNBorder()||isSBorder()||isEBorder()||isWBorder()||isNEBorder()||isSEBorder()||isNWBorder()||isSWBorder();
}

    private Point getMousePosition() {
        return component.getMousePosition();
    }














}