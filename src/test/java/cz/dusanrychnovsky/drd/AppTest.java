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

  private static final float PLAYER_SPEED = 1.f;

  private final Action exit = new Action("exit", InitialPressOnly);
  private final Action pause = new Action("pause", InitialPressOnly);
  private final Action moveLeft = new Action("moveLeft");
  private final Action moveRight = new Action("moveRight");
  private final Action jump = new Action("jump", InitialPressOnly);

  private Window window;
  private Input input;
  private Loop loop;

  private Sprite player;

  private boolean paused;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(DISPLAY_MODES);

    input = new Input(window);
    input.mapToKey(exit, KeyEvent.VK_ESCAPE);
    input.mapToKey(pause, KeyEvent.VK_P);
    input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    input.mapToKey(moveRight, KeyEvent.VK_RIGHT);
    input.mapToKey(jump, KeyEvent.VK_SPACE);

    player = loadPlayer();

    loop = new Loop(this::update, this::draw);
    loop.run();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Sprite loadPlayer() {
    var animation = Animation.builder()
      .addFrame(loadImage("player.png"), 1)
      .build();
    var player = new Player(animation, 0.f, 0.f, 0.f, window.getWidth());
    player.setPosition(
      (window.getWidth() - player.getWidth()) / 2.f,
      window.getHeight() - player.getHeight());
    return player;
  }

  private void update(long elapsedTime) {
    checkSystemInput();
    if (!paused) {
      checkGameInput();
      player.update(elapsedTime);
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

  private void checkGameInput() {
    player.setVelocity(getPlayerDX(), player.getDY());
  }

  private float getPlayerDX() {
    if (moveLeft.isPressed()) {
      if (player.getPosX() > 0) {
        return -PLAYER_SPEED;
      }
    }
    if (moveRight.isPressed()) {
      if (player.getPosX() + player.getWidth() < window.getWidth()) {
        return PLAYER_SPEED;
      }
    }
    return 0.f;
  }

  private synchronized void draw() {
    var g = window.getGraphics();

    g.setColor(Color.black);
    g.fillRect(0, 0, window.getWidth(), window.getHeight());

    g.drawImage(player.getImage(), Math.round(player.getPosX()), Math.round(player.getPosY()), null);

    g.setColor(Color.red);
    g.drawRect(0, 0, 1918, 1198);

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
