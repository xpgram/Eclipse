package battle.entities.unit;

/* This is the base format for all command-unit-type containers.
 * Simply allows me to unify each container under a common variable type,
 * and forces me to include a few basic methods into each one as well.
 */

public abstract class UnitData {
  
  // Reference to the Unit object holding this info.
  Unit parent;
  
  // Constructor that creates pretend, test data.
  public UnitData(Unit parent) {
    this.parent = parent;
    init();
    generateFalseData();
  }
  
  // Constructor that loads data from a file.
  public UnitData(Unit parent, String data) {
    this.parent = parent;
    init();
    load(data);
  }
  
  // Instantiates any necessary fields, such as arrays, before filling them with data.
  abstract void init();
  
  // Loads data from a file.
  abstract void load(String data);
  
  // Saves data to a string, which may be written to a file.
  abstract String save();
  
  // Generates pretend, test data.
  abstract void generateFalseData();
  
}
