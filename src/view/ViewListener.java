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

    public void setGameOver(boolean isGameOver) {
        controller.setGameOver(isGameOver);
    }

    public void setNewGame(boolean isNewGame) {
        controller.setNewGame(isNewGame);
    }
    
    public void setChoosingDifficulty(boolean isChoosingDifficulty) {
        controller.setChoosingDifficulty(isChoosingDifficulty);
    }
    
    public void setShowControls(boolean isShowingControls) {
        controller.setShowingControls(isShowingControls);
    }

}
