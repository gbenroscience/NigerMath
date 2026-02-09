/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commandinterface;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class Caret {

    /**
     * tracks when the enter key is pressed.
     */
    private boolean enterKeyPressed;

    /**
     * The color of this object
     */
    private Color color;
// alternative image......│
    /**
     * The line on the parent Document object that this object is on. The row is
     * zero based.
     */
    private int row;
    /**
     * The column on the parent Document object that this object is on.
     *
     * The number of columns can differ per row. The number of columns depends
     * on the number of characters entered on the row or line. The column is
     * zero based.
     *
     */
    private int column;

    /**
     * The rate at which the Thread blinks on the parent Document object.
     */
    private int blinkRate;
    /**
     * If true then this object will be rendered.
     */
    private boolean visible;


    private CaretState state;

    public Caret() {
        this.color = Color.blue;
        this.blinkRate = 500;
        this.visible = true;
        this.row = 0;
        this.column = 0;
        this.state = CaretState.INITIAL_TYPING;
        
    }

    /**
     *
     * @param document
     * @param g The Graphics object used to draw.
     */
    public void draw(Document document, Graphics g) {
        if (isVisible()) {
            g.setColor(color); 
            Point location = getLocation(document);
            drawCaret:
            {
                Rectangle r = new Rectangle(location.x, location.y, 14, 4);
                g.fill3DRect(r.x, r.y, r.width, r.height, true);
            }

        }//end if
    }//end method draw

     

    /**
     *
     * @return the column attribute.
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @param column The column to set the attribute to.
     * <b color = 'red'>
     * Please call this method only when you are sure that you want to set the
     * column attribute to the parameter specified. No security checkings for
     * the value are allowed for the row attribute here.
     *
     * If you are not so sure, then call the setPosition(row,col) method
     * instead. It supplies data checking and all.
     * </b>
     */
    public void setColumn(int column) {
        this.column = column;
    }//end method

    public int getRow() {
        return row;
    }

    /**
     *
     * @param row The column to set the attribute to.
     * <b color = 'red'>
     * Please call this method only when you are sure that you want to set the
     * row attribute to the parameter specified. No security checkings for the
     * value are allowed for the row attribute here.
     *
     * If you are not so sure, then call the setPosition(row,col) method
     * instead. It supplies data checking and all.
     * </b>
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @param d
     * @param row The row that the position will lie on.
     * @param col The column that the position will lie on.
     */
    public void setPosition(Document d, int row, int col) {
        int row$Col[] = d.normalizePosition(row, col);
        setRow(row$Col[0]);
        setColumn(row$Col[1]);
    }

    /**
     * Sets the row and the column of this Caret object using a knowledge of the
     * index that we wish to move it to in the parent Document object's text.
     * @param d 
     * @param index The index of the position in the parent Document object's
     * text.
     */
    public void setPosition(Document d, int index) {
        int[] row$Col = d.indexToRow$Column(index);
        setPosition(d, row$Col[0], row$Col[1]);
    }

    public CaretState getState() {
        return state;
    }

    public void setState(CaretState state) {
        this.state = state;
    }

    public void setEnterKeyPressed(boolean enterKeyPressed) {
        this.enterKeyPressed = enterKeyPressed;
    }

    public boolean isEnterKeyPressed() {
        return enterKeyPressed;
    }

    /**
     *
     * @return the Point object that represents the current location that
     * corresponds to the row and column attributes.
     */
    public Point getLocation(Document d) {
        return d.convertRow$ColumnToPoint(row, column);
    }

    /**
     *
     * @param document
     * @return The character at the point where the caret currently is.
     */
    public String getChar(Document document) {
        int index = getIndex(document);
        return document.getText().substring(index, index + 1);
    }

    /**
     * @param p The Point at which we wish to determine the occupying character.
     * @return The character at any Point p in the parent Document of this
     * Caret.
     */
    public String getCharAt(Document document, Point p) {
        int row$Col[] =  convertLocationToRow$Column(document, p);
        return document.getScanner().get(row$Col[0]).substring(row$Col[1], row$Col[1] + 1);
    }

    /**
     * @param index The index of the character in the parent Document's text.
     * @return The character at the specified index in the text of the parent
     * Document of this Caret.
     */
    public String getCharAt(Document document, int index) {
        return document.getText().substring(index, index + 1);
    }

    /**
     * @param index The index of the character in the parent Document's text.
     * @return The character at the given row and column in the parent Document
     * of this Caret.
     */
    public String getCharAt(Document document, int row, int column) {
        return document.getScanner().get(row).substring(column, column + 1);
    }

    /**
     *
     * @param p The Point object to convert to row and column values.
     */
    public int[] convertLocationToRow$Column(Document document, Point p) {
        String text = document.getText();
        int len = text.length();

// Check for the one that contains the point and return its
//row and column as the one that contains the point
        for (int i = 0; i < len; i++) {
            Rectangle r = document.getCharRectangle(i);
            if (r.contains(p)) {
                return document.indexToRow$Column(i);
            }
        }//end for loop


        /*
 * If the above fails....
 * Step 1:
 * Find the distances of all the locations of
 * the characters from the point and store them
 * in array dx.
 * Step 2
 * Then find the vertical displacements of
 * the characters from the point and store them
 * in array dy.
 *
 *
         */
        double dx[] = new double[len];

        for (int i = 0; i < len; i++) {
            Point pt = document.getCharLocation(i);
            dx[i] = Math.abs(pt.x - p.x);
        }//end for loop

        /*
 * If the above fails....
 * Find the vertical displacements of
 * the characters from the point.
 * The one that has the least distance from the point
 * should assign its row and column to the point.
 *
         */
        double dy[] = new double[len];

        for (int i = 0; i < len; i++) {
            Point pt = document.getCharLocation(i);
            dy[i] = Math.abs(pt.y - p.y);
        }//end for loop

        try {

            double shortestX = dx[0];
            double shortestY = dy[0];

            int indX = 0;
            int indY = 0;

            for (int i = 1; i < len; i++) {
                if (shortestX > dx[i]) {
                    shortestX = dx[i];
                    indX = i;
                }//end if
                if (shortestY > dy[i]) {
                    shortestY = dy[i];
                    indY = i;
                }//end if
            }//end for loop

            int arrX[] = document.indexToRow$Column(indX);
            int arrY[] = document.indexToRow$Column(indY);

            int nearestRow = arrY[0];
            int nearestCol = arrX[1];

            return new int[]{nearestRow, nearestCol};
        }//end try
        catch (IndexOutOfBoundsException boundsException) {
            return document.indexToRow$Column(0);
        }

    }//end method

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getBlinkRate() {
        return blinkRate;
    }

    public void setBlinkRate(int blinkRate) {
        this.blinkRate = blinkRate;
    }

   


    

    /**
     *
     * @return an int array containing two int values. In the first index is the
     * row of this Caret object. In the second index is the column of this Caret
     * object.
     *
     */
    public int[] getPosition() {
        return new int[]{row, column};
    }

    /**
     *
     * @param row The row in consideration
     * @return the number of characters on the row.
     */
    /**
     * A simple number denoting the index of the character in the Document
     * object that this Caret object is on.
     * @param document
     */
    public int getIndex(Document document) {
        if (document.getText().isEmpty()) {
            return -1;
        }//end if
        else {
            int[] row$Col = document.normalizePosition(row, column);
            return document.row$ColumnToIndex(row$Col[0], row$Col[1]);
        }//en//end else
    }//end method

    public int numOfItemsOnRow(Document document, int row) {
        return document.getScanner().get(row).length();
    }

    /**
     *
     * @param start The index to start copying from.
     * @param end The index to stop copying from.
     * @return a character string consisting of all characters found between
     * start and end-1 in the document object.
     */
    public String copyData(Document document, int start, int end) {
        return document.getText().substring(start, end);
    }

    /**
     *
     * @param start The index to start copying from. It will stop copying at the
     * index or position of this caret object.
     * @return a character string consisting of all characters found between
     * start and end-1 in the document object.
     */
    public String copyData(Document document, int start) {
        return document.getText().substring(start, getIndex(document));
    }//end method

 

    /**
     *
     * This method assumes that a row exists above the Caret object that it can
     * jump to.
     *
     * @param line Jumps to the line above. If the point exactly above this
     * Caret object is a valid Point on the line above it, it occupies that
     * Point, else it jumps to the closest Point object to it on the line.
     */
    public void moveUp(Document document) {
        setPosition(document, row - 1, column);
    }//end method

    /**
     * This method assumes that a row exists above the Caret object that it can
     * jump to.
     *
     * @param col The column to jump to on the row above the current one.
     */
    public void moveUp(Document document, int col) {
        setPosition(document, row - 1, col);
    }//end method

    /**
     * This method assumes that a row exists beneath the Caret object that it
     * can jump to.
     *
     * @param line Jumps to the line below. If the point exactly below this
     * Caret object is a valid Point on the line below it, it occupies that
     * Point, else it jumps to the closest Point object to it on the line.
     */
    public void moveDown(Document document ) {
        setPosition(document, row + 1, 0);
    }//end method

    /**
     * This method assumes that a row exists beneath the Caret object that it
     * can jump to.
     *
     * @param col The column to jump to on the row below the current one
     */
    public void moveDown(Document document, int col) {
        setPosition(document, row + 1, col);
    }

    /**
     * Jumps to the top of the page.
     */
    public void gotoTopOfPage(Document document) {
        setPosition(document, 0, 0);
    }

    /**
     * Jumps to the bottom of the page.
     */
    public void gotoBottomOfPage(Document document) {
        ArrayList<String> scan = document.getScanner();
        int lastRow = scan.size() - 1;
        int lastCol = scan.get(lastRow).length() - 1;
        setPosition(document, lastRow, lastCol);
    }

    /**
     *
     *
     * @param line Moves this object backwards by a step on the parent Document
     * object.
     */
    public void shiftBackwards(Document document) {
        setPosition(document, row, column - 1);
    }//end method

    /**
     * @param line Moves this object backwards by a step on the parent Document
     * object.
     */
    public void shiftBackwards(Document document, int step) {
        setPosition(document, row, column - step);
    }//end method

    /**
     * Moves this object backwards by a step size of 1 on the parent Document
     * object.
     */
    public void shiftForwards(Document document) {
        setPosition(document,row, column + 1);
    }//end method

    /**
     * @param step Moves this object backwards by the step size on the parent
     * Document object.
     */
    public void shiftForwards(Document document, int step) {
        setPosition(document, row, column + step);
    }//end method

    /**
     *
     * The backSpace action
     */
    public void delete(Document d) {
        getCurrent(d).handleBackSpaceKey(d);
    }//end method

    /**
     * Removes all characters between startIndex and endIndex.. both of them
     * inclusive.
     *
     * @param index The index from which data is to be deleted.
     */
    public void delete(Document document, int endIndex) {
        int startIndex = getIndex(document);

//ensure that the startIndex is always the lesser.
        if (startIndex > endIndex) {
            int swap = endIndex;
            endIndex = startIndex;
            startIndex = swap;
        }//end if

        for (int i = endIndex; i >= startIndex; i--) {
            delete(document);
        }//end for loop
    }//end method

    /**
     * Splits a <b color ='red'>row</b>
     * on the position of the caret.
     *
     * @return an array of 2 elements containing in the first index, all of the
     * text on the row before the caret position and in the second index, all of
     * the text on the row after the caret position.
     */
    private String[] splitRowTextOnCaretPosition(Document document) {

        String rowText = "";

        try {
            rowText = document.getScanner().get(row);
        }//end try
        catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Cannot Split Empty Row");
            return new String[]{"", ""};
        }//Empty row

        String textB4Caret = "";
        String textAfterCaret = "";

        int len = rowText.length();
        if (column < len) {
            textB4Caret = rowText.substring(0, column + 1);
            textAfterCaret = rowText.substring(column + 1);
            return new String[]{textB4Caret, textAfterCaret};
        }//end if
        else if (column >= len) {
            textB4Caret = rowText;
            textAfterCaret = "";
            setPosition(document, row, len - 1);
        }//end else if

        return new String[]{textB4Caret, textAfterCaret};

    }//end method

    /**
     * Splits the <b color ='red'> parent Document object's text </b>
     * on the position of the caret.
     *
     * @return an array of 2 elements containing in the first index, all of the
     * text in the parent Document before the caret position and in the second
     * index,all of the text in the parent Document after the caret position.
     */
    private String[] splitDocumentTextOnCaretPosition(Document document) {
        String allText = document.getText();
        int index = getIndex(document);
        String textB4Caret = "";
        String textAfterCaret = "";

        try {
            textB4Caret = allText.substring(0, index + 1);
            textAfterCaret = allText.substring(index + 1);
        }//end try
        catch (IndexOutOfBoundsException boundsException) {
            textB4Caret = allText;
            textAfterCaret = "";
        }
        return new String[]{textB4Caret, textAfterCaret};
    }

    public void print(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    /**
     *
     * Run this code whenever the user presses the enter key.
     *
     */
    public void handleEnterKey(Document document) {
        getCurrent(document).handleEnterKey(document);
    }//end method

    /**
     *
     * @param document
     * @return the Sentence in which this Caret resides currently.
     * @throws NullPointerException if the Caret's position does not contain any
     * Sentence object which could happen if proper initialization of the
     * Document is not carried out.
     */
    public Sentence getCurrent(Document document) throws NullPointerException {
        ArrayList<Sentence> sentences = document.getSentences();

        //  System.out.println(sentences.isEmpty());
        if (sentences.isEmpty()) {
            document.appendSentence();
        }
        /**
         * Record the position of the Caret in the parent Document object.
         */
        int index = getIndex(document);
        if (index < 0) {
            index = 0;
            return sentences.get(0);
        }

        int sz = sentences.size();
        for (int i = 0; i < sz; i++) {
            if (sentences.get(i).contains(document, index)) {
                return sentences.get(i);
            }//end if
        }//end for loop
        return null;
    }

    /**
     *
     * @param text The text to write into the parent Document object.
     */
    public void write(Document document, String text) {
        getCurrent(document).write(document, text);
    }//end write method


    @Override
    public String toString() {

        String caretData = "row = " + row + ", column = " + column + ", color = " + color;

        return caretData + "\n" + super.toString();
    }

}//end class

