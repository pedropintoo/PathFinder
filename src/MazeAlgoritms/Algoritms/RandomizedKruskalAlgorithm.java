package src.MazeAlgoritms.Algoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.MazeAlgoritms.MazeGenerator;

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
    }

}
