package src.DesignDisplay;

public class Pixel {
    public enum PixelType{
        START,
        END,
        AIR,
        WALL,
        NEAR,
        FINAL,
        EXPLORED
    }

    public PixelType type;

    public Pixel(){
        type = PixelType.AIR;
    }


}
