package DigitalClock;

import javax.swing.*;
import java.awt.*;

public class DigitalClock extends JFrame {

    final int WIDTH = 500;
    final int HEIGHT = 200;
    private final Font title_font = new Font("Rockwell", Font.BOLD, 35);
    private final Font clock_font = new Font("Rockwell", Font.BOLD, 25);

    public DigitalClock() {

        super.setTitle("Digital Clock");
        super.setSize(WIDTH, HEIGHT);

        this.setLocationRelativeTo(null);
        this.createGUI();

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    public void createGUI() {

        JLabel heading = new JLabel("Digital Clock");
        JLabel clock = new JLabel();

        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        heading.setFont(this.title_font);

        clock.setHorizontalAlignment(JLabel.CENTER);
        clock.setFont(this.clock_font);

        Timer timer = new Timer(1000, e -> {
            String dateTime = new java.util.Date().toString();
            clock.setText(dateTime);
        });

        timer.start();

        this.setLayout(new GridLayout(2, 1));
        this.add(heading);
        this.add(clock);

    }

    public static void main(String[] args) {
        new DigitalClock();
    }
}
