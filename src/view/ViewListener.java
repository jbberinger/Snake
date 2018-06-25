package view;

import controller.Controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Passes keyPress and other actions from the view to the controller.
 * 
 * @author Justin Beringer
 */
public class ViewListener implements KeyListener {

    boolean isGameOver;
    Controller controller = new Controller();

    public ViewListener() {
        isGameOver = false;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        //System.out.println(key.toString());
        controller.respondToInput(key);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setGameOver() {
        controller.setGameOver();
    }

    public void setNewGame() {
        controller.setNewGame();
    }
    
    public void setChoosingDifficulty() {
        controller.setChoosingDifficulty();
    }
    
    public void setShowControls() {
        controller.setShowingControls();
    }
    
    public void setPlaying() {
        controller.setPlaying();
    }

}
