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
 * Objects of this class are able to keep
 * collecting text input from the user while
 * He/she has not pressed enter.
 * Even if the text becomes too much for a line,
 * it automatically wraps to the next line. However
 * if the user presses enter,
 * then the object sends its text to the appropriate
 * part of the program and becomes disabled Control is then
 * transferred to another object of this class..
 *
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class Document implements KeyListener,MouseListener,MouseMotionListener{



/**
 * The Caret object of this Document.
 */
private final Caret caret;

/**
 * The text that starts every command.
 */
private String commandText;


    /**
     * Determines if or not text can be entered
     * into objects of this class.
     */
    private boolean enabled;
    /**
     * The color of the background
     * of objects of this class.
     */
    private Color bgColor;
    /**
     * The color of the text of objects
     * of this class.
     */
    private Color fgColor;
    /**
     * The font of objects of this class
     */
    private Font font;
    /**
     * The width and height of
     * objects of this class.
     */
    private Dimension size;
/**
 * The location of the Document object.
 */
    private Point location;
    /**
     * The width attribute of this
     * margin field represents the distance between
     * the first letter of the text and the left side of the document.
     *
     * The height attribute of this
     * margin field represents the distance between
     * the first letter of the text and the top of the document.
     *
     */
    private Dimension margin;

    /**
     * The maximum width that a row of text
     * can have on this Document object.
     */
    private int lineWidth;
    /**
     * The distance between lines
     * of text.
     */
    private int lineSpacing;
/**
 * The Graphics object used to
 * display.
 */
    private Graphics graphics;

/**
 * An ArrayList of Sentence objects
 * to be displayed in this Document.
 */
private ArrayList<Sentence>sentences = new ArrayList<Sentence>();


    public Document() {

        JFrame fr = new JFrame("");
        fr.addNotify();
        this.graphics = fr.getGraphics();
        caret = new Caret();
        commandText="";//"cmd>> ";
        this.enabled=true;
        this.fgColor= Color.red;
        this.bgColor=Color.blue;
        this.font= new Font("Times New Roman", Font.BOLD, 15);
        this.size = new Dimension(1000, 900);
        this.margin = new Dimension(10,10);
        this.location = new Point(0,0);
        setLineWidth(900);
        this.lineSpacing=10;
        Caret.setDocument(this);
        caret.setRow(0);
        caret.setColumn(0);
        System.out.println(
     "In the beginning: row = "+caret.getRow()+
     "\ncolumn = "+caret.getColumn()+
     "\nindex = "+caret.getIndex()
                );

addSentence(0);

    }//end method





/**
 *
 * @param g The Graphics object
 * used to draw.
 */
    public void draw( Graphics g ){
try{
       graphics = g;
       graphics.setFont(font);
       graphics.setColor(bgColor);
       graphics.fillRoundRect(location.x, location.y, size.width, size.height, 50,50);
       graphics.fill3DRect(location.x+15, location.y+15, size.width-15, size.height-15, true);

        graphics.setColor(fgColor);

        for( int i=0; i<sentences.size(); i++ ){
        sentences.get(i).draw(g);
        }//end for loop

caret.draw(graphics);
        }//end try
        catch(NullPointerException nolian){
            nolian.printStackTrace();
        }//end catch

    }//end method draw.

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = ( lineWidth < ( size.width-margin.width ) )?lineWidth:size.width-margin.width;
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
 * @return the text stored by all Sentence objects
 * of an object of this class.
 */
    public String getText() {
        int sz=sentences.size();
        String text = "";
for(int i=0;i<sz;i++){
   text = text.concat(sentences.get(i).getText());
}//end for

        return text;
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


    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }


    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    

/**
 * 
 * @param absoluteIndex An absolute index of a character
 * within the main text of this Document.
 * @return the Sentence object containing the character at that index.
 */
public Sentence getSentenceAt( int absoluteIndex ){
int sz=sentences.size();
    for( int i=0;i<sz;i++){
        if( sentences.get(i).contains( absoluteIndex ) ){
            return sentences.get(i);
        }//end if
    }//end for
throw new IndexOutOfBoundsException(" Sentence Not Found! ");
}



/**
 *
 * @param row The row of text
 * @param col The column of text
 * @return the location of the character
 * at position 'row' and 'col'.
 */
public Point indexToLocation( int row, int col ){
    int index = Caret.row$ColumnToIndex(row, col);
    return indexToLocation(index);
}


/**
 *
 * @param index The absolute index of the character
 * within the main text of the Document object.
 * @return the location of the character
 * at index 'index' of  the input string 'text'
 * on the screen .
 */
public Point indexToLocation( int index ){
Sentence sentence = getSentenceAt(index);
    int[]row$Col = Caret.indexToRow$Column(index);
    row$Col = Caret.normalizePosition(row$Col[0],row$Col[1]);
int xPos = location.x+margin.width;
int yPos = location.y+margin.height;
int row = row$Col[0];
int col = row$Col[1];
int width = 0;
int height= 0;

calcWidth$Height:{
try{
if( row == 0){
height = 0;
}//end if
else if(row > 0 && col >= 0){
    int sz=sentences.size();
for(int i=0;i<sz;i++){
Sentence current = sentences.get(i);
if(current==sentence){
    break;
    }//end if
height+=(current.getTextHeight()+lineSpacing);

}//end for loop.

}//end else if
width = sentence.getTextWidth( getScanner().get(row).substring(0, col+1) );
}//end try
catch(IndexOutOfBoundsException boundsException ){
width = 0;
}//end catch
}//end calcWidth$Height
Point p = new Point( xPos+width, yPos+height);

return p;
  
}//end method

    @Override
    public void keyTyped(KeyEvent e) {
        String input = String.valueOf(e.getKeyChar());
        String evtdata = e.paramString().toLowerCase();

        if(!evtdata.contains("backspace")&&!evtdata.contains("escape")&&!evtdata.contains("delete")&&
                !evtdata.contains("enter")){
      caret.write(input);
       }//end if
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String input = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
if( input.equals("backspace") ){
    try{
        if(caret.getIndex()>=0){
caret.delete();
        }
    }//end try
    catch(IndexOutOfBoundsException indexOutOfBoundsException){
    }//end catch
}//end if
 else if(input.equals("enter")){
 caret.handleEnterKey();
 }
 else if(input.equals("left")){
   caret.shiftBackwards();
}
 else if(input.equals("right")){
    caret.shiftForwards();
 }
 else if(input.equals("up")){
caret.moveUp(caret.getColumn());
    
 }
 else if(input.equals("down")){
 caret.moveDown(caret.getColumn());
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
     Point p = e.getPoint();
    int[] row$col = Caret.convertLocationToRow$Column(p);
    caret.setState(CaretState.INSERTING);
    caret.setPosition(row$col[0], row$col[1]);
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
 * @return all rows stored by the Sentences
 * of an object of this class.
 */
    public ArrayList<String> getScanner(){
int sz = sentences.size();
ArrayList<String>scan=new ArrayList<String>();
        for( int i=0;i<sz;i++){
scan.addAll(sentences.get(i).getRows());
        }//end for
return scan;
    }//end method


/**
 * Adds a Sentence object to this Document.
 * The Sentence is initialized with a single space.
 * @param index The index the Sentence will occupy
 * in this Document's Sentence store.
 */
public void addSentence( int index ){

        Sentence.setDocument(this);
        Sentence s1 = new Sentence();
        s1.setColor(fgColor);
        s1.setFont(font);
        s1.setIndex(index);
        s1.setText(" ");
        sentences.add(s1);
}//end method
/**
 * Appends a Sentence object to the end of this Document.
 * The Sentence is initialized with a single space.
 * @param index The index the Sentence will occupy
 * in this Document's Sentence store.
 */
public void appendSentence(){
    Sentence.setDocument(this);
        Sentence s1 = new Sentence();
        s1.setColor(fgColor);
        s1.setFont(font);
        s1.setIndex(sentences.size());
        s1.setText(" ");
        sentences.add(s1);
}//end method

/**
 * Adds a Sentence object to this Document.
 * @param index The index the Sentence will occupy.
 * @param sentence The Sentence object to add to
 * this Document's Sentence store.
 * Inserts the specified Sentence at the specified position in this
 * list. Shifts the Sentence currently at that position (if any) and
 * any subsequent Sentence objects to the right (adds one to their indices).
 */
public void addSentence( int index , Sentence sentence ){
    sentence.setIndex(index);
        sentences.add(index, sentence);
}//end method
/**
 * Appends a Sentence object to the end of this Document.
 * The Sentence is initialized with a single space.
 * @param index The index the Sentence will occupy
 * in this Document's Sentence store.
 */
public void appendSentence( Sentence sentence ){
        sentence.setIndex(sentences.size());
        sentences.add(sentence);
}//end method











}//end class