/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;



import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Objects of this class are able to drag around
 * objects of class DraggableBufferedImage and java.awt.Component
 * on the screen.
 *
 *
 * Uses an highly optimized constant distance
 * algorithm to move a Component around.
 * It checks the initial point where it grips
 * the Component and also measures the initial
 * horizontal distance between that point and the left border
 * of the Component, following which it measures the initial
 * vertical distance between that point and the top border
 * of the Component.
 * Then it ensures that whenever the mouse is dragged, the Component
 * top and left border remain equidistant from the mouse.
 *
 * @author MRSJIBOYE
 */
public class ObjectDragger {


    public static final int COMPONENT_OBJECT = 0;
    public static final int IMAGE_OBJECT = 1;
    /**
     * The current setting for the type
     * of the object. Can only be one of
     * <code>IMAGE_OBJECT</code> and
     * <code>COMPONENT_OBJECT</code> and
     *
     */
    private int objectType;
    private int horizontalScroll=0;
    private int verticalScroll=0;
    private Object dragItem;

private Component topLevel;
int grabDistanceX =0;
int grabDistanceY =0;
boolean dragTopParentOn;
public ObjectDragger( int objectType,Object dragItem, boolean dragTopParentOn ){

    this.objectType = objectType;
    this.dragItem = dragItem;
    this.dragTopParentOn = dragTopParentOn;


    if(isComponent()){

       final Component c = (Component) this.dragItem;
       if( ObjectDragger.this.dragTopParentOn ){
Component tmp = c;
                        while( tmp.getParent() != null ){
                            tmp = tmp.getParent();
                            topLevel = tmp;
                        }
if( topLevel == null ){ topLevel = c; }//The Component was a topLevel one to start with.
        }//end if


        c.addMouseListener( new MouseAdapter() {



                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if( ObjectDragger.this.dragTopParentOn ){
                    Point p = e.getLocationOnScreen();
                    Point loc = topLevel.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;
                    }
                    else{
                    Point p = e.getLocationOnScreen();
                    Point loc = c.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;

                    }


                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    grabDistanceX=0;
                    grabDistanceY=0;
                    c.removeMouseMotionListener(this);
                }

        });
        c.addMouseMotionListener( new MouseMotionAdapter() {

            boolean beginning = true;
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);



                    Point p = e.getLocationOnScreen();

                    if( !ObjectDragger.this.dragTopParentOn ){
                    if(beginning){

                    Point loc = c.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;
                    beginning = false;
                    }
                    Component comp=c.getParent();
 try{
Point parLoc = comp.getLocationOnScreen();
                    c.setLocation(p.x-parLoc.x-grabDistanceX,p.y-parLoc.y-grabDistanceY);
                    }
 catch(Exception ee){
                        comp = c;
Point parLoc = comp.getLocationOnScreen();
                    c.setLocation(p.x-grabDistanceX,p.y-grabDistanceY);

                    }//end catch
                    }//end if
                    else{

                    if(beginning){

                    Point loc = topLevel.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;
                    beginning = false;
                    }

                    topLevel.setLocation(p.x-grabDistanceX,p.y-grabDistanceY);

                    }//end else

                }//end method



        });

    }//end if

    else{

        final utils.DraggableBufferedImage dbi = (utils.DraggableBufferedImage) dragItem;
       final Component comp = dbi.getComponent();

        comp.addMouseListener( new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Point p = e.getLocationOnScreen();
if(dbi.contains(p)){
dbi.setSelected(true);
}
                    if( dbi.contains(p) && dbi.isSelected()  ){
                    Point loc = dbi.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;
                    }//end if

                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    Point p = e.getLocationOnScreen();
                    if( dbi.contains(p) && dbi.isSelected()  ){
                    grabDistanceX=0;
                    grabDistanceY=0;
                    comp.removeMouseMotionListener(this);
                    }
                }


        });


        comp.addMouseMotionListener( new MouseMotionAdapter() {
boolean beginning = true;
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    Point p = e.getLocationOnScreen();
                    if( dbi.contains(p) && dbi.isSelected()  ){
                    if(beginning){

                    Point loc = dbi.getLocationOnScreen();
                    grabDistanceX = p.x - loc.x;
                    grabDistanceY = p.y - loc.y;
                    beginning = false;
                    }

 try{
Point parLoc = comp.getLocationOnScreen();
                    dbi.setLocation(p.x-parLoc.x-grabDistanceX,p.y-parLoc.y-grabDistanceY);
                    comp.repaint();
                    }
 catch(Exception ee){

                    }//end catch
                    }//end if
                }




        });




    }


}

    public void setVerticalScroll(int verticalScroll) {
        this.verticalScroll = verticalScroll;
    }

    public int getVerticalScroll() {
        return verticalScroll;
    }

    public void setHorizontalScroll(int horizontalScroll) {
        this.horizontalScroll = horizontalScroll;
    }

    public int getHorizontalScroll() {
        return horizontalScroll;
    }







        public boolean isImage(){
            return objectType == IMAGE_OBJECT;
        }
        public boolean isComponent(){
            return objectType == COMPONENT_OBJECT;
        }


}