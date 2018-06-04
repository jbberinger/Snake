/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import javax.swing.JPanel;

/**
 * Drawn to signify a Game Over due to collision.
 *
 * @author Justin Beringer
 */
public class GameOverFrame extends JPanel {

    private final int width, height;
    Graphics2D g2d;

    int applesEaten, highScore;

    GameOverFrame(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Color getBackground() {
        return Color.black;
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.green);
        Font font = new Font("Monospaced", Font.PLAIN, width / 8);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, "GAME OVER");
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2),
                height * 8 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));

        font = new Font("Monospaced", Font.PLAIN, width / 15);
        frc = g2d.getFontRenderContext();
        gv = font.createGlyphVector(frc, "Score: " + applesEaten);
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2),
                height * 10 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));

        font = new Font("Monospaced", Font.PLAIN, width / 15);
        frc = g2d.getFontRenderContext();
        gv = font.createGlyphVector(frc, "High Score: " + highScore);
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2),
                height * 12 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));

        g2d.setColor(Color.red);
        font = new Font("Monospaced", Font.PLAIN, width / 20);
        frc = g2d.getFontRenderContext();
        gv = font.createGlyphVector(frc, "Play Again? Y / N");
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2),
                height * 14 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));

    }

    public void updateScores(int applesEaten, int highScore) {
        this.applesEaten = applesEaten;
        this.highScore = highScore;
    }

}