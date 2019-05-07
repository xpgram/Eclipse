/*
 * This class is a singleton accessor to this program's global settings.
 * This class is also how you would affect them, so if you would like to send the shutdown
 * message, it's down there somewhere.
 * I don't think I remember how to write good comments.
 */

package main;

public class Program {

  private static Program p;
  
  // Global settings variables
  boolean running;
  
  
  /* Private constructor stops deliberate instantiation.
   */
  private Program() {
    running = true;
  }
  
  /* Retrieves the singleton object for this class.
   * In turn, this also allows access to this object to any class that imports this one.
   */
  public static Program instance() {
    if (p == null) {
      p = new Program();
    }
    return p;
  }
  
  /* Returns true if the program still intends on running.
   */
  public boolean isRunning() {
    return running;
  }
  
  /* Tells main that the game loop should stop and the whole program should finalize.
   */
  public void shutdown() {
    running = false;
  }
  
}
