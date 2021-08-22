package cz.dusanrychnovsky.drd;

import java.awt.*;

public class Dashboard {

  public static final int WIDTH = 396;
  public static final int HEIGHT = 800;

  private int posX = 0;
  private int posY = 0;

  public Dashboard setPosition(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
    return this;
  }

  public void draw(Graphics2D g) {
    g.setColor(Color.LIGHT_GRAY);
    drawControls(g);
    drawScore(g);
  }

  private void drawControls(Graphics2D g) {
    g.setFont(new Font("Serif", Font.BOLD, 24));
    g.drawString("CONTROLS:", posX + 20, posY + 20);
    g.setFont(new Font("Serif", Font.PLAIN, 18));
    g.drawString("- ESC to quit", posX + 20, posY + 50);
    g.drawString("- P to pause", posX + 20, posY + 75);
    g.drawString("- LEFT and RIGHT to move", posX + 20, posY + 100);
    g.drawString("- SPACE to shoot", posX + 20, posY + 125);
  }

  private void drawScore(Graphics2D g) {
    g.setFont(new Font("Serif", Font.PLAIN, 24));
    g.drawString("SCORE:", posX + 20, posY + 200);
    g.setFont(new Font("Serif", Font.BOLD, 24));
    g.drawString("0", posX + 20, posY + 230);
  }

  private void drawCenteredString(Graphics g, String text, Rectangle rect) {
    FontMetrics metrics = g.getFontMetrics(g.getFont());
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g.drawString(text, x, y);
  }
}
