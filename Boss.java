package videoGame.Sprites;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class Boss extends Sprite{
    private static final  String IMAGE_PATH="assets/Boss.png";
    public static int BOSS_WIDTH=250;
    public static int BOSS_HEIGHT=273;
    public static float STEP_INCREMENT=0;

    public Boss(){
        super(BOSS_WIDTH,BOSS_HEIGHT);
        try
        {
            spriteImage=new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void MoveRight(){
        if(this.x<350){
            this.x+=1;
        }
    }
    public void MoveLeft(){
        if(this.x>10){
            this.x-=1;
        }
    }
    public void MoveDown(){
        if(this.y<50){
            this.y+=1;
        }
    }
    public void increaseDifficulty(){
        STEP_INCREMENT+=0.3F;
    }
}

