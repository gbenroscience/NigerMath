/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package splashyflashy;

import java.awt.Point;

/**
 *
 * @author GBENRO
 */
/**
 * models a line.
 */
public class Line{
    private double angle;
    private double YIntercept;
/**
 * creates a line object,given 2 points on the line
 * @param start
 * @param stop
 */
    public Line( Point start,Point stop ){
   double y1=start.y;double y2=stop.y;
   double x1=start.x;double x2=stop.x;
    double grad= ((y2-y1)/(x2-x1));
    this.angle=Math.atan(grad);
    }


 /**
 * creates a line object,given a point and the angle that the line makes with the horizontal
 * @param the angle that the line makes with the positive horizontal axis
 * @param point a known point on the line
 */
    public Line( Point point,double angle ){
        this.angle=angle;
        //Note this line.This is where transformation occurs. The equation of a straight line from math is y = mx+c
        //But in Java, the graph has undergone a 180 deg rotation so that its equation is now: y = mx-c.So we negate the quantity
        //in the bracket which is the normal value for c in math
this.YIntercept=   -(point.y-Math.atan(angle)*point.x);
    }



   /**
     * creates a line object,given the y-Intercept and the angle that the line makes with the horizontal
     * @param angle the angle between the line and the horizontal
     * @param intercept the y intercept of the line.The intercept here is the -ve of that used in math
    * because Java's graph has undergone a 180 deg rotation so that its definition for straight lines is
    * is y = mx-c and not y = mx+c
    */
    public Line( double angle,double intercept ){
        this.angle=angle;
this.YIntercept=intercept;
    }


    /**
     *
     * @return the gradient of the line
     */
public double gradient(){
return Math.atan(angle);
}

        public double getYIntercept() {
            return YIntercept;
        }

        public void setYIntercept(double YIntercept) {
            this.YIntercept = YIntercept;
        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }


public double getX(double y){
    return   (y-getYIntercept())/(gradient());
}

public double getY(double x){
    return ( gradient()*x+getYIntercept() );
}
/**
 * this method attempts to obtain as an integer value,the point of unit distance from a given point known to lie
 * on a given line.
 * @param p the point from which the new point is to be obtained
 * @return a point approximately 1 unit in distance
 * from Point p.
 */
public Point nextPoint(Point p){

    Point newPoint= new Point(0,0);
    double c1=Math.sin(angle);
    double c2=Math.cos(angle);
    double sub=c2-c1;
    if(Math.abs(sub)<0.1){
        newPoint= new Point(p.x+1,p.y+1);
    }
    else if(Math.abs(sub)>=0.1){
      if(c2>c1){
        newPoint= new Point(p.x,p.y+1);
    }
    else if(c2<c1){
        newPoint= new Point(p.x+1,p.y);
    }


    }

    return newPoint;
}


}//end class Line
