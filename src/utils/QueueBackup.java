/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JIBOYE, OLUWAGBEMIRO OLAOLUWA
 * 
 */
public class QueueBackup {

    /**
     * This writes items on a queue to a file.
     * Individual items on the queue ocuupy
     * distinct lines in the file.
     * @param queue The queue of items
     * @param file The File
     */
    public static void writeQueueItemsToFileLineByLine(ConcurrentLinkedQueue<String>queue, File file){
      if(queue.isEmpty()){
          return;
      }  
        
        int size = queue.size();
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw);) {
        
            for (int i=0;i<size;i++) {
                String s = queue.poll();
                if(s!=null && !s.equals("null")){
                bw.write(s + System.getProperty("line.separator"));

                if (i % 10000 == 0) {
                    System.err.println("Printed " + (i) + " items");
                }
                }
            }
            
        } catch (Exception e) {

        }

    }
    /**
     * This reads the lines of text in a file into a queue.
     * Such that individual file lines become individual indexes on the queue.
     * 
     * @param queue A queue
     * @param file The file
     */
    public static void readFileLinesToQueue(ConcurrentLinkedQueue<String>queue, File file){
   if(queue.isEmpty()){
          return;
      }     
        try( Scanner s = new Scanner(file);) {
while (s.hasNextLine()){
    String text = s.nextLine();
    if(text != null && !text.equals("null")){
    queue.offer(text);
    }
}
 } catch (Exception ex) {
          ex.printStackTrace();
        }
        
        
        System.out.println("Queue size: "+queue.size());        
    }
     public static void showFileLinesToQueue( File file){
   if(file==null || !file.exists()){
          return;
      }     
        try( Scanner s = new Scanner(file);) {
while (s.hasNextLine()){
    String text = s.nextLine();
    if(text != null && !text.equals("null")){
        System.err.println(text);
    }
}
 } catch (Exception ex) {
          ex.printStackTrace();
        }
        
        
       
    }
    
    
    
    public static void main(String[] args) {
        QueueBackup.showFileLinesToQueue(new File("C:/Users/hp/catalina1.out"));
    }
}
