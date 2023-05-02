package src;

public class Pixel {
    public enum PixelType{
        START,
        END,
        AIR,
        WALL,
        NEAR,
        ALTERNATIVE,
        FINAL,
        EXPLORED
    }

    public PixelType type;

    Pixel(){
        type = PixelType.AIR;
    }


}
