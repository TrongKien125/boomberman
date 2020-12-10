package Enities.Mod;

import Enities.Enity;

public abstract class Mob extends Enity {
    protected int type;
    protected int arrow;
    public Mob(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public abstract void move();
}
