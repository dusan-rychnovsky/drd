package cz.dusanrychnovsky.drd;

public class ElapsedTime {

  // below modulo operator (%) is replaced with more efficient bitwise-and (&),
  // taking advantage of the fact that maxSamples is always a power of 2
  //
  // for d a power of two and all x:
  //   x % d == x & (d - 1)
  // for instance
  //   d = 32 (100000)
  //   d - 1 = 31 (011111)

  private final long maxElapsedTime;
  private final long avgPeriod;

  private final int maxSamples;
  private final int maxSamplesMask;
  private final long[] samples;

  private int firstIndex = 0;
  private int numSamples = 0;

  public ElapsedTime() {
    this(6 /* 2^6 = 64 samples */, 100L, 100L);
  }

  public ElapsedTime(int maxSamplesBits, long maxElapsedTime, long avgPeriod) {
    this.maxSamples = 1 << maxSamplesBits;
    this.maxSamplesMask = maxSamples - 1;
    this.samples = new long[maxSamples];
    this.maxElapsedTime = maxElapsedTime;
    this.avgPeriod = avgPeriod;
  }

  public long smoothen(long elapsedTime) {
    add(elapsedTime);
    return getAvg();
  }

  private void add(long elapsedTime) {
    elapsedTime = Math.min(elapsedTime, maxElapsedTime);
    samples[(firstIndex + numSamples) & maxSamplesMask] = elapsedTime;
    if (numSamples < maxSamples) {
      numSamples++;
    }
    else {
      firstIndex = (firstIndex + 1) & maxSamplesMask;
    }
  }

  private long getAvg() {
    var sum = 0L;
    for (var i = numSamples - 1; i >= 0; i--) {
      sum += samples[(firstIndex + i) & maxSamplesMask];
      if (sum >= avgPeriod) {
        return Math.round((double)sum / (numSamples - i));
      }
    }
    return Math.round((double)sum/numSamples);
  }
}
