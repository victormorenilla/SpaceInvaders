package videoGame.Sprites;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Shoot extends Sprite{

    public static final int Shoot_WIDTH=96;
    public static final int Shoot_HEIGHT=96;

    private static final String IMAGE_PATH="assets/misil.png";
    private static final int STEP=4;
    ArrayList shoots= new ArrayList<>();
    public Shoot()
    {
        super(Shoot_WIDTH,Shoot_HEIGHT);

        try{
            spriteImage=new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void position(int x,int y){

    }
    public void move(){
        this.y-=(int)(5);
    }
}
