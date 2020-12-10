package Enities.PowerUp;

import Enities.Convention;
import Enities.Enity;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BombItem extends Enity {
    ImageView imageView;
    Convention convention = new Convention();
    public BombItem(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public void renderBombItem(Group root) {
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
        root.getChildren().add(imageView);
    }

    public void overLaps() {
        imageView.setX(1000);
        imageView.setY(1000);
    }
}
