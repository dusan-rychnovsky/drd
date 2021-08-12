package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;

public class AppTest {

  private static final List<DisplayMode> DISPLAY_MODES = Arrays.asList(
    new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
  );

  private static final int FONT_SIZE = 12;
  private List<String> messages = new ArrayList<>();

  private Window window;
  private Loop loop;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(DISPLAY_MODES);

    loop = new Loop(this::update, this::draw);

    window.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
        var code = e.getKeyCode();
        add("Stisknuto: " + KeyEvent.getKeyText(code));
        e.consume();

        if (code == KeyEvent.VK_ESCAPE) {
          loop.stop();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        var code = e.getKeyCode();
        add("Uvolneno: " + KeyEvent.getKeyText(code));
        e.consume();
      }

      @Override
      public void keyTyped(KeyEvent e) {
        e.consume();
      }
    });

    loop.run();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private void update(long elapsedTime) {
  }

  private synchronized void add(String message) {
    messages.add(message);
    if (messages.size() >= window.getHeight() / FONT_SIZE) {
      messages.remove(0);
    }
  }

  private synchronized void draw() {
    var g = window.getGraphics();

    g.setColor(Color.BLUE);
    g.fillRect(0, 0, window.getWidth(), window.getHeight());

    g.setColor(Color.WHITE);
    var y = FONT_SIZE;
    for (var message : messages) {
      g.drawString(message, 5, y);
      y += FONT_SIZE;
    }

    g.dispose();
    window.update();
  }
}
