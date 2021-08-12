package cz.dusanrychnovsky.drd.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Window {

  private GraphicsDevice device;

  public Window() {
    var environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    device = environment.getDefaultScreenDevice();
  }

  public void setFullScreen(Iterable<DisplayMode> displayModes) {

    var frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    frame.setIgnoreRepaint(true);
    frame.setResizable(false);

    device.setFullScreenWindow(frame);
    if (device.isDisplayChangeSupported()) {
      if (!setDisplayMode(displayModes)) {
        throw new RuntimeException("No supported display mode available.");
      }
    }
    else {
      throw new RuntimeException("Display change not supported.");
    }

    frame.createBufferStrategy(2);

    device.getFullScreenWindow().setFocusTraversalKeysEnabled(false);
  }

  private boolean setDisplayMode(Iterable<DisplayMode> displayModes) {
    var supportedModes = device.getDisplayModes();
    for (var mode : displayModes) {
      for (var supportedMode : supportedModes) {
        if (matches(mode, supportedMode)) {
          device.setDisplayMode(mode);
          return true;
        }
      }
    }
    return false;
  }

  private boolean matches(DisplayMode first, DisplayMode second) {
    if (first.getWidth() != second.getWidth() ||
        first.getHeight() != second.getHeight()) {
      return false;
    }
    if (first.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
        second.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
        first.getBitDepth() != second.getBitDepth()) {
      return false;
    }
    if (first.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
        second.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
        first.getRefreshRate() != second.getRefreshRate()) {
      return false;
    }
    return true;
  }

  public void restoreScreen() {
    var window = device.getFullScreenWindow();
    if (window != null) {
      window.dispose();
    }
    device.setFullScreenWindow(null);
  }

  public Graphics2D getGraphics() {
    return (Graphics2D) device.getFullScreenWindow().getBufferStrategy().getDrawGraphics();
  }

  public void update() {
    var strategy = device.getFullScreenWindow().getBufferStrategy();
    if (!strategy.contentsLost()) {
      strategy.show();
    }
    Toolkit.getDefaultToolkit().sync();
  }

  public void addKeyListener(KeyListener listener) {
    device.getFullScreenWindow().addKeyListener(listener);
  }

  public int getWidth() {
    return device.getFullScreenWindow().getWidth();
  }

  public int getHeight() {
    return device.getFullScreenWindow().getHeight();
  }
}
