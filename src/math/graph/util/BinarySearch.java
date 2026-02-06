/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.graph.util;

import java.util.Random;

/**
 *
 * @author JIBOYE, OLUWAGBEMIRO OLAOLUWA
 *
 */
public class BinarySearch {

    private static double horizontalCoordinates[];

    public static int binarySearch(double searchValue) {
//3.1,3.21,4.13,4.4,5.0,5.2,5.21,5.32,6.0,6.01
        boolean notFound = true;
        int startLen = horizontalCoordinates.length;
        int indexShift = 0;
        while (notFound) {
            int midIndex = indexShift + startLen / 2;

            if (midIndex >= horizontalCoordinates.length - 1) {
                return horizontalCoordinates.length - 1;
            }
//Item is in the left partition.
            if (searchValue < horizontalCoordinates[midIndex]) {
                if (midIndex == 0) {
                    return midIndex;
                }
                if (searchValue >= horizontalCoordinates[midIndex - 1]) {
                    return midIndex;
                }
//System.out.println("1......[indexShift, midIndex] = ["+indexShift+", "+midIndex+"].");
                startLen /= 2;

            } else if (searchValue == horizontalCoordinates[midIndex]) {
                //  System.out.println("Elem found");
                return midIndex;
            } //Item is in the right partition
            else {
                indexShift = midIndex + 1;
                if (indexShift >= horizontalCoordinates.length - 1) {
                    return horizontalCoordinates.length - 1;
                }
                //System.out.println("2......[indexShift, midIndex] = ["+indexShift+", "+midIndex+"].");
                startLen /= 2;
            }

        }
        return -1;
    }

    public static int linearSearch(double searchValue) {
        int i = 0;
        for (double d : horizontalCoordinates) {
            if (d >= searchValue) {
                return i;
            }
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        int ITEMS = 43000000;
        Random choice = new Random();
        horizontalCoordinates = new double[ITEMS];
        for (int i = 0; i < ITEMS; i++) {
            horizontalCoordinates[i] = i + Math.abs(choice.nextFloat());
        }

        double searchItem = choice.nextInt(ITEMS) + choice.nextFloat();

        System.err.println("We want to search for: " + searchItem + " using Binary Search");
        System.err.println("Starting Binary search.");

        double start = System.nanoTime();
        int index = binarySearch(searchItem);
        double duration = System.nanoTime() - start;

        System.err.println("Search ends in " + (duration / 1.0E6) + " ms\n ");

        System.out.println("SearchItem: " + searchItem + "\n\n\nSee index at : " + index + " \nelem to left of index is " + horizontalCoordinates[index - 1]
                + " \nelem to right of index is " + horizontalCoordinates[index]);

        System.err.println("Now we will search for: " + searchItem + " using Linear Search");
        System.err.println("Starting Linear search.");

        start = System.nanoTime();
        index = linearSearch(searchItem);
        duration = System.nanoTime() - start;

        System.err.println("Search ends in " + (duration / 1.0E6) + " ms\n ");

        System.out.println("SearchItem: " + searchItem + "\n\n\nSee index at : " + index + " \nelem to left of index is " + horizontalCoordinates[index - 1]
                + " \nelem to right of index is " + horizontalCoordinates[index]);

    }

}
