package Enities.Mod;

import Enities.Bomb.Bomb;
import Enities.Convention;
import Enities.Enity;
import Enities.Rectangle;
import Enities.ti.Brick;
import Enities.ti.Portal;
import Enities.ti.Wall;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

public class Oneal extends Mob{
    public Convention convention = new Convention();
    private double speed = 5.0;
    private int time = 150;
    public Rectangle nextRectangle = new Rectangle();
    public ImageView imageView;
    private int step = 0;
    private boolean alive = true;
    public Oneal(double x, double y, double width, double heigth, String imgpath) {
        super(x, y, width, heigth, imgpath);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTime(int time) {
        this.time = time;
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean overLaps(Enity enity) {
        return rectangle.overlaps(enity.rectangle);
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

    public void OnealMove(Group root, List<Wall> list_wall , List<Brick> list_brick, List<Bomb> bombList, Portal portal) {
        try {
            Image image = new Image(new FileInputStream(imgpath));
            imageView = new ImageView(image);
            imageView.setY(y);
            imageView.setX(x);
            imageView.setFitHeight(heigth);
            imageView.setFitWidth(width);
            root.getChildren().add(imageView);
            Timeline changeSpeed = new Timeline();
            changeSpeed.setCycleCount(Timeline.INDEFINITE);
            tranfer(list_wall, list_brick, bombList, portal);
            changeSpeed.getKeyFrames().add(new KeyFrame(Duration.millis(5000),(ActionEvent event) -> {
                Random rand = new Random();
                time = (rand.nextInt(6) +1) * 50;
                tranfer(list_wall, list_brick, bombList, portal);
                System.out.println(time);
            }));
            changeSpeed.play();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void tranfer( List<Wall> list_wall , List<Brick> list_brick, List<Bomb> bombList, Portal portal){
        Timeline timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(time),(ActionEvent event) -> {
            if(canMove(list_wall, list_brick, bombList, portal)) {
                move();
                setRectangle(nextRectangle);
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
            if(!alive) {
                afterKill();
                timer.stop();
            }
        }));
        timer.play();
        Timeline changeSpeed = new Timeline();
        changeSpeed.setCycleCount(0);
        changeSpeed.getKeyFrames().add(new KeyFrame(Duration.millis(5000),(ActionEvent event) -> {
            timer.stop();
        }));
        changeSpeed.play();
    }
    public void afterKill() {
        /*Timeline timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(50),(ActionEvent event) -> {
            Image image1 =null;
            try {
                image1 = new Image(new FileInputStream(this.imgpath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(100),(ActionEvent event) -> {
            Image image1 =null;
            try {
                image1 = new Image(new FileInputStream(this.imgpath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);
        }));
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(150),(ActionEvent event) -> {
            Image image1 =null;
            try {
                image1 = new Image(new FileInputStream(this.imgpath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImage(image1);

        }));

        timer.getKeyFrames().add(new KeyFrame(Duration.millis(200),(ActionEvent event) -> {
            imageView.setY(1000);
            imageView.setX(1000);

        }));
        timer.play();*/
        imageView.setY(1000);
        imageView.setX(1000);
    }

    public boolean canMove(List<Wall> list_wall , List<Brick> list_brick, List<Bomb> bombList, Portal portal) {
        setNextRectangle();
        boolean kt = true;
        if (nextRectangle.overlaps(portal.rectangle)) kt = false;
        for (int j = 0; j < list_wall.size(); j++) {
            if (nextRectangle.overlaps(list_wall.get(j).rectangle)){
                kt = false;
                break;
            }
        }

        for (int j = 0; j < list_brick.size(); j++) {
            if (nextRectangle.overlaps(list_brick.get(j).rectangle) || !kt) {
                kt = false;
                break;
            }
        }

        for (int j = 0; j < bombList.size(); j++) {
            if (nextRectangle.overlaps(bombList.get(j).rectangle) || !kt) {
                kt = false;
                break;
            }
        }

        if (kt == false ) {
            Random rand = new Random();
            setArrow(rand.nextInt(4));
        }
        return kt;
    }
    public void move() {
        type = (type +1) % 3;
        if (this.arrow == 0) {
            this.x += this.speed;
            if (type == 0) {
                this.imgpath = convention.Oneal_right;
            }
            else if (type == 1) {
                this.imgpath = convention.Oneal_right1;
            }
            else if (type == 2) {
                this.imgpath = convention.Oneal_right2;
            }
        }
        if (this.arrow == 1) {
            this.x -= this.speed;
            if (type == 0) {
                this.imgpath = convention.Oneal_left;
            }
            else if (type == 1) {
                this.imgpath = convention.Oneal_left1;
            }
            else if (type == 2) {
                this.imgpath = convention.Oneal_right2;
            }
        }
        if (this.arrow == 2) {
            this.y -= this.speed;
            if (type == 0) {
                this.imgpath = convention.Oneal_right;
            }
            else if (type == 1) {
                this.imgpath = convention.Oneal_right1;
            }
            else if (type == 2) {
                this.imgpath = convention.Oneal_right2;
            }
        }
        if (this.arrow == 3) {
            this.y += this.speed;
            if (type == 0) {
                this.imgpath = convention.Oneal_left;
            }
            else if (type == 1) {
                this.imgpath = convention.Oneal_left1;
            }
            else if (type == 2) {
                this.imgpath = convention.Oneal_left2;
            }
        }
        step = (step+1)%8;
        if(step==0) {
            Random rand = new Random();
            setArrow(rand.nextInt(4));
        }
    }
}
