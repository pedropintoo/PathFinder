package src.DesignDisplay;

public class Pixel {
    public enum PixelType{
        START,
        END,
        AIR,
        WALL,
        NEAR,
        FINAL,
        EXPLORED,
        HEAD,
        VISIT // ONLY FOR generateMaze()
    }

    public PixelType type;

    public Pixel(){
        type = PixelType.AIR;
    }


}
