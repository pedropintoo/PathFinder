package src.PathAlgoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;
import src.Display.Panel;

public abstract class PathFinder {
    // Algorithms...

    private boolean shutdown = false;

    private final Board board;
    private final Panel panel;


    private final int DELAY_ANIMATION = 50;

    public PathFinder(Board board, Panel panel) {
        this.board = board;
        this.panel = panel;


    }


    public boolean start() throws InterruptedException {
        // In case that the start or the end does not exist
        if(board.getStartLocation() == null || board.getEndLocation() == null){
            return false;
        }

        board.setCurrentPathFinder(this);

        // Clear the search of map and stop the thread
        board.clearPath();

        int[] startLocation = board.getStartLocation();
        int yStart = startLocation[0];
        int xStart = startLocation[1];


        // Starting thread

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    execute(xStart, yStart);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();



        return true;
    }

    public void stop() throws InterruptedException {
        shutdown = true;
        Thread.sleep(DELAY_ANIMATION);
        shutdown = false;
    }

    public abstract void execute(int xStart, int yStart) throws InterruptedException;


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
