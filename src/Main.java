package src;

import src.DesignDisplay.Board;
import src.Display.Frame;

public class Main {
    public static void main(String[] args) {



        Board board = new Board(1200, 800);


        //for(Pixel pixel : board.getPixels()[50]){
        //    pixel.type = Pixel.PixelType.WALL;
        //}

        // Start the game
        Frame frame = new Frame(board);



    }
}