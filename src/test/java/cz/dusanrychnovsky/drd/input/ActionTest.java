package cz.dusanrychnovsky.drd.input;

import org.junit.Test;

import static cz.dusanrychnovsky.drd.input.Action.Mode.InitialPressOnly;
import static org.junit.Assert.*;

public class ActionTest {

  @Test
  public void normalMode_remainsPressedUntilReleased() {
    var action = new Action("action");
    assertFalse(action.isPressed());
    action.press();
    assertTrue(action.isPressed());
    assertTrue(action.isPressed());
    action.press();
    assertTrue(action.isPressed());
    action.release();
    assertFalse(action.isPressed());
  }

  @Test
  public void initialPressOnlyMode_remainsPressedUntilConsumed() {
    var action = new Action("action", InitialPressOnly);
    assertFalse(action.isPressed());
    action.press();
    assertTrue(action.isPressed());
    assertFalse(action.isPressed());
    action.press();
    assertFalse(action.isPressed());
    action.release();
    assertFalse(action.isPressed());
  }

  @Test
  public void initialPressOnlyMode_remainsPressedUntilReleasedAndPressedAgain() {
    var action = new Action("action", InitialPressOnly);
    assertFalse(action.isPressed());
    action.press();
    assertTrue(action.isPressed());
    action.release();
    assertFalse(action.isPressed());
    action.press();
    assertTrue(action.isPressed());
  }
}
