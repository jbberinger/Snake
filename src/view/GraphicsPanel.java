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
 * Used as parent class for all graphics panels.
 *
 * @author Justin Beringer
 */
public class GraphicsPanel extends JPanel {

    private final int width, height, scale;
    private Graphics2D g2d;

    GraphicsPanel(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Color getBackground() {
        return Color.black;
    }
        
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setGraphics2D(g);
    }

    public void setGraphics2D(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public Font getFont(int fontScale) {
        return new Font("Monospaced", Font.PLAIN, width/fontScale);
    }

    public void drawGlyphVector(Color color, double yPosition, Font font, String text) {
        g2d.setColor(color);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, text);
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), 
                (float) (height * yPosition / 10 - ((int) gv.getVisualBounds().getHeight() / 2)));
    }


}
