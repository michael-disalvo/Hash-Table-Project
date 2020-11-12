
/*
Michael DiSalvo
CSC201 Fall 2020
Programming Assignment 3
October 25, 2020
 */

public class Freq implements Comparable<Freq>{
    private RGB color;
    private int frequency;

    public Freq(){
        color = null;
        frequency = 1;
    }
    //initialize frequency to be one when a Freq is constructed
    public Freq(RGB c){
        color = c;
        frequency = 1;
    }

    public void increment(){
        frequency++;
    }

    public RGB getColor() {
        return color;
    }

    public void setColor(RGB color) {
        this.color = color;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean equals(RGB c){
        return c.getRed() == color.getRed() && c.getBlue() == color.getBlue() && c.getGreen() == color.getGreen();
    }

    //this function allows for sorting of freq objects
    public int compareTo(Freq freq) {
        return Integer.compare(this.frequency, freq.frequency);
    }
}
