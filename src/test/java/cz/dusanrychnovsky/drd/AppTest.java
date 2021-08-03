package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  @Test
  public void test() throws InterruptedException {
    var frame = new JFrame() {
      public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Hello World!", 20, 50);
      }
    };
    frame.getContentPane().setBackground(Color.blue);
    frame.setForeground(Color.white);
    frame.setFont(new Font("Dialog", 0, 24));

    var window = new Window();
    window.setFullScreen(new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN), frame);

    Thread.sleep(5_000);

    window.restoreScreen();
    System.out.println("DONE");
  }
}
