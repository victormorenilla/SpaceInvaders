package videoGame.Scenes;

import javafx.animation.AnimationTimer;

import javafx.animation.KeyValue;
import javafx.scene.image.Image;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videoGame.SpaceInvaders;
import videoGame.Sprites.*;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static javafx.scene.input.KeyCode.*;


public class GameScene extends GeneralScene{
    private static final String BACKGROUND_IMAGE="assets/GS1.jpg";
    private static final String BACKGROUND_IMAGE2="assets/pantalla juego.jpg";
    public static final String BACKGROUND_SONG="assets/music.mp3";
    public static final String SOUND_EFFECT="assets/effects.mp3";
    public static final String ATEX="assets/atex.gif";

    List<Shoot > Allshoots= new ArrayList<>();
    List<Enemy>AllEnemys=new ArrayList<>();
    List<Laser> BossShoot=new ArrayList<>();

    private Image background;
    private Image background2;
    private Image atex;
    private MainCharacter spacecraft;
    public Enemy BadSpaceCraft;
    public Boss boss1=null;
    private Shoot bala;
    private Laser laser;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    public static int points=0;
    private int lives=3;
    boolean on=false;
    private int BossLive=300;

    public GameScene() {
        super();
        try{
            background=new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE2)));
            background2=new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            atex=new Image(Files.newInputStream(Paths.get(ATEX)));
            spacecraft =new MainCharacter();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void draw() {
        BossLive=300;
        reset();
        Allshoots.clear();
        sound=new Media(new File(BACKGROUND_SONG).toURI().toString());
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        activeKeys.clear();
        spacecraft.moveTo(385,675);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                if (points < 20) {
                    gc.drawImage(background, 0, 00);
                    spacecraft.draw(gc);

                    //////////////////crear más naves
                    if (AllEnemys.size() < Enemy.MAX_ENEMY) {
                        BadSpaceCraft = new Enemy();
                        AllEnemys.add(BadSpaceCraft);
                        BadSpaceCraft.moveTo((int) (Math.random() * (GeneralScene.GAME_WIDTH - BadSpaceCraft.ENEMY_WIDTH)) + 1, 0);

                    }
                    ///////////////////crear más disparos
                    if (activeKeys.add(SPACE)) {
                        bala = new Shoot();
                        Allshoots.add(bala);
                        bala.moveTo(spacecraft.getX() + 25, spacecraft.getY() - 15);
                    }
                    for (Shoot s : Allshoots) {
                        s.draw(gc);
                        s.move();
                    }
                    for (Enemy n : AllEnemys) {
                        n.draw(gc);
                        n.move();
                    }
                for (Enemy e : AllEnemys) {
                    for (Shoot f : Allshoots) {
                        if (f.collidesWith(e)) {
                            points += 10;
                            //e.increaseDifficulty();
                            playEffect((SOUND_EFFECT));
                            Allshoots.remove(f);
                            AllEnemys.remove(e);
                        } else if (!f.collidesWith(e) && f.getY() > GeneralScene.GAME_HEIGHT) Allshoots.remove(f);
                        if (!e.collidesWith(spacecraft) && e.getY() > GeneralScene.GAME_HEIGHT) AllEnemys.remove(e);
                        else if (e.collidesWith(spacecraft)) {
                            lives--;
                            playEffect((SOUND_EFFECT));
                            AllEnemys.remove(e);
                            spacecraft.resetPosition();
                        }
                    }
                }
            }
                //boss
                else{

                    gc.drawImage(background2, 0, 00);
                    spacecraft.draw(gc);
                    if(boss1==null){
                        BossShoot.clear();
                        boss1=new Boss();
                        boss1.moveTo(Boss.BOSS_WIDTH-50,15);

                    }
                    else{
                            boss1.draw(gc);
                            boss1.MoveDown();
                        }
                    if(boss1!=null&&BossShoot.size()<2&&BossLive>0){
                        laser=new Laser();
                        int n=(int)(Math.random()*((400+1)-200))+200;
                        BossShoot.add(laser);
                        laser.moveTo(n, boss1.getY());
                    }
                    for (Laser l:BossShoot
                    ) {
                        l.draw(gc);
                        l.moveLaser();
                        if (l.collidesWith(spacecraft) && BossLive > 0) {
                            lives -= 1;
                            playEffect((SOUND_EFFECT));
                            BossShoot.remove(l);
                        }

                        if (l.getY() > GAME_HEIGHT) {
                            BossShoot.remove(l);
                        }
                    }
                    if (activeKeys.add(SPACE)) {
                        bala = new Shoot();
                        Allshoots.add(bala);
                        bala.moveTo(spacecraft.getX() + 25, spacecraft.getY() - 15);
                    }
                    for (Shoot s : Allshoots
                    ) {
                        s.draw(gc);
                        s.move();
                        if (s.collidesWith(boss1)&&BossLive>0) {
                            BossLive -= 10;
                            points += 10;
                            playEffect((SOUND_EFFECT));
                            Allshoots.remove(s);
                        }
                    }
                    scoreBoss();
                    if(BossLive==0){
                        BossShoot.clear();
                        boss1=null;
                        BossShoot.clear();
                        gc.drawImage(atex,225,110);
                        Message();
                    }
                }
                updateHUD();
                ///lives<=0
                if ((lives == 0)|| on) {
                    boss1=null;
                    SpaceInvaders.setScene(SpaceInvaders.CREDIT_SCENE);
                    this.stop();
                    mediaPlayer.stop();
                }
                //////scenes
                if (activeKeys.contains(ESCAPE)) {
                    this.stop();
                    SpaceInvaders.setScene(SpaceInvaders.WELCOME_SCENE);
                } else if (activeKeys.contains(ENTER)) {
                    this.stop();
                    SpaceInvaders.setScene(SpaceInvaders.CREDIT_SCENE);
                } else if (activeKeys.contains(LEFT)) spacecraft.move(MainCharacter.LEFT);
                else if (activeKeys.contains(RIGHT)) spacecraft.move(MainCharacter.RIGHT);
            }
        }.start();
    }
    private void playEffect(String path){
        effect=new Media(new File(path).toURI().toString());
        mediaPlayerEffects=new MediaPlayer(effect);
        mediaPlayerEffects.play();
    }
    private void reset(){
        spacecraft.resetPosition();
        lives=3;
        points=0;
        BadSpaceCraft.STEP_INCREMENT=0f;
    }
    private  void updateHUD(){
        Font myFont= Font.font("Arial", FontWeight.NORMAL,18);
        gc.setFont(myFont);
        gc.setFill(Color.BLUE);
        gc.fillText("Score: "+points,20,GeneralScene.GAME_HEIGHT-15);
        gc.setFill(Color.YELLOW);
        gc.fillText("Lives: "+lives,GAME_WIDTH-100,GeneralScene.GAME_HEIGHT-15);
    }
    private void scoreBoss(){
        Font myFont= Font.font("Arial", FontWeight.NORMAL,22);
        gc.setFont(myFont);
        gc.setFill(Color.BLUE);
        gc.fillText("Boss Live: "+BossLive,GAME_WIDTH-320,GeneralScene.GAME_HEIGHT-15);
    }
    private void Message(){
        Font myFont= Font.font("Arial", FontWeight.NORMAL,30);
        gc.setFont(myFont);
        gc.setFill(Color.BLUE);
        gc.fillText("Congratulations you",GAME_WIDTH-410,GeneralScene.GAME_HEIGHT-400);
        gc.fillText("kill the boss",GAME_WIDTH-380,GeneralScene.GAME_HEIGHT-360);
        gc.fillText("Press enter to Continue",GAME_WIDTH-430,GeneralScene.GAME_HEIGHT-320);
    }
}
