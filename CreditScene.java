package videoGame.Scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videoGame.SpaceInvaders;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CreditScene extends GeneralScene{
    private static final String BACKGROUND_IMAGE="assets/fin.jpg";
    private Image background;
    public CreditScene() {
        super();
         try{
            background=new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showCreditMessage(){
        Font myFont= Font.font("Arial", FontWeight.NORMAL,32);
        gc.setFont(myFont);
        gc.setFill(Color.GREEN);
        gc.fillText("Game Over",230,350);

        myFont=Font.font("Arial",FontWeight.NORMAL,20);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("Your Score: "+GameScene.points,250,410);
        gc.setFill(Color.WHITE);
        gc.fillText("Press Spacebar to go back to welcome scene",138,720);
    }
    @Override
    public void draw() {
        activeKeys.clear();
        new AnimationTimer(){
            public void handle(long CurrentNanoTime) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                gc.drawImage(background,0,0);
                showCreditMessage();

                if (activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceInvaders.setScene(SpaceInvaders.WELCOME_SCENE);
                }
            }
        }.start();
    }
}

