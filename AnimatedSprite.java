package videoGame.Sprites;

public class AnimatedSprite extends Sprite {
    public static final int TOTAL_MOVEMENTS=2;
    public static final int RIGHT=0;
    public static final int LEFT=1;
    public static final int UP=2;
    public static final byte SPRITE_CHANGE=5;

    protected int currentDirection;
    protected byte currentSprite;
    protected  byte currentSpriteChanges;

    protected int[][] spriteXCoordinates=new int[TOTAL_MOVEMENTS][];
    protected  int[][] spriteYCoordinates=new int[TOTAL_MOVEMENTS][];

    public AnimatedSprite(int width,int height){
        super(width,height);
        currentDirection=RIGHT;
        currentSprite=0;
        currentSpriteChanges=0;
    }
    public void animate(int movement)
    {
        if(movement!=currentDirection){
            currentDirection=movement;
            currentSprite=0;
            currentSpriteChanges=0;
        }else{
            currentSpriteChanges++;
            if(currentSpriteChanges>=SPRITE_CHANGE) {
                currentSpriteChanges = 0;
                currentSprite = (byte) ((currentSprite + 1) %
                        spriteXCoordinates[currentDirection].length);
            }
        }
        updateSpriteCoordinates();
    }
    protected void updateSpriteCoordinates()
    {
        spriteX=spriteXCoordinates[currentDirection][currentSprite];
        spriteY=spriteYCoordinates[currentDirection][currentSprite];
    }
}
