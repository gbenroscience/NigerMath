/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.commandline;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public enum CommandParserOutputState{

    TEXT,DISPLAY,ACTION;


    //If the parser output will generate text
      public boolean isText(){
        return this==TEXT;
    }
      //if the parser output will generate a frame on which data is displayed
      public boolean isDisplay(){
        return this==DISPLAY;
    }
      //if the parser output will generate an action such as playing saving or opening a file and so on.
      public boolean isAction(){
        return this==ACTION;
    }


}
