package splashyflashy;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GBENRO
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
public class SplashPanel extends JPanel implements Runnable{
    private ImageIcon img;
    private Thread timer;
    //used to control the brightness of graphics on the SplashScreen
    private float textBrightnessCtrl=1.0f;
    //used as a parameter to rotate graphics on the screen.
    private double angleCtrl=0;
//determines the interval before the parser name string begins to rotate
    private int countDownToRotation=0;

    private int parserNameFontSize=5;

//create rectangle of length 10 units and height 4 units and make it run across the screen from left to right
    Rectangle leftToRightRect=new Rectangle(10,4);
//create rectangle of length 10 units and height 4 units and make it run from the bottom of the screen where
    //rect ends to the top of the screen.
    Rectangle bottomToTopRect=new Rectangle(-20,-20,4,10);//create rectangle of length 10 units and height 4 units
//create rectangle of length 10 units and height 4 units and make it run across the screen from right to left
    //starting where bottomToTopRect ends
    Rectangle rightToLeftRect=new Rectangle(-20,-20,10,4);
//create rectangle of length 10 units and height 4 units and make it run across the screen from top to bottom
    //starting where rightToLeftRect ends.
    Rectangle topToBottomRect=new Rectangle(-20,-20,4,10);//create rectangle of length 10 units and height 4 units
    
    private static final int centralTextPadding = 12;
/**
 * The rate at which screen graphics are updated.
 */
    private int refreshRate;
/**
 * Variable that starts incrementing after the last animation i.e the copyright text
 * has been displayed.It counts up to a value and then loads the final image on to the splash
 */
private int countDownToImageLoad;
/**
 * Outputs the name of the parser character by character
 */
    private int typeParserName;
/**
 * Outputs the copyright..... text character by character
 */
    private int typeCopyRight;
/**
 * The name of the parser.
 */
    private String parserName;
/**
 * The text of the copyright text.
 */
    private String copyRightText;

    //set of ovals used to grace the interface
    private Circle [] circles = new Circle[10];
        //set of ovals used to grace the interface
    private Star [] stars = new Star[3];


