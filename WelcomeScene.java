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

public class WelcomeScene extends GeneralScene
    {
        private static final String BACKGROUND_IMAGE="assets/inicio.jpg";
        private Image background;
        public WelcomeScene()
        {
            super(); try{
            background=new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            showWelcomeMessage();
            }catch (Exception e){
            e.printStackTrace();
            }

        }
        private void showWelcomeMessage()
        {
            Font myFont= Font.font("ComicSans", FontWeight.NORMAL,40);
            gc.setFont(myFont);
            gc.setFill(Color.RED);
            gc.fillText("Space Invaders",160,360);

            myFont=Font.font("Arial",FontWeight.NORMAL,20);
            gc.setFont((myFont));
            gc.setFill(Color.WHITE);
            gc.fillText("Press Space bar to play",185,475);
        }
        @Override
        public void draw()
        {
            activeKeys.clear();
            new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                    gc.drawImage(background,0,0);
                    showWelcomeMessage();

                    if (activeKeys.contains(KeyCode.SPACE)) {
                        this.stop();
                        SpaceInvaders.setScene(SpaceInvaders.GAME_SCENE);
                    } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                        this.stop();
                        SpaceInvaders.exit();
                    }
                }
            }.start();
        }
    }
