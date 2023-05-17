/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pingponggame;

import java.awt.*;

/**
 *
 * @author FR
 */
public class Score {

    static int width;
    static int height;
    int player1 = 0;
    int player2 = 0;

    public Score(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        
        g.drawString("" + player1, width/4 - 30, 100);
        g.drawString("" + player2,(int)(width * (0.75))-15, 100);
    }
}
