package sample;
import Enities.Mod.Oneal;
import Enities.PowerUp.BombItem;
import Enities.PowerUp.FlameItem;
import Enities.PowerUp.SpeedItem;
import Enities.Sound;
import Enities.ti.Brick;
import Enities.ti.Grass;
import Enities.ti.Portal;
import Enities.ti.Wall;
import Enities.Bomb.Bomb;
import Enities.Convention;
import Enities.Mod.Balloom;
import Enities.Player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main extends Application {
    Sound sound1 = new Sound();
    Convention convention = new Convention();
    Stage stage = new Stage();
    Group root = new Group();
    Scene scene =  new Scene(root);
    public int mapWidth = 0;
    public int mapHeigth = 0;
    List<Bomb> list_bomb = new ArrayList<>();
    List<Grass> list_grass = new ArrayList<>();
    List<Brick> list_brick = new ArrayList<>();
    List<Wall> list_wall =  new ArrayList<>();
    List<Balloom> list_balloom = new ArrayList<>();
    List<Player> player = new ArrayList<>();
    List<Portal> portal = new ArrayList<>();
    List<BombItem> bombItemList = new ArrayList<>();
    List<SpeedItem> speedItemList = new ArrayList<>();
    List<FlameItem> flameItemList = new ArrayList<>();
    List<Oneal> list_oneal = new ArrayList<>();
    private int level = 0;
    String[] strMap = {"Map/level1.txt", "Map/level2.txt", "Map/level3.txt", "Map/level4.txt", "Map/Level5.txt"};

    public void renderMap() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(strMap[level-1]));
            String line = br.readLine();
            String[] map_size = line.split("\\s");
            mapWidth = Integer.parseInt(map_size[2]);
            mapHeigth = Integer.parseInt(map_size[1]);
            stage.setWidth(mapWidth * convention.PIXEL_WIDTH + 18);
            stage.setHeight((mapHeigth +1) * convention.PIXEL_HEIGHT);
            int current = 1;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < mapWidth ; i++) {
                    Grass grass = new Grass(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT,  convention.GRASS_PATH);
                    list_grass.add(grass);
                    if (line.charAt(i) == '#') {
                        Wall wall = new Wall(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT , convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.WALL_PATH);
                        list_wall.add(wall);
                    }
                    if (line.charAt(i) == '*') {
                        Brick brick = new Brick(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.BRICK_PATH);
                        list_brick.add(brick);
                        Random random = new Random();
                        switch (random.nextInt(20)) {
                            case 0:
                                BombItem bombItem = new BombItem(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.BombItem);
                                bombItemList.add(bombItem);
                                break;
                            case 1:
                                SpeedItem speedItem = new SpeedItem(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.SpeedItem);
                                speedItemList.add(speedItem);
                                break;
                            case 2:
                                FlameItem flameItem = new FlameItem(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.FlameItem);
                                flameItemList.add(flameItem);
                                break;
                        }
                    }
                    if (line.charAt(i) == 'E') {
                        Brick brick = new Brick(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.BRICK_PATH);
                        list_brick.add(brick);
                        Portal portal = new Portal((i * convention.PIXEL_WIDTH) +5, ((current - 1) * convention.PIXEL_HEIGHT) + 5, 30, 30, convention.Portal);
                        this.portal.add(portal);
                    }
                    if (line.charAt(i) == '1') {
                        Balloom balloom = new Balloom(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.BALLOOM_RIGHT1_PATH);
                        balloom.setArrow(0);
                        balloom.setType(0);
                        list_balloom.add(balloom);
                    }
                    if (line.charAt(i) == '2') {
                        Oneal oneal = new Oneal(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PIXEL_WIDTH, convention.PIXEL_HEIGHT, convention.Oneal_right);
                        oneal.setArrow(0);
                        oneal.setType(0);
                        list_oneal.add(oneal);
                    }
                    if (line.charAt(i) == 'p') {
                        Player player = new Player(i * convention.PIXEL_WIDTH, (current - 1) * convention.PIXEL_HEIGHT, convention.PLAYER_WIDTH, convention.PLAYER_HEIGHT, convention.PLAYER_RIGHT1_PATH);
                        player.setArrow(0);
                        player.setType(0);
                        this.player.add(player);
                    }
                }
                current++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMap()  {
        for (int i = 0; i < list_grass.size(); i++) {
            list_grass.get(i).renderGrass(root);
        }
        for (int i = 0; i < list_wall.size(); i++) {
            list_wall.get(i).renderWal(root);
        }

        portal.get(0).renderPortal(root);
        for (int i = 0; i < bombItemList.size(); i++) {
            bombItemList.get(i).renderBombItem(root);
        }
        for (int i = 0; i < flameItemList.size(); i++) {
            flameItemList.get(i).renderFlameItem(root);
        }
        for (int i = 0; i < speedItemList.size(); i++) {
            speedItemList.get(i).renderSpeedItem(root);
        }

        for (int i = 0; i < list_brick.size(); i++) {
            list_brick.get(i).renderBrick(root);
        }

        for (int i = 0; i < list_balloom.size(); i++) {
            list_balloom.get(i).balloomMove(root, list_wall, list_brick, list_bomb, portal.get(0));
        }
        for (int i = 0; i < list_oneal.size(); i++) {
            list_oneal.get(i).OnealMove(root, list_wall, list_brick, list_bomb, portal.get(0));
        }
    }

    public void setBomb(){
        boolean kt = true;
        Bomb bomb = new Bomb(player.get(0).getX(),player.get(0).getY(),40,40,convention.BOMB_PATH);
        for(int i = 0; i<list_bomb.size(); i++) {
            if (bomb.rectangle.overlaps(list_bomb.get(i).rectangle)) {
                kt = false;
                break;
            }
        }
        if(list_bomb.size() < player.get(0).getNumber_bomb() && kt){
            Media sound = new Media(new File(sound1.SetBomb).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            list_bomb.add(bomb);
            bomb.setLength_flame(player.get(0).getLenght_flame());
            bomb.renderBomb(root, list_wall, list_brick, list_balloom,list_oneal, player);
            Timeline timer = new Timeline();
            timer.setCycleCount(0);
            timer.getKeyFrames().add(new KeyFrame(Duration.millis(2850),(ActionEvent event) -> {
                list_bomb.remove(bomb);
            }));
            timer.play();
        }
    }


    public void Play() {
        renderMap();
        createMap();
        player.get(0).renderPlayer(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                boolean t = false;
                if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
                    player.get(0).setArrow(1);
                    t = true;
                } else if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                    player.get(0).setArrow(2);
                    t = true;
                } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
                    t = true;
                    player.get(0).setArrow(0);
                } else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                    t = true;
                    player.get(0).setArrow(3);
                } else if (event.getCode() == KeyCode.SPACE) {
                    setBomb();
                }
                if(t && player.get(0).getAlive()) player.get(0).PlayerMove(list_wall, list_brick, bombItemList, flameItemList, speedItemList, portal.get(0));
            }
        });
        stage.setScene(scene);
    }

    public boolean checkAlive() {
        for(int i = 0; i < list_balloom.size(); i++) {
            if(list_balloom.get(i).overLaps(player.get(0))) { player.get(0).setAlive(false);
                return false;
            }
        }
        for(int i = 0; i < list_oneal.size(); i++) {
            if(list_oneal.get(i).overLaps(player.get(0))) { player.get(0).setAlive(false);
                return false;
            }
        }
        return player.get(0).getAlive();
    }

    public void Open_Game() {
        Group rootOpen = new Group();
        Scene sceneOpen = new Scene(rootOpen, Color.BLACK);
        stage.setTitle("BOOMBERMAN GAME");
        Text text = new Text("LEVEL " + (level));
        text.setFont(Font.font(50));
        text.setX(310);
        text.setY(270);
        text.setFill(Color.WHITE);
        rootOpen.getChildren().add(text);
        stage.setHeight(600);
        stage.setWidth(800);
        stage.setScene(sceneOpen);
        stage.show();
        Timeline timer = new Timeline();
        timer.setCycleCount(0);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(3000),(ActionEvent event) -> {
            Play();
        }));
        timer.play();
    }

    public void Overgame() {
        Timeline timer = new Timeline();
        timer.setCycleCount(0);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(1000),(ActionEvent event) -> {
            Group rootOvergame = new Group();
            Scene sceneOvergame = new Scene(rootOvergame, Color.BLACK);
            Image image = null;
            try {
                image = new Image(new FileInputStream(convention.GameOver));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
            rootOvergame.getChildren().add(imageView);
            stage.setTitle("BOOMBERMAN GAME");
            stage.setHeight(600);
            stage.setWidth(800);
            stage.setScene(sceneOvergame);
        }));
        timer.play();
    }

    public void LevelUP() {
        if(level < 5) {
        list_balloom.clear();
        list_wall.clear();
        list_grass.clear();
        list_brick.clear();
        player.clear();
        portal.clear();
        list_oneal.clear();
        bombItemList.clear();
        flameItemList.clear();
        speedItemList.clear();
        level++;
        Open_Game();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Media sound = new Media(new File(sound1.SetBomb).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/
        LevelUP();
        Timeline timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(50),(ActionEvent event) -> {
            if (player.size() ==1) {
                checkAlive();
                stage.setTitle("BOOMBERMAN GAME" + "            LEVEL : " + level + "           Lives : " + list_balloom.size());
                if (!player.get(0).getAlive()) {
                    timer.stop();
                    Overgame();
                    player.get(0).playerDead();
                    Media sound = new Media(new File(sound1.Player_Dead).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
                if (list_balloom.size() == 0 && player.get(0).getRectangle().overlaps(portal.get(0).getRectangle())) {
                    Media sound = new Media(new File(sound1.LevelUp).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    LevelUP();
                }
            }}));
    timer.play();
    }


    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }
}