    public SplashPanel(){
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = dim.width / 3;
        int h = 2 * w / 3;
        
        
        setSize(w , h);
        setVisible(true);
        setBackground(Color.black);
        setRefreshRate(1000);
        createOvalSet();
        createStarSet();
        timer = new Thread(this);
        timer.start();
        setBorder(new BevelBorder(BevelBorder.RAISED)  );
              

    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    private boolean start= true;
    private Font parserFont;
 
/**
 * method writeParserName(args) is used to type out the name of the parser onto the splash screen.
 * @param g Graphics object used to diplay the name of the parser
 */
    private void writeParserName(Graphics g){
Graphics2D g2D= (Graphics2D)g;
g2D.setColor(new Color(251,0,153));
parserName="MathematicalNigerian";
if(start){
    g2D.setFont(parserFont = new Font("Lucida Grande",Font.BOLD,++parserNameFontSize));
    start = false;
}
 
int maxTextWidth = SplashPanel.this.getSize().width - 80 - 2 * centralTextPadding;
        FontMetrics fm = g2D.getFontMetrics(parserFont);
        int w = fm.stringWidth(parserName);

        if( w < maxTextWidth){
            parserFont = new Font("Lucida Grande",Font.BOLD,++parserNameFontSize);  
        }
         g2D.setFont(parserFont); 

if(angleCtrl>=-10){
g2D.rotate(angleCtrl+=-0.5,getWidth()/2,getHeight()/2);
}
else if(angleCtrl<-10){
    g2D.drawString(parserName, 40 + centralTextPadding/2, getHeight()/2);
}
if(typeParserName<parserName.length()){
    typeParserName++;
}
//g2D.drawString(parserName.substring(0,typeParserName), 41,rightToLeftRect.getY()+100);
g2D.drawString(parserName, 40 + centralTextPadding/2,getHeight()/2);

        }//end method writeParserName



/**
 * method writeCopyRight(args) is used to type out the name of the parser onto the splash screen.
 * @param g Graphics object used to diplay the name of the parser
 */
    private void writeCopyRight(Graphics g){
Graphics2D g2D= (Graphics2D)g;
g2D.setColor(Color.white);
//Castellar
g2D.setFont(new Font("Microsoft Sans Serif",Font.BOLD+Font.ITALIC,20) );
copyRightText="©G-SOFT Solutions.";


//start typing when the parser name is fully displayed
if(typeCopyRight<copyRightText.length()){
    typeCopyRight++;
}
//g2D.rotate(angleCtrl);
g2D.drawString(copyRightText.substring(0,typeCopyRight), 250,leftToRightRect.getY()-5);

        }//end method writeCopyRight


    private void loadImg(Graphics g){
        try{
File f = new File(System.getProperty("user.dir"));
     String projDir = f.getAbsolutePath();
      img = new ImageIcon(projDir+"/src/Project Images/SplashPix.jpg");
        img.setImage(img.getImage().getScaledInstance(getSize().width, getSize().height, Image.SCALE_SMOOTH ));
      img.paintIcon(SplashPanel.this, g, 0,0);
        }//end try
        catch(NullPointerException nolian){
            nolian.printStackTrace();
        }

    }


private void drawRect(Graphics g){

          leftToRightRect.draw(g ,40 , getHeight() - 61,leftToRightRect.getWidth(), leftToRightRect.getHeight());

        bottomToTopRect.draw(g ,bottomToTopRect.getX(), bottomToTopRect.getY(), bottomToTopRect.getWidth(),bottomToTopRect.getHeight());
        rightToLeftRect.draw(g, rightToLeftRect.getX(),rightToLeftRect.getY(),rightToLeftRect.getWidth(),rightToLeftRect.getHeight() );
        topToBottomRect.draw(g, topToBottomRect.getX(),topToBottomRect.getY(),topToBottomRect.getWidth(),topToBottomRect.getHeight() );

//Prepare leftToRightRect for use in drawing the lower horizontal bar, from left to right.
//These are the drawing conditions for leftToRightRect
           if(leftToRightRect.getWidth()<SplashPanel.this.getSize().width-80){
            leftToRightRect.setWidth(leftToRightRect.getWidth()+5);
            }
//Prepare bottomToTopRect for use in drawing the right vertical bar from bottom to top
           else if(leftToRightRect.getWidth()>=SplashPanel.this.getSize().width-80){
              if(bottomToTopRect.getY()==-20){
                  bottomToTopRect.setX(leftToRightRect.getX()+leftToRightRect.getWidth()-4);
                  bottomToTopRect.setY(leftToRightRect.getY()-10);
              }
 //These are the drawing conditions for bottomToTopRect
              else if( bottomToTopRect.getY()>=70){
           bottomToTopRect.setY(bottomToTopRect.getY()-5);
           bottomToTopRect.setHeight(bottomToTopRect.getHeight()+5);
           }
    //stop drawing bottomToTopRect and start drawing rightToLeftRect horizontally from right to left.
           else if(bottomToTopRect.getY()<70){

             if(rightToLeftRect.getX()==-20){
                 rightToLeftRect.setX(leftToRightRect.getX()+leftToRightRect.getWidth()-10   );
                 rightToLeftRect.setY(  61  );
             }
//These are the drawing conditions for rightToLeftRect
             else if(rightToLeftRect.getWidth()<SplashPanel.this.getSize().width-80){
             rightToLeftRect.setX(rightToLeftRect.getX()-5);
             rightToLeftRect.setWidth(rightToLeftRect.getWidth()+5);
              }
   //stop drawing rightToLeftRect and start drawing topToBottomRect vertically from top to bottom
             else if(rightToLeftRect.getWidth()>=SplashPanel.this.getSize().width-80){
          if(topToBottomRect.getX()==-20){
              topToBottomRect.setX(rightToLeftRect.getX());
              topToBottomRect.setY(61);
          }
//These are the drawing conditions for topToBottomRect
               else if( topToBottomRect.getHeight()<bottomToTopRect.getHeight()+5){
           topToBottomRect.setHeight(topToBottomRect.getHeight()+5);
           }//end else if
//A complete rectangle has been drawn using filled rectangles leftToRightRect,bottomToTopRect,rightToLeftRect,and topToBottomRect
          //activities after rectangle drawing animation go here
               else if( topToBottomRect.getHeight()>=bottomToTopRect.getHeight()+5){
       writeCopyRight(g);
       writeParserName(g);
           }//end else if



              }//end else if


              }//end else if








           }//end else if



}

/**
 * Counts up to n and the loads the final splash image.
 * @param n the number to count to before the image is loaded.
 * @param g the Graphics object used to display the image.
 */
private void imageLoader(Graphics g,int n){
if(countDownToImageLoad<n){
    ++countDownToImageLoad;
}
else if(countDownToImageLoad>=n&&countDownToImageLoad>0){
    loadImg(g); 
}

}

private void displayOvals(Graphics g){
    int xBounds = getWidth();
    int yBounds = getHeight();
for( int i = 0; i<circles.length; i++ ){
    circles[i].move( 5 , 3 );
    circles[i].draw(g, xBounds, yBounds);
}

}


private void displayStars(Graphics g){
for( int i = 0; i<stars.length; i++ ){
    stars[i].move( 5 , 3 );
    stars[i].draw(g, getWidth(),getHeight());
}

}


   private void createOvalSet(){

final int w =getWidth();
final int h = getHeight();

    boolean trulyX = false;
    boolean trulyY = true;
    boolean expanding = true;
    for(int i = 0; i<circles.length; i++){
    final int j =i;
    trulyX = !trulyX;
    trulyY = !trulyY;
    expanding = !expanding;
    final boolean trueX = trulyX;
    final boolean trueY = trulyY;
    final boolean exp = expanding;
    new Thread( new Runnable() {

                @Override
                public void run() {
                  circles[j] = new Circle( new Point( new Random().nextInt(w), new Random().nextInt(h) ),
                          new Color( new Random().nextInt(255),
                          new Random().nextInt(200),
                          new Random().nextInt(125) ),
                          10, trueX, trueY,exp);
                }
            }).start();
}
    }//end method


  private void createStarSet(){
             final int w = getWidth();
             final int h = getHeight();

    boolean trulyX = false;
    boolean trulyY = true;
    for(int i = 0; i<stars.length; i++){
    final int j =i;
    trulyX = !trulyX;
    trulyY = !trulyY;
    final boolean trueX = trulyX;
    final boolean trueY = trulyY;
new Thread(  new Runnable() {

                @Override
                public void run() {
stars[j] = new Star( new Point( new Random().nextInt(w), new Random().nextInt(h) ),
   new Dimension(  5,5 ),
        true, trueX, trueY, Color.pink, 20,20,refreshRate,0.5);
stars[j].setMultiColoredTwinkling(true);
                }
            }).start();


}//end for


    }//end method


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        displayOvals(g);
        displayStars(g);
        drawRect(g);
        imageLoader(g, 500);

    }

    


   

    @Override
public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.currentThread().sleep(refreshRate);
            } catch (InterruptedException e) {
            }
           repaint();
        }
    }

 

}//end class SplashPanel
