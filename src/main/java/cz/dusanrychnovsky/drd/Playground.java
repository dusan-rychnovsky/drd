package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.Sprite;
import cz.dusanrychnovsky.drd.input.Action;
import cz.dusanrychnovsky.drd.input.Input;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyEvent;

import static cz.dusanrychnovsky.drd.input.Action.Mode.InitialPressOnly;

public class Playground {

  public static final int WIDTH = 800;
  public static final int HEIGHT = 800;
  public static final int BORDER_WIDTH = 2;

  private int posX = 0;
  private int posY = 0;

  private final Action shoot = new Action("shoot", InitialPressOnly);
  private final Action moveLeft = new Action("moveLeft");
  private final Action moveRight = new Action("moveRight");

  private final Bullet bullet;
  private final List<Bullet> bullets = new ArrayList<>();

  private final Player player;

  private final Enemy enemy;
  private final List<Enemy> enemies = new ArrayList<>();

  public Playground(Input input, Player player, Bullet bullet, Enemy enemy) {
    this.enemy = enemy;
    this.bullet = bullet;
    this.player = player;

    input.mapToKey(shoot, KeyEvent.VK_SPACE);
    input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    input.mapToKey(moveRight, KeyEvent.VK_RIGHT);
  }

  public Playground init() {
    player.setPosition(
      posX + (WIDTH - player.getWidth()) / 2.f,
      posY + HEIGHT - player.getHeight());
    for (var i = 0; i < 10; i++) {
      enemies.add(
        (Enemy) enemy.clone()
          .setPosition(
            posX + 25 + i * (enemy.getWidth() + 30),
            posY + 10)
          .setVelocity(0.f, .2f)
      );
    }
    return this;
  }

  public Playground setPosition(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
    return this;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }

  public void update(long elapsedTime) {
    checkGameInput();
    updatePlayer(elapsedTime);
    updateBullets(elapsedTime);
    updateEnemies(elapsedTime);
  }

  private void updateEnemies(long elapsedTime) {
    for (var enemy : enemies) {
      enemy.update(elapsedTime);
    }
  }

  private void updateBullets(long elapsedTime) {
    var it = bullets.iterator();
    while (it.hasNext()) {
      var bullet = it.next();
      bullet.update(elapsedTime);
      checkForBulletCollisions(bullet, it);
    }
  }

  private void checkForBulletCollisions(Bullet bullet, Iterator<Bullet> it) {
    if (bullet.getPosY() < posY) {
      it.remove();
    }
  }

  private void updatePlayer(long elapsedTime) {
    player.update(elapsedTime);
    checkForPlayerCollisions();
  }

  private void checkForPlayerCollisions() {
    if (player.getPosX() < posX) {
      player.setPosition(posX, player.getPosY());
      player.setVelocity(0.f, player.getDY());
    }
    if (player.getPosX() + player.getWidth() > posX + WIDTH) {
      player.setPosition(posX + WIDTH - player.getWidth(), player.getPosY());
      player.setVelocity(0.f, player.getDY());
    }
  }

  private void checkGameInput() {
    player.setVelocity(getPlayerDX(), player.getDY());

    if (shoot.isPressed()) {
      bullets.add(
        (Bullet) bullet.clone()
          .setPosition(
            player.getPosX() + (player.getWidth() - bullet.getWidth()) / 2.f,
            player.getPosY() - bullet.getHeight() - 5)
          .setVelocity(0.f, -Bullet.SPEED));
    }
  }

  private float getPlayerDX() {
    if (moveLeft.isPressed()) {
      return -Player.SPEED;
    }
    if (moveRight.isPressed()) {
        return Player.SPEED;
    }
    return 0.f;
  }

  public void draw(Graphics2D g) {
    drawBorder(g);
    player.draw(g);
    for (var bullet : bullets) {
      bullet.draw(g);
    }
    for (var enemy : enemies) {
      enemy.draw(g);
    }
  }

  private void drawBorder(Graphics2D g) {
    g.setColor(Color.red);
    // left
    g.fillRect(posX - BORDER_WIDTH, posY, BORDER_WIDTH, HEIGHT);
    // top
    g.fillRect(posX - BORDER_WIDTH, posY - BORDER_WIDTH, WIDTH + 2 * BORDER_WIDTH, BORDER_WIDTH);
    // right
    g.fillRect(posX + WIDTH, posY, BORDER_WIDTH, HEIGHT);
    // bottom
    g.fillRect(posX - BORDER_WIDTH, posY + HEIGHT, WIDTH + 2 * BORDER_WIDTH, BORDER_WIDTH);
  }
}
