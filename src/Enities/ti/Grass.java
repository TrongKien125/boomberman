package Enities.ti;

import Enities.Enity;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass extends Enity {
    ImageView imageView;
    public Grass(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }
    public void renderGrass(Group root) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(imgpath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setY(y);
        imageView.setX(x);
        imageView.setFitHeight(heigth);
        imageView.setFitWidth(width);
        root.getChildren() .add(imageView);
    }
}
