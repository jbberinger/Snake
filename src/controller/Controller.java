package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import model.Model;
import util.Direction;
import util.ViewListener;

/**
 * Main controller as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public class Controller {

    private static final int TICKS_PER_SECOND = 1000 / 15;
    private static boolean buttonPressed = false;
    private static boolean isGameOver = false;
    private static boolean isNewGame = true;
    private static boolean isPaused = false;

    private static final Model model = new Model();
    private static final ViewListener viewListener = new ViewListener();
    private static Direction direction;
    private static Timer timer;

    public static void main(String[] args) {
        ActionListener taskPerformer = (ActionEvent e) -> {
            model.moveSnake();
            buttonPressed = false;
        };
        timer = new Timer(TICKS_PER_SECOND, taskPerformer);
        timer.setInitialDelay(0);
    }

    /**
     * Maps key presses.
     */
    public void directionInput(KeyEvent key) {
        int keyCode = key.getKeyCode();

        if (isNewGame) {
            isNewGame = false;
            model.continueGame();
            timer.start();
            return;
        }

        if (isGameOver) {
            if (keyCode == KeyEvent.VK_Y) {
                isGameOver = false;
                model.continueGame();
                timer.start();
            } else if (keyCode == KeyEvent.VK_N) {
                System.exit(0);
            }
            return;
        }

        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN && !buttonPressed) {
                    direction = Direction.UP;
                }
                buttonPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP && !buttonPressed) {
                    direction = Direction.DOWN;
                }
                buttonPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT && !buttonPressed) {
                    direction = Direction.LEFT;
                }
                buttonPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT && !buttonPressed) {
                    direction = Direction.RIGHT;
                }
                buttonPressed = true;
                break;
            case KeyEvent.VK_P:
                if (!isPaused) {
                    isPaused = true;
                    model.stopMusic();
                    timer.stop();
                } else {
                    isPaused = false;
                    model.playMusic();
                    timer.start();
                }
                break;
            default:
                break;
        }

        if (direction != null) {
            model.setDirection(direction);
        }
    }

    public void setGameOver(boolean isGameOver) {
        timer.stop();
        Controller.isGameOver = isGameOver;
    }

    public void setNewGame(boolean isNewGame) {
        direction = Direction.UP;
        Controller.isNewGame = isNewGame;
    }

}
