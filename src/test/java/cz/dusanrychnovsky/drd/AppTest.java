package cz.dusanrychnovsky.drd;

import javax.swing.*;
import java.awt.*;

import org.junit.Test;

public class AppTest {

  private static final int FONT_SIZE = 24;

  private Image opaqueImage;
  private Image transparentImage;

  @Test
  public void test() throws InterruptedException {

    opaqueImage = loadImage("circle-opaque.png");
    transparentImage = loadImage("circle-transparent.png");

    var mode = new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
    var frame = createFrame();

    var window = new Window();
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
        drawImage(g, opaqueImage, 0, 0, "Opaque");
        drawImage(g, transparentImage, 800, 0, "Transparent");
      }
    };
  }

  private void drawImage(Graphics g, Image image, int posX, int posY, String caption) {
    g.drawImage(image, posX, posY, null);
    g.drawString(caption, posX + 5, posY + FONT_SIZE + image.getHeight(null));
  }
}
