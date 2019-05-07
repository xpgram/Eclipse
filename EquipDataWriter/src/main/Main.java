/*
 * This program is essentially done. There's only a few things I may or may not want to do.
 * [ ] Accessory-type equips auto set range-style to a new type: None or ---
 * [ ] Group select mode is useable when in Browsing mode. (This disables a few features until group select mode is left)
 * [ ] Remove Traits from the equips list; they aren't fully expressable here: "Strength" raises POW by 1, but only for strength-based weapons, of which there are more than two, so the combo system won't help me.
 * 
 * [ ] Expand Letter-Code-List even more: W, Weapons / M, ManaCast / A, Accessories / T, Techniques / X, Enemy/Monster / P, E/M Properties
 * [ ] Add Tab: "Properties": a stylized attribute that has no duration effect nor an intensity, a quality simply intrinsic to the equipment.
 * [ ] The above line also includes across-the-board properties, such as Genus (Str., 1H Gun, 2H Gun, Mech, Mana, etc.) and Melee/Projectile toggle
 * [ ] Add column: Weight Class, Wt., or Class: Primary, Secondary and 2-Handed; or P, S, 2H. 
 *       I could eliminate the Mov column by classifying "Weight" as Hv., Md., Lt., 2-H
 *       Md. covers your default swords and such. No movement penalty.
 *       Lt. covers your sidearms. Easy to add to any equipment setup. Adds movement, if anything (probs nothing).
 *       Hv. covers your heavier-but-still-middle-class weapons, such that would infer a movement penalty of exactly 1 less.
 *       2-H covers your heavy weapons, the ones which take up 2 slots on the equipment screen. Also infers 1 less movement.
 *     Even using this weight-class system, I still may never actually use Hv. 2-H is kind of enough. But, it does allow me some wiggle room
 *     with penalties if I come up with something heavy-but-not-that-heavy.
 *     
 *     Lt. weapons do not add movement, but a trait-effect might.
 *     "Light Runner" or something could add +1 movement when--and only when--the soldier is equipped with exclusively Secondary-class items, or just no items.
 *     This is a bonus because Md. would "lower" their movement whereas it would not do that for anyone else.
 *     
 *     Oh, that gives me an idea for how to use Hv.
 *     Accessories can have weights, too, especially because they can also affect movement.
 *     Hv armor should be Hv.
 *     I just don't know how to manage the equip screen, in that case.
 *     Is there a "Primary" accessory slot? Are there even variable accessory slots? Like in number.
 *     
 *     Maybe it's like this:
 *       P  Primary Slot
 *       S  Secondary Slot        2-H Weapons take up two slots, at least one of them must be a Primary slot.
 *       A  Armor Slot
 *       T  Tool/Trinket Slot
 *       
 *       P/S Slots vary according to these configurations:
 *         P
 *         P S
 *         P S S
 *         P P S
 *       With the last one being the most rare.
 *       
 *       A/T Slots vary according to these configurations:
 *         A
 *         A T
 *         A T T
 *       Armor can only be equipped in A slots, and trinkets can be equipped in any slot.
 *       
 *       Also, just because I'm inspired:
 *         Chestplate     Slot A  2 DEF
 *         Atlan Armor    Slot A        5 BAR
 *         Heavy Armor    Slot A  4 DEF
 *         Drive Armor    Slot A  2 DEF 1 BAR
 *         Barrier Drive  Slot T        1 BAR       This is already kind of rare/expensive, but as a small-carry device, it allows you to up your mana resistance without taking up an armor slot. Or I suppose you could double-down with Atlan Armor.
 *         Hyper Drive    Slot T        2 BAR
 *         
 *         Cutting Knife  Slot S  1 POW 12 AP
 *         Handgun        Slot S  1 POW 1-2 Rng 6 AP
 *         Grenade        Slot S  2 POW 2-2 Rng 2 Rad 2 AP
 */

package main;


public class Main {
  
  public static String version = "0.1";
  public static String filename = "assets/EquipmentData.dat";
  public static String bckfilename = "assets/EquipmentData_bck.dat";
  
  public static ProgramRedux program = new ProgramRedux();
  public static InputBar inputbar = new InputBar();
  public static DataBook databook = new DataBook();
  
  public static void main(String[] args) {
    
    initialize();
    Processing.run(args);
  }
  
  public static void initialize() {
    databook.loadData(filename);
  }
}
