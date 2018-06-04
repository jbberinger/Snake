package model;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import util.Direction;
import view.View;

/**
 * Main model class as part of MVC design pattern.
 *
 * @author Justin Beringer
 */
public class Model {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int SCALE = 15;
    private final int GROWTH_SPURT = 8;

    private final int MAX_INDEX_X = WIDTH / SCALE;
    private final int MAX_INDEX_Y = HEIGHT / SCALE;

    private int squaresToGrow;
    private int applesEaten = 0;
    private int highScore = 0;
    private Direction direction = Direction.UP;

    private final View view;
    private final Point apple = new Point();
    private final Random random = new Random();
    private final Deque<Point> snakeBody = new ArrayDeque<>();
    private final Set<Point> occupiedPositions = new LinkedHashSet();
    private Clip gameOverSound, eatAppleSound, gameMusicSound;

    public Model() {
        generateSnakeAtCenter();
        generateApple();
        occupiedPositions.add(apple);
        view = new View(WIDTH, HEIGHT, SCALE, snakeBody, apple);
        initSounds();
    }

    /**
     * Generates snake at center of grid moving in upward direction. Consider
     * initializing the snake position and direction randomly.
     */
    private void generateSnakeAtCenter() {
        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        snakeBody.add(new Point(x, y));
        occupiedPositions.add(snakeBody.getFirst());
        squaresToGrow += GROWTH_SPURT;
    }

    /**
     * Generates an apple position randomly and avoids collisions.
     *
     * Originally tried to use HashSet.contains() to check for collisions, but
     * it wasn't catching collisions.
     */
    private void generateApple() {

        int x = 0;
        int y = 0;
        boolean spaceIsOccupied;
        do {
            spaceIsOccupied = false;
            x = random.nextInt((int) MAX_INDEX_X) * SCALE;
            y = random.nextInt((int) MAX_INDEX_Y) * SCALE;
            for (Point point : occupiedPositions) {
                if (point.getX() == x && point.getY() == y) {
                    System.out.println("wtf");
                    spaceIsOccupied = true;
                }
            }
        } while (spaceIsOccupied);
        apple.setLocation(x, y);
    }

    /**
     * Moves snake by moving tail position one grid square in front of the head
     * in the current direction. The translated tail is then dequeued before
     * being queued as the new head. If an apple is eaten, the snake is extended
     * by not dequeuing the tail.
     */
    public void moveSnake() {

        int nextHeadX = (int) snakeBody.getFirst().getX();
        int nextHeadY = (int) snakeBody.getFirst().getY();

        switch (direction) {
            case UP:
                nextHeadY -= SCALE;
                break;
            case DOWN:
                nextHeadY += SCALE;
                break;
            case LEFT:
                nextHeadX -= SCALE;
                break;
            case RIGHT:
                nextHeadX += SCALE;
                break;
            default:
                break;
        }

        if (collided(nextHeadX, nextHeadY)) {
            stopMusic();
            playGameOverSound();
            highScore = Math.max(applesEaten, highScore);
            view.updateScores(applesEaten, highScore);
            view.gameOver();
        }

        snakeBody.getLast().setLocation(nextHeadX, nextHeadY);
        if (ateApple()) {
            playEatAppleSound();
            snakeBody.addFirst(new Point(nextHeadX, nextHeadY));
            occupiedPositions.add(snakeBody.getFirst());
            generateApple();
            applesEaten++;
            squaresToGrow += GROWTH_SPURT;
        } else if (squaresToGrow > 0) {
            snakeBody.addFirst(new Point(nextHeadX, nextHeadY));
            occupiedPositions.add(snakeBody.getFirst());
            squaresToGrow--;
        } else {
            snakeBody.addFirst(snakeBody.removeLast());
        }

        view.updateGridView(snakeBody, apple);

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private boolean ateApple() {
        return snakeBody.getFirst().equals(apple);
    }

    /**
     * Checks for snake collision with self and edges
     */
    private boolean collided(int nextHeadX, int nextHeadY) {
        boolean snakeCollision = snakeBody.contains(new Point(nextHeadX, nextHeadY));
        boolean leftEdgeCollision = nextHeadX < 0;
        boolean rightEdgeCollision = nextHeadX >= WIDTH;
        boolean topEdgeCollision = nextHeadY < 0;
        boolean bottomEdgeCollision = nextHeadY >= HEIGHT;
        return snakeCollision
                || leftEdgeCollision
                || rightEdgeCollision
                || topEdgeCollision
                || bottomEdgeCollision;
    }

    public void newGame() {
        direction = Direction.UP;
        view.newGame();
    }

    public void continueGame() {
        clearModel();
        generateSnakeAtCenter();
        generateApple();
        playMusic();
        view.continueGame();
    }

    public void clearModel() {
        occupiedPositions.clear();
        snakeBody.clear();
        apple.setLocation(0, 0);
        applesEaten = 0;
    }

    private void initSounds() {
        try {

            URL url = this.getClass().getClassLoader().getResource("sound/gameOver.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            gameOverSound = AudioSystem.getClip();
            gameOverSound.open(audioIn);

            url = this.getClass().getClassLoader().getResource("sound/eatApple.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            eatAppleSound = AudioSystem.getClip();
            eatAppleSound.open(audioIn);

            url = this.getClass().getClassLoader().getResource("sound/gameMusic.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            gameMusicSound = AudioSystem.getClip();
            gameMusicSound.open(audioIn);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        }
    }

    public void pauseMusic() {
        if (gameMusicSound.isRunning()) {
            gameMusicSound.stop();
        } else {
            gameMusicSound.loop(100);
            gameMusicSound.start();
        }
    }

    public void playMusic() {
        gameMusicSound.setMicrosecondPosition(0);
        gameMusicSound.loop(100);
        gameMusicSound.start();
    }

    public void stopMusic() {
        gameMusicSound.stop();
    }

    public void playGameOverSound() {
        gameOverSound.setMicrosecondPosition(0);
        gameOverSound.start();
    }

    public void playEatAppleSound() {
        eatAppleSound.setMicrosecondPosition(0);
        eatAppleSound.start();
    }

}
