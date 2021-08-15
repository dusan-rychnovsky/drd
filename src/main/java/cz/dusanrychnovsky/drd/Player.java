package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.AnimatedSprite;
import cz.dusanrychnovsky.drd.graphics.Animation;

public class Player extends AnimatedSprite {

  private final float minLeftX;
  private final float maxRightX;

  public Player(Animation animation, float posX, float posY, float minLeftX, float maxRightX) {
    super(animation, posX, posY);
    this.minLeftX = minLeftX;
    this.maxRightX = maxRightX;
  }

  @Override
  public void update(long elapsedTime) {
    super.update(elapsedTime);
    if (getPosX() < minLeftX) {
      setPosition(minLeftX, getPosY());
    }
    if (getPosX() + getWidth() > maxRightX) {
      setPosition(maxRightX - getWidth(), getPosY());
    }
  }
}
