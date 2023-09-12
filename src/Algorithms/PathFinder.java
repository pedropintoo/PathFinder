package src.Algorithms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.Display.Panel;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public abstract class PathFinder implements Algorithms{
    // Algorithms...

    private boolean shutdown = false;

    private int[][] adjMatrix;

    private final Board board;
    private final Panel panel;

    private final int DELAY_ANIMATION = 150;
    private final int DELAY_SAFE_STOP = 50;


    public PathFinder(Board board, Panel panel) {
        this.board = board;
        this.panel = panel;

    }


    public boolean start() throws InterruptedException {
        // In case that the start or the end does not exist
        if(board.getStartLocation() == null || board.getEndLocation() == null){
            return false;
        }

        // Clear the search of map and stop the thread
        board.stopPathThread();
        board.stopMazeThread();
        board.clearPath();

        board.setCurrentPathFinder(this);


        // Starting thread

        Thread thread = new Thread(() -> {

            try {

                if(execute()){
                    System.out.println("Maze was completed!");
                    getPathPixels().forEach(System.out::println);
                    //solution();
                }else{
                    System.out.println("Maze is impossible!");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();



        return true;
    }

    public void stop() throws InterruptedException {
        shutdown = true;
        Thread.sleep(DELAY_SAFE_STOP);
        shutdown = false;
    }

    public abstract boolean execute() throws InterruptedException;

    public abstract Stack<Pixel> getPathPixels();

    public void solution(){
        // TODO: Algorithm that selects the perfect path
        // required: board already explored successfully!

        ArrayList<Pixel> pathPixels = board.getExploredPixels();
        Pixel startPixel = board.getPixel(board.getStartLocation()[0],board.getStartLocation()[1]);
        Pixel endPixel = board.getPixel(board.getEndLocation()[0],board.getEndLocation()[1]);

        pathPixels.add(startPixel);
        pathPixels.add(endPixel);

        // Adjacency matrix
        adjMatrix = new int[pathPixels.size()][pathPixels.size()];
        Pixel staticPixel;
        Pixel varPixel;
        for(int i = 0; i < pathPixels.size(); i++){
            staticPixel = pathPixels.get(i);
            for(int j = 0; j < pathPixels.size(); j++){
                if(i == j){
                    adjMatrix[i][j] = 0; // 0 cost (same pixel)
                }else{
                    varPixel = pathPixels.get(j);
                    if(board.getLeanPixels(staticPixel).contains(varPixel)) {
                        adjMatrix[i][j] = 1;
                        adjMatrix[j][i] = 1;
                    }
                }
            }
        }

//        for(int[] row : adjMatrix){
//            for(int col: row){
//                System.out.print(col + "  ");
//            }
//            System.out.println();
//        }

        // Finding solution path

        //solution(startPixel, endPixel);

    }


    public boolean isShutdown() {
        return shutdown;
    }

    public Panel getPanel() {
        return panel;
    }

    public int getDELAY_ANIMATION() {
        return DELAY_ANIMATION;
    }

}
