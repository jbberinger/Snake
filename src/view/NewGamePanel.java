package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Drawn when game is first opened.
 *
 * @author Justin Beringer
 */
public class NewGamePanel extends GraphicsPanel {

    NewGamePanel(int width, int height, int scale) {
        super(width, height, scale);
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setGraphics2D(g);
        paintTitle();
        paintSubtitle();
    }

    public void paintTitle() {
        drawGlyphVector(Color.green, 5, getFont(10), "Snake");
    }

    public void paintSubtitle() {
        drawGlyphVector(Color.red, 6, getFont(25), "press any key to continue...");
    }

}
