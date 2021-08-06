package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  private static final int DEMO_TIME = 10_000;

  private Window window;
  private Image bgImage;
  private Sprite sprite;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN));

    bgImage = loadImage("background.jpg");
    sprite = loadSprite();

    loop();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Sprite loadSprite() {

    var player1 = loadImage("player1.png");
    var player2 = loadImage("player2.png");
    var player3 = loadImage("player3.png");
    var animation = Animation.builder()
      .addFrame(player1, 250)
      .addFrame(player2, 150)
      .addFrame(player1, 150)
      .addFrame(player2, 150)
      .addFrame(player3, 200)
      .addFrame(player2, 150)
      .build();

    var sprite = new Sprite(animation, 0.f, 0.f);
    var posX = Math.random() * (window.getWidth() - sprite.getWidth());
    var posY = Math.random() * (window.getHeight() - sprite.getHeight());
    sprite.setPosition((float) posX, (float) posY);
    sprite.setVelocity((float) Math.random() - 0.5f, (float) Math.random() - 0.5f);

    return sprite;
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

    if (sprite.getPosX() <= 0 || sprite.getPosX() + sprite.getWidth() >= window.getWidth()) {
      sprite.setVelocity(-sprite.getDX(), sprite.getDY());
    }

    if (sprite.getPosY() <= 0 || sprite.getPosY() + sprite.getHeight() >= window.getHeight()) {
      sprite.setVelocity(sprite.getDX(), -sprite.getDY());
    }

    sprite.update(elapsedTime);
  }

  private void draw() {
    var g = window.getGraphics();
    g.drawImage(bgImage, 0, 0, null);
    g.drawImage(
      sprite.getImage(),
      Math.round(sprite.getPosX()),
      Math.round(sprite.getPosY()),
      null);
    g.dispose();
    window.update();
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }
}
