package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

public class Window {

  private GraphicsDevice device;

  public Window() {
    var environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    device = environment.getDefaultScreenDevice();
  }

  public void setFullScreen(DisplayMode displayMode, JFrame frame) {
    frame.setUndecorated(true);
    frame.setResizable(false);
    device.setFullScreenWindow(frame);
    if (device.isDisplayChangeSupported()) {
      try {
        device.setDisplayMode(displayMode);
      }
      catch (IllegalArgumentException ex) {
        throw new RuntimeException("Invalid display mode: " + displayMode, ex);
      }
    }
    else {
      throw new RuntimeException("Invalid display mode: " + displayMode);
    }
  }

  public void restoreScreen() {
    var window = device.getFullScreenWindow();
    if (window != null) {
      window.dispose();
    }
    device.setFullScreenWindow(null);
  }
}
