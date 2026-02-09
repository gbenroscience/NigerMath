/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commandinterface;

import java.awt.*;
import java.util.*;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa.
 */
public class Sentence {

    private final Document document;
    /**
     * The text in this Sentence object.
     */
    private String text;

    /**
     * The unique index of this object in the parent Document.
     */
    private int index;

    /**
     * The color used to render this Sentence.
     */
    private Color color;

    private Font font;
    private String id = UUID.randomUUID().toString();

    /**
     * Constructor used by JavaBeans.
     *
     * @param document
     */
    public Sentence(Document document) {
        this.document = document;
        this.text = "";
        this.index = 0;

        /**
         * Initialize font and color to that of the parent document object.
         */
        this.color = document.getFgColor();
        this.font = document.getFont();
        System.out.println("Sentence " + id + " created!");
    }//end constructor;

    public Document getDocument() {
        return document;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    /**
     *
     * @return the text in this sentence object.
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param document
     * @param g The Graphics object used to draw the text embedded in this
     * Sentence.
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);

        ArrayList<String> rows = getRows();
        ArrayList<Point> points = getRowLocations();
        int sz = points.size();
        for (int i = 0; i < sz; i++) {
            g.drawString(rows.get(i), points.get(i).x, points.get(i).y);
        }//end for loop

    }//end method draw.

    /**
     * An absolute index speaks of the index of a character in the text of the
     * parent Document that owns this Sentence object. This method checks if the
     * index supplied as parameter to it falls within the text of this Sentence
     * object.
     *
     * @param document
     * @param absoluteIndex The index to consider.
     * @return true if there exists a relative index in the text of this object
     * that maps onto the supplied absolute index.
     */
    public boolean contains(int absoluteIndex) {
        if (text.isEmpty()) {
            // Treat empty sentence as containing index 0 at its start
            return absoluteIndex == getAbsoluteStartIndex();
        }
        int absStartIndex = getAbsoluteStartIndex();
        int absStopIndex = getAbsoluteStopIndex();
        return (absStartIndex <= absoluteIndex && absStopIndex >= absoluteIndex);
    }

    /**
     *
     * <b color ='blue'>
     * An absolute index speaks of the index of a character in the text of the
     * parent Document that owns this Sentence object. But the text in the
     * parent Document object is composed of a proper arrangement of all text in
     * all its component Sentence objects. So that means that a character seen
     * on a Document actually belongs to one of its Sentence objects. This
     * Sentence object will have its own text and so we wish to know the index
     * of the character in the text of this Sentence object. <br>We define it as
     * the displacement of the character from the start of its text - 1.
     * </b>
     *
     * @param absoluteIndex The index to convert to a relative one.
     * @return the index that this absoluteIndex would point to in the text of
     * this Sentence object.
     */
    public int getRelativeCharIndex(int absoluteIndex) {
    if (text.isEmpty()) {
        // For empty sentence, only index 0 is valid
        if (absoluteIndex == getAbsoluteStartIndex()) {
            return 0;
        }
        throw new IndexOutOfBoundsException("Index " + absoluteIndex + " not valid in empty sentence");
    }

        int absStartIndex = getAbsoluteStartIndex();
        int absStopIndex = getAbsoluteStopIndex();

        if (absStartIndex <= absoluteIndex && absStopIndex >= absoluteIndex) {
            return absoluteIndex - absStartIndex;
        }//end if
        else if (absStartIndex > absoluteIndex) {
            System.out.println("--------index: " + index + ", sentences-count: " + document.getSentences().size() + ", caret-row: " + document.getCaret().getRow() + ", caret-column: " + document.getCaret().getColumn());
            throw new IndexOutOfBoundsException(" Please! Please!! The Index You "
                    + "\nSpecified Does Not Belong In This Sentence! "
                    + "\n Error: Startindex = " + absStartIndex + " and index =  " + absoluteIndex);
        }//end else if
        else if (absoluteIndex > absStopIndex) {
            throw new IndexOutOfBoundsException(" Please!! Please!!! The Index Which You Have"
                    + "\nSpecified Does Not Belong In This Sentence! "
                    + "\n Error: lastIndex = " + absStopIndex + " and index = " + absoluteIndex);
        }//end else if

        throw new IndexOutOfBoundsException("Program Crash Imminent! Index System Failed! What Have You Done?");
    }//end method
 
    /**
     * return the index of the first letter of this object in the overall text
     * of the parent Document.
     *
     * @return
     */
    public int getAbsoluteStartIndex() {
        ArrayList<Sentence> sentences = document.getSentences();
        int pos = 0;
        for (int i = 0; i < index; i++) {
            pos += sentences.get(i).length();
        }//end for loop
        return pos;
    }//end method

    /**
     * return the index of the last letter of this object in the overall text of
     * the parent Document.
     *
     * @return
     */
    public int getAbsoluteStopIndex() {
        ArrayList<Sentence> sentences = document.getSentences();
        int pos = 0;
        for (int i = 0; i <= index; i++) {
            pos += sentences.get(i).length();
        }//end for loop
        return pos - 1;
    }//end method

    /**
     * This method takes the relative index of a character in this Sentence
     * object and returns its index(absolute) in the parent Document object.
     *
     * @param relativeIndex The index of the character in this Sentence's text,
     * taking the relative index of the first character of this Sentence's text
     * to be 0 and that of the last character of this Sentence's text to be
     * number of characters in Sentence - 1.
     * @return the absolute index of the character in the parent Document
     * object.
     * @throws ArrayIndexOutOfBoundsException if the relativeIndex is not
     * between 0(included) and the number of characters in this
     * Sentence(excluded)
     */
    public int getAbsoluteCharIndex(int relativeIndex) {
        if (relativeIndex >= 0 && relativeIndex < length()) {
            ArrayList<Sentence> sentences = document.getSentences();
            int pos = 0;
            for (int i = 0; i < index; i++) {
                pos += sentences.get(i).length();
            }//end for loop
            return pos + relativeIndex;
        }//end loop
        if (relativeIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(" Relative Index " + relativeIndex + " < 0");
        }//end if
        else {
            throw new ArrayIndexOutOfBoundsException(" Relative Index " + relativeIndex + " > " + length());
        }//end else
    }

    /**
     *
     * @return an array containing 2 int values. The first index contains the
     * row which the first letter of the text in this object occupies on the
     * parent Document object. The second contains the column which the first
     * letter of the text in this object occupies on the parent Document object.
     *
     */
    public int[] getAbsoluteStartPosition() {
        return document.indexToRow$Column(getAbsoluteStartIndex());
    }//end method

    /**
     *
     * @return an array containing 2 int values. The first index contains the
     * row which the last letter of the text in this object occupies on the
     * parent Document object. The second index contains the column which the
     * last letter of the text in this object occupies on the parent Document
     * object.
     *
     */
    public int[] getAbsoluteStopPosition() {
        return document.indexToRow$Column(getAbsoluteStopIndex());
    }

    /**
     *
     * @param relativeIndex The index of the character in this Sentence object's
     * text, taking the index of the first character in this Sentence's text as
     * 0 and the index of its last character as number of characters in sentence
     * - 1.
     * @return an array containing 2 int values. The first index contains the
     * row which the character at the specified relative index of the text in
     * this Sentence object occupies on the parent Document object. The second
     * contains the column which the last character of the text in this object
     * occupies on the parent Document object.
     *
     */
    public int[] getAbsoluteCharPosition(int relativeIndex) {
        return document.indexToRow$Column(getAbsoluteCharIndex(relativeIndex));
    }

    /**
     *
     * @return the number of characters stored by this object.
     */
    public int length() {
        return text.length();
    }

    /**
     * return the location of the first letter in this object as a Point object
     * in the overall dimension space of the parent Document.
     */
    public void getLocation() {
        int[] row$Col = getAbsoluteStartPosition();
        document.indexToLocation(row$Col[0], row$Col[1]);
    }//end method

    /**
     *
     * @return organizes the text in this sentence into an ArrayList of rows
     */
    public ArrayList<String> getRows() {
        String st = text;
        ArrayList<String> rows = new ArrayList<>();

        FontMetrics fm = document.getFontMetrics(font);
        int maxWidth = document.getLineWidth();
        int minDistance = fm.stringWidth("@");

        while (!st.isEmpty()) {
            int cutIndex = st.length();
            for (int i = 1; i <= st.length(); i++) {
                String cutText = st.substring(0, i);
                if (fm.stringWidth(cutText) + minDistance > maxWidth) {
                    cutIndex = i;
                    break;
                }
            }
            rows.add(st.substring(0, cutIndex));
            st = st.substring(cutIndex);
        }

        return rows;
    }

    /**
     *
     * @param doc The parent Document object.
     * @return the location of the first character of each row of this Sentence
     * object. This is taken to be the location of the row, and from here, the
     * application will draw the text contained on the row.
     */
    public ArrayList<Point> getRowLocations() {
        ArrayList<String> rows = getRows();
        ArrayList<Point> locations = new ArrayList<Point>();

        int sz = rows.size();
        int pos = 0;
        for (int i = 0; i < sz; i++) {

            if (i == 0) {
                pos = 0;
                int indexOfFirstCharInRow = getAbsoluteCharIndex(pos);
                Point p = document.indexToLocation(indexOfFirstCharInRow);
                locations.add(p);
            }//end if
            else {
                pos += rows.get(i - 1).length();
                int indexOfFirstCharInRow = getAbsoluteCharIndex(pos);
                Point p = document.indexToLocation(indexOfFirstCharInRow);
                locations.add(p);
            }//end else
        }//end for loop

        return locations;
    }//end method

    /**
     * @param text The text whose width is needed.
     * @return the width of the text in the font scheme.
     */
    public int getTextWidth(String text) {
        return document.getFontMetrics(font).stringWidth(text);
    }//end method

    /**
     * @return the height of the text in the font scheme.
     */
    public int getTextHeight() {
        return document.getFontMetrics(font).getHeight();
    }//end method

    /**
     *
     * @param document
     * @param txt The text to enter into this Sentence object.
     */
    public void write(String txt) {
// retrieve the index of the caret.
        int abs_Index = document.getCaret().getIndex();
        if (abs_Index < 0) {
            abs_Index = 0; // clamp
        }
        String part1 = "";
        String part2 = "";

        int rel_Index = getRelativeCharIndex(abs_Index);

//System.out.println( "input to write = "+txt );
//System.out.println( "abs-index = "+abs_Index );
//System.out.println( "rel-index = "+rel_Index );
//System.out.println( "Sentence = "+getText() );
        if (text.isEmpty()) {
            setText(txt);
            document.getCaret().setPosition(txt.length() - 1);
        }//end if
        else {
            try {
                part1 = text.substring(0, rel_Index + 1);
                part2 = text.substring(rel_Index + 1);
                setText(part1 + txt + part2);
                document.getCaret().setPosition(abs_Index + txt.length());
            }//end try
            catch (IndexOutOfBoundsException boundsException) {
                if (part1.isEmpty()) {
                    setText(txt + text);
                    document.getCaret().setPosition(abs_Index + txt.length());
                }//end if
            }//end catch

        }//end else

    }//end method

    /**
     * Run this method whenever the enter key is pressed inside this Sentence
     * object.
     *
     * @return the extra Sentence generated when the Enter key is pressed inside
     * this Sentence.
     *
     */
    public Sentence handleEnterKey() {
// retrieve the index of the caret.
        int abs_Index = document.getCaret().getIndex();
        int rel_Index = getRelativeCharIndex(abs_Index);
        String part1 = text.substring(0, rel_Index + 1);
        String part2 = text.substring(rel_Index + 1);
        setText(part1);
        Sentence sentence = new Sentence(document);
        System.out.println("In handleEnterKey ---Sentence created");
        if (!part2.isEmpty()) {
            sentence.setText(part2);
            sentence.setColor(color);
            sentence.setFont(font);
            document.getSentences().add(index + 1, sentence);
            sentence.setIndex(index + 1);
            int caretIndex = sentence.getAbsoluteCharIndex(part2.length() - 1);
            document.getCaret().setPosition(caretIndex);
            return sentence;
        }//end if
        else {
            part2 = " ";
            sentence.setText(part2);
            sentence.setColor(color);
            sentence.setFont(font);
            document.getSentences().add(index + 1, sentence);
            sentence.setIndex(index + 1);
            int caretIndex = sentence.getAbsoluteCharIndex(part2.length() - 1);
            document.getCaret().setPosition(caretIndex);
            return sentence;
        }
    }//end method

    /**
     * Run this method whenever the backSpace key is pressed inside this
     * Sentence object.
     *
     * @return the deleted character string.
     *
     */
    public String handleBackSpaceKey() {
        int abs_Index = document.getCaret().getIndex();

        // If caret is at start, nothing to delete
        if (abs_Index <= 0) {
            return ""; // or null, depending on your design
        }

        int rel_Index = getRelativeCharIndex(abs_Index);
        String part1 = text.substring(0, rel_Index);
        String charRemoved = text.substring(rel_Index, rel_Index + 1);
        String part2 = text.substring(rel_Index + 1);
        setText(part1 + part2);

        // Clamp caret position so it never goes below 0
        int newIndex = abs_Index - 1;
        if (newIndex < 0) {
            newIndex = 0;
        }
        document.getCaret().setPosition(newIndex);

        return charRemoved;
    }

    /**
     * Run this method whenever a range of characters is deleted simultaneously
     * from this object.
     *
     * @param startIndex The absolute index(inclusive) of the first character to
     * be deleted.
     * @param endIndex The absolute index (inclusive) of the last character to
     * be deleted.
     *
     * @return the deleted character string.
     */
    public String delete(int startIndex, int endIndex) {

        if (contains(startIndex) && contains(endIndex)) {
            int relStartIndex = getRelativeCharIndex(startIndex);
            int relEndIndex = getRelativeCharIndex(endIndex);
            String part1 = text.substring(0, relStartIndex);
            String charStringRemoved = text.substring(relStartIndex, relEndIndex + 1);
            String part2 = text.substring(relEndIndex + 1);
            setText(part1 + part2);
            return charStringRemoved;
        }//end if
        else {
            throw new IndexOutOfBoundsException();
        }//end else
    }//end method

}//end class Sentence
