/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.commandinterface;

import java.util.Random;

/**
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class NewClass {

/**
 *
 * @param index An index in the Document object..usually
 * it is the index of a Caret object
 * in the Document object. Also the index of the character
 * in the parent Document object that this Caret is currently on.
 * @return an array of 2 int values.
 * The first value is the corresponding row
 * The second value is the corresponding column.
 * @throws IndexOutOfBoundsException if the index does
 * not map to any row or column in the parent Document.
 */
public static int[] indexToRow$Column( int index ,int[][]array ) throws IndexOutOfBoundsException{

if( index < 0 ){
    return new int[]{0,-1};
}

 else if (index >= 0) {
int count=0;
int rowLen=0;
while( ( index - (rowLen = array[count].length) ) >= 0 ){
index-=rowLen;
++count;
}//end while
return new int[]{count,index};




}//end else if
throw new IndexOutOfBoundsException("Terrible Error! Cannot Be Accounted For. Index System Has Crashed!index = "+index);
}//end method

 static void print1DArray(int array[]){
    for( int obj:array){
        System.out.print(obj); System.out.print(", ");
    }
    System.out.println();
}//end method
public static void print2DArray(int array[][]){
    for( int obj[]:array){
        print1DArray(obj);
    }
}//end method


public static int[] randomSelectIndicesFromArray(int numberOfSelections,  int array2D[][] ){
Random r = new Random();
int[]choices=new int[numberOfSelections];
int len = array2D.length;
for( int i=0; i < numberOfSelections; i++ ){
    int selectRow = r.nextInt(len);
    int selectColumn = r.nextInt(array2D[selectRow].length);
choices[i]=array2D[selectRow][selectColumn];
}//end for loop
return choices;
}

    public static void main( String args[] ){

int indexArr[][]= new int[][]{
{0,1,2,3},
{4,5,6},
{7,8,9,10,11,12,13,14,15},
{16,17,18,19,20,21,22,23},
{24,25,26,27,28,29},
{30,31,32,33,34,35,36,37,38,39,40},
{41,42,43,44,45,46,47,48},
{49,50,51,52,53,54,55,56},
{57,58,59,60,61,62,63,64,65,66},
{67,68,69,70,71,72,73,74,75,76}
};
System.out.println( "THE TABLE IS OUTPUTTED AUTOMATICALLY BELOW: " );
print2DArray(indexArr);
System.out.println(" Now Randomly Picking Indices From Table");

int[] testIndices= randomSelectIndicesFromArray(10, indexArr);

System.out.println(" Indices Selected From Table Are: ");

print1DArray(testIndices);


System.out.println(" Running Tests on method indexToRow$Column with random values:".toUpperCase());

for( int i = 0; i < testIndices.length; i++ ){
    int[]row$Col = indexToRow$Column(testIndices[i], indexArr);
 System.out.println(" FOR INDEX "+testIndices[i]+"\n ROW = "+row$Col[0]+" COLUMN = "+row$Col[1]);
}


System.out.println(" CONCLUDING TESTS!");
System.out.println(" THANKS.");


    }//end method main.
}//end class.