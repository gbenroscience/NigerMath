/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.graph.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.Timer;
import com.github.gbenroscience.math.Point;
import com.github.gbenroscience.math.graph.Grid;
import com.github.gbenroscience.math.graph.tools.FontStyle;
import com.github.gbenroscience.math.graph.tools.GraphColor;
import com.github.gbenroscience.math.graph.tools.GraphFont;
import java.awt.Graphics;
import math.graph.gui.adapter.SwingDrawingContext; 

/**
 *
 * @author gbemirojiboye
 */
public class GraphPanel extends javax.swing.JPanel implements Printable {

    /**
     * Set this object's attributes when the pointerPressed method is called.
     */
    private java.awt.Point startCoords = new java.awt.Point();
    /**
     * Set this object's attributes when the pointerReleased method is called.
     */
    private Dimension shiftCoords = new Dimension();

    Grid grid;
    String function = "p(x)=3*x^2";
    private int gridSize = 8;
    private boolean showGridLines = true;
    private boolean labelAxis = true;
    private GraphColor gridColor = GraphColor.gray;
    private GraphColor majorAxesColor = GraphColor.orange;
    private GraphColor tickColor = GraphColor.pink;
    private GraphColor plotColor = GraphColor.black;
    private int majorTickLength = 8;
    private int minorTickLength = 4;
    private double lowerXLimit = -200;
    private double upperXLimit = 200;
    private double xStep = 0.1;
    private double yStep = 0.1;
    private GraphFont font = new GraphFont("Times New Roman", FontStyle.BOLD, 14);
    private Point locationOfOrigin = new Point();
    private SwingDrawingContext context;

    /**
     * Creates new form GraphPanel
     */
    public GraphPanel() {
        super(); 
        initComponents();
        context = new SwingDrawingContext((Graphics2D) getGraphics());
        grid = new Grid(function, showGridLines, labelAxis, gridSize, gridColor, majorAxesColor, tickColor, plotColor, majorTickLength,
                minorTickLength, lowerXLimit, upperXLimit, xStep, yStep, font, this);
        
    }

    private String doubleXYToString(double[] xy) {
        return "[x,y] = [" + xy[0] + "," + xy[1] + "]";
    }

    public SwingDrawingContext getContext() {
        return context;
    }
                          
    private void initComponents() {

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

   
    }// </editor-fold>                        

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                  
        java.awt.Point evtLoc = evt.getPoint();
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
    }                                 

    private void formMouseMoved(java.awt.event.MouseEvent evt) {                                
        try {
            setToolTipText(doubleXYToString(grid.convertScreenPointToGraphCoords(evt.getX(), evt.getY())));
        }//end try
        catch (NullPointerException nolian) {
        }
    }                               

    private void formMouseReleased(java.awt.event.MouseEvent evt) {                                   
        java.awt.Point evtLoc = evt.getPoint();
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
    }                                  

    private void formMouseDragged(java.awt.event.MouseEvent evt) {                                  
        java.awt.Point evtLoc = evt.getPoint();

        shiftCoords = new Dimension((int) (evtLoc.x - startCoords.x), (int) (evtLoc.y - startCoords.y));
        Point p = grid.getLocationOfOrigin();
        grid.setLocationOfOrigin(new Point(p.x + shiftCoords.width, p.y + shiftCoords.height));
        startCoords.x = evtLoc.x;
        startCoords.y = evtLoc.y;
        repaint();
    }                                 

    private void formMouseEntered(java.awt.event.MouseEvent evt) {                                  
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        setCursor(cursor);
    }                                 
    boolean reloadGraphics;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if (context!=null || !reloadGraphics) {
            context = new SwingDrawingContext((Graphics2D) g);
            reloadGraphics = true;
        }
        grid.draw(context);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        repaint();
    }

    public void setFunction(String function) {
        this.function = function;
        grid.setFunction(function);
        repaint();
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        grid.setGridSize(gridSize, gridSize);
        repaint();
    }

    public void setShowGridLines(boolean showGridLines) {
        this.showGridLines = showGridLines;
        grid.setShowGridLines(showGridLines);
        repaint();
    }

    public void setLabelAxis(boolean labelAxis) {
        this.labelAxis = labelAxis;
        grid.setLabelAxis(labelAxis);
        repaint();
    }

    public void setGridColor(GraphColor gridColor) {
        this.gridColor = gridColor;
        grid.setGridColor(gridColor);
        repaint();
    }

    public void setMajorAxesColor(GraphColor majorAxesColor) {
        this.majorAxesColor = majorAxesColor;
        grid.setMajorAxesColor(majorAxesColor);
        repaint();
    }

    public void setTickColor(GraphColor tickColor) {
        this.tickColor = tickColor;
        grid.setTickColor(tickColor);
        repaint();
    }

    public void setPlotColor(GraphColor plotColor) {
        this.plotColor = plotColor;
        grid.setPlotColor(plotColor);
        repaint();
    }

    public void setMajorTickLength(int majorTickLength) {
        this.majorTickLength = majorTickLength;
        grid.setMajorTickLength(majorTickLength);
    }

    public void setMinorTickLength(int minorTickLength) {
        this.minorTickLength = minorTickLength;
        grid.setMinorTickLength(minorTickLength);
        repaint();
    }

    public void setLowerXLimit(double lowerXLimit) {
        this.lowerXLimit = lowerXLimit;
        grid.setLowerXLimit(lowerXLimit);
        repaint();
    }

    public void setUpperXLimit(double upperXLimit) {
        this.upperXLimit = upperXLimit;
        grid.setUpperXLimit(upperXLimit);
        repaint();
    }

    public void setxStep(double xStep) {
        this.xStep = xStep;
        grid.setxStep(xStep);
        repaint();
    }

    public void setyStep(double yStep) {
        this.yStep = yStep;
        grid.setyStep(yStep);
        repaint();
    }

    public void setLocationOfOrigin(Point locationOfOrigin) {
        this.locationOfOrigin = locationOfOrigin;
        grid.setLocationOfOrigin(locationOfOrigin);
        repaint();
    }

    public void setLocationOfOrigin(double originX, double originY) {
        this.locationOfOrigin.x = originX;
        this.locationOfOrigin.y = originY;
        setLocationOfOrigin(locationOfOrigin);
        repaint();
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void setFont(java.awt.Font font) {
        super.setFont(font);
        if (context != null) {
            this.font = context.getGraphFont(font);
        }

        Timer t = new Timer(200, (ActionEvent e) -> {
            grid.setFont(GraphPanel.this.font);
            repaint();
        });
        t.setRepeats(false);
        t.start();
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        BufferedImage img = utils.ImageUtilities.createSwingObjectImage(this);
        Graphics2D g2D = (Graphics2D) graphics;
        Dimension d = this.getPreferredSize();
        g2D.drawImage(img, 0, 0, d.width, d.height, this);
        return Printable.PAGE_EXISTS;
    }

                 
}
