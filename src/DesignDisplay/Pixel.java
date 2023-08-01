package src.DesignDisplay;

public class Pixel {
    public enum PixelType{
        START,
        END,
        AIR,
        WALL,
        NEAR,
        EXPLORED,
        HEAD
    }

    public PixelType type;

    public Pixel(){
        type = PixelType.AIR;
    }


}
