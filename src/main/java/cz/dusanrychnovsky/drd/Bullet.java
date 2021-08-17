package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.AnimatedSprite;
import cz.dusanrychnovsky.drd.graphics.Animation;

public class Bullet extends AnimatedSprite {

  public static final float SPEED = 1.f;

  public Bullet(Animation animation) {
    super(animation, 0.f, 0.f);
  }

  public Bullet clone() {
    return new Bullet(getAnimation());
  }
}
