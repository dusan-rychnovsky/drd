package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.AnimatedSprite;
import cz.dusanrychnovsky.drd.graphics.Animation;

public class Player extends AnimatedSprite {

  public static final float SPEED = 1.f;

  public Player(Animation animation, float posX, float posY) {
    super(animation, posX, posY);
  }
}
