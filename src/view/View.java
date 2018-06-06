package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.ViewListener;

/**
 * Main view as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public final class View {

    private final SnakeAndApple snakeAndApple;
    private final ViewListener viewListener = new ViewListener();
    private final GameOverFrame gameOverFrame;
    private final NewGameFrame newGameFrame;
    private JFrame frame;
    private JPanel content;
    private final int scale;
    private List<Image> icons = new ArrayList<>();

    public View(int width, int height, int scale, Deque<Point> snakeBody, Point apple) {
        snakeAndApple = new SnakeAndApple(width, height, scale, snakeBody, apple);
        newGameFrame = new NewGameFrame(width, height, scale);
        gameOverFrame = new GameOverFrame(width, height, scale);
        this.scale = scale;
        initIcons();
        initGridView();
    }

    /**
     * Initializes GUI.
     */
    private void initGridView() {
        frame = new JFrame("Speed Snake");
        frame.addKeyListener(viewListener);
        
        content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);
        
        content.add(newGameFrame);
        frame.add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setIconImages(icons);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateView(Deque<Point> snakeBody, Point apple) {
        snakeAndApple.setSnakeBody(snakeBody, apple);
        snakeAndApple.repaint();
    }

    public void gameOver() {
        System.out.println("GAME OVER");
        viewListener.setGameOver(true);
        content.removeAll();
        content.add(gameOverFrame);
        content.validate();
        content.repaint();
    }

    public void newGame() {
        System.out.println("NEW GAME");
        viewListener.setNewGame(true);
        frame.remove(snakeAndApple);
        frame.add(newGameFrame);
        frame.validate();
        frame.repaint();
    }

    public void continueGame() {
        System.out.println("CONTINUE GAME");
        viewListener.setGameOver(false);
        viewListener.setNewGame(false);
        content.removeAll();
        content.add(snakeAndApple);
        content.validate();
        content.repaint();
    }

    public void updateScores(int applesEaten, int highScore) {
        gameOverFrame.updateScores(applesEaten, highScore);
    }

    private void initIcons() {
        try {
            URL url = this.getClass().getClassLoader().getResource("icon/Icon_128.png");
            icons.add((new ImageIcon(url)).getImage());
        } catch (Exception e) {
        }
    }

}
