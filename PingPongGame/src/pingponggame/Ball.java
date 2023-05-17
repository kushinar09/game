/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pingponggame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author FR
 */
public class Ball extends Rectangle {

    Random random;
    int bXchange;
    int bYchange;
    int ballSpeed = 5;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
//        int randomXDirection = random.nextInt(2);
//        if (randomXDirection == 0) {
//            randomXDirection--;
//        }
//        setXDirection(randomXDirection * ballSpeed);
//
//        int randomYDirection = random.nextInt(2);
//        if (randomYDirection == 0) {
//            randomYDirection--;
//        }
//        setYDirection(randomYDirection * ballSpeed);
        
        int numb = random.nextInt(3);
        int ballX = -ballSpeed;
        int ballY = -ballSpeed;
        switch (numb) {
            case 0:
                ballX = ballSpeed;
                ballY = ballSpeed;
                break;
            case 1:
                ballX = -ballSpeed;
                ballY = ballSpeed;
                break;
            case 2:
                ballX = ballSpeed;
                ballY = -ballSpeed;
                break;
        }
        setXDirection(ballX);
        setYDirection(ballY);

    }

    public void setXDirection(int randomXDirection) {
        bXchange = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        bYchange = randomYDirection;
    }

    public void move() {
        x += bXchange;
        y += bYchange;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }
}
