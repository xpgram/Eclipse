package main;

/* This is the creature entity that inhabits the land of.. Pixiland.
 * This class handles the thinking and the metadata of said creature.
 * 
 * Pixils may "choose" to have certain qualities, but every quality comes with a metabolism cost, so it does not
 * in fact benefit the creature to take on qualities inherently useless to its function.
 * 
 * Death leaves behind ~10% of the energy the creature attained in its life.
 * 
 * Weight affects speed and metabolism. Lighter creatures have lesser defenses, but are agile and easy to keep living.
 * Heavier creatures require much more food, but are natural mountains.
 * Weight also affects EN-expenditure-per-action as a scalar.
 * 
 * I may iterate through a Pixil's actions based on priority, or whatever.
 * Like, Turn   Does the creature want to turn? Sum inputs... no.
 *       Move   Does the creature want to move? Sum inputs... yes.
 *       Attack/Eat/Birth/Nothing   What action does the creature want to take? Sum inputs... Eat wins.
 */

public class Pixil {

  private String name;
  private int EN, ENcap;
  private int HP, HPcap;
  private int weight;       // Positively affects PHY damage/defense? But also makes maintenance more costly.
  
  public Pixil() {
    
  }
  
  private void turn() {       // Free, but only doable once per turn
    
  }
  
  private void move() {       // Moving happens before attacking..
    
  }
  
  private void attack() {
    
  }
  
  private void eat() {
    
  }
  
  private void birth() {
    
  }
}
