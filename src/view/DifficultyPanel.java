package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Displays available difficulties.
 *
 * @author Justin Beringer
 */
public class DifficultyPanel extends GraphicsPanel {

    DifficultyPanel(int width, int height, int scale) {
        super(width, height, scale);
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTitle();
        paintDifficulties();
        paintSubtitle();
    }

    public void paintTitle() {
        drawGlyphVector(Color.green, 4, getFont(10), "Difficulty");
    }

    public void paintDifficulties() {
        drawGlyphVector(Color.red, 4.75, getFont(25), "1 - N00b");
        drawGlyphVector(Color.red, 5.5, getFont(25), "2 - Quick");
        drawGlyphVector(Color.red, 6.25, getFont(25), "3 - Crazy");
    }

    public void paintSubtitle() {
        drawGlyphVector(Color.red, 7.25, getFont(25), "choose difficulty to continue...");
    }

}
