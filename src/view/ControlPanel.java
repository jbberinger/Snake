/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Displays game controls.
 *
 * @author Justin Beringer
 */
public class ControlPanel extends GraphicsPanel {

    ControlPanel(int width, int height, int scale) {
        super(width, height, scale);
        System.out.println("height: " + height);
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setGraphics2D(g);
        paintTitle();
        paintControls();
        paintSubtitle();
    }

    private void paintTitle() {
        drawGlyphVector(Color.green, 4, getFont(10), "Controls");
    }

    private void paintControls() {
        drawGlyphVector(Color.red, 4.75, getFont(25), "WASD or Arrow Keys - control snake");
        drawGlyphVector(Color.red, 5.5, getFont(25), "P - pause");
        drawGlyphVector(Color.red, 6.25, getFont(25), "M - mute");
    }

    public void paintSubtitle() {
        drawGlyphVector(Color.red, 7.25, getFont(25), "press any key to continue...");
    }

}
