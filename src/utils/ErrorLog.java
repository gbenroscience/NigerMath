/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class ErrorLog{
final ArrayList<String>logBook = new ArrayList<String>();



public void writeLog(String error){
logBook.add(error);
}
/**
 * Displays the contents of this logBook on a JTextArea object and then clears its memory.
 * @param area
 */
public void printLog(final JTextArea area){
    for(int i=0;i<logBook.size();i++){
        final int I=i;
        SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
     area.append(logBook.get(I)+"\n");
                }
            });

    }
   clearLog();
}

public void printLog(){
    System.out.println(logBook+"\n"); 
    clearLog();
}

public void clearLog(){
    logBook.clear();
}


}
