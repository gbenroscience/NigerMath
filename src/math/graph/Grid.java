/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math.graph;

import com.github.gbenroscience.parser.*;
import math.graph.util.DrawAdapter;
import static java.lang.Math.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import com.github.gbenroscience.math.Size;
import com.github.gbenroscience.math.matrix.expressParser.Matrix;
import com.github.gbenroscience.math.matrix.expressParser.MatrixValueParser;
import utils.FunctionManager;
import static com.github.gbenroscience.parser.STRING.*;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class Grid {

    /**
     * The function to be plotted. This function can be homogeneous i.e an
     * instruction to plot a single curve or set of points, or heterogeneous i.e
     * instructions to plot a group of curves.
     *
     * For instance, an homogeneous instruction that plots a set of points is
     * [x1,x2,x3,.....:][y1,y2,y3,.......:] The array of numbers...x1,x2.. are a
     * set of x coordinates, and the array of numbers y1,y2....are the
     * corresponding set of y coordinates.
     *
     */
    //private String function;
    /**
     * Object that handles the input expression and scans it into any lesser
     * instructions, determines their types and stores information about all
     * these in one of its attributes.
     */
    private GridExpressionParser gridExpressionParser;

    /**
     * sets whether the grid lines will be visible or not.
     */
    private boolean showGridLines;
    /**
     * sets whether the major axes are labeled with numbers or not
     */
    private boolean labelAxis;

    /**
     * If true,then the object will attempt to draw the graph based on a scale
     * that it generates automatically.
     */
    private boolean autoScaleOn = true;

    /**
     * determine the size of the small boxes that make up the grid.
     */
    private int gridSize;
    /**
     * The color of the grid.
     */
    private Color gridColor;
    /**
     * The color of the major axes, x and y.
     */
    private Color majorAxesColor;
    /**
     * The color of the ticks.
     */
    private Color tickColor;
    /**
     * The color used to generate the plot.
     */
    private Color plotColor;
    /**
     * The upper value of x up to which the graph will be plotted.
     */
    private double upperXLimit;
    /**
     * The lower value of x up to which the graph will be plotted.
     */
    private double lowerXLimit;
    /**
     * The resolution of the graph along x. xStep is the equivalent in
     * calculation of the size (width or height) of each grid box. So if
     * gridSize=2 screen pixels and xStep =0.01. Then a box of length 2 units
     * corresponds to 0.01 units in the user plot along x.
     */
    private double xStep;
    /**
     * The resolution of the graph along y. yStep is the equivalent in
     * calculation of the size (width or height) of each grid box. So if
     * gridSize=2 and yStep =0.01 Then a distance of 2 units on the graph
     * corresponds to 0.01 units in the user plot along y.
     */
    private double yStep;
    /**
     * The length of the longer ticks used to mark off the graph.
     */
    private int majorTickLength;
    /**
     * The maximum number of iterations allowed per plot.
     */
    private static final double MAX_ITERATIONS = 500.0;

    /**
     * The length of the shorter ticks used to mark off the graph
     */
    private int minorTickLength;
    /**
     * The point where the origin of the graph will reside on the screen.
     */
    private Point locationOfOrigin;

    /**
     * The text used to label the horizontal axis. By default , its value is X.
     */
    private Variable horizontalAxisLabel = new Variable("X");
    /**
     * The text used to label the vertical axis. By default , its value is Y.
     */
    private Variable verticalAxisLabel = new Variable("Y");
    /**
     * The Font object used to write on the graph.
     */
    private Font font;
    /**
     * The scale suggested by the programmer. Cane be modified by the user to
     * see more detail.
     */
    private Size defaultScale;
    /**
     * determines the mode in which trig operations will be carried out on
     * numbers.If DRG==0,it is done in degrees if DRG==1, it is done in radians
     * and if it is 2, it is done in grads.By default, it is done in grads.
     */
    private int DRG = 1;
    /**
     * The panel on which this grid will be laid.
     */
    private JComponent component;

    /**
     * @param function The function to be plotted on this Grid object. It may be
     * an algebraic one or a geometric or vertices based one. The algebraic one
     * is entered as y=f(x)..while the geometric one is entered in the form:
     * [2,3,4,5]:[-90,3,15,9]
     * @param showGridLines sets whether the grid lines will be visible or not.
     * @param gridSize determine the size of the small boxes that make up the
     * grid.
     * @param gridColor The color of the grid.
     * @param tickColor The color of the ticks used to mark off the coordinate
     * axes.
     * @param majorTickLength The length of the longer ticks used to mark off
     * the graph.
     * @param minorTickLength The length of the shorter ticks used to mark off
     * the graph
     * @param majorAxesColor The color of the major axes, x and y.
     * @param upperXLimit The upper value of x up to which the graph will be
     * plotted.
     * @param lowerXLimit The lower value of x up to which the graph will be
     * plotted.
     * @param xStep The resolution of the graph along x.
     * @param locationOfOrigin The point where the origin of the graph will
     * reside on the screen.
     * @param The component on which this grid will be laid.
     */
    public Grid(String function, boolean showGridLines, boolean labelAxis,
            int gridSize, Color gridColor, Color majorAxesColor, Color tickColor, Color plotColor,
            int majorTickLength, int minorTickLength,
            double lowerXLimit, double upperXLimit,
            double xStep, double yStep, Font font, JComponent component) {
        /**
         * identifies the kind of input, be it a vertice based plot or an
         * algebraic one. Based on the recommendations of this section, it then
         * sets the graphtype.
         */
        this.component = component;
        this.showGridLines = showGridLines;
        this.labelAxis = labelAxis;
        this.gridSize = gridSize;
        this.gridColor = gridColor;
        this.majorAxesColor = majorAxesColor;
        this.tickColor = tickColor;
        this.plotColor = plotColor;
        setMajorTickLength(majorTickLength);
        setMinorTickLength(minorTickLength);
        setUpperXLimit(upperXLimit);
        setLowerXLimit(lowerXLimit);
        this.xStep = xStep;
        this.yStep = yStep;
        setLocationOfOrigin(new Point(component.getWidth() / 2, component.getHeight() / 2));
        this.font = font;
        //setFunction(function);
    }//end constructor

    /**
     * validates the xstep attribute. It checks if the number of iterations is
     * not greater.
     */
    public void validateMaxIterations() {
        double validateIterations = (upperXLimit - lowerXLimit) / xStep;
        if (validateIterations > MAX_ITERATIONS) {
            xStep = (upperXLimit - lowerXLimit) / MAX_ITERATIONS;
        }
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }

    public JComponent getComponent() {
        return component;
    }


    /*

    public void setFunction(String function) {
        this.function = purifier(function);
        this.gridExpressionParser=new GridExpressionParser(function);
        validateMaxIterations();
        generateAutomaticScale();
    }

    public String getFunction() {
        return function;
    }
     */
    public void setDRG(int DRG) {
        this.DRG = DRG;
    }

    public int getDRG() {
        return DRG;
    }

    public Variable getHorizontalAxisLabel() {
        return horizontalAxisLabel;
    }

    public void setHorizontalAxisLabel(Variable horizontalAxisLabel) {
        this.horizontalAxisLabel = horizontalAxisLabel;
    }

    public Variable getVerticalAxisLabel() {
        return verticalAxisLabel;
    }

    public void setVerticalAxisLabel(Variable verticalAxisLabel) {
        this.verticalAxisLabel = verticalAxisLabel;
    }

    public void setPlotColor(Color plotColor) {
        this.plotColor = plotColor;
    }

    public void setDefaultScale(Size defaultScale) {
        this.defaultScale = defaultScale;
    }

    public boolean isAutoScaleOn() {
        return autoScaleOn;
    }

    public void setAutoScaleOn(boolean autoScaleOn) {
        this.autoScaleOn = autoScaleOn;
    }

    public Size getDefaultScale() {
        return defaultScale;
    }

    public Color getPlotColor() {
        return plotColor;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public GridExpressionParser getGridExpressionParser() {
        return gridExpressionParser;
    }

    public void setGridExpressionParser(GridExpressionParser gridExpressionParser) {
        this.gridExpressionParser = gridExpressionParser;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public static double getMAX_ITERATIONS() {
        return MAX_ITERATIONS;
    }

    public Point getLocationOfOrigin() {
        return locationOfOrigin;
    }

    public void setLocationOfOrigin(Point locationOfOrigin) {
        this.locationOfOrigin = locationOfOrigin;
    }

    public Color getMajorAxesColor() {
        return majorAxesColor;
    }

    public void setMajorAxesColor(Color majorAxesColor) {
        this.majorAxesColor = majorAxesColor;
    }

    public int getMajorTickLength() {
        return majorTickLength;
    }

    public void setMajorTickLength(int majorTickLength) {
        this.majorTickLength = majorTickLength;
    }

    public int getMinorTickLength() {
        return minorTickLength;
    }

    public void setMinorTickLength(int minorTickLength) {
        this.minorTickLength = (minorTickLength < (majorTickLength / 2.0)) ? minorTickLength : majorTickLength / 2;
    }

    public double getLowerXLimit() {
        return lowerXLimit;
    }

    public void setLowerXLimit(double lowerXLimit) {
        this.lowerXLimit = (abs(lowerXLimit) != Double.POSITIVE_INFINITY) ? lowerXLimit : -1 * abs(upperXLimit);
        validateMaxIterations();
    }

    public boolean isShowGridLines() {
        return showGridLines;
    }

    public void setShowGridLines(boolean showGridLines) {
        this.showGridLines = showGridLines;
    }

    public double getUpperXLimit() {
        return upperXLimit;
    }

    public void setUpperXLimit(double upperXLimit) {
        this.upperXLimit = (abs(upperXLimit) != Double.POSITIVE_INFINITY) ? upperXLimit : 1;
        validateMaxIterations();
    }

    public double getxStep() {
        return xStep;
    }

    public void setxStep(double xStep) {
        this.xStep = Math.abs(xStep);
        validateMaxIterations();
    }

    public void setyStep(double yStep) {
        this.yStep = Math.abs(yStep);
    }

    public double getyStep() {
        return yStep;
    }

    public void setLabelAxis(boolean labelAxis) {
        this.labelAxis = labelAxis;
    }

    public boolean isLabelAxis() {
        return labelAxis;
    }

    public void setTickColor(Color tickColor) {
        this.tickColor = tickColor;
    }

    public Color getTickColor() {
        return tickColor;
    }

    /**
     *
     * @param function A command string consisting of functions (geometric and
     * algebraic) to be added to the graph. The functions are separated by a
     * semicolon(;) Calling this method will ensure that the functions contained
     * in the command will be added to the ones already plotted on the graph. An
     * example of the format of function is:
     * [-200,200,300,-200:][1,3,-2,1:];y=@(x)3x+1;y1=@(x)sin(3*x-1)
     */
    public void addFunction(String function) {
        try {
            if (gridExpressionParser != null) {
                GridExpressionParser gep = new GridExpressionParser(function);
                gridExpressionParser.addFunctions(gep);
            }//end if
            else {
                gridExpressionParser = new GridExpressionParser(function);
            }//end else
        }//end try
        catch (NullPointerException exception) {

        }//end catch
    }//end method.

    /**
     *
     * @param function A command string consisting of functions (geometric and
     * algebraic) to be added to the graph. The functions are separated by a
     * semicolon(;) Calling this method will ensure that the functions contained
     * in the command alone will be the functions to be plotted on the graph.
     */
    public void setFunction(String function) {
        try {
            if (gridExpressionParser != null) {
                gridExpressionParser.setInput(function);
            }//end if
            else {
                gridExpressionParser = new GridExpressionParser(function);
            }//end else
        }//end try
        catch (NullPointerException exception) {

        }//end catch
    }

    /**
     * Checks iteratively if or not a value overshoot will occur while plotting
     * the graph.
     *
     * @param function The function string to be plotted.
     */
    public void validateUpperXLimit(MathExpression fun) {
        fun.setValue(horizontalAxisLabel.getName(), String.valueOf(upperXLimit));
        double y = Double.valueOf(fun.solve());
        while (abs(y) == Double.POSITIVE_INFINITY) {
            fun.setValue(horizontalAxisLabel.getName(), String.valueOf(upperXLimit));
            y = Double.valueOf(fun.solve());
            upperXLimit /= 10.0;
        }
    }

    /**
     * Draws the 2 major axes, the x and the y and labels them.
     *
     * @param g The Graphics object used to draw.
     */
    public void drawMajorAxes(Graphics g) {
        Point compLoc = component.getLocation();
        Dimension compSize = component.getSize();
        int xLocOfRightSideOfComponent = compLoc.x + compSize.width;
        int yLocOfBottomSideOfComponent = compLoc.y + compSize.height;

        int xLocOfLeftSideOfComponent = compLoc.x;
        int yLocOfTopSideOfComponent = compLoc.y;
        g.setFont(font);
        DrawAdapter art = new DrawAdapter();
        drawXAxes:
        {
            g.setColor(majorAxesColor);
            g.drawLine(0, locationOfOrigin.y, xLocOfRightSideOfComponent, locationOfOrigin.y);
            g.drawLine(0, locationOfOrigin.y + 1, xLocOfRightSideOfComponent, locationOfOrigin.y + 1);//make the line thick

            drawTicksOnXaxis:
            {
                int x = locationOfOrigin.x;

                int countTicks = 0;

//draw to the right of the y axis
                while (x < xLocOfRightSideOfComponent) {

                    //indicate the name of the horizontal axis.
                    if (labelAxis) {
                        g.drawString(horizontalAxisLabel.getName() + "→", compSize.width - 80, locationOfOrigin.y + majorTickLength + 40);
                    }

//draw the major tick lengths
                    if (countTicks == 0) {
                        g.setColor(tickColor);
                        g.drawLine(x, locationOfOrigin.y, x, locationOfOrigin.y + majorTickLength);

                        String numLabel = String.valueOf(convertScreenPointToGraphPoint(new Point(x, locationOfOrigin.y)).x);
                        if (!numLabel.equals("0.0")) {
                            if (labelAxis) {
                                g.drawString(numLabel, x, locationOfOrigin.y + majorTickLength + 10);
                            }
                        }
                    }//end if
                    else {
                        g.setColor(tickColor);
                        g.drawLine(x, locationOfOrigin.y, x, locationOfOrigin.y + minorTickLength);
                    }//end if
                    x += (gridSize * 2);
                    ++countTicks;
                    if (countTicks == 5) {
                        countTicks = 0;
                    }
                }//end while

                countTicks = 0;
                x = locationOfOrigin.x;

//now draw to the left of the y axis
                while (x + compLoc.x > xLocOfLeftSideOfComponent) {
//draw the major tick lengths
                    if (countTicks == 0) {
                        g.setColor(tickColor);
                        g.drawLine(x, locationOfOrigin.y, x, locationOfOrigin.y + majorTickLength);
                        String numLabel = String.valueOf(convertScreenPointToGraphPoint(new Point(x, locationOfOrigin.y)).x);
                        if (!numLabel.equals("0.0")) {
                            if (labelAxis) {
                                g.drawString(numLabel, x, locationOfOrigin.y + majorTickLength + 10);
                            }
                        }
                    }//end if
                    else {
                        g.setColor(tickColor);
                        g.drawLine(x, locationOfOrigin.y, x, locationOfOrigin.y + minorTickLength);
                    }//end if
                    x -= (gridSize * 2);
                    ++countTicks;
                    if (countTicks == 5) {
                        countTicks = 0;
                    }
                }//end while

            }//end inner label

        }//end outer label

        drawYAxes:
        {

            g.setColor(majorAxesColor);
            g.drawLine(locationOfOrigin.x, 0, locationOfOrigin.x, yLocOfBottomSideOfComponent);
            g.drawLine(locationOfOrigin.x + 1, 0, locationOfOrigin.x + 1, yLocOfBottomSideOfComponent);//make the line thick

            int y = locationOfOrigin.y + compLoc.y;

            int countTicks = 0;
//draw to the right of the y axis
            while (y > yLocOfTopSideOfComponent) {
                //label the vertical axis.
                if (labelAxis) {
                    g.drawString(verticalAxisLabel.getName() + "↑", locationOfOrigin.x + majorTickLength + 50, yLocOfTopSideOfComponent + 50);
                }

//draw the major tick lengths
                if (countTicks == 0) {
                    g.setColor(tickColor);
                    g.drawLine(locationOfOrigin.x, y, locationOfOrigin.x + majorTickLength, y);
                    String numLabel = String.valueOf(convertScreenPointToGraphPoint(new Point(locationOfOrigin.x, y)).y);
                    if (!numLabel.equals("0.0")) {
                        if (labelAxis) {
                            g.drawString(numLabel, locationOfOrigin.x + majorTickLength + 10, y);//thicken the ticks
                        }
                    }
                }//end if
                else {
                    g.setColor(tickColor);
                    g.drawLine(locationOfOrigin.x, y, locationOfOrigin.x + minorTickLength, y);
                }//end if
                y -= (gridSize * 2);
                ++countTicks;
                if (countTicks == 5) {
                    countTicks = 0;
                }
            }//end while

            countTicks = 0;
            y = locationOfOrigin.y + compLoc.y;
//now draw to the left of the y axis
            while (y < yLocOfBottomSideOfComponent) {

//draw the major tick lengths
                if (countTicks == 0) {
                    g.setColor(tickColor);
                    g.drawLine(locationOfOrigin.x, y, locationOfOrigin.x + majorTickLength, y);
                    String numLabel = String.valueOf(convertScreenPointToGraphPoint(new Point(locationOfOrigin.x, y)).y);
                    if (!numLabel.equals("0.0") && labelAxis) {
                        g.drawString(numLabel, locationOfOrigin.x + majorTickLength + 10, y);
                    }
                }//end if
                else {
                    g.setColor(tickColor);
                    g.drawLine(locationOfOrigin.x, y, locationOfOrigin.x + minorTickLength, y);

                }//end if
                y += (gridSize * 2);
                ++countTicks;
                if (countTicks == 5) {
                    countTicks = 0;
                }

            }//end while

        }
        art.fillOval(g, locationOfOrigin.x, locationOfOrigin.y, gridSize, gridSize + 3);
    }//end method drawMajorAxes

    /**
     * draws the grid
     *
     * @param g the Graphics object used to draw.
     */
    public void draw(Graphics g) {
        try {
            g.setFont(font);
            //Point compLoc=component.getLocation();
            //setLocationOfOrigin( new Point(locationOfOrigin.x,locationOfOrigin.y)   );
            DrawAdapter art = new DrawAdapter();

            if (isShowGridLines()) {

                drawHorizontalLines:
                {

                    g.setColor(gridColor);
                    int y = 0;

                    while (y < component.getHeight()) {
                        g.drawLine(0, y, component.getWidth(), y);
                        y += gridSize;
                    }
                }

                drawVerticalLines:
                {
                    int x = 0;
                    while (x < component.getWidth()) {
                        g.drawLine(x, 0, x, component.getHeight());
                        x += gridSize;
                    }
                }

            }

            art.fillOval(g, locationOfOrigin.x, locationOfOrigin.y, gridSize, gridSize);
            drawMajorAxes(g);
            plot(g);
        }//end try
        catch (NumberFormatException numErr) {
            gridExpressionParser.setCanPlot(false);
        } catch (NullPointerException nullErr) {
            nullErr.printStackTrace();
        }
    }//end method

    /**
     *
     * @param gridDistance The horizontal distance along the grid.
     * @return the equivalent horizontal distance in user terms.
     */
    public double convertGridSizeToUserDistanceAlongX(int gridDistance) {
        return gridDistance * xStep / ((double) gridSize);
    }

    /**
     *
     * @param gridDistance The vertical distance along the grid.
     * @return the vertical distance in user terms.
     */
    public double convertGridSizeToUserDistanceAlongY(int gridDistance) {
        return gridDistance * yStep / ((double) gridSize);
    }

    /**
     *
     * @param userX The horizontal distance along the grid in user terms.
     * @return the equivalent horizontal distance in screen terms.
     */
    public long convertUserDistanceAlongX_ToGridSize(double userX) {
        String value = String.valueOf(round((userX * gridSize) / (xStep)));
        int startIndex = value.length();
        try {
            if (value.substring(startIndex - 2).equals(".0")) {
                value = delete(value, startIndex - 2, startIndex);
            }
        }//end try
        catch (IndexOutOfBoundsException indexErr) {

        }
        return Long.valueOf(value);
    }

    /**
     *
     * @param userY The vertical distance along the grid in user terms.
     * @return the equivalent vertical distance in screen terms .
     */
    public long convertUserDistanceAlongY_ToGridSize(double userY) {
        String value = String.valueOf(round((userY * gridSize) / (yStep)));
        int startIndex = value.length();
        try {
            if (value.substring(startIndex - 2).equals(".0")) {
                value = delete(value, startIndex - 2, startIndex);
            }
        }//end try
        catch (IndexOutOfBoundsException indexErr) {

        }

        return Long.valueOf(value);
    }

    /**
     * Converts a point on the screen to its equivalent point in mathematics
     * relative to the specified origin. It is useful basically in labeling the
     * axes during its construction.
     *
     * @param screenPoint The point on the screen.
     * @return the point on the graph.
     */
    public com.github.gbenroscience.math.Point convertScreenPointToGraphPoint(Point screenPoint) {
        double xGraph = convertGridSizeToUserDistanceAlongX(screenPoint.x - locationOfOrigin.x);
        double yGraph = convertGridSizeToUserDistanceAlongY(locationOfOrigin.y - screenPoint.y);
        return new com.github.gbenroscience.math.Point(xGraph, yGraph);
    }

    /**
     * Say the user is about to identify plot Point p = [2,4] on the screen, He
     * passes it in to the draw method as convertGraphPointToScreenPoint(p). and
     * gets his plot.This method takes care of all conversions from the math
     * coordinates to the screen coordinates.
     *
     * @param userPoint The point on the graph to be drawn on the screen.
     * @return the screen equivalent of the point.
     */
    public Point convertGraphPointToScreenPoint(com.github.gbenroscience.math.Point userPoint) {
        double xScreen = locationOfOrigin.x + convertUserDistanceAlongX_ToGridSize(userPoint.x);
        double yScreen = locationOfOrigin.y - convertUserDistanceAlongY_ToGridSize(userPoint.y);//in contrast to screen coordinates math coordinates go up as y increases.
        return new Point((int) round(xScreen), (int) round(yScreen));
    }

    /**
     *
     * @param g The Graphics object used to draw.
     * @param function The function string representing the assignment of an
     * anonymous function to a named function. e.g F=@(x)sqrt(sin(x))
     */
    private void plotFunction(Graphics g, String function) {
        String name = "";//y=@(x)cos(3*x)
//Used to check if the expression stored in an incoming function has changed.
        String comparator = function.substring(function.indexOf("@") + 1);//remove the dependentvariable = @ part of the expression
        int endbracketOfAtOperator = Bracket.getComplementIndex(true, 0, comparator);//get the index of the closing bracket of the parameter list.
        comparator = comparator.substring(endbracketOfAtOperator + 1);//removes the parameter list from the expression.
//remove any bracket pair that may be enclosing the raw expression..e.g. (((sin(x-1)))..should remain sin(x-1 after this stage)
        while (comparator.startsWith("(") && comparator.endsWith(")")) {
            comparator = comparator.substring(1);
            comparator = comparator.substring(0, comparator.length() - 1);
            comparator = comparator.trim();
        }//end while
//Now enclose the raw expression in brackets!
        comparator = "(".concat(comparator.concat(")")).trim();
        int index = function.indexOf("=");
        name = function.substring(0, index).trim();
        Function f = null;
        try {
            f = FunctionManager.lookUp(name);
        }//end try
        catch (NullPointerException exception) {

        }
//Function exists
        if (f != null) {
//same math expression is stored by the function, so the function is optimizable.
            if (f.expressionForm().equals(comparator)) {

            }//end if
            //the function now contains a different math expression.
            else {
                FunctionManager.update(function);
                f = FunctionManager.lookUp(name);
            }//end else

        }//end if
        //Function does not exist
        else {
            f = new Function(function);
            FunctionManager.add(f);
        }

        if (f.numberOfParameters() != 1) {
            JOptionPane.showMessageDialog(null, "Function must have only one independent variable! \'=\' symbol not found");
            FunctionManager.delete(f.getDependentVariable().getName());
            return;
        }//end if
        else {
            setVerticalAxisLabel(new Variable(f.getDependentVariable().getName()));
            setHorizontalAxisLabel(new Variable(f.getIndependentVariables().get(0).getName()));

            MathExpression func = f.getMathExpression();
            func.setDRG(DRG);
            validateUpperXLimit(func);
            for (double x = lowerXLimit; x < upperXLimit; x += xStep) {
                try {
                    func.setValue(horizontalAxisLabel.getName(), String.valueOf(x));
                    double y = Double.parseDouble(func.solve());
                    double x1 = (x + xStep);
                    func.setValue(horizontalAxisLabel.getName(), String.valueOf(x1));

                    double y1 = Double.parseDouble(func.solve());
                    com.github.gbenroscience.math.Point graphPoint = new com.github.gbenroscience.math.Point(x, y);
                    com.github.gbenroscience.math.Point graphPoint1 = new com.github.gbenroscience.math.Point(x1, y1);
                    if (abs(y) != Double.POSITIVE_INFINITY && abs(y1) != Double.POSITIVE_INFINITY) {
                        Point screenPoint = convertGraphPointToScreenPoint(graphPoint);

                        Point screenPoint1 = convertGraphPointToScreenPoint(graphPoint1);
                        g.setColor(plotColor);
                        g.drawLine(screenPoint.x, screenPoint.y, screenPoint1.x, screenPoint1.y);
                    }//end if
                }//end try
                catch (NumberFormatException numErr) {
                    gridExpressionParser.setCanPlot(false);
                    break;
                } catch (ArithmeticException aritErr) {
                    gridExpressionParser.setCanPlot(false);
                    break;
                }

            }//end loop

        }//end else

    }//end method

    /**
     * Checks the expression of a function and certifies it to be 2D and
     * non-implicit, so that it may be plotted.
     *
     * Method that plots the graph of a function of a set of points depending on
     * the state of graph-type.
     *
     */
    private void plotVertices(Graphics g, Matrix horMatrix, Matrix verMatrix) {

        double[][] xArray = horMatrix.getArray();
        double[][] yArray = verMatrix.getArray();
        for (int i = 0; i < xArray[0].length; i++) {
            try {
                double x = xArray[0][i];

                double y = yArray[0][i];

                double x1 = xArray[0][i + 1];

                double y1 = yArray[0][i + 1];

                com.github.gbenroscience.math.Point graphPoint = new com.github.gbenroscience.math.Point(x, y);
                com.github.gbenroscience.math.Point graphPoint1 = new com.github.gbenroscience.math.Point(x1, y1);

                Point screenPoint = convertGraphPointToScreenPoint(graphPoint);

                Point screenPoint1 = convertGraphPointToScreenPoint(graphPoint1);
                g.setColor(plotColor);
                g.drawLine(screenPoint.x, screenPoint.y, screenPoint1.x, screenPoint1.y);

            }//end try
            catch (NumberFormatException numErr) {

            } catch (ArithmeticException aritErr) {

            } catch (IndexOutOfBoundsException indexErr) {

            }
        }//end loop

    }//end method

    /**
     * Executes the plot instructions entered.
     *
     * @param g The Graphics object used to draw.
     */
    private void plot(Graphics g) {

        ArrayList<String> graphs = gridExpressionParser.getPlottable();
        ArrayList<GraphType> graphTypes = gridExpressionParser.getGraphType();

        for (int i = 0; i < graphs.size(); i++) {
            try {
                if (graphTypes.get(i).isFunctionPlot()) {
                    plotFunction(g, graphs.get(i));
                } else if (graphTypes.get(i).isVerticePlot()) {
                    String getInstruction = graphs.get(i);
                    String horizontalCoords = getInstruction.substring(0, getInstruction.indexOf("]") + 1);
                    String verticalCoords = getInstruction.substring(getInstruction.indexOf("]") + 1);
                    Matrix horMatrix = new MatrixValueParser(horizontalCoords).getMatrix();
                    Matrix verMatrix = new MatrixValueParser(verticalCoords).getMatrix();
                    plotVertices(g, horMatrix, verMatrix);
                }
            }//end try
            catch (NullPointerException exception) {

            } catch (InputMismatchException exception) {

            }

        }//end for

    }//end method

    /**
     * Generates automatically the numeric drawing parameters for the function.
     *
     *
     */
    public void generateAutomaticScale() {
        if (isAutoScaleOn()) {
            setxStep(0.1);
            setyStep(0.1);
        }//end if isAutoscaleOn

    }//end method

}//end class
