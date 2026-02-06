/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package splashyflashy;

/**
 *
 * @author GBENRO
 */

import java.awt.Point;
import static java.lang.Math.*;
public class Velocity{
    private double Vx;
    private double Vy;
    private Line line;//line of action of the velocity vector
    /**
     *creates a new Velocity object from data about the the horizontal and
     * vertical components of the velocity vector.It assumes a line of action that passes through
     * the origin.If this is not so,that is if the line of acton does not pass through the origin,
     * please use the other constructor to create the Velocity object.
     * @param Vx the horizontal component of the velocity vector
     * @param Vy the vertical component of the velocity vector
     */
    public Velocity(double Vx,double Vy){
        double angle=atan(Vy/Vx);
        setLine(new Line(new Point(0,0),angle));
        this.Vx=Vx;
        this.Vy=Vy;

    }
    /**
     *creates a new Velocity object from data about the the horizontal and
     * vertical components of the velocity vector.It assumes a line of action that passes through
     * the origin.If this is not so,that is if the line of acton does not pass through the origin,
     * please use the other constructor to create the Velocity object.
     * @param Vx the horizontal component of the velocity vector
     * @param Vy the vertical component of the velocity vector
     */
    public Velocity(Point p,double Vx,double Vy){
        double angle=atan(Vy/Vx);
        setLine(new Line(p,angle));
        this.Vx=Vx;
        this.Vy=Vy;

    }
    /**
     * creates a new Velocity object from available data about the line of action and the magnitude of the Velocity
     * @param line
     * @param magnitude
     */
  public Velocity(Line line, double magnitude){
      this.line=line;
   double angle=line.getAngle();
   this.Vx=magnitude*cos(angle);
   this.Vy=magnitude*sin(angle);
  }


  public static Velocity createVelocity(Line line, double magnitude){
   return new Velocity(line, magnitude);
  }

/**
 * reverses the direction of this Velocity object.
 */
public void reverseMotion(Point p){
Velocity v=new Velocity(p,-Vx, -Vy);
setLine(v.getLine());
setVx(v.getVx());
setVx(v.getVy());
}

/**
 * reverses the direction of this Velocity object.
 */
public void reverseXMotion(Point p){
Velocity v=new Velocity(p,-Vx,Vy);
setLine(v.getLine());
setVx(v.getVx());
setVx(v.getVy());
}
/**
 * reverses the direction of this Velocity object.
 */
public void reverseYMotion(Point p){
Velocity v=new Velocity(p,Vx,-Vy);
setLine(v.getLine());
setVx(v.getVx());
setVx(v.getVy());
}
public static double magnitude(double Vx, double Vy){
   return sqrt( Math.pow(Vx,2)+Math.pow(Vy,2) );
}

    public double magnitude(){
        return sqrt( Math.pow(Vx,2)+Math.pow(Vy,2) );
    }
    public double getAngle(){
        return Math.atan(Vy/Vx);
    }

        public double getVx() {
            return Vx;
        }

        public void setVx(double Vx) {
   double initialMagnitude=magnitude( getVx() , getVy() );
   double finalMagnitude=abs( ( Vx/getVx() )*initialMagnitude );
   double angle = getAngle();
   this.Vx=Vx;
   this.Vy=finalMagnitude*sin(angle);
        }

        public double getVy() {
            return Vy;
        }

        public void setVy(double Vy) {
        double initialMagnitude=magnitude( getVx() , getVy() );
   double finalMagnitude=abs( ( Vy/getVy() )*initialMagnitude );
   double angle = getAngle();
   this.Vy=Vy;
   this.Vx=finalMagnitude*cos(angle);
        }
    public Line getLine() {
        return line;
    }
/**
 * This method assumes that the user has decided to change the line of action and the change is not due to collisions
 * This means that the magnitude of the velocity remains constant while only the compnents(horizontal and vertical)
 * change.
 * @param line the new line of action.
 */
    public void setLine(Line line) {
        this.line = line;
         Velocity v=createVelocity(line, magnitude());
         setVx(v.getVx());
         setVy(v.getVy());
    }


    @Override
public String toString(){
    return  "V = "  +Vx+"i +"+Vy+"j\n:.....θ = "+getAngle()+ ":.....\n|V| = "+magnitude();
}

}//end class Velocity

