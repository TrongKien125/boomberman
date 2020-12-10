package Enities.Bomb;

import Enities.Convention;
import Enities.Enity;
import Enities.Mod.Balloom;
import Enities.Mod.Oneal;
import Enities.Player.Player;
import Enities.ti.Brick;
import Enities.ti.Wall;
import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class Explosion{
    private int length ;
    public double x_Center;
    public double y_Center;
    public Convention convention = new Convention();
    public Flame flame_center;
    public List<Flame> listofFlame_left = new ArrayList<>();
    public List<Flame> listofFlame_rigth= new ArrayList<>();
    public List<Flame> listofFlame_up = new ArrayList<>();
    public List<Flame> listofFlame_Down = new ArrayList<>();
    List<Image> imageList = new ArrayList<>();

    public Explosion(){}
    public Explosion(double  x_Center, double y_Center, int length) {
        this.x_Center = x_Center;
        this.y_Center = y_Center;
        this.length = length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setExplosion() {
        for (int i = 0; i < length ; i++) {
            Flame flame_left_horizotal = new Flame(x_Center  - 40 * (i +1), y_Center, 40,40, convention.Flame_horizotal);
            listofFlame_left.add(flame_left_horizotal);
            Flame flame_rigth_horizotal = new Flame(x_Center + 40 * (i+1), y_Center, 40,40, convention.Flame_horizotal);
            listofFlame_rigth.add(flame_rigth_horizotal);
            Flame flame_up_vertical = new Flame(x_Center, y_Center - 40 * (i +1), 40,40, convention.Flame_vertical);
            listofFlame_up.add(flame_up_vertical);
            Flame flame_down_vertical = new Flame(x_Center, y_Center + 40 * (i+1), 40,40, convention.Flame_vertical);
            listofFlame_Down.add(flame_down_vertical);
        }
        flame_center = new Flame(x_Center, y_Center, 40,40, convention.Flame_center);
        Flame flame_left = new Flame(x_Center - 40 - 40 * length, y_Center, 40,40, convention.Flame_left);
        listofFlame_left.add(flame_left);
        Flame flame_rigth = new Flame(x_Center + 40 + 40 * length, y_Center, 40,40, convention.Flame_rigth);
        listofFlame_rigth.add(flame_rigth);
        Flame flame_up = new Flame(x_Center, y_Center - 40 - 40 * length, 40,40, convention.Flame_up);
        listofFlame_up.add(flame_up);
        Flame flame_down = new Flame(x_Center, y_Center + 40 + 40 * length, 40,40, convention.Flame_down);
        listofFlame_Down.add(flame_down);
    }

    public void setListofImage(){
        try {
            Image image = new Image(new FileInputStream(convention.Flame_center1));
            imageList.add(image);
            Image image1 = new Image(new FileInputStream(convention.Flame_center2));
            imageList.add(image1);
            Image image2 = new Image(new FileInputStream(convention.Flame_left1));
            imageList.add(image2);
            Image image3 = new Image(new FileInputStream(convention.Flame_left2));
            imageList.add(image3);
            Image image4 = new Image(new FileInputStream(convention.Flame_rigth1));
            imageList.add(image4);
            Image image5 = new Image(new FileInputStream(convention.Flame_rigth2));
            imageList.add(image5);
            Image image6 = new Image(new FileInputStream(convention.Flame_up1));
            imageList.add(image6);
            Image image7 = new Image(new FileInputStream(convention.Flame_up2));
            imageList.add(image7);
            Image image8 = new Image(new FileInputStream(convention.Flame_down1));
            imageList.add(image8);
            Image image9 = new Image(new FileInputStream(convention.Flame_down2));
            imageList.add(image9);
            Image image10 = new Image(new FileInputStream(convention.Flame_horizotal1));
            imageList.add(image10);
            Image image11 = new Image(new FileInputStream(convention.Flame_horizatal2));
            imageList.add(image11);
            Image image12 = new Image(new FileInputStream(convention.Flame_vertical1));
            imageList.add(image12);
            Image image13 = new Image(new FileInputStream(convention.Flame_vertical2));
            imageList.add(image13);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void  renderExplosion(Group root, List<Wall> wallList, List<Brick> brickList, List<Balloom> balloomList, List<Oneal> oneals, List<Player> players) {
        setListofImage();
        setExplosion();
        if(flame_center.overLaps(players.get(0))) {
            players.get(0).setAlive(false);
        }
        for (int i = 0; i < listofFlame_left.size(); i++) {
            int kt = 0;
            if(listofFlame_left.get(i).overLaps(players.get(0))) {
                players.get(0).setAlive(false);
            }
            for(int j = 0; j < balloomList.size(); j++) {
                if(listofFlame_left.get(i).overLaps(balloomList.get(j))) {
                    balloomList.get(j).setAlive(false);
                    balloomList.remove(j);
                }
            }
            for(int j = 0; j < oneals.size(); j++) {
                if(listofFlame_left.get(i).overLaps(oneals.get(j))) {
                    oneals.get(j).setAlive(false);
                    oneals.remove(j);
                }
            }
            for(int j = 0; j < wallList.size(); j++) {
                if(listofFlame_left.get(i).overLaps(wallList.get(j))) {
                    kt = 1;
                    break;
                }
            }
            for(int j = 0; j < brickList.size(); j++) {
                if(kt == 1) break;
                if(listofFlame_left.get(i).overLaps(brickList.get(j))) {
                    brickList.get(j).brickExplosion();
                    brickList.remove(j);
                    kt = 1;
                    break;
                }
            }
            if(kt == 1) break;
            else {
                if(i == (listofFlame_left.size() -1)) listofFlame_left.get(i).renderFlame(root, imageList.get(2), imageList.get(3));
                else {
                    listofFlame_left.get(i).renderFlame(root, imageList.get(10), imageList.get(11));
                }
            }
            System.out.println("left");
        }
        for (int i = 0; i < listofFlame_rigth.size(); i++) {
            int kt = 0;
            if(listofFlame_rigth.get(i).overLaps(players.get(0))) {
                players.get(0).setAlive(false);
            }
            for(int j = 0; j < balloomList.size(); j++) {
                if(listofFlame_rigth.get(i).overLaps(balloomList.get(j))) {
                    balloomList.get(j).setAlive(false);
                    balloomList.remove(j);
                }
            }
            for(int j = 0; j < oneals.size(); j++) {
                if(listofFlame_rigth.get(i).overLaps(oneals.get(j))) {
                    oneals.get(j).setAlive(false);
                    oneals.remove(j);
                }
            }
            for(int j = 0; j < wallList.size(); j++) {
                if(listofFlame_rigth.get(i).overLaps(wallList.get(j))) {
                    kt = 1;
                    break;
                }
            }
            for(int j = 0; j < brickList.size(); j++) {
                if(kt == 1) break;
                if(listofFlame_rigth.get(i).overLaps(brickList.get(j))) {
                    brickList.get(j).brickExplosion();
                    brickList.remove(j);
                    kt = 1;
                    break;
                }
            }
            if(kt == 1) break;
            else {
                if( i == listofFlame_rigth.size() -1) listofFlame_rigth.get(i).renderFlame(root, imageList.get(4), imageList.get(5));
                else listofFlame_rigth.get(i).renderFlame(root, imageList.get(10), imageList.get(11));
            }
            System.out.println("rigth");
        }
        for (int i = 0; i < listofFlame_up.size(); i++) {
            int kt = 0;
            if(listofFlame_up.get(i).overLaps(players.get(0))) {
                players.get(0).setAlive(false);
            }
            for(int j = 0; j < balloomList.size(); j++) {
                if(listofFlame_up.get(i).overLaps(balloomList.get(j))) {
                    balloomList.get(j).setAlive(false);
                    balloomList.remove(j);
                }
            }
            for(int j = 0; j < oneals.size(); j++) {
                if(listofFlame_up.get(i).overLaps(oneals.get(j))) {
                    oneals.get(j).setAlive(false);
                    oneals.remove(j);
                }
            }
            for(int j = 0; j < wallList.size(); j++) {
                if(listofFlame_up.get(i).overLaps(wallList.get(j))) {
                    kt = 1;
                    break;
                }
            }
            for(int j = 0; j < brickList.size(); j++) {
                if(kt == 1) break;
                if(listofFlame_up.get(i).overLaps(brickList.get(j))) {
                    brickList.get(j).brickExplosion();
                    brickList.remove(j);
                    kt = 1;
                    break;
                }
            }
            if(kt == 1) break;
            else {
                if( i == listofFlame_up.size()-1) listofFlame_up.get(i).renderFlame(root, imageList.get(6), imageList.get(7));
                else {
                    listofFlame_up.get(i).renderFlame(root, imageList.get(12), imageList.get(13));
                }
            }
            System.out.println("up");
        }
        for (int i = 0; i < listofFlame_Down.size(); i++) {
            int kt = 0;
            if(listofFlame_Down.get(i).overLaps(players.get(0))) {
                players.get(0).setAlive(false);
            }
            for(int j = 0; j < balloomList.size(); j++) {
                if(listofFlame_Down.get(i).overLaps(balloomList.get(j))) {
                    balloomList.get(j).setAlive(false);
                    balloomList.remove(j);
                }
            }
            for(int j = 0; j < oneals.size(); j++) {
                if(listofFlame_Down.get(i).overLaps(oneals.get(j))) {
                    oneals.get(j).setAlive(false);
                    oneals.remove(j);
                }
            }
            for(int j = 0; j < wallList.size(); j++) {
                if(listofFlame_Down.get(i).overLaps(wallList.get(j))) {
                    kt = 1;
                    break;
                }
            }
            for(int j = 0; j < brickList.size(); j++) {
                if(kt == 1) break;
                if(listofFlame_Down.get(i).overLaps(brickList.get(j))) {
                    brickList.get(j).brickExplosion();
                    brickList.remove(j);
                    kt = 1;
                    break;
                }
            }
            if(kt == 1) break;
            else {
                if( i == listofFlame_Down.size() -1) listofFlame_Down.get(i).renderFlame(root, imageList.get(8), imageList.get(9));
                else listofFlame_Down.get(i).renderFlame(root, imageList.get(12), imageList.get(13));
            }
            System.out.println("down");
        }
        flame_center.renderFlame(root, imageList.get(0), imageList.get(1));
    }
}
