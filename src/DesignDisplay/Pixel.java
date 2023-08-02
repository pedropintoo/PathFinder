package src.DesignDisplay;

public class Pixel {

    private PixelType type;

    private final int x;
    private final int y;

    public Pixel(int x, int y){
        this.x = x;
        this.y = y;

        this.type = PixelType.AIR;
    }


    // Getters

    public PixelType getType() {
        return type;
    }

    public int[] getCoords(){
        return new int[] {x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters

    public void setType(PixelType type) {
        this.type = type;
    }
}
