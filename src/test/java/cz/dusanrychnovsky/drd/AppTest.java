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

  private final Action exit = new Action("exit", InitialPressOnly);
  private final Action pause = new Action("pause", InitialPressOnly);
  private final Action jump = new Action("jump", InitialPressOnly);

  private Window window;
  private Input input;
  private Loop loop;

  private Playground playground;
  private Dashboard dashboard;

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
        (window.getWidth() - Playground.WIDTH - 2 * Playground.BORDER_WIDTH - Dashboard.WIDTH) / 2,
        (window.getHeight() - Playground.HEIGHT - 2 * Playground.BORDER_WIDTH) / 2
      )
      .init();

    dashboard = new Dashboard()
      .setPosition(
        playground.getPosX() + Playground.WIDTH + 2 * Playground.BORDER_WIDTH,
        playground.getPosY()
      );

    loop = new Loop(this::update, this::draw);
    loop.run();

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Bullet loadBullet() {
    return new Bullet(
      Animation.basic(loadImage("bullet.png"))
    );
  }

  private Player loadPlayer() {
    return new Player(
      Animation.basic(loadImage("player.png")),
      0.f,
      0.f);
  }

  private Enemy loadEnemy() {
    return new Enemy(
      Animation.basic(loadImage("enemy.png"))
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
    dashboard.draw(g);

    if (paused) {
      g.setColor(Color.GREEN);
      g.setFont(new Font("Serif", Font.BOLD, 64));
      drawCenteredString(g, "... GAME PAUSED ...", new Rectangle(0, 0, window.getWidth(), window.getHeight()));
    }

    g.dispose();
    window.update();
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }

  private void drawCenteredString(Graphics g, String text, Rectangle rect) {
    FontMetrics metrics = g.getFontMetrics(g.getFont());
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g.drawString(text, x, y);
  }
}
