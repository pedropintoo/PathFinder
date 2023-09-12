package src.DesignDisplay;


import src.Algorithms.MazeGenerator;
import src.Algorithms.PathFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Board {
    private final HashSet<Pixel> pixels;

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int PIXEL_SIZE;

    private final int COLS;
    private final int ROWS;

    private PathFinder currentPathFinder;
    private MazeGenerator currentMazeGenerator;


    public Board(int PANEL_WIDTH, int PANEL_HEIGHT, int PIXEL_SIZE){
        this.PANEL_HEIGHT = PANEL_HEIGHT;
        this.PANEL_WIDTH = PANEL_WIDTH;
        this.PIXEL_SIZE = PIXEL_SIZE;

        // Generate rows & cols
        this.ROWS = PANEL_HEIGHT/PIXEL_SIZE;
        this.COLS = PANEL_WIDTH/PIXEL_SIZE;

        // Generate pixels
        this.pixels = new HashSet<>();
        for(int y = 0; y < ROWS; y++){
            for(int x = 0; x < COLS; x++){
                this.pixels.add(new Pixel(x, y));
            }
        }
    }

    public HashSet<Pixel> getPixels() {
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


    public int[] getStartLocation(){

        Pixel tempPixel = pixels.stream()
                .filter(pixel -> pixel.getType() == PixelType.START)
                .findFirst()
                .orElse(null);


        return tempPixel == null ? null : tempPixel.getCoords();
    }

    public int[] getEndLocation(){
        Pixel tempPixel = pixels.stream()
                .filter(pixel -> pixel.getType() == PixelType.END)
                .findFirst()
                .orElse(null);


        return tempPixel == null ? null : tempPixel.getCoords();
    }

    public Pixel getPixel(int x, int y){
        // TODO: Poderei ter de criar um int[][] para tornar mais eficiente!!!
        return pixels.stream()
                .filter(pixel -> Arrays.equals(pixel.getCoords(), new int[]{x, y}))
                .findFirst().orElse(null);
    }

    public ArrayList<Pixel> getLeanPixels(Pixel pixel){
        ArrayList<Pixel> leanPixels = new ArrayList<>();

        leanPixels.add(this.getPixel(pixel.getX(), pixel.getY()-1)); // up
        leanPixels.add(this.getPixel(pixel.getX()+1, pixel.getY())); // right
        leanPixels.add(this.getPixel(pixel.getX(), pixel.getY()+1)); // down
        leanPixels.add(this.getPixel(pixel.getX()-1, pixel.getY())); // left

        leanPixels.removeAll(Collections.singleton(null));

        return leanPixels;
    }

    public ArrayList<Pixel> getExploredPixels(){
        return new ArrayList<>(pixels.stream()
                .filter(pixel -> pixel.getType() == PixelType.EXPLORED)
                .toList());
    }


    public void clearAll() throws InterruptedException {
        pixels.stream()
                .filter(pixel -> pixel.getType() != PixelType.AIR)
                .forEach(pixel -> pixel.setType(PixelType.AIR));
    }

    public void clearPath() throws InterruptedException {
        pixels.stream()
                .filter(pixel -> pixel.getType() == PixelType.NEAR || pixel.getType() == PixelType.EXPLORED || pixel.getType() == PixelType.HEAD)
                .forEach(pixel -> pixel.setType(PixelType.AIR));
    }

    public void stopPathThread() throws InterruptedException {
        if(currentPathFinder != null) currentPathFinder.stop();
    }

    public void stopMazeThread() throws  InterruptedException{
        if(currentMazeGenerator != null) currentMazeGenerator.stop();
    }

    public void setCurrentPathFinder(PathFinder pathFinder) {
        this.currentPathFinder = pathFinder;
    }

    public void setCurrentMazeGenerator(MazeGenerator currentMazeGenerator) {
        this.currentMazeGenerator = currentMazeGenerator;
    }
}
