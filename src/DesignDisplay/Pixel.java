package src.DesignDisplay;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public String toString() {
        return "Pixel{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
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