enum CaretState {

    INITIAL_TYPING, INSERTING, DELETING, ENTERING, HIGHLIGHTING, COPYING, PASTING;

    /**
     *
     * @return true if the Caret is being used to type in the parent Document
     * object without any click being performed yet to insert text somewhere
     * else in the document outside the basic flow of text input.
     */
    public boolean initialTyping() {
        return this == INITIAL_TYPING;
    }

    /**
     *
     * @return true if the Caret has being clicked somewhere in the body of the
     * Document in order to insert text in that location.
     */
    public boolean isInserting() {
        return this == INSERTING;
    }

    /**
     *
     * @return true if the Caret is deleting items from the Document.
     */
    public boolean isDeleting() {
        return this == DELETING;
    }

    /**
     *
     * @return true if the Enter Key is pressed.
     */
    public boolean isEntering() {
        return this == ENTERING;
    }

    /**
     *
     * @return true if the Caret is being used to highlight text.
     */
    public boolean isHighlighting() {
        return this == HIGHLIGHTING;
    }

    /**
     *
     * @return true if the Caret has been told to copy text
     */
    public boolean isCopying() {
        return this == COPYING;
    }

    /**
     *
     * @return true if the Caret has been told to paste text.
     */
    public boolean isPasting() {
        return this == PASTING;
    }
}//end enum CaretState
