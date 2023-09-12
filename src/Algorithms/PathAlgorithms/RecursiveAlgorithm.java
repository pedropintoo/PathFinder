package src.Algorithms.PathAlgorithms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.DesignDisplay.PixelType;
import src.Display.Panel;
import src.Algorithms.PathFinder;

import java.util.*;

public class RecursiveAlgorithm extends PathFinder {

    private final Panel panel;
    private final int DELAY_ANIMATION;
    private final Board board;
    private final int COLS;
    private final int ROWS;
    private Stack<Pixel> stackNearPixels;

    private Stack<Pixel> pathPixels;

    private HashSet<Pixel> alternativePixels;


    public RecursiveAlgorithm(Board board, Panel panel) {
        super(board, panel);

        this.panel = super.getPanel();
        this.DELAY_ANIMATION = super.getDELAY_ANIMATION();
        this.board = board;
        this.COLS = board.getCOLS();
        this.ROWS = board.getROWS();

        this.stackNearPixels = new Stack<>();
        this.pathPixels = new Stack<>();
        this.alternativePixels = new HashSet<>();
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

        Pixel nextPixel;

        if(toExplorePixels.size() == 0){
            // clear the alternativePixels on pathPixels

            if(stackNearPixels.size() == 0) {
                return false;
            }
            nextPixel = stackNearPixels.pop();
            nextPixel.setType(PixelType.HEAD);

            pathPixels.remove(pixel);
            return search(nextPixel);
        }

        //nextPixel = toExplorePixels.get(0); // Next pixel to explore - First
        nextPixel = toExplorePixels.get((int) (Math.random() * (toExplorePixels.size()))); // Next pixel to explore - Random selection
        toExplorePixels.remove(nextPixel);



        toExplorePixels.forEach(pix -> {
            pix.setType(PixelType.NEAR);
            stackNearPixels.add(pix);
        });

        nextPixel.setType(PixelType.HEAD);

        if(!search(nextPixel)){
            pathPixels.remove(pixel);
            return false;
        }else {
            pathPixels.add(pixel);
            return true;
        }

    }



//    // TODO: RECURSIVE ALGORITHM TO FINDING PATH MADE BY ME
//    public boolean search(int xStart, int yStart, boolean animate) throws InterruptedException {
//        if(super.isShutdown()) return true; // end the search
//
//
//        // Animate only when finding new pixels
//        // Not animate when going in reverse
//        if(animate){
//            Thread.sleep(DELAY_ANIMATION);
//        }
//
//
//
//        boolean goBack = false;
//
//        Pixel wallFake = new Pixel(-1,-1);
//        wallFake.setType(PixelType.WALL);
//
//        // Normal rounding 4 pixels, if the pixel is in the edge of panel is a fakeWall
//        Pixel[] pixels =
//                {xStart < 0 || xStart >= COLS ||
//                        yStart - 1 < 0 || yStart - 1 >= ROWS ?
//                        wallFake : board.getPixel(xStart,yStart-1), // 0
//                        xStart + 1 < 0 || xStart + 1 >= COLS ||
//                                yStart < 0 || yStart >= ROWS ?
//                                wallFake : board.getPixel(xStart+1, yStart), // 1
//                        xStart < 0 || xStart >= COLS ||
//                                yStart + 1 < 0 || yStart + 1 >= ROWS ?
//                                wallFake : board.getPixel(xStart, yStart+1), // 2
//                        xStart - 1 < 0 || xStart - 1 >= COLS ||
//                                yStart < 0 || yStart >= ROWS ?
//                                wallFake : board.getPixel(xStart-1, yStart) // 3
//                };
//
//
//
//        // Checking WIN
//        for(int i = 0; i < 4; i++){
//            if(pixels[i].getType() == PixelType.END){
//                if(board.getPixel(xStart,yStart).getType() != PixelType.START)
//                    board.getPixel(xStart,yStart).setType(PixelType.EXPLORED);
//                // Paint lean
//                for(int j = 0; j < 4; j++){
//                    if(i!=j && pixels[j].getType() == PixelType.AIR){
//                        pixels[j].setType(PixelType.NEAR);
//                    }
//                }
//
//                //System.out.println("END");
//                return true;
//            }
//        }
//
//        // GO TO NEXT AIR
//        for(int i = 0; i < 4; i++){
//            if(pixels[i].getType() == PixelType.AIR || (pixels[i].getType() == PixelType.NEAR && !animate)){
//                if(board.getPixel(xStart,yStart).getType() != PixelType.START)
//                    board.getPixel(xStart,yStart).setType(PixelType.EXPLORED);
//
//                if(animate){
//                    // Paint lean
//                    for(int j = 0; j < 4; j++){
//                        if(i!=j && pixels[j].getType() == PixelType.AIR){
//                            pixels[j].setType(PixelType.NEAR);
//                        }
//                    }
//                }
//
//
//
//
//                int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
//                int nextYStart = i == 0? yStart - 1: i == 2? yStart + 1: yStart;
//
//
//                // Painting HEAD
//                if(board.getPixel(nextXStart,nextYStart).getType() != PixelType.START){
//                    board.getPixel(nextXStart, nextYStart).setType(PixelType.HEAD);
//                }
//
//                if(!search(nextXStart, nextYStart, true)){
//                    board.getPixel(nextXStart, nextYStart).setType(PixelType.EXPLORED);
//                    goBack = true;
//                }
//                else
//                    return true;
//
//            }
//        }
//
//        if(goBack){
//            // Go Back
//            for(int i = 0; i < 4; i++){
//                if(pixels[i].getType() == PixelType.NEAR){
//                    if(board.getPixel(xStart,yStart).getType() != PixelType.START)
//                        board.getPixel(xStart,yStart).setType(PixelType.EXPLORED);
//
//
//                    int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
//                    int nextYStart = i == 0? yStart - 1: i == 2? yStart + 1: yStart;
//                    System.out.println("a");
//                    //System.out.println("BACK");
//                    return search(nextXStart, nextYStart, false);
//                }
//            }
//        }
//
//        return false;
//    }
}
