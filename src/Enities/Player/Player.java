package Enities.Player;

import Enities.Convention;
import Enities.Mod.Mob;
import Enities.PowerUp.BombItem;
import Enities.PowerUp.FlameItem;
import Enities.PowerUp.SpeedItem;
import Enities.Rectangle;
import Enities.Sound;
import Enities.ti.Brick;
import Enities.ti.Portal;
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

public class Player extends Mob {
    Sound sound = new Sound();
    public Convention convention = new Convention();
    private double speed = 5.0;
    private int arrow;
    private int type;
    public Rectangle nextRectangle = new Rectangle();
    public ImageView imageView;
    private boolean alive = true;
    private int number_bomb = 1;
    private int lenght_flame = 0;
    public Rectangle nextRectangle2 = new Rectangle();

    public Player(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setArrow(int arrow) {
        this.arrow = arrow;
    }

    public int getArrow() {
        return this.arrow;
    }

    public void setNumber_bomb(int number_bomb) {
        this.number_bomb = number_bomb;
    }

    public int getNumber_bomb() {
        return number_bomb;
    }

    public void setLenght_flame(int lenght_flame) {
        this.lenght_flame = lenght_flame;
    }

    public int getLenght_flame() {
        return lenght_flame;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public boolean getAlive() {
        return this.alive;
    }
    public void setNextRectangle() {
        if(arrow == 0) {
            nextRectangle.set(x + this.speed,y,width,heigth);
        }
        if(arrow == 1) {
            nextRectangle.set(x - this.speed,y,width,heigth);
        }
        if(arrow == 2) {
            nextRectangle.set(x ,y - this.speed,width,heigth);
        }
        if(arrow == 3) {
            nextRectangle.set(x,y + this.speed,width,heigth);
        }
    }


    public void renderPlayer(Group root) {
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

    public void PlayerMove(List<Wall> wallList, List<Brick> brickList, List<BombItem> bombItemList, List<FlameItem> flameItemList, List<SpeedItem> speedItemList, Portal portal) {
        switch (collision(wallList, brickList, bombItemList, flameItemList, speedItemList, portal)) {
            case 0:
                move();
                setRectangle(nextRectangle);
                break;
            case 1:
                break;
            case 2:
                Media sound1 = new Media(new File(sound.PowerUp).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound1);
                mediaPlayer.play();
                number_bomb ++;
                move();
                setRectangle(nextRectangle);
                break;
            case 3:
                Media sound2 = new Media(new File(sound.PowerUp).toURI().toString());
                MediaPlayer mediaPlayer1 = new MediaPlayer(sound2);
                mediaPlayer1.play();
                lenght_flame ++;
                move();
                setRectangle(nextRectangle);
                break;
            case 4:
                Media sound3 = new Media(new File(sound.PowerUp).toURI().toString());
                MediaPlayer mediaPlayer2 = new MediaPlayer(sound3);
                mediaPlayer2.play();
                speed = 10.0;
                move();
                setRectangle(nextRectangle);
                break;
            default: break;
        }
        Image image1 =null;
        try {
            image1 = new Image(new FileInputStream(this.imgpath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImage(image1);
        imageView.setY(y);
        imageView.setX(x);
    }

    public int collision(List<Wall> list_wall, List<Brick> list_brick, List<BombItem> bombItemList, List<FlameItem> flameItemList, List<SpeedItem> speedItemList, Portal portal) {
        setNextRectangle();
        if(nextRectangle.overlaps(portal.getRectangle())) return 1;
        for (int j = 0; j < list_wall.size(); j++) {
            if (nextRectangle.overlaps(list_wall.get(j).getRectangle())) {
                System.out.println("wall" + j);
                return 1;
            }
        }

        for (int j = 0; j < list_brick.size(); j++) {
            if (nextRectangle.overlaps(list_brick.get(j).rectangle)) {
                System.out.println("brick" + j);
                return 1;
            }
        }

        for (int j = 0; j < bombItemList.size(); j++) {
            if (nextRectangle.overlaps(bombItemList.get(j).rectangle)) {
                System.out.println("power up bomb");
                bombItemList.get(j).overLaps();
                bombItemList.remove(j);
                return 2;
            }
        }

        for (int j = 0; j < flameItemList.size(); j++) {
            if (nextRectangle.overlaps(flameItemList.get(j).rectangle)) {
                System.out.println("power up flame");
                flameItemList.get(j).overLaps();
                flameItemList.remove(j);
                return 3;
            }
        }

        for (int j = 0; j < speedItemList.size(); j++) {
            if (nextRectangle.overlaps(speedItemList.get(j).rectangle)) {
                System.out.println("power up speed");
                speedItemList.get(j).overLaps();
                speedItemList.remove(j);
                return 4;
            }
        }
        return 0;
    }

    public void playerDead() {
        Timeline timer = new Timeline();
        timer.setCycleCount(0);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(100),(ActionEvent event) -> {
            Image image1 =null;
            try {
                image1 = new Image(new FileInputStream(convention.Dead1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(200),(ActionEvent event) -> {
            Image image2 =null;
            try {
                image2 = new Image(new FileInputStream(convention.Dead2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image2);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(300),(ActionEvent event) -> {
            Image image3 =null;
            try {
                image3 = new Image(new FileInputStream(convention.Dead3));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image3);

        }));
        timer.play();
    }

    public void move() {
        type = (type + 1) % 3;
        if (this.arrow == 0) {
            this.x += this.speed;
            if (type == 0) {
                this.imgpath = convention.PLAYER_RIGHT1_PATH;
            }
            else if (type == 1) {
                this.imgpath = convention.PLAYER_RIGHT2_PATH;
            }
            else if (type == 2) {
                this.imgpath = convention.PLAYER_RIGHT3_PATH;
            }
        }
        if (this.arrow == 1) {
            this.x -= this.speed;
            if (type == 0) {
                this.imgpath = convention.PLAYER_LEFT1_PATH;
            }
            else if (type == 1) {
                this.imgpath = convention.PLAYER_LEFT2_PATH;
            }
            else if (type == 2) {
                this.imgpath = convention.PLAYER_LEFT3_PATH;
            }
        }
        if (this.arrow == 3) {
            this.y += this.speed;
            if (type == 0) {
                this.imgpath = convention.PLAYER_DOWN1_PATH;
            }
            else if (type == 1) {
                this.imgpath = convention.PLAYER_DOWN2_PATH;
            }
            else if (type == 2) {
                this.imgpath = convention.PLAYER_DOWN3_PATH;
            }
        }
        if (this.arrow == 2) {
            this.y -= this.speed;
            if (type == 0) {
                this.imgpath = convention.PLAYER_UP1_PATH;
            }
            else if (type == 1) {
                this.imgpath = convention.PLAYER_UP2_PATH;
            }
            else if (type == 2) {
                this.imgpath = convention.PLAYER_UP3_PATH;
            }
        }
    }
}
