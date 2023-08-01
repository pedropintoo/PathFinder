package src.MazeAlgoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;

public abstract class MazeGenerator {

    private final Board board;
    public MazeGenerator(Board board) {
        this.board = board;
    }

    public abstract void generateMaze() throws InterruptedException;

}
