package src.Algorithms.PathAlgorithms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.DesignDisplay.PixelType;
import src.Display.Panel;
import src.Algorithms.PathFinder;

import java.util.*;

public class RecursiveAlgorithm extends PathFinder {

    private final int DELAY_ANIMATION;
    private final Board board;

    private Stack<Pixel> pathPixels;



    public RecursiveAlgorithm(Board board, Panel panel) {
        super(board, panel);

        this.DELAY_ANIMATION = super.getDELAY_ANIMATION();
        this.board = board;

        this.pathPixels = new Stack<>();
    }

    @Override
    public boolean execute() throws InterruptedException {
        int[] startLocation = board.getStartLocation();

        return search(board.getPixel(startLocation[0], startLocation[1]));

    }

    @Override
    public Stack<Pixel> getPathPixels() {
        return pathPixels;
    }

    public boolean search(Pixel pixel) throws InterruptedException {
        if(super.isShutdown()) return false; // end the search

        Thread.sleep(DELAY_ANIMATION);

        if(pixel.getType() == PixelType.HEAD) pixel.setType(PixelType.EXPLORED);

        ArrayList<Pixel> toExplorePixels = new ArrayList<>();
        ArrayList<Pixel> leanPixels = board.getLeanPixels(pixel);

        // filter
        for (Pixel pix : leanPixels){
            if (pix.getType() == PixelType.END) {
                pathPixels.add(pixel);
                return true;
            }
            if(pix.getType() == PixelType.AIR) toExplorePixels.add(pix);
        }


        if(toExplorePixels.size() == 0){
            return false;
        }

        Pixel nextPixel;

        while(toExplorePixels.size() > 0){
            //nextPixel = toExplorePixels.get(0); // Next pixel to explore - First
            nextPixel = toExplorePixels.get((int) (Math.random() * (toExplorePixels.size()))); // Next pixel to explore - Random selection
            toExplorePixels.remove(nextPixel);
            toExplorePixels.forEach(pix -> pix.setType(PixelType.NEAR));

            nextPixel.setType(PixelType.HEAD);
            if(search(nextPixel)){
                if(pixel.getType() != PixelType.START) pathPixels.add(pixel);
                return true;
            }
        }

        return false;

    }
}
