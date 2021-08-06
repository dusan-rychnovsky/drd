package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

public class Window {

  private GraphicsDevice device;

  public Window() {
    var environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    device = environment.getDefaultScreenDevice();
  }

  public void setFullScreen(DisplayMode displayMode) {
    var frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    frame.setIgnoreRepaint(true);
    frame.setResizable(false);
    device.setFullScreenWindow(frame);
    if (device.isDisplayChangeSupported()) {
      device.setDisplayMode(displayMode);
    }
    else {
      throw new RuntimeException("Display change not supported.");
    }
    frame.createBufferStrategy(2);
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

  public int getWidth() {
    return device.getFullScreenWindow().getWidth();
  }

  public int getHeight() {
    return device.getFullScreenWindow().getHeight();
  }
}
