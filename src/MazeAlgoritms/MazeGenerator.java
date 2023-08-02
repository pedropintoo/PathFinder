package src.MazeAlgoritms;

import src.DesignDisplay.Board;
import src.DesignDisplay.Pixel;

public abstract class MazeGenerator {

    private final Board board;

    private boolean shutdown = false;

    private final int DELAY_ANIMATION = 1;
    private final int DELAY_SAFE_STOP = 50;

    public MazeGenerator(Board board) {
        this.board = board;
    }

    public void start() throws InterruptedException {


        // Clear all first and stop the thread
        board.stopPathThread();
        board.stopMazeThread();
        board.clearPath();

        board.setCurrentMazeGenerator(this);

        // Starting thread

        Thread thread = new Thread(() -> {

            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();
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

    public int getDELAY_ANIMATION() {
        return DELAY_ANIMATION;
    }
}
