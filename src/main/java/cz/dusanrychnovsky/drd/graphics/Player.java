package cz.dusanrychnovsky.drd.graphics;

import java.awt.*;

public class Player implements Sprite {

  private final Sprite sprite;
  private final float gravityDY;
  private final float jumpDY;
  private final int floorY;
  private boolean jumping;

  public Player(Sprite sprite, float gravityDY, float jumpDY, int floorY) {
    this.sprite = sprite;
    this.gravityDY = gravityDY;
    this.jumpDY = jumpDY;
    this.floorY = floorY;
  }

  @Override
  public Image getImage() {
    return sprite.getImage();
  }

  @Override
  public int getWidth() {
    return sprite.getWidth();
  }

  @Override
  public int getHeight() {
    return sprite.getHeight();
  }

  @Override
  public float getPosX() {
    return sprite.getPosX();
  }

  @Override
  public float getDX() {
    return sprite.getDX();
  }

  @Override
  public float getPosY() {
    return sprite.getPosY();
  }

  @Override
  public float getDY() {
    return sprite.getDY();
  }

  @Override
  public void setPosition(float posX, float posY) {
    sprite.setPosition(posX, posY);
  }

  @Override
  public void setVelocity(float dX, float dY) {
    sprite.setVelocity(dX, dY);
  }

  public void jump() {
    if (!jumping) {
      jumping = true;
      setVelocity(getDX(), jumpDY);
    }
  }

  @Override
  public void update(long elapsedTime) {

    if (jumping) {
      var dY = getDY() + gravityDY * elapsedTime;
      setVelocity(getDX(), dY);
    }

    sprite.update(elapsedTime);

    if (jumping  && getPosY() + getHeight() >= floorY) {
      jumping = false;
      setPosition(getPosX(), floorY - getHeight());
      setVelocity(getDX(), 0.f);
    }
  }
}
