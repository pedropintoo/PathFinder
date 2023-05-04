package src;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.Display.Panel;

public class PathFinder {
    // Algorithms...

    private final Board board;
    private final Panel panel;

    private final int COLS;
    private final int ROWS;

    private final int DELAY_ANIMATION = 5;

    public PathFinder(Board board, Panel panel) {
        this.board = board;
        this.panel = panel;

        this.COLS = board.getCOLS();
        this.ROWS = board.getROWS();

    }


    public boolean start() throws InterruptedException {
        // In case that the start or the end does not exist
        if(getStartLocation()[0] == -1 || getEndLocation()[0] == -1){
            return false;
        }

        // Clear the search of map

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
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

        // Starting thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    search(xStart, yStart, true);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        return true;
    }

    // TODO: RECURSIVE ALGORITHM TO FINDING PATH MADE BY ME
    public boolean search(int xStart, int yStart, boolean animate) throws InterruptedException {
        this.panel.invalidate(); // Do panel.paint()

        // Animate only when finding new pixels
        // Not animate when going in reverse
        if(animate){
            Thread.sleep(DELAY_ANIMATION);
        }

        boolean goBack = false;

        Pixel wallFake = new Pixel();
        wallFake.type = Pixel.PixelType.WALL;

        // Normal rounding 4 pixels, if the pixel is in the edge of panel is a fakeWall
        Pixel[] pixels =
                {xStart < 0 || xStart >= COLS ||
                 yStart - 1 < 0 || yStart - 1 >= ROWS ?
                 wallFake : board.getPixels()[yStart - 1][xStart], // 0
                 xStart + 1 < 0 || xStart + 1 >= COLS ||
                 yStart < 0 || yStart >= ROWS ?
                 wallFake : board.getPixels()[yStart][xStart + 1], // 1
                 xStart < 0 || xStart >= COLS ||
                 yStart + 1 < 0 || yStart + 1 >= ROWS ?
                 wallFake : board.getPixels()[yStart + 1][xStart], // 2
                 xStart - 1 < 0 || xStart - 1 >= COLS ||
                 yStart < 0 || yStart >= ROWS ?
                 wallFake : board.getPixels()[yStart][xStart - 1] // 3
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
            if(pixels[i].type == Pixel.PixelType.AIR || (pixels[i].type == Pixel.PixelType.NEAR && !animate)){
                if(board.getPixels()[yStart][xStart].type != Pixel.PixelType.START)
                    board.getPixels()[yStart][xStart].type = Pixel.PixelType.EXPLORED;

                if(animate){
                    // Paint NEAR
                    for(int j = 0; j < 4; j++){
                        if(i!=j && pixels[j].type == Pixel.PixelType.AIR){
                            pixels[j].type = Pixel.PixelType.NEAR;
                        }
                    }
                }




                int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
                int nextYStart = i == 0? yStart - 1: i == 2? yStart + 1: yStart;


                // Painting HEAD
                if(board.getPixels()[nextYStart][nextXStart].type != Pixel.PixelType.START){
                    board.getPixels()[nextYStart][nextXStart].type = Pixel.PixelType.HEAD;
                    System.out.println(xStart+" "+yStart);
                    System.out.println("HEAD");
                }

                if(!search(nextXStart, nextYStart, true)){
                    board.getPixels()[nextYStart][nextXStart].type = Pixel.PixelType.EXPLORED;
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
                    if(board.getPixels()[yStart][xStart].type != Pixel.PixelType.START)
                        board.getPixels()[yStart][xStart].type = Pixel.PixelType.EXPLORED;


                    int nextXStart = i == 1? xStart + 1: i == 3? xStart - 1: xStart;
                    int nextYStart = i == 0? yStart - 1: i == 2? yStart + 1: yStart;

                    System.out.println("BACK");
                    return search(nextXStart, nextYStart, false);
                }
            }
        }

        return false;
    }


    private int[] getStartLocation(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(this.board.getPixels()[i][j].type == Pixel.PixelType.START){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return new int[] {-1,-1};
    }

    private int[] getEndLocation(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(this.board.getPixels()[i][j].type == Pixel.PixelType.END){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return new int[] {-1,-1};
    }
}
