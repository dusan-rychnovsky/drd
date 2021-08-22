package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.*;
import cz.dusanrychnovsky.drd.graphics.Window;
import cz.dusanrychnovsky.drd.input.*;
import cz.dusanrychnovsky.drd.input.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import static cz.dusanrychnovsky.drd.input.Action.Mode.InitialPressOnly;

public class AppTest {

  private static final List<DisplayMode> DISPLAY_MODES = Arrays.asList(
    new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
  );

  private final int CONTROLS_WIDTH = 396;

  private final Action exit = new Action("exit", InitialPressOnly);
  private final Action pause = new Action("pause", InitialPressOnly);
  private final Action jump = new Action("jump", InitialPressOnly);

  private Window window;
  private Input input;
  private Loop loop;

  private Playground playground;

  private boolean paused;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(DISPLAY_MODES);

    input = new Input(window);
    input.mapToKey(exit, KeyEvent.VK_ESCAPE);
    input.mapToKey(pause, KeyEvent.VK_P);
    input.mapToKey(jump, KeyEvent.VK_SPACE);

    var player = loadPlayer();
    var bullet = loadBullet();
    var enemy = loadEnemy();
    playground = new Playground(input, player, bullet, enemy)
      .setPosition(
        (window.getWidth() - Playground.WIDTH - 2 * Playground.BORDER_WIDTH - CONTROLS_WIDTH) / 2,
        (window.getHeight() - Playground.HEIGHT - 2 * Playground.BORDER_WIDTH) / 2
      )
      .init();

    loop = new Loop(this::update, this::draw);
    loop.run();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Bullet loadBullet() {
    return new Bullet(
      Animation.builder()
        .addFrame(loadImage("bullet.png"), 1)
        .build()
    );
  }

  private Player loadPlayer() {
    return new Player(
      Animation.builder()
        .addFrame(loadImage("player.png"), 1)
        .build(),
      0.f,
      0.f);
  }

  private Enemy loadEnemy() {
    return new Enemy(
      Animation.builder()
        .addFrame(loadImage("enemy.png"), 1)
        .build()
    );
  }

  private void update(long elapsedTime) {
    checkSystemInput();
    if (!paused) {
      playground.update(elapsedTime);
    }
  }

  private void checkSystemInput() {
    if (exit.isPressed()) {
      loop.stop();
    }
    if (pause.isPressed()) {
      setPaused(!paused);
    }
  }

  private void setPaused(boolean paused) {
    if (paused != this.paused) {
      this.paused = paused;
      input.resetActions();
    }
  }

  private synchronized void draw() {
    var g = window.getGraphics();

    g.setColor(Color.black);
    g.fillRect(0, 0, window.getWidth(), window.getHeight());

    playground.draw(g);

    if (paused) {
      g.setColor(Color.WHITE);
      g.drawString("PAUSED", 20, 42);
    }

    g.dispose();
    window.update();
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }
}
