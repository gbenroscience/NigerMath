/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This class provides static methods that can be used in manipulating
 * a JTable object.
 *
 * @author GBEMIRO
 */
public class TableUtils {
private static JTable table;

public TableUtils(JTable table){
    TableUtils.table=table;
    TableUtils.table.repaint();
}

    public static JTable getTable() {
        return TableUtils.table;
    }

    public static void setTable(JTable table) {
        TableUtils.table = table;
    }

/**
 *
 * @param obj the object to search the table for.
 * @return true if the object is found and false otherwise.
 */
public static boolean hasObject( Object obj){
    for(int j=0;j<table.getColumnCount();j++){
      for(int i=0;i<table.getRowCount();i++){
        if(obj.equals(table.getValueAt(i, j))){
            return true;
        }//end if
    }//end inner for loop

    }//end outer for loop
    return false;
}//end method hasObject(args)



/**
 *Searches the table for object along a given column alone.
 * @param obj the object to search the table for.
 * @return true if the object is found and false otherwise.
 */
public static boolean hasObjectOnColumn( Object obj,int column){
      for(int i=0;i<table.getRowCount();i++){
        if(obj.equals(table.getValueAt(i, column))){
            return true;
        }//end if
    }//end inner for loop

    return false;
}//end method hasObject(args)
/**
 *Searches the table for object along a given row alone.
 * @param obj the object to search the table for.
 * @return true if the object is found and false otherwise.
 */
public static boolean hasObjectOnRow( Object obj,int row){
      for(int j=0;j<table.getColumnCount();j++){
        if(obj.equals(table.getValueAt(row, j))){
            return true;
        }//end if
    }//end inner for loop

    return false;
}//end method hasObject(args)




/**CAREFUL!!!
 * This method deletes all entries from a JTable
 */
public static void clearTableData(){
      DefaultTableModel mod = (DefaultTableModel) table.getModel();
 int size=mod.getDataVector().size();
 int i=0;
      while(i<size){
         for(int cols=0;cols<table.getColumnCount();cols++){
           mod.setValueAt(null, i, cols);
       }
       i++;
      }
table.getTopLevelAncestor().repaint();
}
/**
 * This method deletes a single entry from a JTable
 * @param row the row whose entries are to be deleted
 */
public static void removeEntry(int row){
      DefaultTableModel mod = (DefaultTableModel) table.getModel();
       for(int cols=0;cols<table.getColumnCount();cols++){
           mod.setValueAt(null, row, cols);
       }
table.getTopLevelAncestor().repaint();
}


/**CAREFUL!!!
 * This method deletes all rows from a JTable
 */
public static void clearTableRows(){
      DefaultTableModel mod = (DefaultTableModel) table.getModel();
 int size=mod.getDataVector().size();
      while(size>0){
          mod.removeRow(size-1);
           --size;
      }
table.getTopLevelAncestor().repaint();
}
/**
 * This method deletes a single row from a JTable
 * @param row the row whose entries are to be deleted
 */
public static void removeRow(int row){
      DefaultTableModel mod = (DefaultTableModel) table.getModel();
 mod.removeRow(row);
table.getTopLevelAncestor().repaint();
}

/**
 * Adds a row to a JTable object.
 */
public static void addRow(){
       DefaultTableModel mod = (DefaultTableModel) table.getModel();
 mod.addRow(new Vector());
table.getTopLevelAncestor().repaint();
}
/**
 * Writes the array values on a table row.
 * This action is equivalent to an UPDATE command.
 * @param values An array containing string values
 * @param row The row index where the values are to be inserted.
 */
public static void updateRow( Object[] values, int row){

    DefaultTableModel model = (DefaultTableModel)table.getModel();
    int columns = model.getColumnCount();
    int rows = model.getRowCount();
    if( row < rows ){
    if( values.length == columns ){

for( int col = 0; col < values.length; col++ ){
    if( values[col].getClass() == model.getColumnClass(col) ){
model.setValueAt(values[col], row, col);
}
 else{
        JOptionPane.showMessageDialog(null,"TABLE WRITE ERROR!\n WRITE OBJECT TYPE"
                + "\nDOES NOT MATCH DESTINATION COLUMN TYPE!!");
 }

}


table.getTopLevelAncestor().repaint();

return;
    }
 else{
table.getTopLevelAncestor().repaint();
        return;
 }
    }//end if
    else{
        JOptionPane.showMessageDialog(null,"TABLE WRITE ERROR!\n LOCATION "+row+" DOES NOT\n"
                + "EXIST ON THE TABLE.");
    }
    
}//end method

/**
 * 
 * @param value The array containing the values to
 * insert on the row.
 * @param row The index of the row.
 */
public static void insert(Object[]values , int row ){
DefaultTableModel model = (DefaultTableModel)table.getModel();
int columns = model.getColumnCount();
Vector v = model.getDataVector();

v.add(row , new Vector<Object>());
Vector rowData= (Vector) ( v ).elementAt(row);

if( values.length == columns){
for(int col=0; col < values.length;col++){

if( values[col].getClass() == model.getColumnClass(col) ){
rowData.add(values[col]);}
}//end for loop

    }//end if

table.getTopLevelAncestor().repaint();
    }//end method


/**
 * Writes the array values on a table row.
 * This operation will occur even if the
 * row contains values.
 * @param values An array containing string values
 * @param row The row index where the values are to be inserted.
 */
public static void writeOnTable( Object[][] values, int startRow){
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    
    int rows = model.getRowCount();

int diff = rows - (startRow+values.length);
if( diff <= 0 ){
while( true ){
    if( diff <= 0 ){
    addRow();
        ++diff;
    }
 else{
        break;
 }///end else
}//end while loop
}//end outer loop
table.setModel(model);
    for( int row = startRow; row < values.length; row++){
updateRow(values[row], row);
    }//end for

table.getTopLevelAncestor().repaint();
}//end method

/**
 * Removes all empty rows from the table.
 * By empty, we refer to rows containing null
 * or empty strings or only white space.
 * Entries below the empty rows are moved up.
 */
public static void clearEmptyRows(){
    DefaultTableModel model = (DefaultTableModel)table.getModel();
  
Vector v = model.getDataVector();
    int lastEditedRow = lastEditedRow();
for( int i = 0; i <= lastEditedRow; i++){
    try{
Vector rowData= (Vector) ( v ).elementAt(i);
int size = rowData.size();
int j = 0;
int count = 0;

while(j < size){
    if( rowData.get(j) == null || purifier( rowData.get(j).toString() ).isEmpty() ){
      ++count;
    }
  ++j;
}//end while

if( count == size ){
    removeRow(i);
    --i;
    --lastEditedRow;
}
    }
    catch( ArrayIndexOutOfBoundsException arr ){

    }

}//end for

table.getTopLevelAncestor().repaint();
}//end method

/**
 *
 * @return the index of the last row where an entry occurs on the table.
 * This may or may not be the last row on the table.
 * If all rows are empty and un-edited then it returns -1.
 */
public static int lastEditedRow(){
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    int rows = model.getRowCount();
    int cols = model.getColumnCount();

for( int row = rows - 1; row >= 0; row--){
for( int col = 0; col < cols; col++){

Object obj = table.getValueAt(row, col);
if(  obj != null && !purifier( obj.toString() ).equals("") ){
return row;
}//end if
   
}//end inner for
    }//end outer for
    return -1;
    }//end method

/**
 * Grabs the data on a table row
 * and returns it as an array of objects.
 * @param row The row to grab.
 * @return an array containing all values on that row.
 */
public static Object[] getRowData( int row ){
int cols = table.getModel().getColumnCount();
Object[] data = new Object[cols];
for( int col = 0; col < cols; col++){
    data[col] = table.getValueAt(row, col);
}
return data;
}
/**
 * Grabs the data on a table row
 * and returns it as an array of objects.
 * @param column  The column to grab.
 * @return an array containing all values on that column.
 */
public static Object[] getColumnData( int column ){
int rows = table.getModel().getRowCount();
Object[] data = new Object[rows];
for( int row = 0; row < rows; row++){
    data[row] = table.getValueAt(row, column);
}
return data;
}




/**
 *
 * @return an array containing the names
 * of the classes that the objects stored on
 * each column belong to.
 */
public static String[] getColumnTypes(){

int cols = table.getModel().getColumnCount();
String[] data = new String[cols];
for( int col = 0; col < cols; col++){
    data[col] = table.getValueAt(0, col).getClass().getSimpleName();
}
return data;
}


/**
 * purifier allows you to remove all white spaces in a given string,which it receives as its argument
 * @param h The string to free from white space.
 * @return a string devoid of all white space.
 */
private static String purifier(String h){
    String y="";
    for(int i=0;i<h.length();i++){
      if(!h.substring(i,i+1).equals("")&&!h.substring(i,i+1).equals(" ")&&!h.substring(i,i+1).equals("\n")){
          y+=h.substring(i,i+1);
      }
 }
    return y;
}



}//end class TableUtils