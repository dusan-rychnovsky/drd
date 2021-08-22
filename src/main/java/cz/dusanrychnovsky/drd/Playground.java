package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.input.Action;
import cz.dusanrychnovsky.drd.input.Input;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyEvent;

import static cz.dusanrychnovsky.drd.input.Action.Mode.InitialPressOnly;

public class Playground {

  private static final int BORDER_WIDTH = 2;

  private final Action shoot = new Action("shoot", InitialPressOnly);
  private final Action moveLeft = new Action("moveLeft");
  private final Action moveRight = new Action("moveRight");

  private final Bullet bullet;
  private final List<Bullet> bullets = new ArrayList<>();

  private final Player player;
  private final int posX;
  private final int width;
  private final int posY;
  private final int height;

  private final Enemy enemy;
  private final List<Enemy> enemies = new ArrayList<>();

  public Playground(Input input, int posX, int posY, int width, int height, Player player, Bullet bullet, Enemy enemy) {
    this.posX = posX;
    this.width = width;
    this.posY = posY;
    this.height = height;
    this.enemy = enemy;

    input.mapToKey(shoot, KeyEvent.VK_SPACE);
    input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    input.mapToKey(moveRight, KeyEvent.VK_RIGHT);

    this.bullet = bullet;
    this.player = player;
    player.setPosition(
      posX + (width - player.getWidth()) / 2.f,
      posY + height - player.getHeight());

    for (var i = 0; i < 10; i++) {
      enemies.add(
        (Enemy) enemy.clone()
          .setPosition(
            posX + 25 + i * (enemy.getWidth() + 30),
            posY + 10)
        .setVelocity(0.f, .2f)
      );
    }
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
    if (player.getPosX() + player.getWidth() > posX + width) {
      player.setPosition(posX + width - player.getWidth(), player.getPosY());
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
    g.drawImage(player.getImage(), Math.round(player.getPosX()), Math.round(player.getPosY()), null);
    for (var bullet : bullets) {
      g.drawImage(bullet.getImage(), Math.round(bullet.getPosX()), Math.round(bullet.getPosY()), null);
    }
    for (var enemy : enemies) {
      g.drawImage(enemy.getImage(), Math.round(enemy.getPosX()), Math.round(enemy.getPosY()), null);
    }
  }

  private void drawBorder(Graphics2D g) {
    g.setColor(Color.red);
    // left
    g.fillRect(posX - BORDER_WIDTH, posY, BORDER_WIDTH, height);
    // top
    g.fillRect(posX - BORDER_WIDTH, posY - BORDER_WIDTH, width + 2 * BORDER_WIDTH, BORDER_WIDTH);
    // right
    g.fillRect(posX + width, posY, BORDER_WIDTH, height);
    // bottom
    g.fillRect(posX - BORDER_WIDTH, posY + height, width + 2 * BORDER_WIDTH, BORDER_WIDTH);
  }
}
