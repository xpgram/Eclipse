/*
 * None of this is implemented yet.
 * The concept for this design hinges on eliminating statistical barriers.
 * "Rare" drops are always acquirable, there are just extra steps to securing it.
 * 
 * Every unit will have a maximum of two possible item drops, and two associated methods
 * for acquiring those drops. This might simply be killing the unit, or using "Steal"
 * on it, or not lobbing a grenade against the unit (and thus destroying the item).
 * 
 * Chance and Chance2 work the same as Always, but are used for more common enemies.
 * A big guy that's hard to take down could easily drop their item every time just to
 * reward the player for the achievement, but lower-tier enemies that spawn eight at
 * a time might use Chance2 to mitigate the sheer number of items the player will be
 * picking up, adding additional value (and saving memory/grid space) to the item
 * in question.
 */

package battle.entities.unit;

public class UnitVictoryCache extends UnitData {
  /*
  ItemID itemA;
  ItemID itemB;
  DropType typeA;
  DropType typeB;
  */
  
  // DropTriggerType
  //   - Always   (100%)
  //   - Chance   (50%)
  //   - Chance2  (25%)
  //   - Specific Attack(s) Must Kill
  //   - Specific Attack(s) Must Not Be Used
  //   - Specific Attack(S) Must Be Used
  
  
  public UnitVictoryCache(Unit parent) {
    super(parent);
  }
  
  public UnitVictoryCache(Unit parent, String data) {
    super(parent, data);
  }
  
  @Override
  void init() {
    // TODO Auto-generated method stub
  }

  @Override
  void load(String data) {
    // TODO Auto-generated method stub
  }
  
  @Override
  String save() {
    // TODO Auto-generated method stub
    return "";
  }

  @Override
  void generateFalseData() {
    // TODO Auto-generated method stub
    
  }

  
  
}
