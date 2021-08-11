package cz.dusanrychnovsky.drd;

public class Loop {

  private final Update update;
  private final Draw draw;

  private boolean isRunning = true;

  public Loop(Update update, Draw draw) {
    this.update = update;
    this.draw = draw;
  }

  public void run() {
    var currTime = System.currentTimeMillis();
    while (isRunning) {
      var elapsedTime = System.currentTimeMillis() - currTime;
      currTime += elapsedTime;

      update(elapsedTime);
      draw();

      try {
        Thread.sleep(20);
      }
      catch (InterruptedException ex) {}
    }
  }

  private void update(long elapsedTime) {
    update.call(elapsedTime);
  }

  private void draw() {
    draw.call();
  }

  public void stop() {
    isRunning = false;
  }

  public interface Update {
    void call(long elapsedTime);
  }

  public interface Draw {
    void call();
  }
}
