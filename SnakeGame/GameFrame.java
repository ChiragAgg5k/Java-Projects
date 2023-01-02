package SnakeGame;

import javax.swing.*;

/**
 * Created by ChiragAgg5k.
 */
public class GameFrame extends JFrame {

    /**
     * Creates new GameFrame for Snake Game
     */
    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake by ChiragAgg5k");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
