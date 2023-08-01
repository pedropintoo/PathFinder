package src.DesignDisplay;

public class MazeGenerator {

    private final Board board;
    private final int ROWS;
    private final int COLS;
    private int[][] maze;
    private final Pixel[][] pixels;

    public MazeGenerator(Board board) {
        this.board = board;
        this.ROWS = board.getROWS();
        this.COLS = board.getCOLS();
        this.maze = board.getMaze();
        this.pixels = board.getPixels();
    }

    public void generateMaze() throws InterruptedException {
        // TODO: O MAZE GENERATE TEM ERROS E ESTA CONFUSO, MAS FUNCIONA PARA TESTAR

        int count;

        do{
            count = 0;
            board.clearAll();
            recGenMaze();

            for (int i = 0; i < ROWS; i++)  // make a grid of empty rooms
                for (int j = 0; j < COLS; j++) {
                    if(maze[i][j] == 1 && Math.random() < 0.90){
                        pixels[i][j].type = Pixel.PixelType.WALL;
                        count++;

                    }
                }
            System.out.println(count);

        } while (count < 300 || count > 1500);

    }

    // Recursive generation of maze
    // generate the maze
    private void recGenMaze() {



        // Create a random maze.  The strategy is to start with
        // a grid of disconnected "rooms" separated by walls.
        // then look at each of the separating walls, in a random
        // order.  If tearing down a wall would not create a loop
        // in the maze, then tear it down.  Otherwise, leave it in place.
        if (maze == null)
            maze = new int[ROWS][COLS];
        int i,j;
        int emptyCt = 0; // number of rooms
        int wallCt = 0;  // number of walls
        int[] wallrow = new int[(ROWS*COLS)/2];  // position of walls between rooms
        int[] wallcol = new int[(ROWS*COLS)/2];
        for (i = 0; i<ROWS; i++)  // start with everything being a wall
            for (j = 0; j < COLS; j++)
                maze[i][j] = 1;
        for (i = 1; i<ROWS-1; i += 2)  // make a grid of empty rooms
            for (j = 1; j<COLS-1; j += 2) {
                emptyCt++;
                maze[i][j] = -emptyCt;  // each room is represented by a different negative number
                if (i < ROWS-2) {  // record info about wall below this room
                    wallrow[wallCt] = i+1;
                    wallcol[wallCt] = j;
                    wallCt++;
                }
                if (j < COLS-2) {  // record info about wall to right of this room
                    wallrow[wallCt] = i;
                    wallcol[wallCt] = j+1;
                    wallCt++;
                }
            }

        //repaint();
        int r;
        for (i=wallCt-1; i>0; i--) {
            r = (int)(Math.random() * i);  // choose a wall randomly and maybe tear it down
            tearDown(wallrow[r],wallcol[r]);
            wallrow[r] = wallrow[i];
            wallcol[r] = wallcol[i];
        }
        for (i=1; i<ROWS-1; i++)  // replace negative values in maze[][] with emptyCode
            for (j=1; j<COLS-1; j++)
                if (maze[i][j] < 0)
                    maze[i][j] = 0;


    }


    private synchronized void tearDown(int row, int col) {
        // Tear down a wall, unless doing so will form a loop.  Tearing down a wall
        // joins two "rooms" into one "room".  (Rooms begin to look like corridors
        // as they grow.)  When a wall is torn down, the room codes on one side are
        // converted to match those on the other side, so all the cells in a room
        // have the same code.   Note that if the room codes on both sides of a
        // wall already have the same code, then tearing down that wall would
        // create a loop, so the wall is left in place.
        if (row % 2 == 1 && maze[row][col-1] != maze[row][col+1]) {
            // row is odd; wall separates rooms horizontally
            fill(row, col-1, maze[row][col-1], maze[row][col+1]);
            maze[row][col] = maze[row][col+1];

        }
        else if (row % 2 == 0 && maze[row-1][col] != maze[row+1][col]) {
            // row is even; wall separates rooms vertically
            fill(row-1, col, maze[row-1][col], maze[row+1][col]);
            maze[row][col] = maze[row+1][col];
        }
    }

    private void fill(int row, int col, int replace, int replaceWith) {
        // called by tearDown() to change "room codes".

        if (maze[row][col] == replace && row < ROWS-1 && col < COLS -1 && row > 0 && col > 0) {
            maze[row][col] = replaceWith;
            fill( row+1,col,replace,replaceWith);
            fill(row-1,col,replace,replaceWith);
            fill(row,col+1,replace,replaceWith);
            fill(row,col-1,replace,replaceWith);
        }
    }
}
