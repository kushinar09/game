/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingponggame;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author FR
 */
public class GameFrame extends JFrame {

    public GameFrame() {
        this.add(new GameFrame());
        this.setTitle("PingPong");
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
