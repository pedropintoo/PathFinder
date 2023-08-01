package src.MazeAlgoritms.Algoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.MazeAlgoritms.MazeGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        ArrayList<HashSet<Pixel>> cellSet = new ArrayList<>();
        ArrayList<Pixel> safeSet = new ArrayList<>();
        // All the pixels converted to Wall
        // Create a Set for each Cell
        List.of(pixels).forEach(row -> List.of(row).forEach(pixel -> {
            safeSet.add(pixel);
            pixel.type = Pixel.PixelType.WALL;
            cellSet.add(new HashSet<>(List.of(pixel)));
        }));

        int indexPixels = safeSet.size()-1;

        while(cellSet.size() != 1){

            // Chose a random pixel
            int randomPixel = (int) (Math.random() * indexPixels);
            safeSet.get(randomPixel).type = Pixel.PixelType.AIR;

            // Chose a random direction checking conditions (pixels in border)
            int randomDirection = getRandomDirection(randomPixel);

            switch(randomDirection) {
                case 1 -> safeSet.get(randomPixel - board.getCOLS()).type = Pixel.PixelType.AIR; // up
                case 2 -> safeSet.get(randomPixel + 1).type = Pixel.PixelType.AIR; // right
                case 3 -> safeSet.get(randomPixel + board.getCOLS()).type = Pixel.PixelType.AIR; // down
                case 4 -> safeSet.get(randomPixel - 1).type = Pixel.PixelType.AIR; // left
            }
            break;
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
        if(randomPixel > board.getCOLS()*board.getROWS() - board.getROWS() - 1 ){
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
        System.out.println(randomPixel);
        return randomDirection;
    }
}
