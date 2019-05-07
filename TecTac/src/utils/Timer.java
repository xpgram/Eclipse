package utils;

import main.Main;

/* This class keeps time for me in simplified form.
 * I use it for animation cycles, among other things.
 * Keep in mind:
 *    Use, currently, is to set a timer, let it finish, verify it has, do something, then reset the timer.
 *    This means the timer doesn't actually loop continuously, even though it can.
 *    When it loops endlessly, it takes care to remember overflow when it starts over; but when it finishes,
 *    it erases that overflow by pretending to stop at its maximum time value: the limit defined when it was created.
 *    This was to prevent animation errors where position on screen, etc., were dependent on time; you can't have the
 *    end-point randomly bumping around, can you?
 *    But, if we're going to cycle by stopping and starting continuously, then our clock will be off by a little bit
 *    every trip. Over time this will add up and things will fall out of sync.
 *    
 *    Maybe, the forever-looping mode should set a flag every time it does so.
 *    Or maybe the user should just set a flag to always remember overflow, even when finished.
 */

public class Timer {
  /* Purpose:
   *  - Keeps time up to a defined limit
   *  - Can be told to only repeat once, or any int number of times
   *  - Uses real seconds.
   */
  
  private DeltaTime dt = DeltaTime.instance();
  
  public static final long SECONDS_CONVERSION_FACTOR = (long)1e9;
  public static final long MILLIS_CONVERSION_FACTOR = (long)1e6;
  
  long time;            // The current elapsed time toward the time limit.
  long limit;           // The Timer's time limit.
  int lap;              // The current cycle the Timer is iterating through.    I'm realizing that I've misused the word 'lap.'
  int lapLimit;         // The Timer's cycle limit.
  boolean stopped;      // Whether the Timer's clock has been stopped.
  boolean skipframe;    // Indicates the Timer shouldn't count the current frame toward its time. Used to exclude the frame the Timer is counting ~from.~
  boolean pulse;        // Whether the Timer has cycled and reset itself.
  
  
  /* Creates a Timer object with a given time limit. This Timer will elapse exactly once before finishing.
   * In order for the clock to run, the object must be put in the program's update cycle somewhere.
   * @limit: The length in seconds for the timer's clock cycle.
   */
  public Timer(double limit) {
    construct((long)(limit * SECONDS_CONVERSION_FACTOR), 1);
  }
  
  /* Creates a Timer object with a given time limit and cycle limit.
   * In order for the clock to run, the object must be put in the program's update cycle somewhere.
   * @limit: The length in seconds for the timer's clock cycle.
   * @laps: The number of times the timer will cycle before it stops counting. A negative number will set the Timer to loop endlessly.
   */
  public Timer(double limit, int laps) {
    construct((long)(limit * SECONDS_CONVERSION_FACTOR), laps);
  }
  
  /* Creates a Timer object with a given time limit. This Timer will elapse exactly once before finishing.
   * In order for the clock to run, the object must be put in the program's update cycle somewhere.
   * @limit: The length in milliseconds for the timer's clock cycle.
   */
  public Timer(int limit) {
    construct((long)limit * MILLIS_CONVERSION_FACTOR, 1);
  }
  
  /* Creates a Timer object with a given time limit and cycle limit.
   * In order for the clock to run, the object must be put in the program's update cycle somewhere.
   * @limit: The length in milliseconds for the timer's clock cycle.
   * @laps: The number of times the timer will cycle before it stops counting. A negative number will set the Timer to loop endlessly.
   */
  public Timer(int limit, int laps) {
    construct((long)limit * MILLIS_CONVERSION_FACTOR, laps);
  }
  
  /* The private "helper" method to the class' constructors.
   */
  private void construct(long limit, int laps) {
    // TODO Throw exception if length <= 0
    limit = Math.abs(limit);    // Instead, I'm just taking the absolute value for now.
    
    this.time = 0L;
    this.limit = limit;
    this.lap = 0;
    this.lapLimit = laps;
    this.stopped = false;
    this.skipframe = true;
  }
  
