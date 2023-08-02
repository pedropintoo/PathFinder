package src.DesignDisplay;

import java.util.Objects;

public class Pixel {

    private PixelType type;

    private final int x;
    private final int y;

    public Pixel(int x, int y){
        this.x = x;
        this.y = y;

        this.type = PixelType.AIR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x && y == pixel.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
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
