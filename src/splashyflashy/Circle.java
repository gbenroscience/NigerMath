package splashyflashy;




import java.util.Random;
import java.awt.*;


public class Circle{

    /**
     * if true, txpanding circle
     */
private boolean expanding;
    /**
     * The circle center
     */
    private Point center;
  /**
   * The Color of the Circle object
   */
  private Color color;


/**
 * The radius of this object
 */
    private int radius;
    /**
     * is true if this object is moving along the positive x axis.
     */
    private boolean alongPositiveX;
    /**
     * is true if this object is moving along the positive y axis.
     */
    private boolean alongPositiveY;



/**
 *
 * @param center The circle center
 * @param color The color of the circle.
 * @param radius The radius of this object.
 * @param alongPositiveX is true if this object is moving along the positive x axis.
 * @param alongPositiveY is true if this object is moving along the positive y axis.
 * @param expanding 
 */
        public Circle(Point center, Color color,int radius, boolean alongPositiveX, boolean alongPositiveY,boolean expanding) {
            this.center = center;
            this.color = color;
            this.radius = radius;
            this.alongPositiveX = alongPositiveX;
            this.alongPositiveY = alongPositiveY;
            setExpanding(expanding);
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

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }

  private int varyingRad;
public void expand(){
    if( varyingRad<radius  ){
        varyingRad++;
    }
    else{
        varyingRad=0;
    }
}//end method


private double distanceFrom(Circle circle){
    Point p1 = center;
    Point p2 = circle.center;
    double dx = (p1.x-p2.x);
    double dy = (p1.y-p2.y);
    return Math.sqrt( dx*dx + dy*dy  );
}

public void rebound(Circle[] circles){
    for(int i=0;i<circles.length;i++){
        if( circles[i]!=this ){
            if(circles[i].distanceFrom(this)<=(this.radius+circles[i].radius)  ){
            if(circles[i].isAlongPositiveX()){
                circles[i].setAlongPositiveX(false);
            }
            else{
                circles[i].setAlongPositiveX(true);
            }
            if(circles[i].isAlongPositiveY()){
                circles[i].setAlongPositiveY(false);
            }
            else{
                circles[i].setAlongPositiveY(true);
            }
            }//end if
        }//end if
    }

}



/**
 *
 * @param g The Graphics object used to draw.
 */
       public void draw(Graphics g,int xBounds , int yBounds){
expand();
           if(center.x<=0){
               setAlongPositiveX(true);
           }
           else if(center.x >= xBounds){
               setAlongPositiveX(false);
           }
           if(center.y<=0){
               setAlongPositiveY(true);
           }
           else if(center.y >= yBounds){
               setAlongPositiveY(false);
           }

           g.setColor( color );
           if(!expanding){
           g.fillArc(center.x-radius, center.y-radius, 2*radius, 2*radius, 0, 360);
           }
           else{
        g.fillArc(center.x-varyingRad, center.y-varyingRad, 2*varyingRad, 2*varyingRad, 0, 360);
           }

       }




public void move(int dx, int dy){
    Random ran = new Random();
    int varySpeed = ran.nextInt( 6 );


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
    setCenter(new Point(center.x+=dx,center.y));
}
else if(!isAlongPositiveX()){
    setCenter( new Point(center.x-=dx,center.y));
}
if(isAlongPositiveY()){
    setCenter( new Point(center.x,center.y+=dy));
}
else if(!isAlongPositiveY()){
    setCenter( new Point(center.x,center.y-=dy));
}



}

}//end class
