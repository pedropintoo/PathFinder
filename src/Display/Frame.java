package src.Display;


import src.DesignDisplay.Board;

import javax.swing.*;

public class Frame extends JFrame{

    private Panel panel;

    public Frame(Board board){

        this.panel = new Panel(board);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Frame appears in the middle of the sreen
        this.setVisible(true);
    }

}
