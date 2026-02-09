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

    private final Document document;
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

    public Caret(Document document) {
        this.document = document;
        this.color = Color.blue;
        this.blinkRate = 100;
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
    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(color);
            Point location = getLocation();
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
     * @param row The row that the position will lie on.
     * @param col The column that the position will lie on.
     */
    public void setPosition(int row, int col) {
        int[] rowCol = document.normalizePosition(row, col);
        this.row = rowCol[0];
        this.column = rowCol[1];
    }

    /**
     * Sets the row and the column of this Caret object using a knowledge of the
     * index that we wish to move it to in the parent Document object's text.
     *
     * @param index The index of the position in the parent Document object's
     * text.
     */
    public void setPosition(int index) {
        int[] row$Col = document.indexToRow$Column(index);
        setPosition(row$Col[0], row$Col[1]);
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
    public Point getLocation() {
        return document.convertRow$ColumnToPoint(row, column);
    }

    /**
     *
     * @return The character at the point where the caret currently is.
     */
    public String getChar() {
        int index = getIndex();
        return document.getText().substring(index, index + 1);
    }

    /**
     * @param p The Point at which we wish to determine the occupying character.
     * @return The character at any Point p in the parent Document of this
     * Caret.
     */
    public char getCharAt(Point p) {
        int row$Col[] = convertLocationToRow$Column(p);
        return document.getScanner().get(row$Col[0]).charAt(row$Col[1]);
    }

    /**
     * @param index The index of the character in the parent Document's text.
     * @return The character at the specified index in the text of the parent
     * Document of this Caret.
     */
    public char getCharAt(int index) {
        return document.getText().charAt(index);
    }

    /**
     * @param row The row of the character in the parent Document's text.
     * @param column The column the character occupies in the parent Document's
     * text
     * @return The character at the given row and column in the parent Document
     * of this Caret.
     */
    public char getCharAt(int row, int column) {
        return document.getScanner().get(row).charAt(column);
    }

    /**
     *
     * @param p The Point object to convert to row and column values.
     */
    public int[] convertLocationToRow$Column(Point p) {
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
     *
     * @param document
     */
    public int getIndex() {
        // Handle empty text
        if (document.getText().isEmpty()) {
            return 0;
        }

        // Normalize row/col first (handles spill-over and kick-back)
        int[] rowCol = document.normalizePosition(row, column);

        // Convert normalized row/col to absolute index
        return document.row$ColumnToIndex(rowCol[0], rowCol[1]);
    }

    public int numOfItemsOnRow(int row) {
        return document.getScanner().get(row).length();
    }

    /**
     *
     * @param start The index to start copying from.
     * @param end The index to stop copying from.
     * @return a character string consisting of all characters found between
     * start and end-1 in the document object.
     */
    public String copyData(int start, int end) {
        return document.getText().substring(start, end);
    }

    /**
     *
     * @param start The index to start copying from. It will stop copying at the
     * index or position of this caret object.
     * @return a character string consisting of all characters found between
     * start and end-1 in the document object.
     */
    public String copyData(int start) {
        return document.getText().substring(start, getIndex());
    }//end method

    private int[] clampPosition(int row, int col) {
        ArrayList<String> scan = document.getScanner();

        // Handle empty document
        if (scan.isEmpty()) {
            return new int[]{0, 0};
        }

        // Clamp row
        if (row < 0) {
            row = 0;
        }
        if (row >= scan.size()) {
            row = scan.size() - 1;
        }

        // Clamp column
        int rowLen = scan.get(row).length();
        if (col < 0) {
            col = 0;
        }
        if (col >= rowLen) {
            col = rowLen - 1;
        }

        return new int[]{row, col};
    }

    /**
     *
     * This method assumes that a row exists above the Caret object that it can
     * jump to.
     *
     * Jumps to the line above. If the point exactly above this Caret object is
     * a valid Point on the line above it, it occupies that Point, else it jumps
     * to the closest Point object to it on the line.
     */
    public void moveUp() {
        int newRow = row - 1;
        int newCol = column;

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * This method assumes that a row exists above the Caret object that it can
     * jump to.
     *
     * @param col The column to jump to on the row above the current one.
     */
    public void moveUp(int col) {
        int newRow = row - 1;
        int newCol = col;

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * This method assumes that a row exists beneath the Caret object that it
     * can jump to.
     *
     * Jumps to the line below. If the point exactly below this Caret object is
     * a valid Point on the line below it, it occupies that Point, else it jumps
     * to the closest Point object to it on the line.
     */
    public void moveDown() {
        int newRow = row + 1;
        int newCol = column;

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * This method assumes that a row exists beneath the Caret object that it
     * can jump to.
     *
     * @param col The column to jump to on the row below the current one
     */
    public void moveDown(int col) {
        int newRow = row + 1;
        int newCol = col;

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * Jumps to the top of the page.
     */
    public void gotoTopOfPage() {
        // Always clamp to the very first character
        int[] pos = clampPosition(0, 0);
        setPosition(pos[0], pos[1]);
    }

    /**
     * Jumps to the bottom of the page.
     */
    public void gotoBottomOfPage() {
        ArrayList<String> scan = document.getScanner();

        if (scan.isEmpty()) {
            // Empty document → clamp to start
            setPosition(0, 0);
            return;
        }

        int lastRow = scan.size() - 1;
        int lastCol = scan.get(lastRow).length() - 1;

        // Clamp to the last valid character
        int[] pos = clampPosition(lastRow, lastCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * Moves this object backwards by a step on the parent Document object.
     */
    public void shiftBackwards() {
        int newRow = row;
        int newCol = column - 1;

        if (newCol < 0) {
            newRow = row - 1;
            if (newRow >= 0) {
                newCol = document.getScanner().get(newRow).length() - 1;
            } else {
                newRow = 0;
                newCol = 0;
            }
        }

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    public void shiftBackwards(int step) {
        int newRow = row;
        int newCol = column - step;

        // If moving left past start of row, kick back into previous rows
        while (newCol < 0 && newRow > 0) {
            newRow--;
            newCol += document.getScanner().get(newRow).length();
        }

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * Moves this object forwards by a step size of 1 on the parent Document
     * object.
     */
    public void shiftForwards() {
        int newRow = row;
        int newCol = column + 1;

        int rowLen = document.getScanner().get(row).length();
        if (newCol >= rowLen) {
            newRow = row + 1;
            newCol = 0;
        }

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     * @param step Moves this object forwards by the step size on the parent
     * Document object.
     */
    public void shiftForwards(int step) {
        int newRow = row;
        int newCol = column + step;

        // If moving right past end of row, spill into next rows
        while (newRow < document.getScanner().size()) {
            int rowLen = document.getScanner().get(newRow).length();
            if (newCol < rowLen) {
                break;
            }
            newCol -= rowLen;
            newRow++;
        }

        int[] pos = clampPosition(newRow, newCol);
        setPosition(pos[0], pos[1]);
    }

    /**
     *
     * The backSpace action
     */
    public void delete() {
        getCurrent().handleBackSpaceKey();
    }//end method

    /**
     * Removes all characters between startIndex and endIndex.. both of them
     * inclusive.
     *
     * @param endIndex The index from which data is to be deleted.
     */
    public void delete(int endIndex) {
        int startIndex = getIndex();

        // If caret is invalid or document is empty, nothing to delete
        if (startIndex < 0 || document.getText().isEmpty()) {
            return;
        }

        // Ensure startIndex is always the lesser
        if (startIndex > endIndex) {
            int swap = endIndex;
            endIndex = startIndex;
            startIndex = swap;
        }

        // If range is empty or startIndex == endIndex == 0, nothing to delete
        if (startIndex == endIndex && startIndex == 0) {
            return;
        }

        // Clamp to valid text length
        int textLen = document.getText().length();
        if (endIndex >= textLen) {
            endIndex = textLen - 1;
        }

        for (int i = endIndex; i >= startIndex; i--) {
            // Safe delete: only if index > 0
            if (getIndex() > 0) {
                delete();
            }
        }
    }

    /**
     * Splits a <b color ='red'>row</b>
     * on the position of the caret.
     *
     * @return an array of 2 elements containing in the first index, all of the
     * text on the row before the caret position and in the second index, all of
     * the text on the row after the caret position.
     */
    private String[] splitRowTextOnCaretPosition() {

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
            setPosition(row, len - 1);
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
    private String[] splitDocumentTextOnCaretPosition() {
        String allText = document.getText();
        int index = getIndex();
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
    public void handleEnterKey() {
        getCurrent().handleEnterKey();
    }//end method

    /**
     *
     * @param document
     * @return the Sentence in which this Caret resides currently.
     * @throws NullPointerException if the Caret's position does not contain any
     * Sentence object which could happen if proper initialization of the
     * Document is not carried out.
     */
    public Sentence getCurrent1() throws NullPointerException {
        ArrayList<Sentence> sentences = document.getSentences();

        /**
         * Record the position of the Caret in the parent Document object.
         */
        int index = getIndex();
        if (index < 0) {
            index = 0;
            return sentences.get(0);
        }

        int sz = sentences.size();
        for (int i = 0; i < sz; i++) {
            if (sentences.get(i).contains(index)) {
                return sentences.get(i);
            }//end if
        }//end for loop
        return null;
    }

    public Sentence getCurrent() throws NullPointerException {
        ArrayList<Sentence> sentences = document.getSentences();
        int index = getIndex();

        if (index < 0) {
            index = 0;
        }

        for (Sentence s : sentences) {
            if (s.contains(index)) {
                return s;
            }
        }

        // Fallback: if no sentence contains the index, return the first one
        if (!sentences.isEmpty()) {
            return sentences.get(0);
        }

        // As a last resort, create a new sentence
        return document.appendSentence();
    }

    /**
     *
     * @param text The text to write into the parent Document object.
     */
    public synchronized void write(String text) {
        if (text != null && !text.isEmpty()) {
            if (column < 0) {
                shiftForwards(Math.abs(column));//shift forward by absolute value of negative column index
            }
            getCurrent().write(text);
        }
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
