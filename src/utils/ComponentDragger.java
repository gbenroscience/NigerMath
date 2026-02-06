/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author GBEMIRO
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * Objects of this class have the ability to drag objects
 * of class java.awt.Window and javax.swing.JComponent.
 *
 * This will help application programmers write code that can windows that
 * have no title bar around and so on.
 *
 * @version 1.0
 * @since Mathron Version 1.0,2010
 * @author GBEMIRO
 */

public class ComponentDragger {

//The x-location of the point where the ,mouse is first pressed on the JComponent object to be used for the drag
    private int initMousePressedX;
//The y-location of the point where the ,mouse is first pressed on the JComponent object to be used for the drag
    private int initMousePressedY;
//The x-location of the top left corner of the top level parent of the JComponent.
    private int initXOnComponentTopParent;

//The y-location of the top left corner of the top level parent of the JComponent.
    private int initYOnComponentTopParent;


  private MouseWatch1 watch1;
  private MouseWatch2 watch2;

  private MouseWatch3 watch3;
  private MouseWatch4 watch4;
    /**
     * Call this constructor to drag any object of class Window around
     * given that it has a JComponent object attached to it.
     *
     * This constructor shall be called in association with a
     * mousePressed event attached to the object to be dragged.
     *
     *
     * @param comp the JComponent object attached to the window to be dragged.
     * @param evt the MouseEvent object
     *@param dragTopParentOn If true the component is dragged alongside its top level
     * ancestor i.e a JFrame or a JWindow or a Frame or other.
     * Else, only the component is dragged.
     *
     *
     */
    public ComponentDragger( final JComponent comp,MouseEvent evt,boolean dragTopParentOn ){
if(dragTopParentOn){
                Point initComponentTopParentLocation=comp.getTopLevelAncestor().getLocation();
                initXOnComponentTopParent=initComponentTopParentLocation.x;
                initYOnComponentTopParent=initComponentTopParentLocation.y;

                Point evtPoint=evt.getLocationOnScreen();

                initMousePressedX=evtPoint.x;
                initMousePressedY=evtPoint.y;






 
watch1 = new MouseWatch1(comp);
watch2 = new MouseWatch2( comp);

comp.addMouseListener(watch1);
comp.addMouseMotionListener(watch2); 

}//end if
else{


   Point initComponentTopParentLocation=comp.getLocation();
                initXOnComponentTopParent=initComponentTopParentLocation.x;
                initYOnComponentTopParent=initComponentTopParentLocation.y;

                Point evtPoint=evt.getLocationOnScreen();

                initMousePressedX=evtPoint.x;
                initMousePressedY=evtPoint.y;







watch3 = new MouseWatch3(comp);
watch4 = new MouseWatch4( comp);

comp.addMouseListener(watch3);
comp.addMouseMotionListener(watch4);



}
    }//end constructor ComponentDragger(args)






private class MouseWatch1 extends MouseAdapter{
  private JComponent comp;

  public MouseWatch1(JComponent comp){
      this.comp=comp;
  }

         @Override
            public void mouseReleased(MouseEvent e) {

           comp.removeMouseMotionListener(watch2);
           comp.removeMouseListener(this);
            }




}

private class MouseWatch2 extends MouseMotionAdapter{
  private JComponent comp;

    public MouseWatch2(JComponent comp){
      this.comp=comp;
  }
          @Override
            public void mouseDragged(MouseEvent e) {
                try{
                   Point p=e.getLocationOnScreen();
                    int x=p.x;
                    int y=p.y;

  comp.getTopLevelAncestor().setLocation(initXOnComponentTopParent+(x-initMousePressedX),
                     initYOnComponentTopParent+(y-initMousePressedY));

                }
                catch(NullPointerException nolian){

                }

            }



}




private class MouseWatch3 extends MouseAdapter{
  private JComponent comp;

  public MouseWatch3(JComponent comp){
      this.comp=comp;
  }

         @Override
            public void mouseReleased(MouseEvent e) {

           comp.removeMouseMotionListener(watch4);
           comp.removeMouseListener(this);
            }




}

private class MouseWatch4 extends MouseMotionAdapter{
  private JComponent comp;

    public MouseWatch4(JComponent comp){
      this.comp=comp;
  }
          @Override
            public void mouseDragged(MouseEvent e) {
                try{
                   Point p=new Point(e.getXOnScreen(),e.getYOnScreen());
                    int x=p.x;
                    int y=p.y;

  comp.setLocation(initXOnComponentTopParent+(x-initMousePressedX),
                     initYOnComponentTopParent+(y-initMousePressedY));

                }
                catch(NullPointerException nolian){

                }

            }



}


}//end class ComponentDragger