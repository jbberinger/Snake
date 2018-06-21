package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Drawn to signify a Game Over due to collision.
 *
 * @author Justin Beringer
 */
public class GameOverPanel extends GraphicsPanel {

    int applesEaten, highScore;
    private String difficulty;

    GameOverPanel(int width, int height, int scale) {
        super(width, height, scale);
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setGraphics2D(g);
        paintGameOver();
        paintStats();
        paintPlayAgain();
    }

    public void paintGameOver() {
        drawGlyphVector(Color.red, 3.5, getFont(10), "Game Over");
    }

    public void paintStats() {
        drawGlyphVector(Color.green, 4.5, getFont(25), "Difficulty: " + difficulty);
        drawGlyphVector(Color.green, 5.5, getFont(25), "Score: " + applesEaten);
        drawGlyphVector(Color.green, 6.5, getFont(25), "High Score: " + highScore);
    }

    public void paintPlayAgain() {
        drawGlyphVector(Color.red, 7.75, getFont(25), "Play Again? Y/N...");
    }

    public void update(String difficulty, int applesEaten, int highScore) {
        this.difficulty = difficulty;
        this.applesEaten = applesEaten;
        this.highScore = highScore;
    }

}
