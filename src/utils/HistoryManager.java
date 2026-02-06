/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author GBEMIRO
 */

import java.util.ArrayList;
import javax.swing.JTable;
public class HistoryManager{
public ArrayList<String>history=new ArrayList<String>();

    public HistoryManager(){
    }

    private int getRowIndex( JTable table ) {
        TableUtils.setTable(table);
        return TableUtils.lastEditedRow();
    }

 
    private void recordHistory(String expr,JTable table){
        history.add(0,expr);
        TableUtils.setTable(table);
    }
    public void deleteHistory(int index){
        history.remove(index);
        TableUtils.removeRow(index);
        TableUtils.clearEmptyRows();
    }
    public void deleteHistory(String expr){
        int index = history.indexOf(expr);
        history.remove(expr);
        TableUtils.removeRow(index);
        TableUtils.clearEmptyRows();
    }
    public void clearHistory(){
        history.clear();
        TableUtils.clearTableData();
    }
/**
 * Displays the Variables managed by objects of this class on a table
 * @param expr The expression to be recorded.
 * @param table the displaying table.
 */
public void writeHistoryToTable(String expr ,JTable table){

recordHistory(expr,table);
int rowIndex = getRowIndex(table);

TableUtils.insert(new Object[]{Integer.valueOf(0),history.get(0), Time.timeGetter()  }, 0);

for( int i = 0; i <= rowIndex+1;  i++){
    table.setValueAt(String.valueOf(i), i, 0); 
}

}//end method writeVariablesToTable


}//end class