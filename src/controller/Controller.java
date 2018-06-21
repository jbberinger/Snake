package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import model.Model;
import util.Direction;

/**
 * Main controller as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public class Controller {

    private static int TICKS_PER_SECOND;
    private static ActionListener taskPerformer;
    private static Timer timer;
    private static boolean buttonPressed = false;

    private boolean isGameOver = false;
    private boolean isNewGame = true;
    private boolean isPaused = false;
    private boolean isChoosingDifficulty = false;
    private boolean isShowingControls = false;
    private static final Model model = new Model();
    private Direction direction;

    public static void main(String[] args) {
        taskPerformer = (ActionEvent e) -> {
            model.moveSnake();
            buttonPressed = false;
        };
        timer = new Timer(TICKS_PER_SECOND, taskPerformer);
        timer.setInitialDelay(0);
    }

    /**
     * Maps key presses.
     */
    public void respondToInput(KeyEvent key) {
        int keyCode = key.getKeyCode();
        
        if (keyCode == KeyEvent.VK_M){
            model.toggleMute();
        }

        if (isNewGame) {
            isNewGame = false;
            model.showControls();
            return;
        }

        if (isShowingControls) {
            isShowingControls = false;
            model.chooseDifficulty();
            return;
        }

        if (isChoosingDifficulty) {
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
            isChoosingDifficulty = false;
            model.continueGame();
            timer.start();
            return;
        }

        if (isGameOver) {
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
            isGameOver = false;
            return;

        }

        switch (key.getKeyCode()) {

            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN && !buttonPressed) {
                    direction = Direction.UP;
                    buttonPressed = true;
                }
                break;
            case KeyEvent.VK_W:
                if (direction != Direction.DOWN && !buttonPressed) {
                    direction = Direction.UP;
                    buttonPressed = true;
                }
                break;

            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP && !buttonPressed) {
                    direction = Direction.DOWN;
                    buttonPressed = true;
                }
                break;
            case KeyEvent.VK_S:
                if (direction != Direction.UP && !buttonPressed) {
                    direction = Direction.DOWN;
                    buttonPressed = true;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT && !buttonPressed) {
                    direction = Direction.LEFT;
                    buttonPressed = true;
                }
                break;
            case KeyEvent.VK_A:
                if (direction != Direction.RIGHT && !buttonPressed) {
                    direction = Direction.LEFT;
                    buttonPressed = true;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT && !buttonPressed) {
                    direction = Direction.RIGHT;
                    buttonPressed = true;
                }
                break;
            case KeyEvent.VK_D:
                if (direction != Direction.LEFT && !buttonPressed) {
                    direction = Direction.RIGHT;
                    buttonPressed = true;
                }
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
        this.isGameOver = isGameOver;
    }

    public void setNewGame(boolean isNewGame) {
        direction = Direction.UP;
        this.isNewGame = isNewGame;
    }

    public void setChoosingDifficulty(boolean isChoosingDifficulty) {
        timer.stop();
        this.isChoosingDifficulty = isChoosingDifficulty;
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

    public void setShowingControls(boolean isShowingControls) {
        this.isShowingControls = isShowingControls;
    }

}
