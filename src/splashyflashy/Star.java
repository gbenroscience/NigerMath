package splashyflashy;


/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */





import java.util.Random;
import java.awt.*;
/**
 * Models a four pronged star.
 * @author GBEMIRO
 */
public class Star{

    /**
     * is true if this object is moving along the positive x axis.
     */
    private boolean alongPositiveX;
    /**
     * is true if this object is moving along the positive y axis.
     */
    private boolean alongPositiveY;


/**The point on the top left corner
 * of an imaginary rectangle that houses the star
 * such that the star's top prong touches the
 * midpoint of the top line of the rectangle,
 * its bottom prong touches the midpoint of the
 * bottom line of the rectangle,
 * its left prong touches the midpoint of the
 * left line of the rectangle
 * and its right prong touches the
 * midpoint of the right line of the rectangle,
 *
 */
    Point starCenter;

    /**
     * Determines the star's bulkiness.
     * The property here is such that its width coordinate
     * will set the horizontal width of the top and bottom prong base of the star
     * while its height coordinate will set the vertical height of the
     * left and right prong base of the star.
     */
    private Dimension starThickness;
    private boolean twinkling;
    private Color nonTwinklingColor;
    private int starHeight;//the vertical extent of the rectangle containing the star
    private int starWidth;//the horizontal extent of the rectangle containing the star.
    private int twinkleRate;//the rate at which the star will change color or twinkle.


    private boolean multiColoredTwinkling;


/**
 * The angle of rotation of the star,
 * if it is rotating.
 * Angle zero means that it is not rotating.
 */
    private double theta;

    /**
     * Create's a new Star object.
     * @param starCenter  the point at the top left of the star's enclosing rectangle
     * @param starThickness the thickness dimension of the star: i.e the width of its prong base.
     * @param twinkling the twinkling state of the star.
     * @param alongPositiveX  is true if this object is moving along the positive x axis.
     * @param alongPositiveY  is true if this object is moving along the positive y axis.
     * @param nonTwinklingColor the color of the star if its is not twinkling.
     * @param starHeight the height of the star
     * @param starWidth the width of the star.
     * @param twinkleRate the rate at which the star will change color or twinkle.
     */
    public Star(Point starCenter, Dimension starThickness, boolean twinkling, boolean alongPositiveX, boolean alongPositiveY,
            Color nonTwinklingColor,
            int starHeight, int starWidth,int twinkleRate) {
        this.starCenter = starCenter;
        this.twinkling = twinkling;
        this.nonTwinklingColor = nonTwinklingColor;
        this.starHeight = starHeight;
        this.starWidth = starWidth;
        this.twinkleRate = twinkleRate;
        this.alongPositiveX = alongPositiveX;
        this.alongPositiveY = alongPositiveY;
        setStarThickness(starThickness);
    }



