/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 *@author JIBOYE Oluwagbemiro Olaoluwa
 */
public class SpeedTest {


    public static void main( String args[] ){

            System.out.println(System.getProperty("java.library.path"));


//start test
double start = System.nanoTime();
//test code

start = (System.nanoTime() - start)/(1.0E6);
//print code run time
System.out.println( "---- EXECUTED IN "+start+" ms");



//start another test
start = System.nanoTime();
//test code

start = (System.nanoTime() - start)/(1.0E6);
System.out.println( "---- EXECUTED IN "+start+" ms");






    }//end main

}
