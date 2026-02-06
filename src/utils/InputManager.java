/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import com.github.gbenroscience.parser.*;
import java.util.ArrayList;
import javax.swing.text.JTextComponent;

/**
 *
 * @author GBEMIRO
 */
public class InputManager {
private String expr;
private ArrayList<String> store=new ArrayList<String>();
/**
 * Creates a new InputManager object
 * @param expr the expression entered into the system
 */
    public InputManager(String expr) {
        this.expr = expr;
    store=new MathScanner(expr).scanner();
    }
/**
 *
 * @param expr sets the expression enterd into the system.
 */
    public void setExpr(String expr) {
        this.expr = expr;
        store=new MathScanner(expr).scanner();
    }
/**
 *
 * @return the expression enterd into the system.
 */
    public String getExpr() {
        return expr;
    }
/**
 *
 * @param store sets the storage for the scanned data.
 */
    public void setStore(ArrayList<String> store) {
        this.store = store;
    }
/**
 *
 * @return the storage for the scanned data.
 */
    public ArrayList<String> getStore() {
        return store;
    }








public void modifyInput(JTextComponent inputField){


    String modified="";
    for(int i=0;i<store.size();i++){
        if(Operator.isOperatorString(store.get(i))){
        modified+=" "+store.get(i);

        }
        else{
           modified+=store.get(i);
        }
    }



    try{
     int size=store.size();
     if( Operator.isOperatorString(store.get(size-1)) ){
      store.add(size-1, " ");
      //String modified="";
      size=store.size();
      for(int i=0;i<size;i++){
          modified+=store.get(i);
      }//end for
     inputField.setText( modified );
     }//end if
}//end try
      catch(IndexOutOfBoundsException indErr){

      }

}




























}
