package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by ChiragAgg5k.
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int TOTAL_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int[] x = new int[TOTAL_UNITS];
    static int[] y = new int[TOTAL_UNITS];
    static final int DELAY = 100;
    int snakeSize = 6;
    int foodEaten;
    int foodX;
    int foodY;

    JButton restart = new JButton("Play Again");

    Random random;
    Timer timer = new Timer(DELAY, this);

    // directions are mapped according aswd. d= left , w= up and so one
    char direction = 'd';

    boolean running = false;

    /**
     * Creates new GamePanel for Snake Game
     */
    GamePanel() {

        restart.addActionListener(e -> restart());
        this.add(restart);
        restart.setVisible(false);

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        startGame();

    }

    /**
     * Starts the game
     */
    public void startGame() {

        this.setBackground(Color.decode("#005d32"));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#044527"), 10));
        this.addKeyListener(new keyboardInputs());

        newFood();
        running = true;
        timer.start();

    }

    /**
     * Paints apple at the selected x and y coordinates,
     * the snake parts at x y coordinates in the array
     * and the score at the top
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            g.setColor(Color.RED);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeSize; i++) {
                if (i == 0) {
                    g.setColor(Color.decode("#00e5e5"));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.decode("#009999"));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Rockwell", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, 45);

        } else {
            gameOver(g);
        }
    }

    /**
     * Moves the snake according to the direction from the keyboard inputs
     * Also constantly moves the snake in the direction it is facing
     */
    public void moveSnake() {

        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            // up
            case 'w' -> y[0] = y[0] - UNIT_SIZE;

            // right
            case 'd' -> x[0] = x[0] + UNIT_SIZE;

            // left
            case 'a' -> x[0] = x[0] - UNIT_SIZE;

            // down
            case 's' -> y[0] = y[0] + UNIT_SIZE;
        }

    }

    /**
     * Generates random x and y coordinates for the apple
     */
    public void newFood() {
        foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    /**
     * Checks if the snake has eaten the apple
     * If yes, then it generates a new apple and increases the snake size
     */
    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeSize++;
            foodEaten++;
            newFood();
        }
    }

    /**
     * Checks if the snake has hit the wall or itself
     */
    public void checkCollisions() {

        // collision check with body
        for (int i = snakeSize; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        // collision check with border
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    /**
     * Shows the game over screen
     */
    public void gameOver(Graphics g) {

        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#111211"), 10));
        this.timer.stop();

        g.setColor(Color.RED);
        g.setFont(new Font("Rockwell", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2 - 50);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Rockwell", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + foodEaten)) / 2, SCREEN_HEIGHT / 2);

        restart.setBounds(SCREEN_WIDTH / 2 - 100 / 2, SCREEN_HEIGHT / 2 + 20, 100, 50);
        restart.setVisible(true);

        g.setColor(Color.white);
        g.setFont(new Font("Rockwell", Font.BOLD, 15));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("OR press esc to exit", (SCREEN_WIDTH - metrics3.stringWidth("OR press esc to exit")) / 2, SCREEN_HEIGHT / 2 + 100);
    }

    /**
     * Restarts the game
     */
    public void restart() {
        snakeSize = 6;
        foodEaten = 0;
        direction = 'd';
        restart.setVisible(false);

        x = new int[TOTAL_UNITS];
        y = new int[TOTAL_UNITS];

        this.startGame();
    }

    /**
     * Overrides the actionPerformed method from the ActionListener interface
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            moveSnake();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    public class keyboardInputs extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'd') {
                        direction = 'a';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'a') {
                        direction = 'd';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 's') {
                        direction = 'w';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'w') {
                        direction = 's';
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (!running) {
                        restart();
                    }
                }

                case KeyEvent.VK_ESCAPE -> {
                    if (!running) {
                        System.exit(0);
                    } else {
                        running = false;
                    }
                }
            }
        }
    }
}
