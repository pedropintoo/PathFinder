package src.Display;

import src.DesignDisplay.Board;
import src.DesignDisplay.MazeGenerator;
import src.DesignDisplay.Pixel;
import src.PathAlgoritms.PathFinder;
import src.PathAlgoritms.RecursiveAlgorithm;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class Panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener, FocusListener{
    // Declarations

    private static final int DELAY = 1; // milliseconds

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;

    private final int COLS;
    private final int ROWS;

    public final int PIXEL_SIZE;

    private Timer timer;

    private Board board;

    private PathFinder pathFinder;

    private boolean hasStart = false;
    private boolean hasEnd = false;

    private boolean mouseWallDown = false;
    private boolean mouseClearDown = false;

    Panel(Board board){
        this.PANEL_HEIGHT = board.getPANEL_HEIGHT();
        this.PANEL_WIDTH = board.getPANEL_WIDTH();

        this.ROWS = board.getROWS();
        this.COLS = board.getCOLS();


        this.board = board;

        this.PIXEL_SIZE = board.getPIXEL_SIZE();

        this.pathFinder = new RecursiveAlgorithm(board, this);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addFocusListener(this);


        timer = new Timer(Panel.DELAY,this);
        timer.start();


    }

    public void paint(Graphics g) {

        // Set up Graphics2D
        Graphics2D g2D = (Graphics2D) g;

        // Loop through the pixels in the board and draw each one as a square
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){

                // Calculate the coordinates of the top-left corner of the square to be drawn
                int x = j * PIXEL_SIZE;
                int y = i * PIXEL_SIZE;

                // Set the color of the square based on the type of the pixel
                switch (this.board.getPixels()[i][j].type) {
                    case AIR -> g.setColor(Color.LIGHT_GRAY);
                    case WALL -> g.setColor(Color.BLUE);
                    case START -> g.setColor(Color.GREEN);
                    case END -> g.setColor(Color.RED);
                    case NEAR -> g.setColor(Color.GRAY);
                    case EXPLORED -> g.setColor(Color.YELLOW);
                    case HEAD -> g.setColor(Color.PINK);
                }

                // Draw the square
                g.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: A cada DELAY ms vai fazer esta função

        // Repaint para re scaling
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = e.getY() / PIXEL_SIZE;
        int col = e.getX() / PIXEL_SIZE;

        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> {
                // clear current start pixel
                if(hasStart){
                    for(int i = 0; i < ROWS; i++){
                        for(int j = 0; j < COLS; j++){
                            if(this.board.getPixels()[i][j].type == Pixel.PixelType.START) {
                                this.board.getPixels()[i][j].type = Pixel.PixelType.AIR;
                            }
                        }
                    }
                }
                // gen new start pixel
                hasStart = true;
                board.getPixels()[row][col].type = Pixel.PixelType.START;
            }

            case MouseEvent.BUTTON3 -> {
                // clear current end pixel
                if(hasEnd){
                    for(int i = 0; i < ROWS; i++){
                        for(int j = 0; j < COLS; j++){
                            if(this.board.getPixels()[i][j].type == Pixel.PixelType.END){
                                this.board.getPixels()[i][j].type = Pixel.PixelType.AIR;

                            }
                        }
                    }
                }
                // gen new end pixel
                hasEnd = true;
                board.getPixels()[row][col].type = Pixel.PixelType.END;
            }
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case  MouseEvent.BUTTON1 -> mouseWallDown = true;

            case  MouseEvent.BUTTON3 -> mouseClearDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()){
            case  MouseEvent.BUTTON1 -> mouseWallDown = false;

            case  MouseEvent.BUTTON3 -> mouseClearDown = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
        mouseWallDown = false;
        mouseClearDown = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseWallDown) {
            // paint walls
            int row = e.getY() / PIXEL_SIZE;
            int col = e.getX() / PIXEL_SIZE;
            board.getPixels()[row][col].type = Pixel.PixelType.WALL;
            repaint();
        }
        else if (mouseClearDown) {
            // clear pixels
            int row = e.getY() / PIXEL_SIZE;
            int col = e.getX() / PIXEL_SIZE;
            board.getPixels()[row][col].type = Pixel.PixelType.AIR;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE -> {
                try {
                    if(!pathFinder.start()){
                        System.out.println("ERROR IN PATH FINDER!! ");
                    }
                } catch (InterruptedException ex) {
                    System.out.println("ERROR");
                }
            }

            case KeyEvent.VK_C -> {
                try {
                    board.clearAll();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            case KeyEvent.VK_V -> {
                try {
                    board.clearPath();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            case KeyEvent.VK_M -> {
                // TODO: O MAZE GENERATE TEM ERROS E ESTA CONFUSO, MAS FUNCIONA PARA TESTAR

                // Generate Maze
                MazeGenerator mazeGen = new MazeGenerator(board);
                try {
                    mazeGen.generateMaze();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}


