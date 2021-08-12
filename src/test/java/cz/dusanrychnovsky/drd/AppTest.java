package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.Animation;
import cz.dusanrychnovsky.drd.graphics.Sprite;
import cz.dusanrychnovsky.drd.graphics.Window;
import cz.dusanrychnovsky.drd.input.Action;
import cz.dusanrychnovsky.drd.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import javax.swing.*;

import static cz.dusanrychnovsky.drd.input.Action.Mode.InitialPressOnly;

public class AppTest {

  private static final List<DisplayMode> DISPLAY_MODES = Arrays.asList(
    new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
  );

  private static final float PLAYER_SPEED = .3f;

  private final Action exit = new Action("exit", InitialPressOnly);
  private final Action moveLeft = new Action("moveLeft");
  private final Action moveRight = new Action("moveRight");

  private Window window;
  private Input input;
  private Loop loop;

  private Image bgImage;
  private Sprite player;

  @Test
  public void test() {

    window = new Window();
    window.setFullScreen(DISPLAY_MODES);

    input = new Input(window);
    input.mapToKey(exit, KeyEvent.VK_ESCAPE);
    input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    input.mapToKey(moveRight, KeyEvent.VK_RIGHT);

    bgImage = loadImage("background.jpg");
    player = loadPlayer();

    loop = new Loop(this::update, this::draw);
    loop.run();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Sprite loadPlayer() {
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

    return sprite;
  }

  private void update(long elapsedTime) {
    checkSystemInput();
    checkGameInput();
    player.update(elapsedTime);
  }

  private void checkSystemInput() {
    if (exit.isPressed()) {
      loop.stop();
    }
  }

  private void checkGameInput() {
    var dX = 0.f;
    if (moveLeft.isPressed()) {
      dX = -PLAYER_SPEED;
    }
    if (moveRight.isPressed()) {
      dX = PLAYER_SPEED;
    }
    player.setVelocity(dX, 0.f);
  }

  private synchronized void draw() {
    var g = window.getGraphics();

    g.drawImage(bgImage, 0, 0, null);
    g.drawImage(player.getImage(), Math.round(player.getPosX()), Math.round(player.getPosY()), null);

    g.dispose();
    window.update();
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }
}
