package src.MazeAlgoritms.Algoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.MazeAlgoritms.MazeGenerator;

import java.util.*;

public class RandomizedKruskalAlgorithm extends MazeGenerator {
    private final Board board;
    private final Pixel[][] pixels;
    public RandomizedKruskalAlgorithm(Board board) {
        super(board);
        this.board = board;
        this.pixels = board.getPixels();
    }

    @Override
    public void generateMaze() throws InterruptedException {
        // Clear all first
        board.clearAll();

        HashMap<Integer, HashSet<Pixel>> cellMap = new HashMap<>(); // map of pixel -> set
        ArrayList<Pixel> allPixels = new ArrayList<>(); // list of all pixels
        // All the pixels converted to Wall
        // Create a Set for each Cell
        List.of(pixels).forEach(row -> List.of(row).forEach(pixel -> {
            allPixels.add(pixel);
            pixel.type = Pixel.PixelType.WALL;
            cellMap.put(allPixels.size()-1,new HashSet<>(List.of(pixel)));
        }));

        int numSets = allPixels.size(); // number of sets

        int indexPixels = allPixels.size()-1;

        while(numSets > 1){


            // Chose a random pixel in chess (spaced) layout
            int randomPixel;
            do{
                randomPixel = (int) (Math.random() * indexPixels);
                System.out.println(randomPixel + "    -  "+randomPixel/board.getCOLS());
            }while(randomPixel % 2 != 0 ||  ((randomPixel/board.getCOLS())) % 2 != 0);

            allPixels.get(randomPixel).type = Pixel.PixelType.AIR;

            // Chose a random direction checking conditions (pixels in border)
            int randomDirection = getRandomDirection(randomPixel);

            int leanPixel = 0;
            switch(randomDirection) {
                case 1 -> leanPixel = randomPixel - board.getCOLS(); // up
                case 2 -> leanPixel = randomPixel + 1; // right
                case 3 -> leanPixel = randomPixel + board.getCOLS(); // down
                case 4 -> leanPixel = randomPixel - 1; // left
            }
            allPixels.get(leanPixel).type = Pixel.PixelType.AIR;

            // Add the set of leanPixel to randomPixel's Set
            cellMap.get(randomPixel).addAll(cellMap.get(leanPixel));
            numSets--;

        }

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
