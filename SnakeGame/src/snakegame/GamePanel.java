/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author FR
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int MEAT_SIZE = UNIT_SIZE * 2;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int meatsEaten = 0;
    int meatX;
    int meatY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    public JButton start;
    private String msg = "";
    //String fpath = "C:\\Users\\FR\\Documents\\GitHub\\game\\SnakeGame\\src\\snakegame\\record.txt";
    Path rootDir = Paths.get(".").normalize().toAbsolutePath();
    String fpath = rootDir.toString() + "/src/" + "record.txt";

    public GamePanel() {
        random = new Random();
        start = new JButton("Start");
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
        add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {              
                start.setFocusable(false);
                start.hide();
                startGame();
            }
        });
        
        //startGame();
    }

    public void startGame() {
        newMeat();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
            g.setColor(Color.red);
            g.fillOval(meatX, meatY, MEAT_SIZE, MEAT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.setColor(Color.BLUE);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
//                } else if(i == bodyParts-1){
//                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//                    g.setColor(Color.BLUE);
//                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.setColor(Color.white);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metr = getFontMetrics(g.getFont());
            g.drawString("Score: " + meatsEaten, (SCREEN_WIDTH - metr.stringWidth("Score: " + meatsEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g, msg);
        }
    }

    public void newMeat() {
        meatX = random.nextInt((int) (SCREEN_WIDTH / MEAT_SIZE)) * MEAT_SIZE;
        meatY = random.nextInt((int) (SCREEN_HEIGHT / MEAT_SIZE)) * MEAT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkMeat() {
        if (x[0] >= meatX && y[0] >= meatY && x[0] < meatX + MEAT_SIZE && y[0] < meatY + MEAT_SIZE) {
            bodyParts++;
            meatsEaten++;
            newMeat();
        }
    }

    public void checkCollishion() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) & (y[0] == y[i])) {
                running = false;
                msg = "bodyCollishion";
            }
        }
        //left
        if (x[0] < 0) {
            //x[0] = SCREEN_WIDTH - UNIT_SIZE;
            running = false;
            msg = "borderCollishion";
        }
        //right
        if (x[0] > SCREEN_WIDTH - UNIT_SIZE) {
            //x[0] = 0;
            running = false;
            msg = "borderCollishion";
        }
        //up
        if (y[0] < 0) {
            //y[0] = SCREEN_HEIGHT - UNIT_SIZE;
            running = false;
            msg = "borderCollishion";
        }
        //down
        if (y[0] > SCREEN_HEIGHT - UNIT_SIZE) {
            //y[0] = 0;
            running = false;
            msg = "borderCollishion";
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g, String msg) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 80));
        FontMetrics metr1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metr1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2 - 25);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metr2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + meatsEaten, (SCREEN_WIDTH - metr2.stringWidth("Score: " + meatsEaten)) / 2, (SCREEN_HEIGHT / 2) + 25);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metr3 = getFontMetrics(g.getFont());
        g.drawString("Error: " + msg, (SCREEN_WIDTH - metr3.stringWidth("Eror: " + msg)) / 2, (SCREEN_HEIGHT / 2) + 75);

        int record = 0;
//        File log = new File(fpath);

        try {
            Scanner scanner = new Scanner(new File(fpath));
            while (scanner.hasNextInt()) {
                record = scanner.nextInt();
            }
//            try (BufferedReader br = new BufferedReader(fr)) {
//
//                record = br.read();
//                if(record < meatsEaten) record = meatsEaten;
//                String str = "" + record;
//                FileWriter fw = new FileWriter(log);
//                fw.write(record);
//                fw.close();
//            }
            if(record < meatsEaten) record = meatsEaten;
            FileWriter fw = new FileWriter(fpath, false);
            String str = "" + record;
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metr4 = getFontMetrics(g.getFont());
        g.drawString("Highest Score: " + record, (SCREEN_WIDTH - metr4.stringWidth("Highest Score: " + record)) / 2, (SCREEN_HEIGHT / 2) + 100);

    }

//    public void checkSpeed(){
//        if(meatsEaten == 3) DELAY = DELAY - 10;
//        if(meatsEaten == 6) DELAY = DELAY - 10;
//        if(meatsEaten == 9) DELAY = DELAY - 20;
//        if(meatsEaten == 12) DELAY = DELAY - 20;
//        if(meatsEaten == 15) DELAY = DELAY - 20;
//        if(meatsEaten == 18) DELAY = DELAY - 20;
//        if(meatsEaten == 21) DELAY = DELAY - 20;
//        if(meatsEaten == 24) DELAY = DELAY - 20;
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        if (running) {
            move();
            checkMeat();
            checkCollishion();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
            }
        }
    }
}
