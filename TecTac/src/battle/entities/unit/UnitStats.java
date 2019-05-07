/*
 * This class manages the "hard-stats."
 * 
 * maxHP  : Health points
 * maxAP  : Ammo Points
 * PHY    : Physical and strength-based skills.
 * TCH    : Technical skill, particularly of the manadrive.
 * INT    : Skills with high-concept concepts.
 * DEX    : Performance skill, particularly with the hands.
 * MCH    : Skill in operating big machinery.
 * MOV    : Move range
 * 
 * As for the application of these stats (the abilities they let you use), I need to remember:
 *   This is not a game about relative skill levels.
 *   This is a game about preparation and execution.
 *   This is why you don't ~really~ need evasion or varying powers among weapon types. One sword is good enough.
 *   This game is about different kinds of swords having ~different pros and cons,~ not inferior/superior strength.
 *   Unlike Final Fantasy, you're not going to find a Mythril sword with +3 attack compared to what you have,
 *   what you'll find is a ~new weapon~ that happens to be a sword that is better at piercing thin armors, but ultimately less powerful.
 *   So, I can have more than one kind of sword in the game, but their differences must still be focused on practicality and purpose,
 *   like Swords to Axes to Bows to Magic, etc.
 *   
 *   Sword, Dagger, Great Sword, Rapier
 * 
 * Alt:
 * PHY    : Phys. Strength & Acrobatics                   : Heavy Weapons, Acrobatics, Strength Feats; Exhaustive Things
 * TCH    : Technical Skill, Electrical & Mechanical      : Guns, Manadrives, Other Technology-Focused Actions
 * INT    : Intellect, High-Concept Abilities             : Math, Logic and Reason; Clever Abilities; Also Academia Abilities (Medicine)
 * DEX    : Fine Motor Skill, Accuracy & Sleight of Hand  : Accuracy, Trickery, Complex Movement; Ex. Counterattack/Parry would rely on DEX
 * DRV/ENG: Machine Operative Skills, Vehicles            :   ...? I think Tinkery covers it. Vehicles, though...
 *   TNK    : Tinker Skill                                  : I like Tinker a lot, but operating your manadrive isn't always about rewiring it, I suppose.
 *   I want to use ENG (Engineering) for vehicles, etc., but I don't want ignorantly discredit the academic school of engineering.
 *   I'm talking about literally engineering a train, but academically it has more to do with inventive design and creative problem solving.
 *   DRV does a shittier job communicating the skills necessary to operate an airliner, but I like how direct it is.
 *   What does DRV mean? It means Drive. What is Drive for? It's for driving tanks and shit.
 *   Maybe I could be sly? ENG stands for Engine, not necessarily Engineering. This adds an extra communication step, but it should
 *   bring to mind "operating an engine." I would hope, anyway.
 *   
 *   If you're going to operate really complex machinery, like an Aircraft Carrier (which actually isn't done by one person),
 *   you need to know how to fix the damn thing, too. That requires some serious know-how.
 *   That's what Machinery, or Engineering is supposed to cover. "Practical" kind of hilariously doesn't cut it.
 *   
 *   In battle, soldiers are:
 *   Strengthing their way through something (running, lifting, enduring, etc.)
 *   Smarting their way through something (coming up with plans, solving problems, calculating trajectories for big guns, etc.)
 *   Fixing things (repairing armor/equipment, unjamming guns, soldering metal bits back together, etc.)
 *   Communicating (giving or receiving orders, providing feedback, being clear/concise [not strictly relevant to my game])
 *   Operating something (vehicles, large guns, their small guns technically; their tools in general, really)
 *   Maintaining Form (CQC combat, staying in cover, staying in line (think 300 soldier wall), 
 *   
 * 
 * Removed:
 * AGI    : Speed/Evasion (a unit's natural defense)
 * 
 * As a style choice, I want certainty. The game should feel more like AW than FE in battle.
 * If AGI were included, it would have grown steadily with a unit, raising their
 * natural defense over time, mitigating the need for armor.
 * 
 * TODO
 * As another style choice, I used short ints a whole bunch down there. The type mix-match between ints
 * occurs a couple bits of times forcing me to cast and such. I think I was doing it to save memory?
 * PHY isn't a value that ever needs to be greater than 5, so there's a lot of unused space in an int.
 * Change this, maybe? I don't really know what to think, I'm too tired.  
 */

package battle.entities.unit;

public class UnitStats extends UnitData {

  static final int METER_STAT_CAP = 10;
  static final int MAX_LEVEL = 5;
  static final int XP_CAP = 100;
  static final int MOV_MAX_MOD = 2;
  
  // This is silly. I'm too distracted and unable to focus to come up with anything better though.
  // This is used in getStatIndex to identify which stat is in which index.
  static final StatID[] STAT_IDS = {StatID.PHY, StatID.TCH, StatID.INT, StatID.DEX, StatID.MCH};
  
  // Stat vars.
  short maxHP, maxAP, MOV;        // 3 Quality stats.  (Removed AGI)
  int[] levels;                   // 5 Qualifier stats.
  int[] meters;                   // 5 Qualifier stat XP meters.
  short MOVmodifier = 0;          // Used to affect MOV since it doesn't grow.
  //short PhysDmgModifier = 0;
  //short ManaDmgModifier = 0;
  
  
  public UnitStats(Unit parent) {
    super(parent);
  }
  
  public UnitStats(Unit parent, String data) {
    super(parent, data);
  }
  
  protected void init() {
    levels = new int[STAT_IDS.length];
    meters = new int[STAT_IDS.length];
  }
  
  public void raiseHP() {
    if (maxHP < METER_STAT_CAP)
      maxHP++;
  }
  
  public void raiseAP() {
    if (maxAP < METER_STAT_CAP)
      maxAP++;
  }
  
  public void setMOVmodifier(int mod) {
    if (mod > 2) mod = 2;
    if (mod < -2) mod = -2;
    
    MOVmodifier = (short)mod;
  }
  
  public void addXP(StatID id, short xp) {
    short idx = getStatIndex(id);
    
    if (levels[idx] < MAX_LEVEL)
      meters[idx] += xp;
    
    if (meters[idx] >= XP_CAP) {
      levels[idx]++;
      meters[idx] -= XP_CAP;
      // Set level up flag for animation later?
      // Or send a message to set a flag somewhere, actually?
    }
  }
  
  /* This class uses an array to keep track of the five academic stats (because they function
   * similarly). This class takes a StatID value and figures out which index it refers to.
   */
  public short getStatIndex(StatID id) {
    short idx = -1;
    for (short i = 0; i < STAT_IDS.length; i++) {
      if (STAT_IDS[i] == id) {
        idx = i;
        break;
      }
    }
    
    return idx;
  }
  
  public short getMaxHP() {
    return maxHP;
  }
  
  public short getMaxAP() {
    return maxAP;
  }
  
  public short getMOV() {
    return MOV;
  }
  
  public int getStat(StatID id) {
    short idx = getStatIndex(id);
    return levels[idx];
  }
  
  public String save() {
    String result = "";
    // TODO Write data to string.
    return result;
  }
  
  public void load(String data) {
    // TODO Load data from string.
  }
  
  
  public void generateFalseData() {
    maxHP = 4;  // Random -1,+1
    maxAP = 4;  // Random -1,+1
    
    for (int i = 0; i < levels.length; i++) {
      levels[i] = 1;
      meters[i] = 0;
    }
    
    MOV = 3;  // Not randomized, but affected by equipment.
  }
  
}
