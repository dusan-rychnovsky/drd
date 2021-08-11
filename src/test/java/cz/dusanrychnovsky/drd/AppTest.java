package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;

public class AppTest {

  private static final int DEMO_TIME = 10_000;
  private static final List<DisplayMode> DISPLAY_MODES = Arrays.asList(
    new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
  );

  private long totalTime = 0;

  private Window window;
  private Loop loop;
  private Image bgImage;
  private Sprite sprite;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(DISPLAY_MODES);

    bgImage = loadImage("background.jpg");
    sprite = loadSprite();

    loop = new Loop(this::update, this::draw);
    loop.run();

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

  private void update(long elapsedTime) {

    totalTime += elapsedTime;
    if (totalTime >= DEMO_TIME) {
      loop.stop();
    }

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
    drawSprite(g, sprite);
    g.dispose();
    window.update();
  }

  private void drawSprite(Graphics2D g, Sprite sprite) {
    var transform = new AffineTransform();
    transform.setToTranslation(sprite.getPosX(), sprite.getPosY());
    if (sprite.getDX() < 0) {
      transform.scale(-1, 1);
      transform.translate(-sprite.getWidth(), 0);
    }
    g.drawImage(sprite.getImage(), transform, null);
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }
}
