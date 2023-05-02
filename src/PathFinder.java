package src;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;

public class PathFinder {
    // Algorithms...

    private Board board;

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;


    public PathFinder(Board board) {
        this.board = board;

        this.PANEL_HEIGHT = board.getPixels().length;
        this.PANEL_WIDTH = board.getPixels()[0].length;

    }


    public boolean start(){
        // In case that the start or the end does not exist
        if(getStartLocation()[0] == -1 || getEndLocation()[0] == -1){
            return false;
        }

        return true;
    }

    private int[] getStartLocation(){
        for(int i = 0; i < PANEL_HEIGHT; i++){
            for(int j = 0; j < PANEL_WIDTH; j++){
                if(this.board.getPixels()[i][j].type == Pixel.PixelType.START){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return new int[] {-1,-1};
    }

    private int[] getEndLocation(){
        for(int i = 0; i < PANEL_HEIGHT; i++){
            for(int j = 0; j < PANEL_WIDTH; j++){
                if(this.board.getPixels()[i][j].type == Pixel.PixelType.END){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return new int[] {-1,-1};
    }
}
