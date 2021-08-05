package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  private static final int FONT_SIZE = 24;

  private Window window;
  private Image opaqueImage;
  private Image transparentImage;

  @Test
  public void test() throws InterruptedException {

    opaqueImage = loadImage("circle-opaque.png");
    transparentImage = loadImage("circle-transparent.png");

    window = new Window();
    var mode = new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
    var frame = createFrame();
    window.setFullScreen(mode, frame);

    Thread.sleep(5_000);

    window.restoreScreen();
    System.out.println("DONE");
  }

  private Image loadImage(String path) {
    return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
  }

  private JFrame createFrame() {
    return new JFrame() {
      {
        getContentPane().setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", 0, FONT_SIZE));
      }
      public void paint(Graphics g) {
        super.paint(g);
        benchmarkImage(g, opaqueImage, 0, 0, "Opaque");
        benchmarkImage(g, transparentImage, 800, 0, "Transparent");
      }
    };
  }

  private void drawImage(Graphics g, Image image, int posX, int posY, String caption) {
    g.drawImage(image, posX, posY, null);
    g.drawString(caption, posX + 5, posY + FONT_SIZE + image.getHeight(null));
  }

  private void benchmarkImage(Graphics g, Image image, int posX, int posY, String caption) {
    int marginX = window.getWidth() - image.getWidth(null);
    int marginY = window.getHeight() - image.getHeight(null);

    int num = 0;
    var startTime = System.currentTimeMillis();
    while (System.currentTimeMillis() - startTime < 1500) {
      var x = Math.round((float)Math.random() * marginX);
      var y = Math.round((float)Math.random() * marginY);
      g.drawImage(image, x, y, null);
      num++;
    }

    var time = System.currentTimeMillis() - startTime;
    var speed = num * 1000f / time;
    System.out.println(caption + ": " + speed + "imgs/sec");
  }
}
