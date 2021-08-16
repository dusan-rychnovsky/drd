package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.input.Action;
import cz.dusanrychnovsky.drd.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Playground {

  private static final int BORDER_WIDTH = 2;

  private final Action moveLeft = new Action("moveLeft");
  private final Action moveRight = new Action("moveRight");

  private final Player player;
  private final int posX;
  private final int width;
  private final int posY;
  private final int height;

  public Playground(Input input, int posX, int posY, int width, int height, Player player) {
    this.posX = posX;
    this.width = width;
    this.posY = posY;
    this.height = height;

    input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    input.mapToKey(moveRight, KeyEvent.VK_RIGHT);

    this.player = player;
    player.setPosition(
      posX + (width - player.getWidth()) / 2.f,
      posY + height - player.getHeight());
  }

  public void draw(Graphics2D g) {
    drawBorder(g);
    g.drawImage(player.getImage(), Math.round(player.getPosX()), Math.round(player.getPosY()), null);
  }

  public void update(long elapsedTime) {
    checkGameInput();
    player.update(elapsedTime);
    checkForCollisions();

  }

  private void checkForCollisions() {
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
