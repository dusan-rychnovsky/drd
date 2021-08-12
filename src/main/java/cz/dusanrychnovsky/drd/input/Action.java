package cz.dusanrychnovsky.drd.input;

import static cz.dusanrychnovsky.drd.input.Action.Mode.*;

public class Action {

  public enum Mode { Normal, InitialPressOnly };

  private final String name;
  public Mode mode;
  private boolean pressed;
  private boolean waitingForRelease;

  public Action(String name, Mode mode) {
    this.name = name;
    this.mode = mode;
  }

  public Action(String name) {
    this(name, Mode.Normal);
  }

  public String getName() {
    return name;
  }

  public synchronized void press() {
    if (!waitingForRelease) {
      pressed = true;
      if (mode == InitialPressOnly) {
        waitingForRelease = true;
      }
    }
  }

  public synchronized void release() {
    pressed = waitingForRelease = false;
  }

  public synchronized boolean isPressed() {
    var result = pressed;
    if (mode == InitialPressOnly) {
      pressed = false;
    }
    return result;
  }
}
