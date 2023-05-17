/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingponggame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 * @author FR
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 640;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    public static int speed = 0;
    public static Timer timer;

    public boolean right, down, running = false;
//    public SpelTimerTask spelTask;
    public Rectangle ball, block1, block2, space;
    public JButton start;

    Random random;

    public GamePanel() {
        random = new Random();
        space = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        ball = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, UNIT_SIZE, UNIT_SIZE);
        block1 = new Rectangle(0, 0, UNIT_SIZE, 4 * UNIT_SIZE);
        block2 = new Rectangle(SCREEN_WIDTH - UNIT_SIZE, 0, UNIT_SIZE, 4 * UNIT_SIZE);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        //this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball = new Rectangle(0, 0, 20, 20);
                start.setFocusable(false);
                start.hide();
                startGame();
            }
        });
        add(start);
    }

//    class SpelTimerTask extends TimerTask {
//
//        public void run() {
//            repaint();
//            if (running) {
//                move();
//                repaint();
//            }
//        }
//    }

    public void startGame() {
        running = true;
        timer = new Timer(70, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
//        if (running) {
            //g.clearRect(space.x, space.y, space.width, space.height);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            }
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.fillOval(ball.x, ball.y, ball.width, ball.height);
            g.setColor(Color.red);
            g.fillRect(block1.x, block1.y, block1.width, block1.height);
            g.setColor(Color.blue);
            g.fillRect(block2.x, block2.y, block2.width, block2.height);
        //}
    }

    public void move() {
        if (right) {
            ball.x += UNIT_SIZE * (1 + 1 * speed);
        } else {
            ball.x -= UNIT_SIZE * (1 + 1 * speed);
        }
        if (down) {
            ball.y += UNIT_SIZE * (1 + 1 * speed);
        } else {
            ball.y -= UNIT_SIZE * (1 + 1 * speed);
        }

        if (ball.y >= block1.y && ball.y <= (block1.y + block1.height) && ball.x == block1.width) {
            right = true;
        }
        if (ball.y >= block2.y && ball.y <= (block2.y + block1.height) && ball.x == (SCREEN_WIDTH - block2.width + ball.width)) {
            right = false;
        }
        if (ball.y <= 10) {
            down = true;
        }
        if (ball.y >= SCREEN_HEIGHT - ball.height - 10) {
            down = false;
        }
    }

    public void checkGameOver() {
        if (ball.x <= 10 || ball.x >= SCREEN_WIDTH - 10) {
            running = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkGameOver();
        }
        repaint();
    }
}
