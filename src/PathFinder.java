package src;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.Display.Panel;

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

        // Clear the search of map

        for(int i = 0; i < PANEL_HEIGHT; i++){
            for(int j = 0; j < PANEL_WIDTH; j++){
                Pixel.PixelType pixelType = this.board.getPixels()[i][j].type;
                if(pixelType == Pixel.PixelType.EXPLORED || pixelType == Pixel.PixelType.FINAL || pixelType == Pixel.PixelType.NEAR ){
                    this.board.getPixels()[i][j].type = Pixel.PixelType.AIR;
                }
            }
        }

        int[] startLocation = getStartLocation();
        int yStart = startLocation[0];
        int xStart = startLocation[1];

        System.out.println(xStart + " " + yStart);

        search(xStart, yStart);

        return true;
    }

    public boolean search(int xStart, int yStart){

        boolean goBack = false;

        Pixel wallFake = new Pixel();
        wallFake.type = Pixel.PixelType.WALL;

        // Normal rounding 4 pixels, if the pixel is in the edge of panel is a fakeWall
        Pixel[] pixels =
                {xStart < 0 || xStart >= PANEL_WIDTH/ Panel.PIXEL_SIZE ||
                 yStart - 1 < 0 || yStart - 1 >= PANEL_HEIGHT/ Panel.PIXEL_SIZE ?
                 wallFake : board.getPixels()[yStart - 1][xStart],
                 xStart + 1 < 0 || xStart + 1 >= PANEL_WIDTH/ Panel.PIXEL_SIZE ||
                 yStart < 0 || yStart >= PANEL_HEIGHT/ Panel.PIXEL_SIZE ?
                 wallFake : board.getPixels()[yStart][xStart + 1],
                 xStart < 0 || xStart >= PANEL_WIDTH/ Panel.PIXEL_SIZE ||
                 yStart + 1 < 0 || yStart + 1 >= PANEL_HEIGHT/ Panel.PIXEL_SIZE ?
                 wallFake : board.getPixels()[yStart + 1][xStart],
                 xStart - 1 < 0 || xStart - 1 >= PANEL_WIDTH/ Panel.PIXEL_SIZE ||
                 yStart < 0 || yStart >= PANEL_HEIGHT/ Panel.PIXEL_SIZE ?
                 wallFake : board.getPixels()[yStart][xStart - 1]
                };

        // Checking WIN
        for(int i = 0; i < 4; i++){
            if(pixels[i].type == Pixel.PixelType.END){
                if(board.getPixels()[yStart][xStart].type != Pixel.PixelType.START)
                    board.getPixels()[yStart][xStart].type = Pixel.PixelType.EXPLORED;
                // Paint NEAR
                for(int j = 0; j < 4; j++){
                    if(i!=j && pixels[j].type == Pixel.PixelType.AIR){
                        pixels[j].type = Pixel.PixelType.NEAR;
                    }
                }
                System.out.println("END");
                return true;
            }
        }

        // GO TO NEXT AIR
        for(int i = 0; i < 4; i++){
            if(pixels[i].type == Pixel.PixelType.AIR){
                if(board.getPixels()[yStart][xStart].type != Pixel.PixelType.START)
                    board.getPixels()[yStart][xStart].type = Pixel.PixelType.EXPLORED;
                // Paint NEAR
                for(int j = 0; j < 4; j++){
                    if(i!=j && pixels[j].type == Pixel.PixelType.AIR){
                        pixels[j].type = Pixel.PixelType.NEAR;
                    }
                }

                int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
                int nextYStart = i == 0? yStart -1: i == 2? yStart + 1: yStart;

                if(!search(nextXStart, nextYStart)){
                    board.getPixels()[nextXStart][nextYStart].type = Pixel.PixelType.EXPLORED;
                    goBack = true;
                }
                else
                    return true;

            }
        }

        if(goBack){
            // Go Back
            for(int i = 0; i < 4; i++){
                if(pixels[i].type == Pixel.PixelType.NEAR){
                    board.getPixels()[yStart][xStart].type = Pixel.PixelType.EXPLORED;
                    // Paint NEAR
                    for(int j = 0; j < 4; j++){
                        if(i!=j && pixels[j].type == Pixel.PixelType.AIR){
                            pixels[j].type = Pixel.PixelType.NEAR;
                        }
                    }

                    int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
                    int nextYStart = i == 0? yStart -1: i == 2? yStart + 1: yStart;

                    return search(nextXStart, nextYStart);
                }
            }
        }


        return false;




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
