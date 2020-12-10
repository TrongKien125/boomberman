package Enities.Bomb;

import Enities.Convention;
import Enities.Enity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Flame extends Enity {
    public Flame(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public void renderFlame(Group root,Image image1,Image image2) {
        try {
            Image image = new Image(new FileInputStream(imgpath));
            ImageView imageView = new ImageView(image);
            imageView.setY(y);
            imageView.setX(x);
            imageView.setFitHeight(heigth);
            imageView.setFitWidth(width);
            root.getChildren() .add(imageView);
            Timeline timer = new Timeline();
            timer.setCycleCount(0);
            timer.getKeyFrames().add(new KeyFrame(Duration.millis(50),(ActionEvent event) -> {
                imageView.setImage(image1);
            }));
            timer.getKeyFrames().add(new KeyFrame(Duration.millis(100),(ActionEvent event) -> {
                imageView.setImage(image2);
            }));
            timer.getKeyFrames().add(new KeyFrame(Duration.millis(150),(ActionEvent event) -> {
                imageView.setY(1000);
                imageView.setX(1000);
            }));
            timer.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean collideWithBaloom() {
        boolean kt = false;
        return true;
    }
    public boolean overLaps(Enity enity) {
        return this.rectangle.overlaps(enity.rectangle);
    }
}
