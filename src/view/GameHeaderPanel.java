package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Displays live game stats.
 *
 * @author Justin Beringer
 */
public class GameHeaderPanel extends GraphicsPanel {

    private String difficulty, applesEaten, highScore, sound;
    private final int scale;

    GameHeaderPanel(int width, int height, int scale) {
        super(width, scale * 2, scale);
        this.scale = scale;
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintHeader();
    }

    public void paintHeader() {
        drawGlyphVector(Color.white, 5, new Font("Monospaced", Font.PLAIN, scale), 
                "Difficulty: " + difficulty + "   High Score: " + highScore
                + "   Apples Eaten: " + applesEaten + "   sound: " + sound);
    }

    public void update(String difficulty, int highScore, int applesEaten, boolean soundOn) {
        this.difficulty = difficulty;
        this.highScore = String.valueOf(highScore);
        this.applesEaten = String.valueOf(applesEaten);
        if(soundOn){
        sound = "on";
        }else{
            sound = "off";
        }
    }

}
