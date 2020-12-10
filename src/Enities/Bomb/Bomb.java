package Enities.Bomb;

import Enities.Convention;
import Enities.Enity;
import Enities.Mod.Balloom;
import Enities.Player.Player;
import Enities.Sound;
import Enities.ti.Brick;
import Enities.ti.Wall;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Bomb extends Enity {
    Sound sound = new Sound();
    private int type;
    public Convention convention =  new Convention();
    private int length_flame;
    public Bomb(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
        setXY();
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setLength_flame(int l) {
        this.length_flame = l;
    }

    public int getLength_flame() {
        return length_flame;
    }

    public void setXY() {
        if (this.x % 40 >= 30) {
            this.x += 40 - (this.x % 40);
        } else {
            this.x -= this.x % 40;
        }
        if (this.y % 40 >= 20) {
            this.y += 40 - (this.y % 40);
        } else {
            this.y -= this.y % 40;
        }
        this.rectangle.x = x;
        this.rectangle.y = y;
    }

    public void renderBomb(Group root, List<Wall> wallList, List<Brick> brickList, List<Balloom> balloomList, List<Player> players) {
        try {
            Image image = new Image(new FileInputStream(convention.BOMB_PATH));
            ImageView imageView = new ImageView(image);
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitWidth(width);
            imageView.setFitHeight(heigth);
            root.getChildren().add(imageView);
        AtomicInteger a = new AtomicInteger();
        Timeline timer = new Timeline();
        timer.setCycleCount(3);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(300),(ActionEvent event) -> {
            Image image1 = null;
            try {
                image1 = new Image(new FileInputStream(convention.BOMB_1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);
            System.out.println("1");
            a.getAndIncrement();
        }));

        timer.getKeyFrames().add(new KeyFrame(Duration.millis(600),(ActionEvent event) -> {
            Image image2 = null;
            try {
                image2 = new Image(new FileInputStream(convention.BOMB_2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image2);
            System.out.println("2");
        }));

        timer.getKeyFrames().add(new KeyFrame(Duration.millis(900),(ActionEvent event) -> {
            Image image3 = null;
            try {
                image3 = new Image(new FileInputStream(convention.BOMB_PATH));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image3);
            if(a.get() == 3 ) {
                Media sound1 = new Media(new File(sound.BOMB).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound1);
                mediaPlayer.play();
                imageView.setX(1000);
                imageView.setY(1000);
                Explosion explosion = new Explosion(getX(),getY(),length_flame);
                explosion.renderExplosion(root, wallList, brickList, balloomList, players);
            }
        }));
        timer.play();
    } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    }