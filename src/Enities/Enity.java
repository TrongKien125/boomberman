package Enities;

public abstract class Enity {
    protected double x;
    protected double y;
    protected double width;
    protected double heigth;
    protected String imgpath;
    public Rectangle rectangle = new Rectangle();

    public Enity(double x, double y, double width, double heigth, String imgpath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.imgpath =  imgpath;
        this.rectangle.set(x,y,width,heigth);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setHeigth(double heigth) {
        this.heigth = heigth;
    }

    public double getHeigth() {
        return heigth;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }
}
