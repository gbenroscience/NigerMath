/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MessageFlash.java
 *
 * Created on Jul 23, 2010, 7:44:47 AM
 */

package gui;

/**
 *
 * @author GBEMIRO
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import utils.ComponentDragger;
public class MessageFlash extends javax.swing.JPanel{
private Timer timer;
    JWindow window;
    private boolean closeWindow;
    private MouseEvent evt;

    /** Creates new form MessageFlash
     * @param frame
     * @param evt
     */
    public MessageFlash(JFrame frame, MouseEvent evt) {
           initComponents();

 this.evt = evt;
   window = new JWindow(frame);
        window.add(this);
        window.setVisible(false);
        window.setSize(424,424);
        window.setLocation(evt.getLocationOnScreen());
        window.setAlwaysOnTop(true);
        titleBar1.getClosePanel1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt){
                setCloseWindow(true);
                start();//start the animation that will shrink the window to size 0
            }


        });

        titleBar1.addMouseListener(new MouseAdapter() {
            @Override
          public void mousePressed(MouseEvent evt){
                new ComponentDragger(titleBar1, evt,true);
         }

        });



    }//end constructor 
/**
 *
 * @return tru if the window is closeable or is being closed
 */
    public boolean isCloseWindow() {
        return closeWindow;
    }
/**
 *
 * @param closeWindow sets the closeable state of this window
 */
    public void setCloseWindow(boolean closeWindow) {
        this.closeWindow = closeWindow;
    }
/**
 *
 * @return the MouseEvent object that determines the location of this window
 */
    public MouseEvent getEvt() {
        return evt;
    }
/**
 *
 * @param evt sets the MouseEvent object that determines the location of this window
 */
    public void setEvt(MouseEvent evt) {
        this.evt = evt;
    }




/**
 *
 * @return the JTextArea object where description info. are typed
 */
    public JTextArea getDescrArea() {
        return descrArea;
    }
/**
 * 
 * @param descrArea the JTextArea object where descriptions are typed
 */
    public void setDescrArea(JTextArea descrArea) {
        this.descrArea = descrArea;
    }
/**
 *
 * @return the area where formulae or Variables and their values are typed in
 */
    public JTextArea getFormArea() {
        return formArea;
    }
/**
 *
 * @param formArea the area where the Variables/Values or formulae are typed in.
 */
    public void setFormArea(JTextArea formArea) {
        this.formArea = formArea;
    }
/**
 *
 * @return the animation timer of objects of this class
 */
    public Timer getTimer() {
        return timer;
    }
/**
 *
 * @param timer sets the animation timer of objects of this class
 */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
/**
 *
 * @return the description label of objects of this class
 */
    public JLabel getTypeDescrLabel() {
        return typeDescrLabel;
    }
/**
 *
 * @param typeDescrLabel sets the description label of objects of this class
 */
    public void setTypeDescrLabel(JLabel typeDescrLabel) {
        this.typeDescrLabel = typeDescrLabel;
    }
/**
 *
 * @return the label that prompts the user to type the formula or variable
 */
    public JLabel getTypeFormLabel() {
        return typeFormLabel;
    }
/**
 *
 * @param typeFormLabel sets the label that prompts the user to type the formula or variable
 */
    public void setTypeFormLabel(JLabel typeFormLabel) {
        this.typeFormLabel = typeFormLabel;
    }
/**
 *
 * @return the displaying window for objects of this class
 */
    public JWindow getWindow() {
        return window;
    }
/**
 *
 * @param window sets the displaying window for objects of this class
 */
    public void setWindow(JWindow window) {
        this.window = window;
    }



/**
 * starts the animation
 */

public void start(){
if(timer==null){
    timer=new Timer(20,new TimeHandler());
    timer.start();
}//end if

else if(!timer.isRunning()){
    timer.restart();
}

}
/**
 * stops the animation
 */
