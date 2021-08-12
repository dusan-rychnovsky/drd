package cz.dusanrychnovsky.drd.input;

import cz.dusanrychnovsky.drd.graphics.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

  // absolute majority of key codes (as defined in java.awt.KeyEvent) are lower than 600
  public static final int NUM_KEY_CODES = 600;

  public Action[] keyActions = new Action[NUM_KEY_CODES];

  public Input(Window window) {
    window.addKeyListener(this);
  }

  public void mapToKey(Action action, int code) {
    keyActions[code] = action;
  }

  public void resetActions() {
    for (var action : keyActions) {
      if (action != null) {
        action.release();
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    var action = getKeyAction(e);
    if (action != null) {
      action.press();
    }
    e.consume();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    var action = getKeyAction(e);
    if (action != null) {
      action.release();
    }
    e.consume();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    e.consume();
  }

  private Action getKeyAction(KeyEvent e) {
    var code = e.getKeyCode();
    if (code < keyActions.length) {
      return keyActions[code];
    }
    else {
      return null;
    }
  }

}
