package videoGame.Scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyValue;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videoGame.SpaceInvaders;
import videoGame.Sprites.Enemy;
import videoGame.Sprites.MainCharacter;
import videoGame.Sprites.Shoot;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameScene extends GeneralScene{
    private static final String BACKGROUND_IMAGE="assets/pantalla juego.jpg";
    public static final String BACKGROUND_SONG="assets/music.mp3";
    public static final String SOUND_EFFECT="assets/effects.mp3";
    public static final String Shoot="assets/misil.png";
    ArrayList shoots= new ArrayList<>();
    private Image background;
    private MainCharacter spacecraft;
    public Enemy BadSpaceCraft=null;
    private Image misil;
    private Shoot bala=null;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    public static int points=0;
    private int lives=3;
    boolean on=false;
    private int cont=0;
    public GameScene() {
        super();
        try{
            background=new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            spacecraft =new MainCharacter();
            misil=new Image(Files.newInputStream(Paths.get(Shoot)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void draw() {
        reset();

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

                gc.drawImage(background,0,00);
                spacecraft.draw(gc);
                if(BadSpaceCraft!=null) {
                    BadSpaceCraft.draw(gc);
                }
                if (activeKeys.contains(KeyCode.SPACE))
                 {
                    on=true;
                    bala=new Shoot();
                    shoots.add(bala);
                    bala.moveTo(spacecraft.getX()+25, spacecraft.getY()-15 );
                }
                if(on) {
                    bala.draw(gc);
                    bala.move();
                }
                    updateHUD();

                if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceInvaders.setScene(SpaceInvaders.WELCOME_SCENE);
                } else if (activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    SpaceInvaders.setScene(SpaceInvaders.CREDIT_SCENE);
                }
                else if (activeKeys.contains(KeyCode.LEFT)) {
                    spacecraft.move(MainCharacter.LEFT);
                }
                else if (activeKeys.contains(KeyCode.RIGHT)) {
                    spacecraft.move(MainCharacter.RIGHT);
                }
                //////////////////crear más naves

                if(BadSpaceCraft==null){
                    BadSpaceCraft=new Enemy();
                    BadSpaceCraft.moveTo((int)(Math.random()*(GeneralScene.GAME_WIDTH - BadSpaceCraft.ENEMY_WIDTH)),0);
                    on=false;
                }else{
                    BadSpaceCraft.move();
                    if(BadSpaceCraft.collidesWith(spacecraft)||BadSpaceCraft.getY()> GeneralScene.GAME_HEIGHT){
                        lives--;
                        playEffect((SOUND_EFFECT));
                        BadSpaceCraft=null;
                        misil=null;
                        on=false;
                        spacecraft.resetPosition();
                    }
                    if(on&& bala.collidesWith(BadSpaceCraft)){
                        points+=10;
                        BadSpaceCraft.increaseDifficulty();
                        playEffect((SOUND_EFFECT));
                        BadSpaceCraft=null;
                        on=false;
                    }
                    if(on&&bala.getY()>GeneralScene.GAME_HEIGHT){
                        bala=null;
                        on=false;
                    }
                }
                if((lives==0)){
                    this.stop();
                    mediaPlayer.stop();
                    SpaceInvaders.setScene(SpaceInvaders.CREDIT_SCENE);
                }
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
}