public void stop(){
    if(timer!=null){
timer.stop();
    }
}

class TimeHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
  if(isCloseWindow()){      
      zoomToClose();
        }
}


}

/**
 * closes this window by shrinking its size to zero
 */
public void zoomToClose(){
            Dimension dim=window.getSize();
            Point loc=window.getLocation();
            int horLoc=loc.x;
            int verLoc=loc.y;

            int horSize = dim.width;
            int verSize = dim.height;
       window.setSize(horSize-=20,verSize-=20);
       window.setLocation(horLoc+=20,verLoc);
       repaint();



        if(horSize==0&&verSize==0){
            stop();//stop timer
            window.setVisible(false);
            setCloseWindow(false); //after the closing animation, reset the closeWindow condition back to false
        }
}//end method





/**
 * closes this window by expanding its size from zero to a given maximum
 */
public void zoomToOpen(){

            Dimension dim=window.getSize();
            Point loc=window.getLocation();
            int horLoc=loc.x;
            int verLoc=loc.y;

            int horSize = dim.width;
            int verSize = dim.height;


        if(horSize==0&&verSize==0){
            stop();//stop timer
            window.setVisible(false);
        }


       window.setSize(horSize-=20,verSize-=20);
       window.setLocation(horLoc+=20,verLoc);
        repaint();



        if(horSize==0&&verSize==0){
            stop();//stop timer
            window.setVisible(false);
        }



}//end close



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descrArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        formArea = new javax.swing.JTextArea();
        typeFormLabel = new javax.swing.JLabel();
        typeDescrLabel = new javax.swing.JLabel();
        titleBar1 = new gui.tidBitGraphics.TitleBar();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nigermath.NigerMathApp.class).getContext().getResourceMap(MessageFlash.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        descrArea.setBackground(resourceMap.getColor("descrArea.background")); // NOI18N
        descrArea.setColumns(20);
        descrArea.setFont(resourceMap.getFont("descrArea.font")); // NOI18N
        descrArea.setForeground(resourceMap.getColor("descrArea.foreground")); // NOI18N
        descrArea.setLineWrap(true);
        descrArea.setRows(5);
        descrArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        descrArea.setCaretColor(resourceMap.getColor("descrArea.caretColor")); // NOI18N
        descrArea.setName("descrArea"); // NOI18N
        jScrollPane3.setViewportView(descrArea);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        formArea.setBackground(resourceMap.getColor("formArea.background")); // NOI18N
        formArea.setColumns(20);
        formArea.setFont(resourceMap.getFont("formArea.font")); // NOI18N
        formArea.setForeground(resourceMap.getColor("formArea.foreground")); // NOI18N
        formArea.setLineWrap(true);
        formArea.setRows(5);
        formArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        formArea.setCaretColor(resourceMap.getColor("formArea.caretColor")); // NOI18N
        formArea.setName("formArea"); // NOI18N
        jScrollPane2.setViewportView(formArea);

        typeFormLabel.setFont(resourceMap.getFont("typeFormLabel.font")); // NOI18N
        typeFormLabel.setText(resourceMap.getString("typeFormLabel.text")); // NOI18N
        typeFormLabel.setName("typeFormLabel"); // NOI18N

        typeDescrLabel.setFont(resourceMap.getFont("typeDescrLabel.font")); // NOI18N
        typeDescrLabel.setText(resourceMap.getString("typeDescrLabel.text")); // NOI18N
        typeDescrLabel.setName("typeDescrLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeFormLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(typeDescrLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(typeFormLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeDescrLabel)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane2, jScrollPane3});

        titleBar1.setName("titleBar1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(titleBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents










    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descrArea;
    private javax.swing.JTextArea formArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private gui.tidBitGraphics.TitleBar titleBar1;
    private javax.swing.JLabel typeDescrLabel;
    private javax.swing.JLabel typeFormLabel;
    // End of variables declaration//GEN-END:variables

}