package battle.entities.unit;

import java.io.File;
import java.util.Scanner;

import utils.FileScanner;

/* This container just has the unit's name.
 * It still exists because I'm not convinced I won't have more ID-type info to throw in here, but currently it's a principles class.
 */

public class UnitInfo extends UnitData {

  private String name;
  //short visualType    // Not sure what to call this. I imagine having a "database" of chibi soldier sprites, and this number just picks one.
  
  public UnitInfo(Unit parent) {
    super(parent);
  }
  
  public UnitInfo(Unit parent, String data) {
    super(parent, data);
  }
  
  @Override
  protected void init() {
    this.name = "";
  }

  /* Reads the unit's info from a string, elements separated by ;
   */
  @Override
  void load(String data) {
    this.name = data;   // TODO I guess?
  }
  
  /* Writes the unit's info to a string, elements separated by ; 
   */
  public String save() {
    return this.name;
  }

  /* Invents pretend unit data for testing purposes.
   */
  @Override
  void generateFalseData() {
    // Read a random name from the list of names written into one of the assets files.
    String filename = "assets/static/character_names";
    int total = FileScanner.countLines(filename);
    int nameIndex = (int)(Math.random()*total);
    this.name = FileScanner.readLineNumber(filename, nameIndex);
  }
  
  /* Returns the name of the unit as a String object.
   */
  public String name() {
    return this.name;
  }
}
