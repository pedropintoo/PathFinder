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
    Stack<Pixel> stackNearPixels;

    public RecursiveAlgorithm(Board board, Panel panel) {
        super(board, panel);

        this.panel = super.getPanel();
        this.DELAY_ANIMATION = super.getDELAY_ANIMATION();
        this.board = board;
        this.COLS = board.getCOLS();
        this.ROWS = board.getROWS();

        this.stackNearPixels = new Stack<>();
    }

    @Override
    public void execute() throws InterruptedException {
        int[] startLocation = board.getStartLocation();
        int xStart = startLocation[0];
        int yStart = startLocation[1];

        search(board.getPixel(xStart, yStart));
    }

    public boolean search(Pixel pixel) throws InterruptedException {
        if(super.isShutdown()) return true; // end the search
        
        Thread.sleep(DELAY_ANIMATION);
        
        // Alterar a randomDirection para considerar os pixeis em volta
        Pixel nextPixel = getRandomDirection(pixel);

        if(stackNearPixels.size() > 0){
            if(nextPixel == null){
                return search(stackNearPixels.lastElement());
            }else{
                return search(nextPixel);
            }
        }else {
            return false;
        }
    }

    private Pixel getRandomDirection(Pixel pixel) {
        // 1 -> up // 2 -> right // 3 -> down // 4 -> left
        // Verify 4 conditions of border pixels
        int randomDirection;
        int[] directions = new int[] {1,2,3,4};
        Set<Integer> condition = new HashSet<>();
        
        if(pixel.getY() == 0){
            // Can not go upper
            condition.add(1);
        }
        if(pixel.getY() == ROWS-1){
            // Can not go down
            condition.add(3);
        }
        if(pixel.getX() == 0){
            // Can not go left
            condition.add(4);
        }
        if(pixel.getX() == COLS-1){
            // Can not go right
            condition.add(2);
        }

        ArrayList<Pixel> leanPixels = new ArrayList<>();
        
        Pixel lean;
        for(int d : directions){
            if(condition.contains(d)) continue; // can not go this direction
            switch (d){
                case 1 -> {
                    lean = board.getPixel(pixel.getX(), pixel.getY()-1);
                    if(lean.getType() == PixelType.AIR) {
                        leanPixels.add(lean); // up
                    }
                }
                case 2 -> {
                    lean = board.getPixel(pixel.getX()+1, pixel.getY());
                    if(lean.getType() == PixelType.AIR) {
                        leanPixels.add(lean); // right
                    }
                }
                case 3 -> {
                    lean = board.getPixel(pixel.getX(), pixel.getY()+1); // down
                    if(lean.getType() == PixelType.AIR) {
                        leanPixels.add(lean); // down
                    }
                }
                case 4 -> {
                    lean = board.getPixel(pixel.getX()-1, pixel.getY()); // left
                    if(lean.getType() == PixelType.AIR) {
                        leanPixels.add(lean); // left
                    }
                }
            }
        }

        if(leanPixels.size() == 0) return null;

        lean = leanPixels.get(0); // Next pixel to explore - First
        //lean = leanPixels.get((int) (Math.random() * leanPixels.size())); // Next pixel to explore - Random selection
        leanPixels.remove(lean);

        leanPixels.forEach(pix -> {
            pix.setType(PixelType.NEAR);
            stackNearPixels.add(pix);
        });

        lean.setType(PixelType.EXPLORED);

        return lean;
    }
    



//
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
//                        pixels[j].setType(PixelType.lean);
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
//            if(pixels[i].getType() == PixelType.AIR || (pixels[i].getType() == PixelType.lean && !animate)){
//                if(board.getPixel(xStart,yStart).getType() != PixelType.START)
//                    board.getPixel(xStart,yStart).setType(PixelType.EXPLORED);
//
//                if(animate){
//                    // Paint lean
//                    for(int j = 0; j < 4; j++){
//                        if(i!=j && pixels[j].getType() == PixelType.AIR){
//                            pixels[j].setType(PixelType.lean);
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
//                if(pixels[i].getType() == PixelType.lean){
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
