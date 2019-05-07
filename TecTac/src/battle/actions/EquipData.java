/*
 * This class holds all relevant information for a piece of equipment.
 * This information is read-only and primarily handled by EquipmentDatabaseManager.
 * 
 * This class is like a quantized EquipmentDatabaseManager.
 * 
 * Data can be loaded any time after instantiation, but after the first load,
 * the object becomes read only. (I can't be sure that this is necessary. Especially
 * if the EDB never get()'s EquipData objects for you, which I think it won't.)
 * 
 *  - Name
 *  - Description
 *  - Sell Value, and
 *  - Stat Requirements
 * 
 * TODO [COMPLETED...?] StatReq will need to be its own type, the mess down there is ridiculous.
 */

package battle.actions;

import java.util.List;

import battle.constants.TargetType;
import battle.entities.unit.StatID;
import battle.entities.unit.UnitStats;
import battle.utils.Attribute;
import battle.utils.AttributeTagList;
import battle.utils.StatVal;

public class EquipData {

  EquipID id;
  boolean passive;
  
  String name;
  String description;
  String shortDescription;
  int value;      // Pennie, Nickel, Rones, G, Imperiums (?), Quatters, Sizzlers, ...
  
  TargetType tType;
  int POW;
  int AP;
  int Range;
  int nullRange;
  int DEF;
  int RES;
  int FireAff;
  int IceAff;     // Elemental affinities. Simply subtracted from damage when invoked: 0 is neutral, -2 is weak, and 2 is resistant. 
  int ThunAff; 
  AreaEffectMap areaMap;
  AttributeTagList attributes;
  
  List<StatVal> statReqs;   // Specifies stats and levels needed to equip.
  List<StatVal> statPros;   // Specifies which stats recieve XP when used.
  
  //Sprite icon;        // Little icon. Used in menus.
  //ActionAnim anim;    // Used in battle, eye candy. Useless(?) for passives.
  
  
  public EquipData() {
    generateFalseData();
  }
  
  public EquipData(String data) {
    loadString(data);
  }
  
  public void loadString(String data) {
    // if (this.id == null ||
    //     this.id.equals(EquipID.NONVALID_ID)) {}
    
    // TODO Parse string for gold nuggers
    // TODO Remove this method? After load, there shouldn't be any need to change it.
    //      Or make it private, anyway.
    
    // EquipID
    // Passivity
    // Name... blah blah blah
  }
  
  public void generateFalseData() {
    this.id = new EquipID(EquipID.NONVALID_ID);
    this.passive = false;
    
    String[] mockNames = {"Iron Sword", "Fire", "Blowback Pistol", "Gunhilde", "Fyrnpoly"};
    this.name = mockNames[0]; // TODO randomize
    String[] mockDescs = new String[15];
    mockDescs[0] = "Gets up in the other guy, and just when he's like \"Oh, this is probably fine,\" it gives him a real good poke, and then he's like \"Oh man, this isn't fine at all\" 'cause now he been got when he ain't been got before.";
    mockDescs[1] = "When some guy is about to get up in your way and says \"Dude, your mouth is gettin' tight and I'm gonna punch it loose!\" you'll just be like \"Whatever, man\" and you'll stay tight forever.";
    mockDescs[2] = "Everyone in the room will get really upset and they'll say \"This is really bad news for me\" and then you can finally be the one with the best news around.";
    mockDescs[3] = "When a clever guy wants to get smart on you, you can just say \"Well, get smart on THIS\" as you flail around and probably punch him in the face.";
    mockDescs[4] = "Proves that you're a big guy and other guys are just little guys and you can touch them whenever you want to.";
    mockDescs[5] = "If you're afraid of bugs then this is great because bugs will think that you're a big bug trying to bite them and make them itchy.";
    mockDescs[6] = "There is no way you don't want to use this shit, this shit is like FIRE and you ALWAYS want it in your favorite bad guy even if it's like a MILLION dollars you'd buy this.";
    mockDescs[7] = "Gets inside someone else's house, and then they'll say \"Fuck you! I'm gonna leave and be homeless then!\" 'cause you're so irritating to be around.";
    mockDescs[8] = "When a guy puts his hands on you, you can put your hands on him, and then whoever's hands find the best place to be gets to push the other guy so hard he falls down.";
    mockDescs[9] = "If you're about to get stung, you can throw faces like they pissed you off, and then they'll leave?";
    mockDescs[10] = "Comes up to some other guy and says \"You're my best friend now, so I'm going to touch your back!\" and then makes them feel really special.";
    mockDescs[11] = "Gives somebody a hot, tasty milk adventure for their mouth but it's cold and tastes like cherry syrup.";
    mockDescs[12] = "Makes the other guy get banged on with a big rock until he's dead on the floor.";
    mockDescs[13] = "Makes you hide in the bushes, and then the other guy will be like \"Man, it sure is nice just being alone with these bushes,\" but then you'll pop out and say \"I'm not real bushes!\" and he'll momentarily forget what reality is--so this inflicts confusion.";
    mockDescs[14] = "No one thinks you're good at being a tough guy, so then you use this to get tough on their butthole.";
    this.description = mockDescs[0]; // TODO Randomize
    this.shortDescription = "Man, you hella wanna buy this just DO IT. And fuck you, also.";
    this.value = 12000;
    
    this.tType = TargetType.Enemy;
    this.POW = 2;    // TODO Randomize this too
    this.AP = 1;
    this.Range = 3;
    this.nullRange = 1;
    this.DEF = 0;
    this.RES = 0;
    this.FireAff = 0;
    this.IceAff = 0;
    this.ThunAff = 0;
    this.areaMap = new AreaEffectMap();
    this.attributes = new AttributeTagList();
    
    this.statReqs.add(new StatVal());
  }
  
  public boolean meetsRequirements(UnitStats stats) {
    boolean result = true;
    StatID stat;
    
    for (StatVal s : statReqs) {
      stat = s.getStat();
      if (s.getValue() > stats.getStat(stat))
        result = false;
    }
    return result;
  }
  
  // #### All Getter Methods Relating To Equipment Stats Written Below ####
  
  public EquipID getID() {
    return this.id;
  }
  
  public boolean isPassive() {
    return this.passive;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getShortDescription() {
    return this.shortDescription;
  }
  
  public int getValue() {
    return this.value;
  }
  
  public TargetType getTargetType() {
    return this.tType;
  }
  
  public int getPOW() {
    return this.POW;
  }
  
  public int getAP() {
    return this.AP;
  }
  
  public int getRange() {
    return this.Range;
  }
  
  public int getNullRange() {
    return this.nullRange;
  }
  
  public int getDEF() {
    return this.DEF;
  }
  
  public int getRES() {
    return this.RES;
  }
  
  public int getFireAffinity() {
    return this.FireAff;
  }
  
  public int getIceAffinity() {
    return this.IceAff;
  }
  
  public int getThunderAffinity() {
    return this.ThunAff;
  }
  
  public AreaEffectMap getAreaMap() {
    return this.areaMap;
  }
  
  public AttributeTagList getAttributes() {
    AttributeTagList result = new AttributeTagList();
    result.copy(attributes);
    return result;
  }
  
  // Returns true if a valid instance of Attribute att is found in the list of effects. 
  public boolean containsAttribute(Attribute a) {
    return attributes.contains(a);
  }
  
  public List<StatVal> getStatReqs() {
    return this.statReqs;
  }
  
  public List<StatVal> getStatPros() {
    return this.statPros;
  }
}
