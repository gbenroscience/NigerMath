/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GBEMIRO
 */
public class FormulaeManager {
    ArrayList<Formulae>formulaStore=new ArrayList<Formulae>();


/**
 * records formulae as they are stored by the user
 * @param expr the formula to store
 */
    public void recordFormula(String expr){
        Formulae formulae=new Formulae(expr);
        if(!hasFormula(formulae)){
        formulaStore.add( formulae ); 
        } 
    }
    /**
     * deletes Formulae objects based on their indices
     * @param index the position of the Formulae object within the store
     */
    public void deleteFormula(int index){
        formulaStore.remove(index);
    }
    /**
     * deletes Formulae objects based on their original expressions
     * @param index the position of the Formulae object within the store
     */
    public void deleteFormula(String expr){
        for(int i=0;i<formulaStore.size();i++){
            Formulae form = new Formulae(expr);
            if(formulaStore.get(i).getExpression().equals(form.getExpression())&&
                    formulaStore.get(i).getVariable().equals( form.getVariable() )  ){
                formulaStore.remove( i );
                break;
            }//end if
        }
        
    }

    /**
     *
     * @param formula
     * @return true if the Formulae manager already contains this
     * Formulae object.
     */
    public boolean hasFormula(Formulae formula){

    for(int i = 0;i<formulaStore.size();i++){
    if(  (formulaStore.get(i).getVariable()+"="+formulaStore.get(i).getExpression()).toUpperCase().equals(
            (formula.getVariable()+"="+formula.getExpression()).toUpperCase())  ){
        return true;
    }//end if


    }//end for loop
return false;
    }//end method


    /**
     * deletes all Formulae objects in the storage.
     */
    public void clearFormula(){
        formulaStore.clear();
    }








    /**
 * displays the Variables managed by objects of this class on a table
 * @param table the displaying table
 */
public void writeFormulaeToTable(JTable table){

    for(int j=0;j<table.getColumnCount();j++){
  for(int i=0;i<formulaStore.size();i++){
 if(i>=table.getRowCount()){
     try{
      Vector<Object>obj=new Vector<Object>();
      DefaultTableModel mod = (DefaultTableModel) table.getModel();
      mod.addRow(obj);
      table.setModel(mod);
     }
     catch(NullPointerException nolian){

     }
     catch(IndexOutOfBoundsException indErr){

     }
  }//end if
Formulae formulae=formulaStore.get(i);

if(j==0){
  table.setValueAt(i, i, j);
}
else if(j==1){
 table.setValueAt(formulae.getVariable(),i, j);
}
else if(j==2){
 table.setValueAt(formulae.getExpression(),i, j);
}
else if(j==3){
 table.setValueAt(formulae.getDescription(),i, j);
}

  }//end inner for
    }//end outer for

}//end method writeVariablesToTable






}
