/*
 * This class is a helper to main, just a clean place for me to put all my update(), draw(), input() and init() instructions.
 * I may move those to their own helper classes if they get too complex.
 * 
 * Actually, intuition tells me I should use interfaces to determine what the game is doing.
 * So, each interface would describe those four functions in its own way, and they'd be swappable, which this
 * class would handle. It would help with stringing together all these different modes: start screen, file select,
 * loading screen(?), world map, battle map, etc.
 * I haven't gotten that far yet, though.
 * 
 * Anyway, I was trying (foolishly?) to protect the integrity of the game loop by only letting it be instantiated once.
 * The idea being that if someone malicious tried to grab these functions and use them in a method call occurring inside
 * one of these functions, then they could crash it via stack overflow. I don't know if this fear is rational.
 * What I ~don't~ like is that I put these functions in this file to clean up their action, but because of this protection
 * of mine, there's still "junk" at the top of the class. I really just want like four static methods. They'd be public,
 * though, which is the problem.
 * 
 * Also, this really needs to be a state machine like my Turn Engine. There needs to be a way to seamlessly transition
 * from opening screen, to file select screen, to world map, to town and shop menu, to pause menu, to battle maps, etc.
 * I can't say I 100% understand how to stitch all those together without ~any~ problems, but states, push() and pop()
 * seem like the best way to go (as described in that article). The most confusing part for me, probably honestly, is
 * the push() and pop() part. I get what it's doing, but I don't yet understand how it's helpful. I'll have to get dirty
 * with it before I'll know.
 * 
 * 
 * TODO
 * I've finished (I think) most of the things I had before, all rewritten for this much more grown up, tasty codebase
 * experience. I still have a few things left to do though.
 *  - I need to spawn units to the field.
 *      In Processing this was handled by BattleParty, but I think it should go elsewhere. I just haven't decided yet.
 *      Maybe a spawn manager? Or EntitiesList? Or the Grid?
 *      The Grid, or the Map actually, should have a list of spawnable places in arbitrary numerical order, organized by team.
 *      BattleParty, however it's done, should never have more units than the map spawnable places.
 *      Then, whatever does the thing, just matches up BattleParty to SpawnLocsAllies in numerical order.
 *  - I need to setup the camera and draw methods
 *      I segregated all the draw functions from units and the grid and anything else which may have been tempted.
 *      I need a draw manager that pulls information from everywhere and draws it all in correct sequence.
 *      That might be it's own crazy hours-long thing.
 *  - Probably more things as well but I haven't looked everywhere in my program yet to find out for sure
 *      I'll list them all here as I discover them, though.
 *  - Oh, here's one: does TurnMachine work?
 *      No, it doesn't. It isn't finished.
 *      What I was doing last: I was going through each state, finding out which object calls were commented
 *      out (place-holders until those types were actually written) and fixing them up so that they actually
 *      work.
 *  - Test Out All These Shits
 *      I've written ~a lot,~ but I haven't actually tried any of it out. I could use the main loop here to
 *      individually test different types. I think most of it should be okay, though. I could also do this
 *      only after attempting the whole thing: fix up the turn machine, put final touches on types everywhere,
 *      run the program, find out all of its bones are broken, like seriously every single one, and then do
 *      this main-loop troubleshooting. Probably more fun that way.
 *      
 * All in all, I'm pretty proud. I managed to port most of the nitty-bitties over while adding new, more in-depth
 * functionality to it at the same time. The whole thing, I'm sure, isn't perfect yet; there are probably a lot of
 * problems with the whole thing, some really terrible, ugly, unmanageable problems lurking beneath the surface.
 * But, it's all way more robust than it ~was,~ so I'll take it!
 * 
 * ^ Specifically, what I'm worried about is my own inexperience with all of this. I'm discovering things as
 *   fast as I'm writing them, and as a result I've had to make decisions I wasn't sure about, so I expect
 *   there to be issues en masse simply due to having a young sense of "best practice."
 *   This is one of those projects that gets entirely rewritten midway through its life cycle because, while it
 *   is functional and somewhat maintainable, it is objectively horrible.
 *   My comments are all over the place in terms of form and consistency, case in point.
 *   I'm just rambling.
 *   Am I cool? 
 */

package main;

import battle.ui.GridCursor;
import graphics.drawmaster.DrawMaster;
import input.VirtualController;
import statemachines.TurnMachine;

public class Game {
  
  // Other class references
  private static TurnMachine engine;
  private static VirtualController controller;
  
  // Whether to allow instantiation.
  private static boolean instantiated = false;
  
  // Inaccessible constructor
  private Game() {
  }
  
  // Retrieves the singleton instance, but only once.
  public static Game instance() {
    if (!instantiated) {
      instantiated = true;
      return new Game();
    }
    return null;
  }  
  
  
  public void init() {
    // The importance of this function is to construct certain core classes and then initialize them.
    // The initialization step is where they collect their references to other singletons, an important thing to keep separate from
    // construction; doing things in one step tends to cause stack overflows.
    
    // As a rule of thumb, I guess, no class should call a singleton in its constructor.
    // This is an annoying rule to have, I'm probably going to break it a lot.
    // But, it's only annoying because I don't know what the solution is yet.
    
    // TODO Construct and initialize the turn machine.
    engine = TurnMachine.instance();
    engine.init();
    
    controller = VirtualController.instance();
  }
  
  
  public void update() {
    // Call TurnMachine's update phase, probably. What else?
    // In the future, discern which mode we're in, and then call that machine's update phase.
    // This is a global switch, essentially.
    engine.update();
  }
  
  
  public void draw() {
    // Very important.
    // Like update, figure out who is doing the stuff, and tell them to continue with the stuff.
    // I need to write a draw-master class first, however.
    // There's no one to call. How will we see anything?
    
    // TODO Implement... the student thing I was using. I forget what it was called.
    // TODO Implement it such that I can see the bugs as they appear.
    // TODO Also, call the implementation something specific so I can easily write and switch to a different graphicsmaster.
    
    // Until I find a better place to put these things, animation state timers and et cetera will be placed here, I suppose.
    GridCursor.instance().updateAnimationState();
  }
  
  
  public void input() {
    // Re-direct program flow into the battle.inputmaster or the world.inputmaster
    // Just as the other methods do.
    
    // TODO Add at least rudimentary input functionality so that I may control the program through my bug tests.
    // TODO Begin implementing inputmaster
    
    controller.update();
  }
  
  
  public void close() {
    // Delete everything. And/or save everything.
    // In fact, I wonder if Game.save() is a function I need.
    // ...
    // Probably.
    // Well, no. Actually, Game is just a global switch that redirects flow to the rest of the program,
    // depending on what should or should not be running. Save() probably needs to be handled... somewhere.
    // My point being, you wouldn't call Game.instance().save(), you would want to call something else,
    // even though that call makes a lot of english sense.
    // Game is kind of the master control unit. Maybe that does make sense.
    // You can only ever have one instance, though, and Main.java has that instance.
    
    // Everything important is a singleton. So, a savemaster of sorts should be able to accurately collect
    // and write down all the important data. And that savemaster would A) manage files, and B) be callable
    // anywhere, like say from a world-map pause menu after the player selects "Save Game."
    // I guess that makes sense.
  }
  
}
