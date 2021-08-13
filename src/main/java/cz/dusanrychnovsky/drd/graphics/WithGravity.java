package cz.dusanrychnovsky.drd.graphics;

import java.awt.*;

public class WithGravity implements Sprite {

  private final Sprite sprite;
  private final float gravity;
  private final int floorY;

  public WithGravity(Sprite sprite, float gravity, int floorY) {
    this.sprite = sprite;
    this.gravity = gravity;
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

  @Override
  public void update(long elapsedTime) {

    var dY = getDY() + gravity * elapsedTime;
    if (getPosY() + getWidth() >= floorY) {
      dY = 0.f;
      setPosition(getPosX(), floorY - getWidth());
    }
    setVelocity(getDX(), dY);

    sprite.update(elapsedTime);
  }
}
