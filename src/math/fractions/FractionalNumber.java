/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package math.fractions;

import javax.swing.JOptionPane;
import static java.lang.Math.*;
/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class FractionalNumber {

    private long numerator;

    private long denominator;

    public FractionalNumber(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

reduce();

    }//end constructor

    public FractionalNumber() {
        this.numerator = 1;
        this.denominator = 1;
    }

    public long getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public long getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
/**
 * Reduces the rational number to its simplest form.
 */
public void reduce(){

    double den = numerator>=denominator?numerator:denominator;

    for(int i=2;i<den;i++){
        if( numerator%i==0 && denominator%i==0){
         numerator/=i;
         denominator/=i;
         while( numerator%i==0 && denominator%i==0 ){
         numerator/=i;
         denominator/=i;
         }//end while

        }//end if
       
if(numerator == 1 || denominator == 1){
    break;
}
else if( isEven(numerator) && isPowerOf2(numerator) &&!isEven(denominator) ){
    break;
}
 else if( isEven(denominator) && isPowerOf2(denominator) &&!isEven(numerator) ){
    break;
}
    }//end for


}//end method
/**
 * 
 * @param num The number
 * @return true if the number is a power of 2 i.e 1,2,4,8,16........
 */
public static boolean isPowerOf2( long num){
    double x = log(num)/log(2);
    return ( x % 2.0 == 0 )||( x%2.0 == 1 );
}

/**
 *
 * @param num The number that we wish to check
 * @return true if the number is even.
 */
public static boolean isEven(long num){
    return num % 2 == 0;
}


public FractionalNumber add(FractionalNumber rationale){
return new FractionalNumber( numerator*rationale.denominator + denominator * rationale.numerator   , denominator*rationale.denominator);
}

public FractionalNumber minus(FractionalNumber rationale){
return new FractionalNumber( numerator*rationale.denominator - denominator * rationale.numerator   , denominator*rationale.denominator);
}


public FractionalNumber multiply(FractionalNumber rationale){
    return new FractionalNumber(numerator*rationale.numerator, denominator*rationale.denominator);
}

public FractionalNumber divide(FractionalNumber rationale){
    return new FractionalNumber(numerator*rationale.denominator, rationale.denominator*rationale.numerator);
}






    @Override
public String toString(){
    return String.valueOf(numerator)+"/"+String.valueOf(denominator);
}


    public static void main(String args[]){
        FractionalNumber  rational1 = new FractionalNumber(75,20);
        FractionalNumber  rational2 = new FractionalNumber(2,5);

        System.out.println(rational1); 
rational1.getDenominator();

        int d1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the numerator"));
        int d2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the denominator"));

        FractionalNumber num = new FractionalNumber(d1,d2);

        JOptionPane.showMessageDialog(null, num);


    }


}//end class