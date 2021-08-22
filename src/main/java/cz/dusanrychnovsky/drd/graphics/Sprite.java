package cz.dusanrychnovsky.drd.graphics;

import java.awt.*;

public interface Sprite {
  Image getImage();
  int getWidth();
  int getHeight();
  float getPosX();
  float getDX();
  float getPosY();
  float getDY();
  Sprite setPosition(float posX, float posY);
  Sprite setVelocity(float dX, float dY);
  void update(long elapsedTime);
  void draw(Graphics2D g);
}
