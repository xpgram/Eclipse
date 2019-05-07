package main;

import graphics.processing.Processing;    // Remove this library when Processing's API is no longer needed.
import utils.DeltaTime;

public class Main {
  
  public static long framecount = 0;
  
  public static void main(String[] args) {
    
    Game game = Game.instance();
    Program prog = Program.instance();
    DeltaTime dt = DeltaTime.instance();
    
	  game.init();
	  Processing.run(args);      // Remove or change this line when you want to swap out the graphics API.
	  while (prog.isRunning()) {
	    framecount++;
	    dt.updateDelta();              // TODO Probably put the frame-limiting loop here.
	    game.input();            // I can ... handle input without Processing, but it may be trickier.
	    game.update();           // Update must be handled without Processing because otherwise I have no logic to divorce when I unplug.
	    game.draw();             // Currently (on a separate thread?) Processing.draw() is where anything useful actually happens.
	  }
	  game.close();
	}
	
}


// TODO Add a frame-limiting loop: 60fps.
// Timers will count frames instead of fractions of seconds.
// I'm having trouble calculating delta for some reason, it's not making any sense to me.
// The irony: I think I still need delta for the frame-limiter. We'll see.