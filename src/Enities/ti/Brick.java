package Enities.ti;

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

public class Brick extends Enity {
    ImageView imageView;
    Convention convention = new Convention();
    public Brick(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public void renderBrick(Group root) {
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

    public void brickExplosion() {
        Timeline timer = new Timeline();
        timer.setCycleCount(3);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(50),(ActionEvent event) -> {
            Image image1 = null;
            try {
                image1 = new Image(new FileInputStream(convention.BRICK_Explosion));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(100),(ActionEvent event) -> {
            Image image2 = null;
            try {
                image2 = new Image(new FileInputStream(convention.BRICK_Explosion_1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image2);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(150),(ActionEvent event) -> {
            Image image3 = null;
            try {
                image3 = new Image(new FileInputStream(convention.BRICK_Explosion_2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image3);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(200),(ActionEvent event) -> {
            imageView.setY(1000);
            imageView.setX(1000);
        }));
        timer.play();
    }
}
