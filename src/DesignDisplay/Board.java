package src.DesignDisplay;

public class Board {
    private Pixel[][] pixels;

    public Board(int PANEL_WIDTH, int PANEL_HEIGHT){
        this.pixels = new Pixel[PANEL_HEIGHT][PANEL_WIDTH];
        for(int i = 0; i < PANEL_HEIGHT; i++){
            for(int j = 0; j < PANEL_WIDTH; j++){
                this.pixels[i][j] = new Pixel();
            }
        }
    }

    public Pixel[][] getPixels() {
        return this.pixels;
    }
}
