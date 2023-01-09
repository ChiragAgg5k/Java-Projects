package PingPong;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{

    static final int WIDTH = 1000;
    static final int HEIGHT = (WIDTH*5)/9;
    static final Dimension SCREEN_SIZE = new Dimension(WIDTH,HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Random random;
    Graphics graphics;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        score = new Score(WIDTH,HEIGHT);

    }
    @Override
    public void run() {

    }
}
