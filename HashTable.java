import java.lang.Math;
import java.util.ArrayList;
/*
Michael DiSalvo
CSC201 Fall 2020
Programming Assignment 3
October 25, 2020
 */

public class HashTable {
    //hast table includes an array list of Freqs, the capacity of the arrayList (M), and the number of freqs in the Hash table
    private ArrayList<Freq> ht;
    //physical size
    private int maxSize;
    //logical size
    private int size;

    //variables for tracking efficiency
    private int numberOfCollisions = 0;
    private int numberOfRehashes = 0;

    public ArrayList<Freq> getHashTable() {
        return ht;
    }

    public int getSize(){ return size;}
    public int getMaxSize(){ return maxSize;}

    //constructs the HashTable Object by initializing array
    public HashTable(){
        maxSize = 0;
        size = 0;
        ht = null;
    }
    public HashTable(int initialMaxSize){
        if (!isPrime(initialMaxSize))
            maxSize = generateNextMaxSize(initialMaxSize);
        else
            maxSize = initialMaxSize;
        size = 0;
        ht = new ArrayList<>(maxSize);
        for (int i = 0; i < maxSize; i++){
            ht.add(null);
        }
    }

    //resizes the ht to be the smallest prime greater than its current size, and re-hashes all Freqs
    private void resizeTable(){
        int oldMax = maxSize;
        maxSize = generateNextMaxSize(oldMax);
        HashTable tmp = new HashTable(maxSize);
        //transfer all elements of old ht to the temp ht
        for (int i = 0; i < oldMax; i++){
            //if the slot in ht is not null insert the Freq into tmp hash table
            if (ht.get(i) != null) {
                tmp.insert(ht.get(i));
            }
        }
        //set this.ht equal to tmp's hash table
        this.ht = tmp.getHashTable();
    }
    //quadratic probe function
    private int p(RGB c, int i){
        return (h(c) + i*i) % maxSize;
    }

    //tells us if an integer n is prime
    private static boolean isPrime(int n){
        int nRoot = (int) Math.ceil(Math.sqrt(n));
        //if n is even then it is not prime
        if (n % 2 == 0)
            return false;
        else {
            //exhaustively check every number less than the square root of n
            for (int i = 3; i < nRoot; i += 2) {
                //if a number divides n, n is not prime
                if (n % i == 0){
                    return false;
                }
            }
            //if we did not find a number that divides n, n is prime
            return true;
        }
    }

    //generate smalled prime number greater than twice the input
    private static int generateNextMaxSize(int oldMax){
        //we will find the first prime number larger than 2*oldMax
        int n = oldMax*2 + 1;
        //flag when a prime is found
        boolean flag = false;
        while (!flag){
            n += 2;
            //if we find a prime, end the loop
            if (isPrime(n)){
                flag = true;
            }
        }
        //return that prime
        return n;
    }


    //returns the hash value between 0 and maxSize - 1 for the RGB object
    private int h(RGB c){ return ((c.getGreen() + 1) * (c.getBlue() + 1) * (c.getRed() + 1)) % maxSize; }

    public void insert(RGB c){
        //if the argument is null then do nothing
        if (c == null){return;}
        int i;
        //continue through probe sequence until an empty slot is found
        for(i = 0; ht.get(p(c, i)) != null; i++){
            //if the current slot is the same as c, increment the Freq and end function
            if (ht.get(p(c, i)).equals(c)){
                ht.get(p(c, i)).increment();
                return;
            }
            else{
                //if not the same RGB than it was a collision
                numberOfCollisions++;
            }

        }
        //if for loop gets finished, we did not find a match for c and we found an empty slot
        //so we insert a new Freq at this slot
        ht.set(p(c, i), new Freq(c));
        //increment the size because we added a new element
        size++;
        if (size >= maxSize/2) {
            numberOfRehashes++;
            this.resizeTable();
        }
    }
    //when we are doing the resize process we are going to want to insert an already existing Freq object
    //this function does the same as insert(RBG) but just inserts the Freq argument at the correct open slot
    private void insert(Freq f){
        //if the argument is null then do nothing (can happen when resizing the array)
        if (f == null){return;}
        RGB c = f.getColor();
        int i;
        //continue through probe sequence until an empty slot is found
        for(i = 0; ht.get(p(c, i)) != null; i++){
            //if the current slot is the same as c, increment the Freq and end function
            if (ht.get(p(c, i)).equals(c)){
                ht.get(p(c, i)).increment();
                return;
            }
            else{
                numberOfCollisions++;
            }
            //if not the same, try the next slot in the probe sequence
        }
        //if for loop gets finished, we did not find a match for c and we found an expty slot
        //so we insert original Freq at this slot
        ht.set(p(c, i) % maxSize, f);
        //increment the size because we added a new element
        size++;
        if (size >= maxSize/2) {
            numberOfRehashes++;
            this.resizeTable();
        }


    }
    //given an RGB, this function will return the frequency of that color if its in the hash table
    //if it is not in the table it will return a -1
    public int getCount(RGB c){
        for(int i = 0; ht.get(p(c, i)) != null; i++){
            //if the current slot is the same as c, increment the Freq and end function
            if (ht.get(p(c, i)).equals(c)){
                return ht.get(p(c, i)).getFrequency();
            }
        }
        //if for loop is completed than c is not in the hast table
        return -1;
    }

    //prints out the hash table, mainly for testing purposes
    public void print(){
        for (Freq i : ht){
            if (i != null)
                System.out.println(i.getColor().string() + " " + i.getFrequency());
            else
                System.out.println("null");
        }
    }

    //will return an array list of the 256 most frequent RGB colors
    public ArrayList<Freq> getMostFrequent() {
        ArrayList<Freq> tmp = new ArrayList<>(size);
        if (size >= 256) {
            //create a new array list which will hold all the Freq objects from the hast table
            //insert each Freq object
            for (Freq i : ht) {
                if (i != null) {
                    tmp.add(i);
                }
            }
            //sort the array list
            tmp.sort(Freq::compareTo);
            ArrayList<Freq> finalList = new ArrayList<>(256);
            //only keep the most frequent 256 colors
            for (int i = size - 1; i >= size - 256; i--) {
                finalList.add(tmp.get(i));
            }
            return finalList;
        }
        //if the hash table has less than 256 slots filled
        else{
            for (Freq i : ht){
                if ( i != null)
                    tmp.add(i);
            }
            tmp.sort(Freq::compareTo);
            //reverse the order of tmp
            for (int i = 0; i < tmp.size()/2 ; i++){
                Freq f = tmp.get((tmp.size() - 1) - i);
                tmp.set((tmp.size() - 1) - i, tmp.get(i));
                tmp.set(i, f);
            }
            return tmp;
        }
    }

    //prints our what the starting size was, how many collisions, how many rehashes occurred, and the final size.
    public void printEfficiencyResults(int startingSize){
        System.out.println("Starting hash table size: " + startingSize);
        System.out.println("Number of collisions: " + numberOfCollisions);
        System.out.println("Number of rehashes: " + numberOfRehashes);
        System.out.println("Final physical size: " + maxSize);
    }
}
