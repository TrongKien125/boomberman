package Enities;

public class Rectangle {
    public double x;
    public double y;
    public double width;
    public double heigth;

    public Rectangle(){
    }
    public Rectangle(double x, double y, double width, double heigth){
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public void set(double x, double y, double width, double heigth){
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public boolean overlaps(Rectangle other) {
        boolean noOverLaps =
                this.x + this.width <= other.x ||
                        other.x + other.width <= this.x ||
                        this.y + this.heigth <= other.y ||
                        other.y + other.heigth <= this.y;
        return !noOverLaps;
    }
}