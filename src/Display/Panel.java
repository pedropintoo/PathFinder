package src.Display;

import src.DesignDisplay.Board;
import src.DesignDisplay.PixelType;
import src.Algorithms.MazeAlgorithms.RandomizedKruskalAlgorithm;
import src.Algorithms.MazeGenerator;
import src.Algorithms.PathFinder;
import src.Algorithms.PathAlgorithms.RecursiveAlgorithm;

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

    private final Timer timer;

    private final Board board;

    private final PathFinder pathFinder;


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
        this.board.getPixels()
                .forEach(pixel -> {
                    // Set the color of the square based on the type of the pixel
                    switch (pixel.getType()){
                        case AIR -> g.setColor(Color.LIGHT_GRAY);
                        case WALL -> g.setColor(Color.BLUE);
                        case START -> g.setColor(Color.GREEN);
                        case END -> g.setColor(Color.RED);
                        case NEAR -> g.setColor(Color.GRAY);
                        case EXPLORED -> g.setColor(Color.YELLOW);
                        case HEAD -> g.setColor(Color.PINK);
                    }
                    // Calculate the coordinates of the top-left corner of the square to be drawn
                    // Draw the square
                    g.fillRect(pixel.getX() * PIXEL_SIZE, pixel.getY() * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                });
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: A cada DELAY ms vai fazer esta função

        // Repaint para re scaling
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / PIXEL_SIZE;
        int y = e.getY() / PIXEL_SIZE;

        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> {
                // clear current start pixel
                this.board.getPixels().stream()
                        .filter(pixel -> pixel.getType() == PixelType.START)
                        .findFirst().ifPresent(pixel -> pixel.setType(PixelType.AIR));

                // gen new start pixel
                this.board.getPixel(x, y).setType(PixelType.START);
            }

            case MouseEvent.BUTTON3 -> {
                // clear current end pixel
                this.board.getPixels().stream()
                        .filter(pixel -> pixel.getType() == PixelType.END)
                        .findFirst().ifPresent(pixel -> pixel.setType(PixelType.AIR));

                // gen new end pixel
                this.board.getPixel(x, y).setType(PixelType.END);

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
        // Optional
        mouseWallDown = false;
        mouseClearDown = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int x = e.getX() / PIXEL_SIZE;
        int y = e.getY() / PIXEL_SIZE;

        if (mouseWallDown) {
            // paint walls
            this.board.getPixel(x, y).setType(PixelType.WALL);
            repaint();
        }
        else if (mouseClearDown) {
            // clear pixels
            this.board.getPixel(x, y).setType(PixelType.AIR);
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
                MazeGenerator mazeGen = new RandomizedKruskalAlgorithm(board);
                try {
                    mazeGen.start();
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