  /* Updates the Timer's clock with the amount of time elapsed since the last update() call.
   */
  public void update() {
    // This is horrible form. What am I doing? I like my exits at the top, though; it keeps my code less indented down below.
    if (this.stopped)     return;
    if (this.skipframe) { this.skipframe = false; return; }
    
    this.time += dt.delta();    // This, I believe adds the frame's delta value, meaning calling update() twice adds delta twice.
    
    // Time keeping
    if (this.time >= this.limit) {
      this.pulse = true;  // Indicates a cycle was completed.
      
      // End of lap
      if (this.lap < this.lapLimit || this.lapLimit <= 0) {
        this.lap++;
        this.time -= this.limit;
      }
      // End of watch
      else {
        this.time = this.limit;
        this.stopped = true;
      }
    }
  }
  
  /* Resets the timer to its initial conditions.
   */
  public void reset() {
    this.time = 0L;
    this.lap = 0;
    this.stopped = false;
    this.skipframe = true;
    this.pulse = false;
  }
  
  /* Starts the Timer's counting cycle again, assuming it had been stopped before.
   * Will not start the timer if the timer has reached its cycle limit.
   */
  public void start() {
    if (this.finished() == false) {
      this.stopped = false;
      this.skipframe = true;
    }
  }
  
  /* Stops the Timer's counting cycle.
   */
  public void stop() {
    this.stopped = true;
  }
  
  /* Tells the timer to auto-assume a finished state.
   */
  public void finish() {
    this.time = this.limit;
    this.lap = this.lapLimit;
    this.stopped = true;
  }
  
  /* Returns a double equal to the Timer's current clock time in seconds.
   */
  public double time() {
    return ((double)this.time / SECONDS_CONVERSION_FACTOR);
  }
  
  /* Returns an int equal to the Timer's current clock time in seconds, truncated to the nearest second.
   */
  public int seconds() {
    return (int)(this.time / SECONDS_CONVERSION_FACTOR);
  }
  
  /* Returns an int equal to the Timer's current clock time in milliseconds, truncated to the nearest millisecond.
   */
  public int millis() {
    return (int)(this.time / MILLIS_CONVERSION_FACTOR);
  }
  
  /* Returns a long equal to the Timer's current clock time in nanoseconds.
   */
  public long nanos() {
    return this.time;
  }
  
  /* Returns a double equal to the Timer's total elapsed time across all laps.
   */
  public double totalElapsedTime() {
    return time() + (this.lap * limit());
  }
  
  /* Returns a double equal to the Timer's set clock length in seconds.
   */
  public double limit() {
    return ((double)this.limit / SECONDS_CONVERSION_FACTOR);
  }
  
  /* Returns an int equal to the Timer's set clock length in seconds, truncated to the nearest second.
   */
  public int limitSeconds() {
    return (int)(this.limit / SECONDS_CONVERSION_FACTOR);
  }
  
  /* Returns an int equal to the Timer's set clock length in milliseconds, truncated to the nearest millisecond.
   */
  public int limitMillis() {
    return (int)(this.limit / MILLIS_CONVERSION_FACTOR);
  }
  
  /* Returns a long equal to the Timer's set clock length in nanoseconds, truncated to the nearest nanosecond.
   */
  public long limitNanos() {
    return this.limit;
  }
  
  /* Returns an int equal to the Timer's current lap, or cycle iteration.
   */
  public int lap() {
    return this.lap;
  }
  
  /* Returns an int equal to the Timer's maximum number of laps before complete cycle halt.
   */
  public int lapLimit() {
    return this.lapLimit;
  }
  
  /* Returns true if the timer is not counting time.
   */
  public boolean stopped() {
    return this.stopped;
  }
  
  /* Returns a boolean that is true if the Timer has elapsed through all laps.
   */
  public boolean finished() {
    return (this.lap == this.lapLimit);
  }
  
  /* Returns a boolean that is true if the timer has completed a lap since the last call of this method,
   * or true if it has completed its first lap, period.
   * Returning true "consumes" the pulse; subsequent calls will return false unless another cycle has completed.
   */
  public boolean pulse() {
    boolean r = false;
    if (this.pulse) {
      this.pulse = false;
      r = true;
    }
    return r;
  }
}
