package src.Algorithms.MazeAlgorithms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.DesignDisplay.PixelType;
import src.Algorithms.MazeGenerator;

import java.util.*;

public class RandomizedKruskalAlgorithm extends MazeGenerator {
    private final Board board;
    private final HashSet<Pixel> pixels;

    private final int COLS;
    private final int ROWS;

    private final int DELAY_ANIMATION;

    public RandomizedKruskalAlgorithm(Board board) {
        super(board);
        this.board = board;
        this.pixels = board.getPixels();
        this.COLS = board.getCOLS();
        this.ROWS = board.getROWS();
        this.DELAY_ANIMATION = super.getDELAY_ANIMATION();
    }

    @Override
    public void execute() throws InterruptedException {

        HashMap<Pixel, HashSet<Pixel>> cellMap = new HashMap<>(); // map of pixel -> set

        // All the pixels converted to Wall
        // Create a Set for each Pixel (in chess layout)
        pixels.forEach(pixel -> {
            pixel.setType(PixelType.WALL);
            if(isChessLayout(pixel)){
                cellMap.put(pixel,new HashSet<>(List.of(pixel)));
            }
        });


        HashSet<Pixel> visitedPixels = new HashSet<>();
        Pixel firstPixel = null;
        boolean first = true;

        while(visitedPixels.size() < cellMap.size()){
            if(super.isShutdown()) return; // end the generation

            Thread.sleep(DELAY_ANIMATION);

            // Chose a random pixel in chess (spaced) layout
            int randomX;
            int randomY;
            Pixel randomPixel;
            do{
                randomX = (int) (Math.random() * COLS);
                randomY = (int) (Math.random() * ROWS);
                randomPixel = this.board.getPixel(randomX,randomY);

            }while(visitedPixels.contains(randomPixel) || !isChessLayout(randomPixel));

            if(first){
                firstPixel = randomPixel;
                first = false;
            }

            // Chose a random direction checking conditions (pixels in border)
            int randomDirection = getRandomDirection(randomPixel);

            Pixel leanPixel = null;
            Pixel betweenPixel = null;

            switch(randomDirection) {
                case 1 -> {
                    betweenPixel = board.getPixel(randomX, randomY+1); // up
                    leanPixel = board.getPixel(randomX, randomY+2); // up
                }
                case 2 -> {
                    betweenPixel = board.getPixel(randomX+1, randomY); // right
                    leanPixel = board.getPixel(randomX+2, randomY); // right
                }
                case 3 -> {
                    betweenPixel = board.getPixel(randomX, randomY-1); // down
                    leanPixel = board.getPixel(randomX, randomY-2); // down
                }
                case 4 -> {
                    betweenPixel = board.getPixel(randomX-1, randomY); // left
                    leanPixel = board.getPixel(randomX-2, randomY); // left
                }
            }

            if(leanPixel == null || betweenPixel == null) continue;

            // Join the sets to the biggest set
            //System.out.println(randomPixel+" - "+betweenPixel+" - "+leanPixel);
            if(cellMap.get(randomPixel).contains(leanPixel)) {
                continue;
            }

            // Clear all the 3 pixels
            randomPixel.setType(PixelType.AIR);
            betweenPixel.setType(PixelType.AIR);
            leanPixel.setType(PixelType.AIR);


            cellMap.get(leanPixel).addAll(cellMap.get(randomPixel));

            Pixel finalLeanPixel = leanPixel;
            cellMap.get(randomPixel).forEach(pixel -> cellMap.replace(pixel, cellMap.get(finalLeanPixel)));


            if(cellMap.get(leanPixel).contains(firstPixel)){
                visitedPixels.addAll(cellMap.get(randomPixel));
            }

        }
    }


    private boolean isChessLayout(Pixel pixel){
        // Pixels: $

        // $ | $ | $
        // - | - | -
        // $ | $ | $
        // - | - | -
        // $ | $ | $

        return pixel.getX() % 2 == 0 && pixel.getY() % 2 == 0;
    }

    private int getRandomDirection(Pixel pixel) {
        // 1 -> up // 2 -> right // 3 -> down // 4 -> left
        // Verify 4 conditions of border pixels
        int randomDirection;
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

        do{
            randomDirection = (int) (Math.random() * 4) + 1;
        }while(condition.contains(randomDirection));

        return randomDirection;
    }
}
