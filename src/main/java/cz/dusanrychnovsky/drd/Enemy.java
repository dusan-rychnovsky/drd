package cz.dusanrychnovsky.drd;

import cz.dusanrychnovsky.drd.graphics.AnimatedSprite;
import cz.dusanrychnovsky.drd.graphics.Animation;

public class Enemy extends AnimatedSprite {

  public Enemy(Animation animation) {
    super(animation, 0.f, 0.f);
  }

  public Enemy clone() {
    return new Enemy(getAnimation());
  }
}
