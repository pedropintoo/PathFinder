package src.Display;

import src.DesignDisplay.Board;
import src.DesignDisplay.MazeGenerator;
import src.DesignDisplay.Pixel;
import src.PathAlgoritms.PathFinder;

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

        this.pathFinder = new PathFinder(board, this);

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
                    case START, FINAL -> g.setColor(Color.GREEN);
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

        if (e.getButton() == MouseEvent.BUTTON1) {

            // Replace the start Pixel
            if(hasStart){
                for(int i = 0; i < ROWS; i++){
                    for(int j = 0; j < COLS; j++){
                        if(this.board.getPixels()[i][j].type == Pixel.PixelType.START) {
                            this.board.getPixels()[i][j].type = Pixel.PixelType.AIR;
                        }
                    }
                }
            }
            hasStart = true;
            board.getPixels()[row][col].type = Pixel.PixelType.START;
        } else if (e.getButton() == MouseEvent.BUTTON3) {

            // Replace the end Pixel
            if(hasEnd){
                for(int i = 0; i < ROWS; i++){
                    for(int j = 0; j < COLS; j++){
                        if(this.board.getPixels()[i][j].type == Pixel.PixelType.END){
                            this.board.getPixels()[i][j].type = Pixel.PixelType.AIR;

                        }
                    }
                }
            }
            hasEnd = true;
            board.getPixels()[row][col].type = Pixel.PixelType.END;
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseWallDown = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseClearDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseWallDown = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseClearDown = false;
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
            int row = e.getY() / PIXEL_SIZE;
            int col = e.getX() / PIXEL_SIZE;
            board.getPixels()[row][col].type = Pixel.PixelType.WALL;
            repaint();
        }
        else if (mouseClearDown) {
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
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            try {
                if(!pathFinder.start()){
                    System.out.println("ERROR IN PATH FINDER!! ");
                }
            } catch (InterruptedException ex) {
                System.out.println("ERROR");
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_C){
            board.clearAll();
        }
        if(e.getKeyCode() == KeyEvent.VK_V){
            board.clearPath();
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            // TODO: O MAZE GENERATE TEM ERROS E ESTA CONFUSO, MAS FUNCIONA PARA TESTAR

            // Generate Maze
            MazeGenerator mazeGen = new MazeGenerator(board);
            mazeGen.generateMaze();
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

    public int getPIXEL_SIZE() {
        return this.PIXEL_SIZE;
    }
}


