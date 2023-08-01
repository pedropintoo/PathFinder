package src.DesignDisplay;


import src.PathAlgoritms.PathFinder;

public class Board {
    private final Pixel[][] pixels;
    private int[][] maze;

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int PIXEL_SIZE;

    private final int COLS;
    private final int ROWS;

    private PathFinder currentPathFinder;


    public Board(int PANEL_WIDTH, int PANEL_HEIGHT, int PIXEL_SIZE){
        this.PANEL_HEIGHT = PANEL_HEIGHT;
        this.PANEL_WIDTH = PANEL_WIDTH;
        this.PIXEL_SIZE = PIXEL_SIZE;

        // Generate rows & cols & pixels
        this.ROWS = PANEL_HEIGHT/PIXEL_SIZE;
        this.COLS = PANEL_WIDTH/PIXEL_SIZE;
        this.pixels = new Pixel[ROWS][COLS];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                this.pixels[i][j] = new Pixel();
            }
        }
    }

    public Pixel[][] getPixels() {
        return this.pixels;
    }

    public int getPANEL_HEIGHT() {
        return this.PANEL_HEIGHT;
    }

    public int getPANEL_WIDTH() {
        return this.PANEL_WIDTH;
    }

    public int getPIXEL_SIZE() {
        return this.PIXEL_SIZE;
    }

    public int getCOLS() {
        return this.COLS;
    }

    public int getROWS() {
        return this.ROWS;
    }

    public int[][] getMaze() {
        return maze;
    }

    public int[] getStartLocation(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(pixels[i][j].type == Pixel.PixelType.START){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return null;
    }

    public int[] getEndLocation(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(pixels[i][j].type == Pixel.PixelType.END){
                    return new int[] {i, j}; //  {y,x}
                }
            }
        }
        return null;
    }



    public void clearAll() throws InterruptedException {
        if(currentPathFinder != null) currentPathFinder.stop();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                this.pixels[i][j] = new Pixel();
            }
        }
    }

    public void clearPath() throws InterruptedException {
        if(currentPathFinder != null) currentPathFinder.stop();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(this.pixels[i][j].type == Pixel.PixelType.NEAR || this.pixels[i][j].type == Pixel.PixelType.EXPLORED || this.pixels[i][j].type == Pixel.PixelType.HEAD){
                    this.pixels[i][j].type = Pixel.PixelType.AIR;
                }
            }
        }
    }




    public void setCurrentPathFinder(PathFinder pathFinder) {
        this.currentPathFinder = pathFinder;
    }
}
