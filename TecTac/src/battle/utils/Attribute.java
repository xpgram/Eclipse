/*
 * There are so many variations because all the different kinds of types of things are located here.
 * This can't be the way to go.
 * 
 * I may want to use context interpretation.
 * "Fire" would mean different things depending on where it's located. For instance:
 * 
 * Fire - As a member of ActionData     : Causes Fire Damage: +2 to fire weak target.
 * Fire - As a member of PassiveData    : Adds Protection Against Fire: -5 from enemy's fire attack.
 * Fire - As a member of UnitCondition  : Causes Weakness to Fire
 * 
 */

package battle.utils;

public enum Attribute {
  None,
  
  // Weapon Effects
  IgnoreDEF,
  MagicDamage,
  
  // Armor Effects
  
  
  // Elements
  Fire,
  Volt,
  Frost,
  Water,
  Juice,
  
  // Status Effects
  Poison,
  Crippled,
  Scanned
}
