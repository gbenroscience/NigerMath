/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import gui.MessageFlash;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;


/**
 *
 * @author GBEMIRO
 */
public class WindowList extends JList{
private JWindow window;
private MouseHandler mousey;
/**
 * Creates a new Displayable JList
 *
 * @param frameOwner the JFrame object used to ensure that the JWindow displays well so that components on it are
 * focusable and usable
 * @param listData the Vector that contains the items to be displayed on the JList attriubute object of this class
 * @param flash the MessageFlash object that is created in response to selecting a particular
 * row on the JList
 * @param evt the MouseEvent (a right click) that initiated or triggered the actions
 */
    public WindowList(JFrame frameOwner,Vector<String>listData,final MessageFlash flash,MouseEvent evt){
        window = new JWindow(frameOwner);
        setListData(listData);
        setVisibleRowCount(listData.size());
        setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
      
window.setSize(80,60);
window.setLocation(evt.getLocationOnScreen());
window.setVisible(true);
setSelectedIndex(-1);
            window.add( new JScrollPane( this ) );








    }



 /**
 * Creates a new Displayable JList framed in a JWindow.
 *
 * @param frameOwner the JFrame object used to ensure that the JWindow displays well so that components on it are
 * focusable and usable
 * @param listData the Vector that contains the items to be displayed on the JList attriubute object of this class
 */
    public WindowList(JFrame frameOwner,Vector<String>listData){
    window = new JWindow(frameOwner);
        setListData(listData);
   setVisibleRowCount(listData.size());
      setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

            window.add( new JScrollPane( this ) );

    }//end WindowList constructor



/**
 *
 * @return the data Vector for items to be displayed in the JList
 */
    public JWindow getWindow() {
        return this.window;
    }//end method getWindow


/**
 * @param window  sets the JWindow to be used to frame this JList.
 */
    public void setWindow(JWindow window) {
this.window=window;
    }//end method setWindow(args)






    /**
 * Creates a new Displayable JList
 *
 * @param listData the Vector that contains the items to be displayed on the JList attriubute object of this class
 * @param flash the MessageFlash object that is created in response to selecting a particular
 * row on the JList
 * @param evt the MouseEvent (a right click) that initiated or triggered the actions
 */
    public void setData(Vector<String>listData,final MessageFlash flash, final MouseEvent evt){
        setListData(listData);
        setVisibleRowCount(5);
        setSelectionMode( ListSelectionModel.SINGLE_SELECTION );




window.setLocation(evt.getLocationOnScreen());
window.setSize(80,60);
window.setVisible(true);


mousey = new MouseHandler(flash);
this.addMouseListener(mousey);


    }


private class MouseHandler extends MouseAdapter{
    MessageFlash flash;
    public MouseHandler(final MessageFlash flash){
        this.flash = flash;
    }
Point pt;

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
          if(getSelectedIndex()==0){
              flash.getTypeFormLabel().setText("ENTER VARIABLE/VALUE" );
              flash.getWindow().setVisible(true);
          }
          else if(getSelectedIndex()==1){
         // manager.execute("");
          }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                removeMouseListener(this);
              window.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               pt =e.getPoint();
                int anchorIndex = WindowList.this.locationToIndex(pt);
               WindowList.this.addSelectionInterval( anchorIndex,anchorIndex );
            }

            @Override
            public void mouseExited(MouseEvent e) {
               pt =e.getPoint();
                int anchorIndex = WindowList.this.locationToIndex(pt);
              WindowList.this.addSelectionInterval( anchorIndex,anchorIndex );
            }

        @Override
        public void mouseMoved(MouseEvent e) {
              pt =e.getPoint();
                int anchorIndex = WindowList.this.locationToIndex(pt);
                WindowList.this.addSelectionInterval( anchorIndex,anchorIndex );
        }

}






}
