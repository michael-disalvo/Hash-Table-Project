
/*
Michael DiSalvo
CSC201 Fall 2020
Programming Assignment 3
October 25, 2020
 */


public class RGB {
    private int red;
    private int green;
    private int blue;

    public RGB(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }
    public RGB(){
        red = -1;
        green = -1;
        blue = -1;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public String string(){
        return "(" + String.valueOf(red) + " " + String.valueOf(green) + " " + String.valueOf(blue) + ")";
    }
}
