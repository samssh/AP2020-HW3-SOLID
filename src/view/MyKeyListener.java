package view;

import controller.Controller;
import controller.Request;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            Controller.getInstance().addRequest(Request.RIGHT);
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            Controller.getInstance().addRequest(Request.LEFT);
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            Controller.getInstance().addRequest(Request.UP);
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            Controller.getInstance().addRequest(Request.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}

