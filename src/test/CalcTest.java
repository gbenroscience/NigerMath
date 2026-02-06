/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import utils.MathExpressionManager;

/**
 *
 * @author JIBOYE, OLUWAGBEMIRO OLAOLUWA
 * 
 */
public class CalcTest {

    public static void main(String[] args) {
        MathExpressionManager manager = new MathExpressionManager();
        
        String problem = "r=4;r*6";
        System.out.println("Problem: "+problem+"\n Result: "+ manager.solve(problem));
        
        
        System.out.println("apple".compareTo("banana"));
    }
   
    
}