     /**
     * Create's a new Star object.
     * @param starCenter  the point at the top left of the star's enclosing rectangle
     * @param starThickness the thickness dimension of the star: i.e the width of its prong base.
     * @param twinkling the twinkling state of the star
     * @param nonTwinklingColor the color of the star if its is not twinkling.
     * @param starHeight the height of the star
     * @param starWidth the width of the star.
     * @param twinkleRate the rate at which the star will change color or twinkle.
     * @param theta * The angle of rotation of the star,
     * if it is rotating.
     * Angle zero means that it is not rotating.
     */
    public Star(Point starCenter, Dimension starThickness, boolean twinkling, boolean alongPositiveX, boolean alongPositiveY,
            Color nonTwinklingColor, int starHeight,
            int starWidth,int twinkleRate,double theta) {
        this.starCenter = starCenter;
        this.twinkling = twinkling;
        this.nonTwinklingColor = nonTwinklingColor;
        this.starHeight = starHeight;
        this.starWidth = starWidth;
        this.twinkleRate = twinkleRate;
        this.theta = theta;
        this.alongPositiveX = alongPositiveX;
        this.alongPositiveY = alongPositiveY;
        this.twinkling = twinkling;
        setStarThickness(starThickness);
    }


/**
 *
 * @param starWidth sets the star's width
 */
    public void setStarWidth(int starWidth) {
        this.starWidth = starWidth;
    }
/**
 *
 * @return the star's width
 */
    public int getStarWidth() {
        return starWidth;
    }
/**
 *
 * @param starHeight  sets the star's height
 */
    public void setStarHeight(int starHeight) {
        this.starHeight = starHeight;
    }
/**
 *
 * @return the star's height
 */
    public int getStarHeight() {
        return starHeight;
    }
/**
 *
 * @param twinkling  sets if the star is twinkling or not
 */
    public void setTwinkling(boolean twinkling) {
        this.twinkling = twinkling;
    }
/**
 *
 * @return true if the star is twinkling
 */
    public boolean isTwinkling() {
        return twinkling;
    }
/**
 *
 * @param nonTwinklingColor set's the color to use
 * for the star if it is not twinkling.
 */
    public void setNonTwinklingColor(Color nonTwinklingColor) {
        this.nonTwinklingColor = nonTwinklingColor;
    }
/**
 *
 * @return the color used to display a non-twinkling Star object
 */
    public Color getNonTwinklingColor() {
        return nonTwinklingColor;
    }


/**
 *
 * @param starCenter  set's the coordinate of the star's center
 */
  public void setStarCenter(Point starCenter) {
        this.starCenter = starCenter;
    }

/**
 *
 * @return the point at the center of the star
 */

    public Point getStarCenter() {
        return starCenter;
    }
/**
 *
 * @param twinkleRate sets the rate at which the star will twinkle.
 */
    public void setTwinkleRate(int twinkleRate) {
        this.twinkleRate = twinkleRate;
    }
/**
 *
 * @return the rate at which the star will twinkle
 */
    public int getTwinkleRate() {
        return twinkleRate;
    }

    public boolean isAlongPositiveX() {
        return alongPositiveX;
    }

    public void setAlongPositiveX(boolean alongPositiveX) {
        this.alongPositiveX = alongPositiveX;
    }

    public boolean isAlongPositiveY() {
        return alongPositiveY;
    }

    public void setAlongPositiveY(boolean alongPositiveY) {
        this.alongPositiveY = alongPositiveY;
    }




/**
 *
 * @param starThickness  sets the star's width
 */
    public void setStarThickness(Dimension starThickness) {
        int w =starThickness.width;
        int h = starThickness.height;

        if( w<starWidth&&h<starHeight){
            this.starThickness =starThickness;
        }
        else if( w>=starWidth&&h<starHeight){
           this.starThickness  = new Dimension( starWidth/2, h );
        }
        else if( w<starWidth&&h>=starHeight){
           this.starThickness  = new Dimension(w , starHeight/2 );
        }
        else if( w>=starWidth&&h>=starHeight){
           this.starThickness  = new Dimension( starWidth/2, starHeight/2 );
        }

    }
/**
 *
 * @return the thickness dimension of the star: i.e the width of its prong base.
 */
    public Dimension getStarThickness() {
        return starThickness;
    }
/**
 *
 * @param multiColoredTwinkling set whether this star will
 * twinkle over all range of colors or just between yellow and white
 */
    public void setMultiColoredTwinkling(boolean multiColoredTwinkling) {
        this.multiColoredTwinkling = multiColoredTwinkling;
    }
/**
 *
 * @return true if this star will
 * twinkle over all range of colors or just between yellow and white
 */
    public boolean isMultiColoredTwinkling() {
        return multiColoredTwinkling;
    }
/**
 * This Star object's rotation will increase in steps of theta so the size of theta
 * will in an animated environment determine the angular velocity of rotation of the star
 * @param theta the stepwise angle of rotation of this Star object
 */
    public void setTheta(double theta) {
        this.theta = theta;
    }
/**
 *
 * @return the stepwise angle of rotation of this Star object
 */
    public double getTheta() {
        return theta;
    }


