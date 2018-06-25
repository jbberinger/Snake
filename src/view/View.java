package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main view as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public final class View {

    private final GamePanel gamePanel;
    private final GameHeaderPanel gameHeaderPanel;
    private final GameOverPanel gameOverPanel;
    private final NewGamePanel newGamePanel;
    private final DifficultyPanel difficultyPanel;
    private final ControlPanel controlPanel;
    private final ViewListener viewListener = new ViewListener();
    private JFrame frame;
    private JPanel content;
    private final int scale;
    private final List<Image> icons = new ArrayList<>();

    public View(int width, int height, int scale, Deque<Point> snakeBody, Point apple) {
        gamePanel = new GamePanel(width, height, scale, snakeBody, apple);
        newGamePanel = new NewGamePanel(width, height, scale);
        gameOverPanel = new GameOverPanel(width, height, scale);
        difficultyPanel = new DifficultyPanel(width, height, scale);
        gameHeaderPanel = new GameHeaderPanel(width, height, scale);
        controlPanel = new ControlPanel(width, height, scale);
        this.scale = scale;
        initIcons();
        initGridView();
    }

    /**
     * Initializes GUI.
     */
    private void initGridView() {
        frame = new JFrame("Snake");
        frame.addKeyListener(viewListener);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        frame.add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        newGame();
        frame.setIconImages(icons);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateView(Deque<Point> snakeBody, Point apple, String difficulty, int highScore, int applesEaten, boolean soundOn) {
        gamePanel.setSnakeBody(snakeBody, apple);
        updateGameHeaderPanel(difficulty, highScore, applesEaten, soundOn);
        gamePanel.repaint();
    }
    
    public void updateGameHeaderPanel(String difficulty, int highScore, int applesEaten, boolean soundOn) {
        gameHeaderPanel.update(difficulty, highScore, applesEaten, soundOn);
        gameHeaderPanel.repaint();
    }
    
    public void updateGameOverPanel(String difficulty, int applesEaten, int highScore) {
        gameOverPanel.update(difficulty, applesEaten, highScore);
    }

    public void gameOver() {
        System.out.println("GAME OVER");
        viewListener.setGameOver();
        content.removeAll();
        content.add(gameOverPanel);
        content.validate();
        content.repaint();
    }

    public void newGame() {
        System.out.println("NEW GAME");
        viewListener.setNewGame();
        content.removeAll();
        content.add(newGamePanel);
        content.validate();
        content.repaint();
    }
    
    public void showControls() {
        System.out.println("NEW GAME");
        viewListener.setShowControls();
        content.removeAll();
        content.add(controlPanel);
        content.validate();
        content.repaint();
    }

    public void chooseDifficulty() {
        System.out.println("CHOOSE DIFFICULTY");
        viewListener.setChoosingDifficulty();
        content.removeAll();
        content.add(difficultyPanel);
        content.validate();
        content.repaint();
    }

    public void continueGame() {
        System.out.println("CONTINUE GAME");
        viewListener.setGameOver();
        viewListener.setNewGame();
        viewListener.setChoosingDifficulty();
        viewListener.setPlaying();
        content.removeAll();
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        content.validate();
        content.repaint();
    }

    private void initIcons() {
        try {
            URL url = this.getClass().getClassLoader().getResource("icon/Icon_128.png");
            icons.add((new ImageIcon(url)).getImage());
        } catch (Exception e) {
        }
    }

}
