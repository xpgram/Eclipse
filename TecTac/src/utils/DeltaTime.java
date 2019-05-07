package utils;

import java.util.concurrent.TimeUnit;

import main.Main;

public class DeltaTime {

  private static DeltaTime dt;
  
  // Fields relevant to delta time.
  private long timestamp;
  private long timestamp_last;
  private long deltatime;
  private double deltatime_seconds;
  
  // Private constructor
  private DeltaTime() {
    this.timestamp = System.nanoTime();
    this.timestamp_last = this.timestamp;
    this.deltatime = 0;
  }
  
  // Returns ~the~ instance of DeltaTime.
  public static DeltaTime instance() {
    if (dt == null)
      dt = new DeltaTime();
    return dt;
  }
  
  /* Calculates the time in nanoseconds since the last updateDelta() call.
   * For maximum effectiveness, this method should only be called once per frame.
   */
  public void updateDelta() {
    this.timestamp_last = this.timestamp;
    this.timestamp = System.nanoTime();
    this.deltatime = timestamp - timestamp_last;
    this.deltatime_seconds = (double)this.deltatime / 1.0e9;
  }
  
  /* A static function, globally accessible by any part of the program which needs delta time since the last update cycle.
   * @return: A double equal to the number of seconds since the last update cycle (or the last call to updateDelta(), anyway).
   */
  public double delta() {
    return this.deltatime;       // A nanosecond is one billionth of a second.
  }
  
}
