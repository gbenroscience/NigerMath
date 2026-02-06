/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GraphPaper.java
 *
 * Created on 19-Oct-2010, 17:43:50
 */

package math.graph.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import math.graph.Grid;
import util.imageprocessor.ImageUtilities;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class GraphPaper extends javax.swing.JPanel implements Printable {
private Grid grid;
    /**
     * Set this object's attributes
     * when the pointerPressed method is called.
     */
    private Point startCoords=new Point();
    /**
     * Set this object's attributes
     * when the pointerReleased method is called.
     */
    private Dimension shiftCoords=new Dimension();
    /** Creates new form GraphPaper */
    public GraphPaper() {
        initComponents();

     setVisible(true);

    
grid = new Grid("",true, true,
        10,
        Color.green, Color.blue, Color.black,Color.red,
        15, 5, -100, 150, 10, 0.1,
        new Font("Arial Bold",Font.PLAIN,15),this);

    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    
    @Override
    public void paintComponent(Graphics g){
        try{
        super.paintComponent(g);
        grid.draw(g);
        //System.out.println(">>Functions: "+FunctionManager.FUNCTIONS);
        }
        catch(NullPointerException nol){
//
        }

    }//end method

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        setName("Form"); // NOI18N
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 662, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        try{
            setToolTipText( String.valueOf( grid.convertScreenPointToGraphPoint(new Point(evt.getX(),evt.getY())) ) );
        }//end try
        catch(NullPointerException nolian){
        }
}//GEN-LAST:event_formMouseMoved

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
Point evtLoc=evt.getPoint();
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
Point evtLoc=evt.getPoint();
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
    }//GEN-LAST:event_formMouseReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
Point evtLoc=evt.getPoint();

        shiftCoords = new Dimension( (int) (evtLoc.x - startCoords.x), (int) (evtLoc.y - startCoords.y) );
        Point p = grid.getLocationOfOrigin();
        grid.setLocationOfOrigin( new Point( p.x+shiftCoords.width,p.y+shiftCoords.height ) );
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
 repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
      Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
      setCursor(cursor);
    }//GEN-LAST:event_formMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

BufferedImage img = ImageUtilities.createSwingObjectImage(this);
Graphics2D g2D = (Graphics2D) graphics;
Dimension d = this.getPreferredSize();
 g2D.drawImage(img, 0,0,d.width,d.height, this );
        return Printable.PAGE_EXISTS;
    }






}
