package src.MazeAlgoritms.Algoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.MazeAlgoritms.MazeGenerator;

import java.util.*;

public class RandomizedKruskalAlgorithm extends MazeGenerator {
    private final Board board;
    private final HashSet<Pixel> pixels;
    public RandomizedKruskalAlgorithm(Board board) {
        super(board);
        this.board = board;
        this.pixels = board.getPixels();
    }

    @Override
    public void generateMaze() throws InterruptedException {
        // Clear all first
        board.clearAll();

        // Pixels: $
        // Walls: | & -
        // isChessCondition()

        // $ | $ | $
        // - | - | -
        // $ | $ | $
        // - | - | -
        // $ | $ | $

        HashMap<Integer, HashSet<Pixel>> cellMap = new HashMap<>(); // map of pixel -> set
        ArrayList<Pixel> allPixels = new ArrayList<>(); // list of all pixels

        // All the pixels converted to Wall
        // Create a Set for each Cell

        List.of(pixels).forEach(row -> List.of(row).forEach(pixel -> {
            pixel.type = Pixel.PixelType.WALL;
            int indexPixel = allPixels.size();
            pixel.setIndex(indexPixel);
            allPixels.add(pixel);
            if(isChessLayout(indexPixel)){
                cellMap.put(indexPixel,new HashSet<>(List.of(pixel)));
            }
        }));

        int indexPixels = allPixels.size();

        HashSet<Integer> tempPixels = new HashSet<>();

        while(tempPixels.size() < cellMap.size()){


            // Chose a random pixel in chess (spaced) layout
            int randomPixel;
            do{
                randomPixel = (int) (Math.random() * indexPixels);

            }while(tempPixels.contains(randomPixel) || !isChessLayout(randomPixel));


            // Chose a random direction checking conditions (pixels in border)
            int randomDirection = getRandomDirection(randomPixel);

            int leanPixel;
            int betweenPixel;
            switch(randomDirection) {
                case 1 -> {
                    betweenPixel = randomPixel - board.getCOLS(); // up
                    leanPixel = randomPixel - board.getCOLS() * 2; // up
                }
                case 2 -> {
                    betweenPixel = randomPixel + 1; // right
                    leanPixel = randomPixel + 2; // up
                }
                case 3 -> {
                    betweenPixel = randomPixel + board.getCOLS(); // down
                    leanPixel = randomPixel + board.getCOLS() * 2; // up
                }
                case 4 -> {
                    betweenPixel = randomPixel - 1; // left
                    leanPixel = randomPixel - 2; // up
                }
                default -> {
                    leanPixel = 0;
                    betweenPixel = 0;
                }
            }



            // Unite the sets to the biggest set
            //System.out.println(randomPixel+" - "+betweenPixel+" - "+leanPixel);
            if(cellMap.get(randomPixel).contains(allPixels.get(leanPixel))) {
                continue;
            }

            allPixels.get(randomPixel).type = Pixel.PixelType.AIR;
            allPixels.get(betweenPixel).type = Pixel.PixelType.AIR;
            allPixels.get(leanPixel).type = Pixel.PixelType.AIR;

            HashSet<Pixel> temp = new HashSet<>();

            temp.addAll(cellMap.get(randomPixel));
            temp.addAll(cellMap.get(leanPixel));

            cellMap.get(randomPixel).forEach(pixel -> cellMap.replace(pixel.getIndex(), temp));
            cellMap.get(leanPixel).forEach(pixel -> cellMap.replace(pixel.getIndex(), temp));

            if(tempPixels.size() <= temp.size()){
                tempPixels = new HashSet<>();
                HashSet<Integer> finalTempPixels = tempPixels;
                temp.forEach(t -> finalTempPixels.add(t.getIndex()));
            }


        }

    }

    private boolean isChessLayout(int randomPixel){
        // Pixels: $

        // $ | $ | $
        // - | - | -
        // $ | $ | $
        // - | - | -
        // $ | $ | $

        return randomPixel % 2 == 0 && ((randomPixel/board.getCOLS())) % 2 == 0;
    }

    private int getRandomDirection(int randomPixel) {
        // 1 -> up // 2 -> right // 3 -> down // 4 -> left
        // Verify 4 conditions of border pixels
        int randomDirection;
        Set<Integer> condition = new HashSet<>();
        if(randomPixel < board.getCOLS()){
            // Can not go upper
            condition.add(1);
        }
        if(randomPixel > board.getCOLS()*board.getROWS() - board.getCOLS() - 1 ){
            // Can not go down
            condition.add(3);
        }
        if(randomPixel % board.getCOLS() == 0 || randomPixel == 0){
            // Can not go left
            condition.add(4);
        }
        if((randomPixel+1) % board.getCOLS() == 0){
            // Can not go right
            condition.add(2);
        }

        do{
            randomDirection = (int) (Math.random() * 4) + 1;
        }while(condition.contains(randomDirection));

        return randomDirection;
    }
}
