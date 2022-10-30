package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int TOTAL_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int[] x = new int[TOTAL_UNITS];
    static final int[] y = new int[TOTAL_UNITS];
    static final int DELAY = 100;
    int snakeSize = 6;
    int foodEaten;
    int foodX;
    int foodY;

    Random random;
    Timer timer;

    // directions are mapped according aswd. d= left , w= up and so one
    char direction = 'd';

    boolean running = false;

    GamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#005d32"));
        this.setFocusable(true);
        this.addKeyListener(new keyboardInputs());
        startGame();

    }

    public void startGame() {

        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //drawLines(g);

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
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, 45);

        } else {
            gameOver(g);
        }
    }

    public void drawLines(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, UNIT_SIZE * i, SCREEN_WIDTH, UNIT_SIZE * i);
        }
    }

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

    public void newFood() {
        foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeSize++;
            foodEaten++;
            newFood();
        }
    }

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

    public void gameOver(Graphics g) {

        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

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
                case KeyEvent.VK_LEFT:
                    if (direction != 'd') {
                        direction = 'a';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'a') {
                        direction = 'd';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 's') {
                        direction = 'w';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'w') {
                        direction = 's';
                    }
                    break;
            }
        }
    }
}
