package videoGame.Sprites;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;


public class Laser extends Sprite {

    public static final int Shoot_WIDTH = 96;
    public static final int Shoot_HEIGHT = 96;

    private static final String IMAGE_PATH2 = "assets/laser1.png";

    public Laser() {
        super(Shoot_WIDTH, Shoot_HEIGHT);

        try {
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH2)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void moveLaser() {
        this.y += 4;
    }
}