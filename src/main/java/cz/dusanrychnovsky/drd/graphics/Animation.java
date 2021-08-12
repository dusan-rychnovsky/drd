package cz.dusanrychnovsky.drd.graphics;

import java.awt.*;
import java.util.ArrayList;

public class Animation {

  private final ArrayList<Frame> frames;
  private final long totalDuration;
  private long animationTime;
  private int currIndex;

  private Animation(ArrayList<Frame> frames, long totalDuration) {
    this.frames = frames;
    this.totalDuration = totalDuration;
  }

  public static Builder builder() {
    return new Builder();
  }

  public synchronized void start() {
    animationTime = currIndex = 0;
  }

  public synchronized void update(long elapsedTime) {
    animationTime += elapsedTime;

    if (animationTime >= totalDuration) {
      animationTime = animationTime % totalDuration;
      currIndex = 0;
    }

    while (animationTime > frames.get(currIndex).getEndTime()) {
      currIndex++;
    }
  }

  public synchronized Image getImage() {
    return frames.get(currIndex).getImage();
  }

  public static class Builder {

    private final ArrayList<Frame> frames = new ArrayList<>();
    private long totalDuration = 0;

    public Builder addFrame(Image image, long duration) {
      totalDuration += duration;
      frames.add(new Frame(image, totalDuration));
      return this;
    }

    public Animation build() {
      if (frames.isEmpty()) {
        throw new IllegalStateException("Empty animation.");
      }
      return new Animation(frames, totalDuration);
    }
  }

  private static class Frame {
    private final Image image;
    private final long endTime;

    public Frame(Image image, long endTime) {
      this.image = image;
      this.endTime = endTime;
    }

    public Image getImage() {
      return image;
    }

    public long getEndTime() {
      return endTime;
    }
  }
}
