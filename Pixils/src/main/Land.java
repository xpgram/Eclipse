package main;

/* This represents a unit of land, that which pixils live on.
 * Mostly a metadata container, this class keeps track of food quantity, food type, climate, etc.
 * 
 * Not all lands should fruit.
 * Fuck grass, man.
 * Fruit is where it's at.
 * Fruit or meat, anyway.
 * Land either has some fucking fruit, or it has a dead guy.
 */

public class Land {

  public static enum Terrain {
    Earth,
    Water,
    DeepWater,      // Probably won't use. Meant to differentiate ponds from oceans.
    Mountain        // Indistinguishable from 'walls' or 'simply uninhabitables'
  }
  
  public static final int DECAY = 3;          // The rate at which meat decays per tick.
  public static final int GROWTH = 2;         // The rate at which veggies grow per tick.
  public static final int GROWTH_CAP = 100;   // The maximum richness veggies may grow to.
  public static final int BARREN_CAP = -40;   // The minimum EN level. Below 0 is nutritionally useless, so negatives are "how long" land remains foodless.
  public static final int TOXICITY_CAP = 200; // The 100% value for maximum poisonousness.
  
  private Terrain terrain;
  private boolean meat;
  private int toxicity;
  private int EN;
  
  public Land() {
    this.meat = false;
    this.toxicity = 0;
    this.EN = 0;
  }
  
  public void setFood(boolean meat, int toxicity, int EN) {
    this.meat = meat;
    this.toxicity = toxicity;
    this.EN = EN;
    constrain();
  }
  
  private void constrain() {
    if (this.toxicity > TOXICITY_CAP)
      this.toxicity = TOXICITY_CAP;
    else if (this.toxicity < 0)
      this.toxicity = 0;
    
    if (this.EN < BARREN_CAP)
      this.EN = BARREN_CAP;
    if (veggie() && this.EN > GROWTH_CAP)
      this.EN = GROWTH_CAP;
  }
  
  public boolean veggie() {
    return this.meat == false;
  }
  
  public boolean meat() {
    return this.meat;
  }
  
  public int toxicity() {
    return this.toxicity;
  }
  
  public int EN() {
    return this.EN;
  }
  
  public boolean consumed() {
    return this.EN <= 0; 
  }
  
  public int consume(int bite) {
    int r = bite;
    this.EN -= bite;
    if (this.EN < 0)
      r += this.EN;     // Reduce content of bite to give eater the appropriate EN.
    return r;
  }
  
  public void decay() {
    if (meat()) {
      this.EN -= DECAY;
      this.toxicity += 1;
      constrain();
    }
  }
  
  public void expire() {
    // Use toxicity (of meat) to determine how long land should be barren for.
  }
  
  public void grow() {
    if (veggie()) {
      this.EN += GROWTH;
      constrain();
    }
  }
  
}
