/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commandinterface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * Objects of this class are able to keep collecting text input from the user
 * while He/she has not pressed enter. Even if the text becomes too much for a
 * line, it automatically wraps to the next line. However if the user presses
 * enter, then the object sends its text to the appropriate part of the program
 * and becomes disabled Control is then transferred to another object of this
 * class..
 *
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class Document implements KeyListener, MouseListener, MouseMotionListener, Runnable {

    public final Object lock = new Object();
    private JComponent renderer;

    /**
     * The Caret object of this Document.
     */
    private final Caret caret;

    /**
     * The text that starts every command.
     */
    private String commandText;

    /**
     * Determines if or not text can be entered into objects of this class.
     */
    private boolean enabled;
    /**
     * The color of the background of objects of this class.
     */
    private Color bgColor;
    /**
     * The color of the text of objects of this class.
     */
    private Color fgColor;
    /**
     * The font of objects of this class
     */
    private Font font;
    /**
     * The width and height of objects of this class.
     */
    private Dimension size;
    /**
     * The location of the Document object.
     */
    private Point location;
    /**
     * The width attribute of this margin field represents the distance between
     * the first letter of the text and the left side of the document.
     *
     * The height attribute of this margin field represents the distance between
     * the first letter of the text and the top of the document.
     *
     */
    private Dimension margin;

    /**
     * The maximum width that a row of text can have on this Document object.
     */
    private int lineWidth;
    /**
     * The distance between lines of text.
     */
    private int lineSpacing;

    /**
     * An ArrayList of Sentence objects to be displayed in this Document.
     */
    private volatile ArrayList<Sentence> sentences = new ArrayList<Sentence>();

    private final Thread timer;

    public Document() {

        caret = new Caret(this);
        commandText = "";//"cmd>> ";
        this.enabled = true;
        this.fgColor = Color.red;
        this.bgColor = Color.blue;
        this.font = new Font("Times New Roman", Font.BOLD, 15);
        this.size = new Dimension(1000, 900);
        this.margin = new Dimension(10, 10);
        this.location = new Point(0, 0);
        setLineWidth(900);
        this.lineSpacing = 10;
        caret.setRow(0);
        caret.setColumn(0);
        printCaretData();

        addSentence(0);
        this.timer = new Thread(this);
        timer.start();
    }//end method

    private void printCaretData() {
        System.out.println(
                "Caret data: row = " + caret.getRow()
                + "\ncolumn = " + caret.getColumn()
                + "\nindex = " + caret.getIndex()
                +"\navailable-sentences = "+sentences.size()
        );
    }

    public void repaint() {
        if (renderer != null) {
            renderer.repaint();
        }
    }

    public void repaint(Rectangle r) {
        if (renderer != null) {
            renderer.repaint(r);
        }
    }

    public void setRenderer(JComponent renderer) {
        this.renderer = renderer;
    }

    public JComponent getRenderer() {
        return renderer;
    }

    /**
     *
     * @param g The Graphics object used to draw.
     */
    public void draw(Graphics g) {
        try {
            g.setFont(font);
            g.setColor(bgColor);
            g.fillRoundRect(location.x, location.y, size.width, size.height, 50, 50);
            g.fill3DRect(location.x + 15, location.y + 15, size.width - 15, size.height - 15, true);

            g.setColor(fgColor);

            for (int i = 0; i < sentences.size(); i++) {
                sentences.get(i).draw(g);
            }//end for loop

            caret.draw(g);
        }//end try
        catch (NullPointerException nolian) {
            nolian.printStackTrace();
        }//end catch

    }//end method draw.

    public int getLineWidth() {
        return lineWidth;
    }

    public final void setLineWidth(int lineWidth) {
        this.lineWidth = (lineWidth < (size.width - margin.width)) ? lineWidth : size.width - margin.width;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Color getFgColor() {
        return fgColor;
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    /**
     *
     * @return the text stored by all Sentence objects of an object of this
     * class.
     */
    public String getText() {
        int sz = sentences.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sz; i++) {
            sb.append(sentences.get(i).getText());
        }//end for
        return sb.substring(0, sb.length());
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public Dimension getMargin() {
        return margin;
    }

    public void setMargin(Dimension margin) {
        this.margin = margin;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Caret getCaret() {
        return caret;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    /**
     *
     *
     * @param absoluteIndex An absolute index of a character within the main
     * text of this Document.
     * @return the Sentence object containing the character at that index.
     */
    public Sentence getSentenceAt(int absoluteIndex) {
        int sz = sentences.size();
        /*Sentence[] sen = new Sentence[1];
        sentences.stream().forEach(s -> {
          if(s.contains(absoluteIndex)){
              sen[0]=s;
          }
        });
        if(sen[0]!=null){return sen[0];}*/
        synchronized (lock) {
            for (int i = 0; i < sz; i++) {
                if (sentences.get(i).contains(absoluteIndex)) {
                    return sentences.get(i);
                }//end if
            }//end for

        }
        System.out.println("appendSentence>>> in getSentenceAt()");
        return appendSentence();
    }

    private void resetIndex() {
        resetIndex(0);
    }

    private void resetIndex(int fromIndex) {
        int sz = sentences.size();
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (fromIndex < sz) {
            for (int i = fromIndex; i < sz; i++) {
                Sentence s = sentences.get(i);
                s.setIndex(i);
            }
        }
    }

    /**
     *
     * @param row The row of text
     * @param col The column of text
     * @return the location of the character at position 'row' and 'col'.
     */
    public Point indexToLocation(int row, int col) {
        int index = row$ColumnToIndex(row, col);
        return indexToLocation(index);
    }

    public FontMetrics getFontMetrics(Font font) {
        if (renderer != null) {
            return renderer.getFontMetrics(font);
        } else {
            // Fallback during early init (rare)
            return Toolkit.getDefaultToolkit().getFontMetrics(font);
        }
    }

    /**
     *
     * @param index The absolute index of the character within the main text of
     * the Document object.
     * @return the location of the character at index 'index' of the input
     * string 'text' on the screen .
     */
    public Point indexToLocation(int index) {
        Sentence sentence = getSentenceAt(index);
        int[] row$Col = indexToRow$Column(index);
        row$Col = normalizePosition(row$Col[0], row$Col[1]);
        int xPos = location.x + margin.width;
        int yPos = location.y + margin.height;
        int row = row$Col[0];
        int col = row$Col[1];
        int width = 0;
        int height = 0;

        calcWidth$Height:
        {
            try {
                if (row == 0) {
                    height = 0;
                }//end if
                else if (row > 0 && col >= 0) {
                    int sz = sentences.size();
                    for (int i = 0; i < sz; i++) {
                        Sentence current = sentences.get(i);
                        if (current == sentence) {
                            break;
                        }//end if
                        height += (current.getTextHeight() + lineSpacing);

                    }//end for loop.

                }//end else if
                width = sentence.getTextWidth(getScanner().get(row).substring(0, col + 1));
            }//end try
            catch (IndexOutOfBoundsException boundsException) {
                width = 0;
            }//end catch
        }//end calcWidth$Height
        Point p = new Point(xPos + width, yPos + height);

        return p;

    }//end method

    @Override
    public void keyTyped(KeyEvent e) {
        synchronized (lock) {
            String input = String.valueOf(e.getKeyChar());
            String evtdata = e.paramString().toLowerCase();

            if (!evtdata.contains("backspace") && !evtdata.contains("escape") && !evtdata.contains("delete")
                    && !evtdata.contains("enter")) {
                printCaretData();
                System.out.println("sentences: " + sentences.size());
                caret.write(input);
            }//end if
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (lock) {
            String input = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
            if (input.equals("backspace")) {
                try {
                    if (caret.getIndex() >= 0) {
                        caret.delete();
                    }
                }//end try
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                }//end catch
            }//end if
            else if (input.equals("enter")) {
                caret.handleEnterKey();
            } else if (input.equals("left")) {
                caret.shiftBackwards();
            } else if (input.equals("right")) {
                caret.shiftForwards();
            } else if (input.equals("up")) {
                caret.moveUp();
            } else if (input.equals("down")) {
                caret.moveDown();
            }
            repaint();
        }

    }//end method

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (lock) {
            Point p = e.getPoint();
            int[] row$col = caret.convertLocationToRow$Column(p);
            caret.setState(CaretState.INSERTING);
            caret.setPosition(row$col[0], row$col[1]);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return all rows stored by the Sentences of an object of this class.
     */
    public ArrayList<String> getScanner() {
        int sz = sentences.size();
        ArrayList<String> scan = new ArrayList<>();
        for (int i = 0; i < sz; i++) {
            scan.addAll(sentences.get(i).getRows());
        }//end for
        return scan;
    }//end method

    /**
     * Adds a Sentence object to this Document. The Sentence is initialized with
     * a single space.
     *
     * @param index The index the Sentence will occupy in this Document's
     * Sentence store.
     * @return
     */
    public final Sentence addSentence(int index) {
        Sentence s1 = new Sentence(this);
        System.out.println("addSentence(int index) ---Sentence created");
        s1.setColor(fgColor);
        s1.setFont(font);
        s1.setText("");
        addSentence(index, s1);
        return s1;
    }//end method

    /**
     * Appends a Sentence object to the end of this Document. The Sentence is
     * initialized with a single space.
     *
     * @return
     */
    public Sentence appendSentence() {
        Sentence s1 = new Sentence(this);
        System.out.println("appendSentence() ---Sentence created");
        s1.setColor(fgColor);
        s1.setFont(font);
        s1.setIndex(sentences.size());
        s1.setText("");
        sentences.add(s1);
        return s1;
    }//end method

    /**
     * Adds a Sentence object to this Document.
     *
     * @param index The index the Sentence will occupy.
     * @param sentence The Sentence object to add to this Document's Sentence
     * store. Inserts the specified Sentence at the specified position in this
     * list. Shifts the Sentence currently at that position (if any) and any
     * subsequent Sentence objects to the right (adds one to their indices).
     * @return
     */
    public Sentence addSentence(int index, Sentence sentence) {
        sentences.add(index, sentence);
        resetIndex(index);
        return sentence;
    }//end method

    /**
     * Appends a Sentence object to the end of this Document. The Sentence is
     * initialized with a single space.
     *
     * @param sentence
     * @return
     */
    public Sentence appendSentence(Sentence sentence) {
        sentence.setIndex(sentences.size());
        sentences.add(sentence);
        return sentence;
    }//end method

    /**
     *
     * @param index An index in the Document object..usually it is the index of
     * a Caret object in the Document object. Also the index of the character in
     * the parent Document object that this Caret is currently on.
     * @return an array of 2 int values. The first value is the corresponding
     * row The second value is the corresponding column.
     * @throws IndexOutOfBoundsException if the index does not map to any row or
     * column in the parent Document.
     */
    public int[] indexToRow$Column(int index) throws IndexOutOfBoundsException {
        ArrayList<String> scan = getScanner();
        int sz = scan.size();
        int len = getText().length();
        if (len == 0) {
            return new int[]{0, -1};
        } else if (index < 0) {
            return new int[]{0, -1};
        } else if (index >= 0) {

            if (index >= len) {
                index = len - 1;
            }

            int drow = -1;
            int dcol = -1;
            int count = 0;
            int rowLen = 0;
            while ((index - (rowLen = scan.get(count).length())) >= 0) {
                index -= rowLen;
                ++count;
            }//end while
            drow = count;
            dcol = index;
            return new int[]{drow, dcol};
        }//end else if
        throw new IndexOutOfBoundsException("Terrible Error! Cannot Be Accounted For. Index System Has Crashed!index = " + index);
    }//end method

    /**
     *
     * @param row The row.
     * @param column The column.
     * @return Takes a row and column value and returns the index they refer to
     * in the parent Document's text.
     *
     */
    public int row$ColumnToIndex(int row, int column) {

        if (!getText().isEmpty()) {

            ArrayList<String> store = getScanner();
            int sz = store.size();

            int accIndex = 0;
            for (int i = 0; i < sz; i++) {

                int syz = store.get(i).length();
                if (i < row) {
                    accIndex += syz;
                }//end if
                else if (i == row) {
                    accIndex += (column);
                    break;
                }//end else if

            }//end for loop

            int len = getText().length();

            if (accIndex < len) {
                return accIndex;
            }//end if
            else {
                throw new IndexOutOfBoundsException("Row&Column " + "[" + row + ", " + column + "] reported at index = " + accIndex
                        + "\nare outside document index '" + (len - 1) + "'");
            }//end else
        }//end if
        //empty document
        else {
            return -1;
        }

    }//end method

    /**
     *
     * @param row The row
     * @param column The column which may spill-over or kick-back. To spill-over
     * means that the column is larger than the column size for the specified
     * row. To kick-back means that the column is less than zero which is the
     * minimum column index for any given row (i.e a row must contain at least
     * one element)
     *
     * @return The appropriate row and column, by shifting back along the rows
     * till the negative column becomes zero. The interpretation for kick-back
     * is: Let row 3 contain 4 columns and let row 2 contain 8 columns:
     * Attempting to move -6 columns on row 3 would cause a kick-back onto row
     * 2: row = 3,column = -6 ==== row = 2 column = 2.
     *
     * The interpretation for spill-over is: Let row 3 contain 4 columns and let
     * row 4 contain 11 columns: Attempting to move 12 columns on row 3 would
     * cause a spill-over onto row 4: row = 3,column = 12 ==== row = 4 column =
     * 7.
     *
     */
    public int[] normalizePosition(int row, int column) {
        ArrayList<String> scan = getScanner();
        int accIndex = 0;
        for (int i = 0; i < scan.size(); i++) {
            int rowLen = scan.get(i).length();
            if (i < row) {
                accIndex += rowLen;
            }//end if
            else if (i == row) {
                accIndex += (column);
                break;
            }//end else if
        }//end for

        return indexToRow$Column(accIndex);
    }//end method.

    /**
     *
     * @param row The row.
     * @param col The column
     * @return The Point object corresponding to the row in question.
     */
    public Point convertRow$ColumnToPoint(int row, int col) {
        int[] normalized = normalizePosition(row, col);
        int index = row$ColumnToIndex(normalized[0], normalized[1]);
        return indexToLocation(index);
    }//end method

    /**
     *
     * @param row The row that the character lies on.
     * @param column The column that the character lies on.
     * @return The Rectangle enclosing the character.
     */
    public Rectangle getCharRectangle(int row, int column) {
        int[] row$Col = normalizePosition(row, column);
        int index = row$ColumnToIndex(row$Col[0], row$Col[1]);
        String textAtIndex = getText().substring(index, index + 1);
        return new Rectangle(indexToLocation(index),
                new Dimension(caret.getCurrent().getTextWidth(textAtIndex),
                        caret.getCurrent().getTextHeight()));
    }//end method

    /**
     *
     * @param index The index of the character in the parent Document object's
     * underlying text.
     * @return The Rectangle enclosing the character.
     */
    public Rectangle getCharRectangle(int index) {
        String textAtIndex = getText().substring(index, index + 1);
        return new Rectangle(indexToLocation(index),
                new Dimension(caret.getCurrent().getTextWidth(textAtIndex),
                        caret.getCurrent().getTextHeight()));
    }//end method

    /**
     *
     * @param row The row that the character lies on.
     * @param column The column that the character lies on.
     * @return The location of this character.
     */
    public Point getCharLocation(int row, int column) {
        int[] row$Col = normalizePosition(row, column);
        int index = row$ColumnToIndex(row$Col[0], row$Col[1]);
        return getCharLocation(index);
    }//end method

    /**
     *
     * @param index The index of the character in the parent Document object's
     * underlying text.
     * @return The location of this character.
     */
    public Point getCharLocation(int index) {
        return new Point(indexToLocation(index));
    }//end method

    public int reduceRowToMaxCharsAllowed(String text) {
        int len = text.length();
        int maxWidth = getLineWidth();
        int i = 0;

        // Use Document's font metrics directly
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
        int minDistance = fm.stringWidth("@");

        for (i = 0; i < len; i++) {
            String cutText = text.substring(0, i + 1);
            if (fm.stringWidth(cutText) + minDistance > maxWidth) {
                return i + 1;
            }
        }
        return i; // return actual number of chars that fit
    }

    /**
     * Reduces a row that has more text than it should hold to a number of
     * characters that can fit on the row.
     *
     * @param text The overflowing text
     * @return the number of characters counting from the beginning of the row
     * that will maximally fit on the row. If the text on the row has not
     * overflown the row limit,it will return the number of characters in this
     * text.
     */
    public int reduceRowToMaxCharsAllowed1(String text) {
        int len = text.length();
        int maxWidth = getLineWidth();
        int i = 0;
        int minDistance = caret.getCurrent().getTextWidth("@");
        for (i = 0; i < len; i++) {
            String cutText = text.substring(0, i + 1);
            if (caret.getCurrent().getTextWidth(cutText) + minDistance > maxWidth) {
                return i + 1;
            }//end if

        }//end for loop
        return i + 1;
    }

    /**
     *
     * @param row The row in question
     * @return true if the row can still take more characters and false
     * otherwise..
     */
    public boolean rowCanTakeMoreText(int row) {
        ArrayList<String> scan = this.getScanner();
        String text = "";
        try {
            text = scan.get(row);
            return textCanStillEnterDocumentRow(text);
        }//end try
        catch (IndexOutOfBoundsException ind) {
            return true;
        } catch (NullPointerException nol) {
            return true;
        }
    }//end method.

    /**
     *
     * @param row The row in question
     * @return true if, when placed in a Document row more text can still be
     * added to the row.
     */
    public boolean textCanStillEnterDocumentRow(String text) {
        int rowWidth = caret.getCurrent().getTextWidth(text);
        int maxWidth = this.getLineWidth();
        /**
         * The @ character has perhaps the largest font size of characters in
         * many fonts. Use it as the minimum distance between the row width and
         * the maximum width. If the row width < maxWidth but
         *  row width+min-distance >= maxWidth, then assume that the row is
         * filled.
         *
         */
        int minDistance = caret.getCurrent().getTextWidth("@");

        /**
         * Check if: 1. The row width is equal to the maximum width. 2. The row
         * width is less than the maximum width but the row width+the average
         * character width is greater than the maximum width If 1. is
         * true...return true. If 2 is true, return true. Else return false.
         */
        if (rowWidth + minDistance <= maxWidth) {
            return true;
        } else {
            return false;
        }
    }//end method.

    /**
     *
     * @param row The row in question
     * @return true if the row has exceeded the maxWidth allowed for a row by
     * the parent Document object.
     */
    public boolean rowIsOverFlowing(int row) {

        ArrayList<String> scan = this.getScanner();
        String text = "";
        try {
            text = scan.get(row);
            return textOverFlowsDocumentRow(text);
        }//end try
        catch (IndexOutOfBoundsException ind) {
            return false;
        } catch (NullPointerException nol) {
            return false;
        }
    }//end method.

    /**
     *
     * @param text The text in question
     * @return true if the text overflows a row in the parent Document object.
     * <b color ='red'>NOTE This method returns false if the text is larger than
     * the row.</b>
     */
    public boolean textOverFlowsDocumentRow(String text) {
        int rowWidth = caret.getCurrent().getTextWidth(text);
        int maxWidth = this.getLineWidth();
        return rowWidth > maxWidth;
    }//end method.

    /**
     *
     * @param row The row in question
     * @return true if the row has at least equaled the maxWidth allowed for a
     * row by the parent Document object.
     * <b color ='red'>NOTE</b> This method returns false if the text on the row
     * is larger than its recommended value.
     */
    public boolean rowIsFilled(int row) {

        ArrayList<String> scan = this.getScanner();
        String text = "";
        try {
            text = scan.get(row);
            return textFillsDocumentRow(text);
        }//end try
        catch (IndexOutOfBoundsException ind) {
            return false;
        } catch (NullPointerException nol) {
            return false;
        }
    }//end method.

    /**
     *
     * @param text The text in question
     * @return true if the text fits a row in the parent Document object.
     * <b color ='red'>NOTE</b> This method returns false if the text is larger
     * than the row.
     */
    public boolean textFillsDocumentRow(String text) {

        int rowWidth = caret.getCurrent().getTextWidth(text);
        int maxWidth = this.getLineWidth();
        /**
         * The @ character has perhaps the largest font size of characters in
         * many fonts. Use it as the minimum distance between the row width and
         * the maximum width. If the row width < maxWidth but
         *  row width+min-distance >= maxWidth, then assume that the row is
         * filled.
         *
         */
        int minDistance = caret.getCurrent().getTextWidth("@");

        /**
         * Check if: 1. The row width is less than the maximum width but the row
         * width+the average character width is greater than the maximum width
         * 2. The row width is equal to the maximum width. If 1. is
         * true...return true. If 2 is true, return true. Else return false.
         */
        if (rowWidth < maxWidth && (rowWidth + minDistance) > maxWidth) {
            return true;
        } else if (rowWidth == maxWidth) {
            return true;
        } else {
            return false;
        }
    }//end method

    public void garbageCollectSentences() {
        SwingUtilities.invokeLater(() -> {
            java.util.List<Sentence> garbage = new ArrayList<>();
            sentences.stream().forEach((s) -> {
                if (s.getText().isEmpty() && caret.getCurrent() != s) {
                    garbage.add(s);
                }//end if
            });
            sentences.removeAll(garbage);
            resetIndex();
            System.out.println("Garbage collector run. Deleted " + garbage.size() + " items");
        });

    }

    @Override
    public synchronized void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.currentThread().sleep(caret.getBlinkRate());

                if (caret.isVisible()) {
                    caret.setVisible(false);
                } else if (!caret.isVisible()) {
                    caret.setVisible(true);
                }
                repaint();

            }//end try
            catch (InterruptedException intEx) {

            }//end catch
        }//end while loop

    }

}//end class
