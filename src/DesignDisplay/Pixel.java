package src.DesignDisplay;

import java.util.Objects;

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

    private int index;

    public Pixel(){
        type = PixelType.AIR;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return index == pixel.index && type == pixel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, index);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
