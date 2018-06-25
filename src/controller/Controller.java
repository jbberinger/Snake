package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.Timer;
import model.Model;
import util.Direction;
import util.GameState;

/**
 * Main controller as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public class Controller {

    private static int TICKS_PER_SECOND;
    private static ActionListener taskPerformer;
    private static Timer timer;
    private static final Deque<Direction> directionQueue = new ArrayDeque<>();

    private GameState gameState;
    private static final Model model = new Model();

    public static void main(String[] args) {
        taskPerformer = (ActionEvent e) -> {
            model.setDirection(directionQueue.getLast());
            model.moveSnake();
        };
        timer = new Timer(TICKS_PER_SECOND, taskPerformer);
        timer.setInitialDelay(0);
    }

    /**
     * Maps key presses.
     */
    public void respondToInput(KeyEvent key) {
        System.out.println("keyPress");
        int keyCode = key.getKeyCode();

        if (keyCode == KeyEvent.VK_M) {
            model.toggleMute();
        }

        if (gameState == GameState.NEW_GAME) {
            model.showControls();
            return;
        }

        if (gameState == gameState.CONTROLS) {
            model.chooseDifficulty();
            return;
        }

        if (gameState == GameState.DIFFICULTY) {
            switch (keyCode) {
                case KeyEvent.VK_1:
                    setDifficulty(0);
                    break;
                case KeyEvent.VK_2:
                    setDifficulty(1);
                    break;
                case KeyEvent.VK_3:
                    setDifficulty(2);
                    break;
                default:
                    return;
            }
            model.continueGame();
            timer.start();
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            switch (keyCode) {
                case KeyEvent.VK_Y:
                    model.chooseDifficulty();
                    break;
                case KeyEvent.VK_N:
                    model.quit();
                    break;
                default:
                    return;
            }
            return;
        }

        if (gameState == GameState.PLAYING) {
            
            switch (key.getKeyCode()) {
                case KeyEvent.VK_UP:
                    directionQueue.add(Direction.UP);
                    break;
                case KeyEvent.VK_W:
                    directionQueue.add(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    directionQueue.add(Direction.DOWN);
                    break;
                case KeyEvent.VK_S:
                    directionQueue.add(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    directionQueue.add(Direction.LEFT);
                    break;
                case KeyEvent.VK_A:
                    directionQueue.add(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    directionQueue.add(Direction.RIGHT);
                    break;
                case KeyEvent.VK_D:
                    directionQueue.add(Direction.RIGHT);
                    break;

                case KeyEvent.VK_P:
                    if (gameState != GameState.PAUSED) {
                        gameState = GameState.PAUSED;
                        model.stopMusic();
                        timer.stop();
                    } else {
                        gameState = GameState.PLAYING;
                        model.playMusic();
                        timer.start();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public void setGameOver() {
        timer.stop();
        gameState = GameState.GAME_OVER;
    }

    public void setNewGame() {
        directionQueue.clear();
        directionQueue.add(Direction.UP);
        gameState = GameState.NEW_GAME;
    }

    public void setChoosingDifficulty() {
        timer.stop();
        gameState = GameState.DIFFICULTY;
    }

    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:
                TICKS_PER_SECOND = 1000 / 8;
                timer = new Timer(TICKS_PER_SECOND, taskPerformer);
                break;
            case 1:
                TICKS_PER_SECOND = 1000 / 15;
                timer = new Timer(TICKS_PER_SECOND, taskPerformer);
                break;
            case 2:
                TICKS_PER_SECOND = 1000 / 20;
                timer = new Timer(TICKS_PER_SECOND, taskPerformer);
                break;
            default:
                break;
        }
        model.setDifficulty(difficulty);
    }

    public void setShowingControls() {
        gameState = GameState.CONTROLS;
    }

    public void setPlaying() {
        gameState = GameState.PLAYING;
    }

}
