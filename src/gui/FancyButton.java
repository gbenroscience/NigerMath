/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 *@author JIBOYE Oluwagbemiro Olaoluwa
 */
public abstract class FancyButton extends JPanel implements ActionListener,Runnable{

private String text;
private Point location;
private Dimension size;
private Color background;
private Color foreground;
private Font font;
private Image backImg;
/**
 *
 * @param text The button text
 * @param location The location of the top left side of the button.
 * @param size The dimension of the button...width,height
 * @param background The button color.
 * @param foreground The color of the text on the button.
 * @param font The Font used to draw the text on the button.
 */
    public FancyButton(String text, Point location, Dimension size, Color background, Color foreground, Font font) {
        this.text = text;
        this.location = location;
        this.size = size;
        this.background = background;
        this.foreground = foreground;
        this.font = font;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;  
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


public void updateBackground( ) {
	try {
		Robot rbt = new Robot( );
		Toolkit tk = Toolkit.getDefaultToolkit( );
		Dimension dim = tk.getScreenSize( );
		backImg = rbt.createScreenCapture(
		new Rectangle(0,0,(int)dim.getWidth( ),
                          (int)dim.getHeight( ))
                          );
	} catch (Exception ex) {
		ex.printStackTrace( );
	}
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


  public void draw(Graphics g){
      g.setColor(background);
      g.fill3DRect(location.x, location.y, size.width, size.height, true);
      g.setColor(foreground);
      g.setFont(font);
      

  }

  /**
 *
 * @return a Dimension object whose width attribute
 * is the width of text to be displayed and whose
 * height is the height of the text to be displayed.
 */
    public Dimension getTextSize(){
            FontMetrics fm =  getFontMetrics(font);
     return new Dimension(  fm.stringWidth(text) , fm.getHeight()  );
    }

/**
 *
 * @return a Rectangle object whose width attribute
 * is the width of text to be displayed and whose
 * height is the height of the text to be displayed,
 * and whose location represents the location of the
 * text.
 */
    public Rectangle getTextRectangle(){
    Dimension d = getTextSize();
    Point loc = getCentralizedTextLocation();
     return new Rectangle(loc, d);
    }

/**
 *
 * @return a Point object which is the
 * recommended location for text to be displayed in this
 * CustomButton object.
 */
    private Point getCentralizedTextLocation(){
           Dimension dim = getTextSize();
           Dimension size = getSize();
           Point p = getLocation();

           if( size.width >= dim.width && size.height >= dim.height ){
return new Point( (size.width - dim.width)/2 ,(size.height + dim.height/2)/2 );
           }
 else{
return new Point( p );
 }

    }//end method

    public abstract void actionPerformed(ActionEvent e);





}
