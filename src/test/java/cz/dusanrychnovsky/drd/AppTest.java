package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  private static final int DEMO_TIME = 5_000;

  private Window window;
  private Image bgImage;
  private Animation animation;

  @Test
  public void test() {

    bgImage = loadImage("background.jpg");
    animation = loadAnimation();

    window = new Window();
    window.setFullScreen(new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN));

    loop();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Animation loadAnimation() {
    var player1 = loadImage("player1.png");
    var player2 = loadImage("player2.png");
    var player3 = loadImage("player3.png");
    return Animation.builder()
      .addFrame(player1, 250)
      .addFrame(player2, 150)
      .addFrame(player1, 150)
      .addFrame(player2, 150)
      .addFrame(player3, 200)
      .addFrame(player2, 150)
      .build();
  }

  private void loop() {
    var startTime = System.currentTimeMillis();
    var currTime = startTime;

    while (currTime - startTime < DEMO_TIME) {
      var elapsedTime = System.currentTimeMillis() - currTime;
      currTime += elapsedTime;

      update(elapsedTime);
      draw();

      try {
        Thread.sleep(20);
      }
      catch (InterruptedException ex) {}
    }
  }

  private void update(long elapsedTime) {
    animation.update(elapsedTime);
  }

  private void draw() {
    var g = window.getGraphics();
    g.drawImage(bgImage, 0, 0, null);
    g.drawImage(animation.getImage(), 0, 0, null);
    g.dispose();
    window.update();
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }
}
