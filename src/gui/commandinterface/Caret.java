/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.commandinterface;

import com.github.gbenroscience.parser.STRING;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class Caret implements Runnable{
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
 * The line on the parent Document object
 * that this object is on.
 * The row is zero based.
 */
private int row;
/**
 * The column on the parent Document object
 * that this object is on.
 *
 * The number of columns can differ per
 * row. The number of columns depends on the number
 * of characters entered on the row or line.
 * The column is zero based.
 *
 */
private int column;

private final Thread timer;
/**
 * The rate at which the Thread blinks on the parent
 * Document object.
 */
private int blinkRate;
/**
 * If true then this object will
 * be rendered.
 */
private boolean visible;



private static Document document;


private CaretState state;

    public Caret() {
this.color = Color.blue;
this.blinkRate = 500;
this.visible = true;
this.row = 0;
this.column=0;
this.state = CaretState.INITIAL_TYPING;
this.timer = new Thread(this);
timer.start();
    }

/**
 *
 * @param g The Graphics object used to draw.
 * @param doc The Document object that will use this Caret object.
 */
public void draw(Graphics g){
    if(isVisible()){
    g.setColor(color);
    Font f = document.getFont();
    f = new Font( f.getFamily(),Font.BOLD,f.getSize());
    g.setFont(f);
    Point location = getLocation();
drawCaret:{
    Rectangle r = new Rectangle(location.x, location.y, 14, 4);
    g.fill3DRect(r.x, r.y, r.width, r.height, true);
}


    }//end if
}//end method draw


    public static void setDocument(Document document) {
        Caret.document = document;
   }

    public static Document getDocument() {
        return document;
    }
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
 * Please call this method only when you are sure that
 * you want to set the column attribute to the parameter
 * specified.
 * No security checkings for the value are allowed
 * for the row attribute here.
 *
 * If you are not so sure, then call the setPosition(row,col)
 * method instead. It supplies data checking and all.
 * </b>
 */
   public void setColumn(int column){
this.column = column;
    }//end method

    public int getRow() {
        return row;
    }
/**
 *
 * @param row The column to set the attribute to.
 * <b color = 'red'>
 * Please call this method only when you are sure that
 * you want to set the row attribute to the parameter
 * specified.
 * No security checkings for the value are allowed
 * for the row attribute here.
 *
 * If you are not so sure, then call the setPosition(row,col)
 * method instead. It supplies data checking and all.
 * </b>
 */
    public void setRow(int row) {
 this.row= row;
    }
    /**
     *
     * @param row The row that the position will lie on.
     * @param col The column that the position will lie on.
     */
public void setPosition( int row , int col){
  int row$Col[] = normalizePosition(row, col);
    setRow(row$Col[0]);
    setColumn(row$Col[1]);
}

    /**
     * Sets the row and the column of this Caret object
     * using a knowledge of the index that we wish to move
     * it to in the parent Document object's text.
     * @param index The index of the position in the
     * parent Document object's text.
     */
public void setPosition( int index ){
    int[]row$Col=indexToRow$Column(index);
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
 * @return the Point object that represents the current
 * location that  corresponds to the row and column attributes.
 */
    public Point getLocation() {
    return convertRow$ColumnToPoint(row, column);
    }

/**
 *
 * @param row The row.
 * @param col The column
 * @return The Point object corresponding
 * to the row in question.
 */
public static Point convertRow$ColumnToPoint( int row , int col ){
        int[] normalized = normalizePosition(row, col);
        int index = row$ColumnToIndex(normalized[0], normalized[1]);
return document.indexToLocation(index);
}//end method

/**
 *
 * @param row The row that the character lies on.
 * @param column The column that the character lies on.
 * @return The Rectangle enclosing the character.
 */
public static Rectangle getCharRectangle( int row,int column ){
int[] row$Col = normalizePosition(row, column);
int index = row$ColumnToIndex(row$Col[0], row$Col[1]);
String textAtIndex = document.getText().substring(index, index+1);
return new Rectangle( document.indexToLocation(index) ,
        new Dimension(document.getCaret().getCurrent().getTextWidth(textAtIndex),
        document.getCaret().getCurrent().getTextHeight()) );
}//end method

/**
 *
 * @param index The index of the character
 * in the parent Document object's underlying text.
 * @return The Rectangle enclosing the character.
 */
public static Rectangle getCharRectangle( int index ){
String textAtIndex = document.getText().substring(index, index+1);
return new Rectangle( document.indexToLocation(index) ,
        new Dimension(document.getCaret().getCurrent().getTextWidth(textAtIndex),
        document.getCaret().getCurrent().getTextHeight()) );
}//end method
/**
 *
 * @param row The row that the character lies on.
 * @param column The column that the character lies on.
 * @return The location of this character.
 */
public static Point getCharLocation( int row,int column ){
int[] row$Col = normalizePosition(row, column);
int index = row$ColumnToIndex(row$Col[0], row$Col[1]);
return getCharLocation(index);
}//end method
/**
 *
 * @param index The index of the character
 * in the parent Document object's underlying text.
 * @return The location of this character.
 */
public static Point getCharLocation( int index ){
return new Point( document.indexToLocation(index) );
}//end method
/**
 *
 * @return The character at the point where the caret currently is.
 */
public String getChar(){
    int index = getIndex();
    return document.getText().substring(index,index+1);
}

/**
 * @param p The Point at which we wish to determine the
 * occupying character.
 * @return The character at any Point p
 * in the parent Document of this Caret.
 */
public String getCharAt( Point p ){
     int row$Col[] = convertLocationToRow$Column(p);
    return document.getScanner().get(row$Col[0]).substring(row$Col[1], row$Col[1]+1);
}
/**
 * @param index The index of the character in
 * the parent Document's text.
 * @return The character at the specified index
 * in the text of the parent Document of this Caret.
 */
public String getCharAt( int index ){
    return document.getText().substring(index, index+1);
}

/**
 * @param index The index of the character in
 * the parent Document's text.
 * @return The character at the given row
 * and column in the parent Document of this Caret.
 */
public String getCharAt( int row , int column ){
    return document.getScanner().get(row).substring(column, column+1);
}



/**
 *
 * @param p The Point object to convert
 * to row and column values.
 */
public static int[] convertLocationToRow$Column( Point p ){
String text = document.getText();
int len = text.length();

// Check for the one that contains the point and return its
//row and column as the one that contains the point
for( int i=0; i<len;i++){
    Rectangle r = getCharRectangle(i);
    if (r.contains(p)){
        return indexToRow$Column(i);
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

for( int i=0; i<len;i++){
    Point pt = getCharLocation(i);
    dx[i]=Math.abs(pt.x-p.x);
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

for( int i=0; i<len;i++){
    Point pt = getCharLocation(i);
    dy[i]=Math.abs(pt.y-p.y);
}//end for loop




try{

double shortestX=dx[0];
double shortestY=dy[0];

int indX=0;
int indY=0;


for( int i=1; i<len;i++){
   if( shortestX > dx[i] ){
       shortestX=dx[i];
       indX=i;
   }//end if
      if( shortestY > dy[i] ){
       shortestY=dy[i];
       indY=i;
   }//end if
}//end for loop

int arrX[] = indexToRow$Column(indX);
int arrY[] = indexToRow$Column(indY);


int nearestRow = arrY[0];
int nearestCol = arrX[1];

 return new int[]{nearestRow,nearestCol};

    }//end try
catch( IndexOutOfBoundsException boundsException){
    return indexToRow$Column(0);
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
 * @param index An index in the Document object..usually
 * it is the index of a Caret object
 * in the Document object. Also the index of the character
 * in the parent Document object that this Caret is currently on.
 * @return an array of 2 int values.
 * The first value is the corresponding row
 * The second value is the corresponding column.
 * @throws IndexOutOfBoundsException if the index does
 * not map to any row or column in the parent Document.
 */
public static int[] indexToRow$Column( int index ) throws IndexOutOfBoundsException{
ArrayList<String> scan = document.getScanner();
int sz=scan.size();
int len = document.getText().length();
if(len == 0){
    return new int[]{0,-1};
}
else if( index < 0) {
    return new int[]{0,-1};
}

 else if (index >= 0) {

     if(index>=len){
         index=len-1;
     }

int drow =-1;
int dcol=-1;
int count=0;
int rowLen=0;
while( ( index - (rowLen = scan.get(count).length()) ) >= 0 ){
index-=rowLen;
++count;
}//end while
drow = count;dcol=index;
return new int[]{drow,dcol};
}//end else if
throw new IndexOutOfBoundsException("Terrible Error! Cannot Be Accounted For. Index System Has Crashed!index = "+index);
}//end method


/**
 *
 * @param row The row.
 * @param column The column.
 * @return Takes a row and column value
 * and returns the index they refer to in
 * the parent Document's text.
 *
 */
public static int row$ColumnToIndex( int row , int column ){

    if( !document.getText().isEmpty() ){

        ArrayList<String> store = document.getScanner();
        int sz = store.size();

        int accIndex=0;
        for(int i=0;i < sz; i++){

int syz = store.get(i).length();
if( i < row ){
accIndex+=syz;
}//end if
 else if( i == row ){
     accIndex+=(column);
     break;
 }//end else if

        }//end for loop



int len = document.getText().length();


 if( accIndex < len ){
return accIndex;
        }//end if
 else{
    throw new IndexOutOfBoundsException("Row&Column "+"["+row+", "+column+"] reported at index = "+accIndex
           + "\nare outside document index '"+(len-1)+"'");
 }//end else
    }//end if
//empty document
    else{
        return -1;
    }

}//end method




/**
 *
 * @param row The row
 * @param column The column which may spill-over or kick-back.
 * To spill-over means that the column is larger than the column
 * size for the specified row.
 * To kick-back means that the column is less than zero
 * which is the minimum column index for any given row
 * (i.e a row must contain at least one element)
 *
 * @return The appropriate row and column, by shifting back along the
 * rows till the negative column becomes zero.
 * The interpretation for kick-back is:
 * Let row 3 contain 4 columns and let
 * row 2 contain 8 columns:
 * Attempting to move -6 columns on row 3 would cause a kick-back onto row 2:
 * row = 3,column = -6 ==== row = 2 column = 2.
 *
 * The interpretation for spill-over is:
 * Let row 3 contain 4 columns and let
 * row 4 contain 11 columns:
 * Attempting to move 12 columns on row 3 would cause a spill-over onto row 4:
 * row = 3,column = 12 ==== row = 4 column = 7.
 *
 */
public static int[] normalizePosition( int row , int column ){
ArrayList<String>scan = document.getScanner();
int accIndex=0;
   for( int i=0; i<scan.size();i++ ){
int rowLen = scan.get(i).length();
if( i < row ){
    accIndex+=rowLen;
}//end if
 else if( i==row ){
    accIndex+=(column);
    break;
}//end else if
}//end for

  return indexToRow$Column(accIndex);
}//end method.
/**
 *
 * @return an int array
 * containing two int values.
 * In the first index is the row
 * of this Caret object.
 * In the second index is the column
 * of this Caret object.
 *
 */
public int[] getPosition(){
return new int[]{row,column};
}
/**
 *
 * @param row The row in consideration
 * @return the number of characters on the row.
 */
/**
 * A simple number denoting
 * the index of the character in the
 * Document object that this Caret object is on.
 */
    public int getIndex() {
        if( document.getText().isEmpty() ){
            return -1;
        }//end if
else{
   int[] row$Col = normalizePosition(row, column);
return row$ColumnToIndex(row$Col[0], row$Col[1]);
        }//en//end else
}//end method

public int numOfItemsOnRow( int row ){
return document.getScanner().get(row).length();
}

/**
 *
 * @param start The index to start copying from.
 * @param end The index to stop copying from.
 * @return a character string consisting of all characters
 * found between start and end-1 in the document object.
 */
 public String copyData(int start,int end){
return document.getText().substring(start, end);
    }


/**
 *
 * @param start The index to start copying from.
 * It will stop copying at the index or position
 * of this caret object.
 * @return a character string consisting of all characters
 * found between start and end-1 in the document object.
 */
    public String copyData(int start){
return document.getText().substring(start, getIndex());
    }//end method











    @Override
    public void run() {
       Thread me = Thread.currentThread();
       while( timer==me ){
           try{
Thread.currentThread().sleep(blinkRate);

if( isVisible() ){
    setVisible(false);
}
 else if(!isVisible()){
     setVisible(true);
 }


ArrayList<Sentence>sentences=document.getSentences();
int sz=sentences.size();
for(int i=0;i<sz;i++){
    if(sentences.get(i).getText().isEmpty()){
     sentences.remove(i);
    }//end if
}

       }//end try
           catch(InterruptedException intEx){

           }//end catch
       }//end while loop

    }



/**
 *
 * This method assumes that a row exists
 * above the Caret object that it can jump to.
 * @param line Jumps to the line above.
 * If the point exactly above this Caret object
 * is a valid Point on the line above it, it occupies
 * that Point, else it jumps to the closest Point object
 * to it on the line.
 */
public void moveUp() {
setPosition(row-1,column);
}//end method

/**
 * This method assumes that a row exists
 * above the Caret object that it can jump to.
 * @param col The column to jump to on the row above the
 * current one.
 */
public void moveUp(int col) {
    setPosition(row-1, col);
}//end method

/**
 * This method assumes that a row exists
 * beneath the Caret object that it can jump to.
 *
 * @param line Jumps to the line below.
 * If the point exactly below this Caret object
 * is a valid Point on the line below it, it occupies
 * that Point, else it jumps to the closest Point object
 * to it on the line.
 */
public void moveDown(){
    setPosition(row+1,0);
}//end method
/**
 * This method assumes that a row exists
 * beneath the Caret object that it can jump to.
 * @param col The column to jump to
 * on the row below the current one
 */
public void moveDown( int col ){
    setPosition(row+1,col);
}


/**
 * Jumps to the top of the page.
 */
public void gotoTopOfPage(){
setPosition(0,0);
}
/**
 * Jumps to the bottom of the page.
 */
public void gotoBottomOfPage(){
    ArrayList<String> scan = document.getScanner();
    int lastRow = scan.size()-1;
    int lastCol = scan.get(lastRow).length()-1;
setPosition(lastRow,lastCol);
}





/**
 *
 *
 * @param line Moves this object backwards by a step
 * on the parent Document object.
 */
public void shiftBackwards() {
    setPosition(row, column-1);
}//end method

/**
 * @param line Moves this object backwards by a step
 * on the parent Document object.
 */
public void shiftBackwards( int step ) {
    setPosition(row,column-step);
}//end method


/**
 * Moves this object backwards by a step size of 1
 * on the parent Document object.
 */
public void shiftForwards() {
    setPosition(row, column+1);
}//end method

/**
 * @param step  Moves this object backwards by the step size
 * on the parent Document object.
 */
public void shiftForwards( int step ) {
    setPosition(row, column+step);
}//end method



/**
 *
 * The backSpace action
 */
public void delete(){
getCurrent().handleBackSpaceKey();
}//end method


/**
 * Removes all characters between startIndex and endIndex..
 * both of them inclusive.
 * @param index The index from which data is to be deleted.
 */
public void delete( int endIndex ){
     int startIndex = getIndex();

//ensure that the startIndex is always the lesser.
     if(startIndex > endIndex){
         int swap = endIndex;
         endIndex=startIndex;
         startIndex = swap;
     }//end if

for( int i=endIndex;i>=startIndex;i--){
delete();
}//end for loop
}//end method



/**
 * Splits a <b color ='red'>row</b>
 * on the position of the caret.
 * @return an array of 2 elements containing
 * in the first index, all of the text on the row before the caret
 * position and in the second index, all of the text on the row after
 * the caret position.
 */
private String[] splitRowTextOnCaretPosition(){

    String rowText="";


try{
 rowText = document.getScanner().get(row);
   }//end try
catch(IndexOutOfBoundsException ioobe){
 System.out.println("Cannot Split Empty Row");
    return new String[]{"",""};
}//Empty row


  String textB4Caret = "";
  String textAfterCaret = "";

  int len =rowText.length();
  if( column < len){
 textB4Caret = rowText.substring(0 , column+1);
  textAfterCaret = rowText.substring(column+1);
  return new String[]{textB4Caret,textAfterCaret};
  }//end if
  else if( column >= len ){
  textB4Caret = rowText;
  textAfterCaret="";
  setPosition(row,len-1);
  }//end else if

return new String[]{textB4Caret,textAfterCaret};

}//end method

/**
 * Splits the <b color ='red'> parent Document object's text </b>
 * on the position of the caret.
 * @return an array of 2 elements containing
 * in the first index, all of the text in the parent Document before the caret
 * position and in the second index,all of the text in the parent Document after
 * the caret position.
 */
private String[] splitDocumentTextOnCaretPosition(){
    String allText =   document.getText();
     int index = getIndex();
  String textB4Caret = "";
  String textAfterCaret = "";

  try{
  textB4Caret = allText.substring(0 , index+1);
  textAfterCaret = allText.substring(index+1);
  }//end try
  catch(IndexOutOfBoundsException boundsException){
  textB4Caret = allText;
  textAfterCaret="";
  }
  return new String[]{textB4Caret,textAfterCaret};
}

public void print( String msg){
       JOptionPane.showMessageDialog(null,msg);
}
/**
 *
 * Run this code whenever the user presses the enter key.
 *
 */
public void handleEnterKey(){
getCurrent().handleEnterKey();
}//end method

/**
 *
 * @return the Sentence in which this Caret resides
 * currently.
 * @throws NullPointerException if the Caret's position
 * does not contain any Sentence object which could happen
 * if proper initialization of the Document is not carried out.
 */
public Sentence getCurrent() throws NullPointerException{
     ArrayList<Sentence>sentences= document.getSentences();


   //  System.out.println(sentences.isEmpty());

    if(sentences.isEmpty()){
     document.appendSentence();
    }
    /**
     * Record the position of the Caret
     * in the parent Document object.
     */
    int index = getIndex();
    if( index < 0 ){
        index = 0;
        return sentences.get(0);
    }

    int sz=sentences.size();
    for(int i=0;i<sz;i++){
        if( sentences.get(i).contains(index) ){
            return sentences.get(i);
        }//end if
    }//end for loop
System.out.println("num of sentences = "+sz);
return null;
}


/**
 *
 * @param text The text to write into the parent Document object.
 */
public void write( String text ){  
getCurrent().write(text);
    }//end write method



/**
 * Reduces a row that has more text than it should hold
 * to a number of characters that can fit on the row.
 * @param text The overflowing text
 * @return the number of characters counting from the
 * beginning of the row that will maximally fit
 * on the row.
 * If the text on the row has not overflown the row
 * limit,it will return the number of characters in this text.
 */
public static int reduceRowToMaxCharsAllowed( String text){
 int len = text.length();
 int maxWidth = document.getLineWidth();
 int i=0;
 int minDistance = document.getCaret().getCurrent().getTextWidth("@");
  for(i=0;i<len;i++){
      String cutText = text.substring(0,i+1);
      if(document.getCaret().getCurrent().getTextWidth(cutText)+minDistance>maxWidth){
return i+1;
      }//end if

  }//end for loop
return i+1;
}


/**
 *
 * @param row The row in question
 * @return true if the row can still take more characters
 * and false otherwise..
 */
public static boolean rowCanTakeMoreText( int row ){
  ArrayList<String>scan = document.getScanner();
String text="";
  try{
 text = scan.get(row);
return textCanStillEnterDocumentRow(text);
  }//end try
  catch(IndexOutOfBoundsException ind){
      return true;
  }
  catch(NullPointerException nol){
      return true;
  }
}//end method.


/**
 *
 * @param row The row in question
 * @return true if, when placed in a Document row more text can still be added to the row.
 */
public static boolean textCanStillEnterDocumentRow( String text ){
int rowWidth = document.getCaret().getCurrent().getTextWidth(text);
int maxWidth = document.getLineWidth();
/**
 * The @ character has perhaps the largest
 * font size of characters in many fonts.
 * Use it as the minimum distance between
 * the row width and the maximum width.
 * If the row width < maxWidth but
 *  row width+min-distance >= maxWidth,
 * then assume that the row is filled.
 *
 */
int minDistance = document.getCaret().getCurrent().getTextWidth("@");

/**
 * Check if:
 * 1. The row width is equal to the maximum width.
 * 2. The row width is less than the maximum width
 * but the row width+the average character width is
 * greater than the maximum width
 * If 1. is true...return true.
 * If 2 is true, return true.
 * Else return false.
 */
if( rowWidth+minDistance <= maxWidth){
    return true;
}
 else{
    return false;
 }
}//end method.




/**
 *
 * @param row The row in question
 * @return true if the row has exceeded the maxWidth
 * allowed for a row by the parent Document object.
 */
public static boolean rowIsOverFlowing( int row ){

      ArrayList<String>scan = document.getScanner();
String text="";
  try{
 text = scan.get(row);
return textOverFlowsDocumentRow(text);
  }//end try
  catch(IndexOutOfBoundsException ind){
      return false;
  }
  catch(NullPointerException nol){
      return false;
  }
}//end method.

/**
 *
 * @param text The text in question
 * @return true if the text overflows a row in the parent Document object.
 * <b color ='red'>NOTE This method returns false if
 * the text is larger than the row.</b>
 */
public static boolean textOverFlowsDocumentRow( String text ){
int rowWidth = document.getCaret().getCurrent().getTextWidth(text);
int maxWidth = document.getLineWidth();
return rowWidth > maxWidth;
}//end method.
/**
 *
 * @param row The row in question
 * @return true if the row has at least equaled the maxWidth allowed
 * for a row by the parent Document object.
 * <b color ='red'>NOTE</b> This method returns false if
 * the text on the row is larger than its recommended value.
 */
public static boolean rowIsFilled( int row ){

      ArrayList<String>scan = document.getScanner();
String text="";
  try{
 text = scan.get(row);
return textFillsDocumentRow(text);
  }//end try
  catch(IndexOutOfBoundsException ind){
      return false;
  }
  catch(NullPointerException nol){
      return false;
  }
}//end method.

/**
 *
 * @param text The text in question
 * @return true if the text fits a row in the parent Document object.
 * <b color ='red'>NOTE</b> This method returns false if
 * the text is larger than the row.
 */
public static boolean textFillsDocumentRow(String text){

int rowWidth = document.getCaret().getCurrent().getTextWidth(text);
int maxWidth = document.getLineWidth();
/**
 * The @ character has perhaps the largest
 * font size of characters in many fonts.
 * Use it as the minimum distance between
 * the row width and the maximum width.
 * If the row width < maxWidth but
 *  row width+min-distance >= maxWidth,
 * then assume that the row is filled.
 *
 */
int minDistance = document.getCaret().getCurrent().getTextWidth("@");

/**
 * Check if:
 * 1. The row width is less than the maximum width
 * but the row width+the average character width is
 * greater than the maximum width
 * 2. The row width is equal to the maximum width.
 * If 1. is true...return true.
 * If 2 is true, return true.
 * Else return false.
 */
 if( rowWidth<maxWidth && (rowWidth + minDistance) > maxWidth ){
     return true;
 }
 else if(rowWidth == maxWidth) {
    return true;
}
 else{
    return false;
 }
}//end method



    @Override
    public String toString() {

        String caretData="row = "+row+", column = "+column+", color = "+color+"\n"
                        +",location = "+getLocation()+", index = "+getIndex();

        return caretData+"\n"+super.toString();
    }











}//end class

enum CaretState{

INITIAL_TYPING,INSERTING,DELETING,ENTERING,HIGHLIGHTING,COPYING,PASTING;


/**
 *
 * @return true if the Caret is being used to type
 * in the parent Document object without any click being
 * performed yet to insert text somewhere else in the document
 * outside the basic flow of text input.
 */
public boolean initialTyping(){
    return this == INITIAL_TYPING;
}
/**
 *
 * @return true if the Caret has being clicked somewhere
 * in the body of the Document in order to insert text in that
 * location.
 */
public boolean isInserting(){
    return this == INSERTING;
}
/**
 *
 * @return true if the Caret is deleting
 * items from the Document.
 */
public boolean isDeleting(){
    return this == DELETING;
}
/**
 *
 * @return true if the Enter Key is pressed.
 */
public boolean isEntering(){
    return this == ENTERING;
}
/**
 *
 * @return true if the Caret is being used to
 * highlight text.
 */
public boolean isHighlighting(){
    return this == HIGHLIGHTING;
}
/**
 *
 * @return true if the Caret has been told to copy
 * text
 */
public boolean isCopying(){
    return this == COPYING;
}
/**
 *
 * @return true if the Caret has been told to paste text.
 */
public boolean isPasting(){
    return this == PASTING;
}
}//end enum CaretState