/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public enum Mode {
    CALCULATOR,COMMAND;

    public boolean isCalculator(){
        return this==CALCULATOR;
    }
public boolean isCOMMAND(){
    return this==COMMAND;
}
}
