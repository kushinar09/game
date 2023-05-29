/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingponggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author FR
 */
public class GamePanel extends JPanel implements Runnable {

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = (int) (SCREEN_WIDTH * (0.555));
    static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    static final int BALL_SIZE = 20;
    static final int PLAYER_WIDTH = 25;
    static final int PLAYER_HEIGHT = 100;
    final int maxspeed = 14;
    final int speedOfPlayer = 10;
    Thread gameThread;
    Image image;
    Graphics g;
    Random random;
    Player player1, player2;
    Ball ball;
    Score score;

    public GamePanel() {
        newPlayer();
        newBall();
        score = new Score(SCREEN_WIDTH, SCREEN_HEIGHT);
        setFocusable(true);
        addKeyListener(new eventOfGame());
        setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newPlayer() {
        player1 = new Player(0, (int) ((SCREEN_HEIGHT - PLAYER_HEIGHT) / 2), PLAYER_WIDTH, PLAYER_HEIGHT, 1);
        player1.speed = speedOfPlayer;
        player2 = new Player(SCREEN_WIDTH - PLAYER_WIDTH, (int) ((SCREEN_HEIGHT - PLAYER_HEIGHT) / 2), PLAYER_WIDTH, PLAYER_HEIGHT, 2);
        player2.speed = speedOfPlayer;
    }

    public void newBall() {
        random = new Random();
        int Yball = random.nextInt(SCREEN_HEIGHT - BALL_SIZE);
        ball = new Ball(SCREEN_WIDTH / 2, Yball, BALL_SIZE, BALL_SIZE);
    }

    public void move() {
        player1.move();
        player2.move();
        ball.move();
    }

    public void paint(Graphics gn) {
        image = createImage(getWidth(), getHeight());
        g = image.getGraphics();
        draw(g);
        gn.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.drawLine(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
        player1.draw(g);
        player2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void checkCollision() {
        //player not out of screen
        if (player1.y < 0) {
            player1.y = 0;
        }
        if (player2.y < 0) {
            player2.y = 0;
        }
        if (player1.y > SCREEN_HEIGHT - player1.height) {
            player1.y = SCREEN_HEIGHT - player1.height;
        }
        if (player2.y > SCREEN_HEIGHT - player2.height) {
            player2.y = SCREEN_HEIGHT - player2.height;
        }

        //bounce top and bottom
        if (ball.y <= 0 || ball.y >= SCREEN_HEIGHT - ball.height) {
            ball.setYDirection(-ball.bYchange);
        }

        //bounce player
        if (ball.intersects(player1)) {
            ball.bXchange = Math.abs(ball.bXchange);
            if(ball.bXchange < maxspeed) ball.bXchange++; 
            if (ball.bYchange > 0 && ball.bYchange < maxspeed) {
                ball.bYchange++; 
            } else if(ball.bYchange > -maxspeed){
                ball.bYchange--;
            }
            ball.setXDirection(ball.bXchange);
            ball.setYDirection(ball.bYchange);
        }
        if (ball.intersects(player2)) {
            ball.bXchange = Math.abs(ball.bXchange);
            if(ball.bXchange < maxspeed) ball.bXchange++; 
            if (ball.bYchange > 0 && ball.bYchange < maxspeed) {
                ball.bYchange++; 
            } else if(ball.bYchange > -maxspeed){
                ball.bYchange--;
            }
            ball.setXDirection(-ball.bXchange);
            ball.setYDirection(ball.bYchange);
        }
        
        //ball touch bound
        if(ball.x <= 0){
            score.player2++;
            newBall();
            newPlayer();
        }
        
        if(ball.x >= SCREEN_WIDTH - ball.width){
            score.player1++;
            newBall();
            newPlayer();
        }
    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class eventOfGame extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            player2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }
}
