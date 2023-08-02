package src.Display;


import src.DesignDisplay.Board;

import javax.swing.*;

public class Frame extends JFrame{

    public Frame(Board board){

        Panel panel = new Panel(board);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Frame appears in the middle of the screen
        this.setVisible(true);
    }

}
