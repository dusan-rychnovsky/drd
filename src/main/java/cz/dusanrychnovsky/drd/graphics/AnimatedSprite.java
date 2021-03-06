package cz.dusanrychnovsky.drd.graphics;

import java.awt.*;

public class AnimatedSprite implements Sprite {

  private final Animation animation;
  private float posX;
  private float posY;
  private float dX;
  private float dY;

  public AnimatedSprite(Animation animation, float posX, float posY) {
    this.animation = animation;
    this.posX = posX;
    this.posY = posY;
  }

  public Sprite setPosition(float posX, float posY) {
    this.posX = posX;
    this.posY = posY;
    return this;
  }

  public Sprite setVelocity(float dX, float dY) {
    this.dX = dX;
    this.dY = dY;
    return this;
  }

  public void update(long elapsedTime) {
    animation.update(elapsedTime);
    posX += dX * elapsedTime;
    posY += dY * elapsedTime;
  }

  public void draw(Graphics2D g) {
    g.drawImage(animation.getImage(), Math.round(getPosX()), Math.round(getPosY()), null);
  }

  public Animation getAnimation() {
    return animation;
  }

  public Image getImage() {
    return animation.getImage();
  }

  public int getWidth() {
    return getImage().getWidth(null);
  }

  public int getHeight() {
    return getImage().getHeight(null);
  }

  public float getPosX() {
    return posX;
  }

  public float getDX() {
    return dX;
  }

  public float getPosY() {
    return posY;
  }

  public float getDY() {
    return dY;
  }
}
