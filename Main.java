import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
Michael DiSalvo
CSC201 Fall 2020
Programming Assignment 3
October 25, 2020
 */

public class Main {
    //this program reads in RGB colors from a raw image file and will find the 256 most frequent colors using a hast table
    //it takes a file name, the height (in num of pixels) and width (in num of pixels) of the file
    public static void main(String[] args) throws IOException {
        int startingSize = 1001;
        String filename = args[0];
        int h = Integer.parseInt(args[1]);
        int w = Integer.parseInt(args[2]);
        long elapsedTime = 0;

        HashTable hashTable = new HashTable(startingSize);
        try {
            InputStream is = new FileInputStream(filename);
            // create data input stream
            DataInputStream input = new DataInputStream(is);

            long start = System.nanoTime();
            //for each pixel, read the values into an RGB object and insert it into the hast table
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    RGB pixel = new RGB();
                    pixel.setRed(input.readUnsignedByte());
                    pixel.setGreen(input.readUnsignedByte());
                    pixel.setBlue(input.readUnsignedByte());
                    hashTable.insert(pixel);
                }
            }
            long end = System.nanoTime();
            elapsedTime = end - start;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ArrayList<Freq> mostFrequent;
        mostFrequent = hashTable.getMostFrequent();
        int j = 1;
        for (Freq i : mostFrequent) {
            if (j % 8 == 0)
                System.out.println(i.getColor().string() + " Frequency: " + i.getFrequency());
            else
                System.out.print(i.getColor().string() + " Frequency: " + i.getFrequency() + '\t');
            j++;
        }
        System.out.println();
        System.out.println("File: " + filename);
        System.out.println("Runtime: " + (elapsedTime / 1000000) + " ms");
        hashTable.printEfficiencyResults(startingSize);



    }
}
