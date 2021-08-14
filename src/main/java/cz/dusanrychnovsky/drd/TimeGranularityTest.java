package cz.dusanrychnovsky.drd;

public class TimeGranularityTest {
  public static void main(String[] args) {

    var NUM_SAMPLES = 100;
    var samples = new long[NUM_SAMPLES];

    var prevTime = System.currentTimeMillis();
    var count = 0;
    while (count < NUM_SAMPLES) {
      var currTime = System.currentTimeMillis();
      var elapsedTime = currTime - prevTime;
      if (elapsedTime != 0L) {
        samples[count] = elapsedTime;
        prevTime = currTime;
        count++;
      }
    }

    for (var sample : samples) {
      System.out.println(sample);
    }

    System.out.println("DONE");
  }
}
