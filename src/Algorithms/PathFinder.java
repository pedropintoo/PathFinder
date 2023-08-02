package src.Algorithms;

import src.DesignDisplay.Board;
import src.Display.Panel;

public abstract class PathFinder implements Algorithms{
    // Algorithms...

    private boolean shutdown = false;

    private final Board board;
    private final Panel panel;

    private final int DELAY_ANIMATION = 30;
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
                execute();
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

    public abstract void execute() throws InterruptedException;


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
