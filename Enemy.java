package videoGame.Sprites;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Enemy extends Sprite{
    private static final  String IMAGE_PATH="assets/nave enemiga.png";
    public static int MAX_ENEMY=5;
    public static int ENEMY_WIDTH=50;
    public static int ENEMY_HEIGHT=50;
    public static float STEP_INCREMENT=0;

    public Enemy(){
        super(ENEMY_WIDTH,ENEMY_HEIGHT);
        try
        {
            spriteImage=new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void move(){
        this.y+=(int)(2+STEP_INCREMENT);
    }
    public void increaseDifficulty(){
        STEP_INCREMENT+=0.2F;
    }
}
