package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  @Test
  public void test() throws InterruptedException {

    var mode = new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
    var frame = createFrame();

    var window = new Window();
    window.setFullScreen(mode, frame);

    Thread.sleep(5_000);

    window.restoreScreen();
    System.out.println("DONE");
  }

  private JFrame createFrame() {
    return new JFrame() {
      {
        getContentPane().setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", 0, 24));
      }
      public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Hello World!", 20, 50);
      }
    };
  }
}