    public double starBaseWidth(){
       Point p1 = getTopProngBaseLeftMostLocation();
       Point p2 = getTopProngBaseRightMostLocation();
       return Point.distance(p1.x, p1.y,p2.x,p2.y);

    }

/**
 *
 * @return the Point at the tip of the top prong
 */

    private Point getTopProngLocation(){
        return new Point( getStarCenter().x ,getStarCenter().y-getStarHeight()/2  );
    }
/**
 *
 * @return the Point at the tip of the bottom prong
 */
    private Point getBottomProngLocation(){
        return new Point(  getStarCenter().x, getStarCenter().y+getStarHeight()/2  );
    }
/**
 *
 * @return the Point at the tip of the left prong
 */
    private Point getLeftProngLocation(){
        return new Point( ( getStarCenter().x -getStarWidth()/2), getStarCenter().y );
    }
    /**
 *
 * @return the Point at the tip of the right prong
 */
    private Point getRightProngLocation(){
        return new Point( ( getStarCenter().x+getStarWidth()/2 ), getStarCenter().y );
    }


/**
 *
 * @return the Point at the left most
 * part of the base of the top prong
 */
    public Point getTopProngBaseLeftMostLocation(){
        return new Point( getStarCenter().x-(getStarThickness().width/2), getStarCenter().y-(getStarThickness().height/2) );
    }
/**
 *
 * @return the Point at the right most
 * part of the base of the top prong
 */
    public Point getTopProngBaseRightMostLocation(){
        return new Point( getStarCenter().x+(getStarThickness().width/2), getStarCenter().y-(getStarThickness().height/2) );
    }
/**
 *
 * @return the Point at the left most
 * part of the base of the bottom prong
 */
    public Point getBottomProngBaseLeftMostLocation(){
        return new Point( getStarCenter().x-(getStarThickness().width/2), getStarCenter().y+(getStarThickness().height/2) );
    }
/**
 *
 * @return the Point at the right most
 * part of the base of the bottom prong
 */
    public Point getBottomProngBaseRightMostLocation(){
        return new Point( getStarCenter().x+(getStarThickness().width/2), getStarCenter().y+(getStarThickness().height/2) );
    }





/**
 * Used to fill out stars.
 * @param g the Graphics object used to render the star
 * @param comp the JComponent to draw the star on.
 */
   public void draw( Graphics g, int xBounds , int yBounds){

           if(starCenter.x<=0){
               setAlongPositiveX(true);
           }
           else if(starCenter.x >= xBounds){
               setAlongPositiveX(false);
           }
           if(starCenter.y<=0){
               setAlongPositiveY(true);
           }
           else if(starCenter.y >= yBounds){
               setAlongPositiveY(false);
           }


if(!isTwinkling()){
g.setColor(nonTwinklingColor);
}

else if(isTwinkling()){
    if(isMultiColoredTwinkling()){
       g.setColor( new Color(new Random().nextInt(255) , new Random().nextInt(255) , new Random().nextInt(255)   )   );
    }
    else{
       g.setColor( new Color(255 , 100+new Random().nextInt(155)    , new Random().nextInt(255)   )   );
    }
}//end else if

Point topProng = getTopProngLocation();
Point topProngBaseLeftMost = getTopProngBaseLeftMostLocation();
Point topProngBaseRightMost = getTopProngBaseRightMostLocation();

Point leftProng = getLeftProngLocation();
Point rightProng = getRightProngLocation();

Point bottomProng = getBottomProngLocation();
Point bottomProngBaseLeftMost = getBottomProngBaseLeftMostLocation();
Point bottomProngBaseRightMost = getBottomProngBaseRightMostLocation();




int xPoints[] = {
topProng.x,topProngBaseLeftMost.x,
topProngBaseLeftMost.x,leftProng.x,
leftProng.x,bottomProngBaseLeftMost.x,
bottomProngBaseLeftMost.x,bottomProng.x,
bottomProng.x,bottomProngBaseRightMost.x,
bottomProngBaseRightMost.x,rightProng.x,
rightProng.x,topProngBaseRightMost.x,
topProngBaseRightMost.x,topProng.x
};

int yPoints[] = {
topProng.y,topProngBaseLeftMost.y,
topProngBaseLeftMost.y,leftProng.y,
leftProng.y,bottomProngBaseLeftMost.y,
bottomProngBaseLeftMost.y,bottomProng.y,
bottomProng.y,bottomProngBaseRightMost.y,
bottomProngBaseRightMost.y,rightProng.y,
rightProng.y,topProngBaseRightMost.y,
topProngBaseRightMost.y,topProng.y
};


g.fillPolygon(xPoints, yPoints,xPoints.length);










    }//end method drawStar












/**
 * Causes the star to drift or shoot across the screen
 * @param dx the amount of motion along the horizontal
 * @param dy the amount of motion along the vertical
 */
public void move(int dx, int dy){
    Random ran = new Random();
    int varySpeed = ran.nextInt( Math.abs( (int) Math.floor( dx*dx + dy*dy  )/4 ) );


    int varyXAlongPositive = ran.nextInt(2);
    int varyYAlongPositive = ran.nextInt(2);


 if(  varyXAlongPositive == 0 ){
     dx+=varySpeed;
 }
 else if(  varyXAlongPositive == 1 ){
     dx-=varySpeed;
 }

if(  varyYAlongPositive == 0 ){
     dy+=varySpeed;
 }
 else if(  varyYAlongPositive == 1 ){
     dy-=varySpeed;
 }






if(isAlongPositiveX()){
   setStarCenter(new Point(starCenter.x+=dx,starCenter.y));
}
else if(!isAlongPositiveX()){
   setStarCenter( new Point(starCenter.x-=dx,starCenter.y));
}
if(isAlongPositiveY()){
    setStarCenter( new Point(starCenter.x,starCenter.y+=dy));
}
else if(!isAlongPositiveY()){
    setStarCenter( new Point(starCenter.x,starCenter.y-=dy));
}



}



/**
 * Causes the star to drift or shoot across the screen
 */
public void moveRandomly(){
    int xChoice = new Random().nextInt(2);
     int yChoice = new Random().nextInt(2);


     int dx=0;
      int dy=0;

     if(xChoice==0){
         dx=-1*new Random().nextInt(  new Random().nextInt(200));
     }
     else if(xChoice==1){
         dx=new Random().nextInt(   new Random().nextInt(200)  );
     }
    if(yChoice==0){
        dy=-1*new Random().nextInt(  new Random().nextInt(200));
    }
    else if(yChoice==1){
         dy=new Random().nextInt(  new Random().nextInt(200));
    }
    setStarCenter(new Point (starCenter.x+dx,

            starCenter.y+dy) );
}//end method moveRandomly

/**
 * Causes the star to drift or shoot across the screen
 * @param dx the maximum horizontal distance that the star should move in a step
 * @param  dy the maximum vertical distance that the star should move in a step
 */
public void moveRandomlyWithMaxSteps(int dx,int dy){
    int xChoice = new Random().nextInt(2);
     int yChoice = new Random().nextInt(2);



     if(xChoice==0){
         dx=-1*new Random().nextInt(dx);
     }
     else if(xChoice==1){
         dx=new Random().nextInt(dx);
     }
    if(yChoice==0){
        dy=-1*new Random().nextInt(dy);
    }
    else if(yChoice==1){
         dy=new Random().nextInt(dy);
    }
    setStarCenter(new Point (starCenter.x+dx,

            starCenter.y+dy) );
}//end method moveRandomly
/**
 * Moves the star's centerto the new point specified in the parameter list of the method
 *@param newPoint the newPoint to move the star to.
 */
public void translate(Point newPoint){
setStarCenter(newPoint);
}


/**
 *
 * @param g the Graphics objet used to render the Star object.
 * @param theta rotates the star continuously in increasing angles of theta.
 */
public void rotate(Graphics g,double theta){
    this.theta+=theta;
       Graphics2D g2D=(Graphics2D) g;
   g2D.rotate(this.theta, getStarCenter().x, getStarCenter().y);
}


}
