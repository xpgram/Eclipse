package main;

public enum Attribute {
  None,
  
  // Attack Types (Qualifiers for "accuracy" checks)
  Projectile,   // Bows, guns; can hit fliers
  Missile,      // Rockets, harpoons; can hit slow-moving fliers
  
  // Damage Types
  ManaDAM,        // This maybe isn't important: W.Type has the type "Mana," which ought to describe spells and special physical weapons accurately.
  ExplosiveDAM,
  
  //Elements
  Fire,
  Volt,
  Frost,
  Water,
  
  // Weapon Effects
  IgnoreDEF,
  
  // Armor Effects
  FireWard,
  VoltWard,
  FrostWard,
  
  // Ailment Effects
  ArmorBreak,
  BarrierBreak,
  Enfeeble,       // Weakens physical power
  Disrupt,        // Weakens/halts mannial power
  Ground,         // Disallows "Flying" bonus on targets
  Poison,         // Steady damage per turn
  Cripple,        // Prevents use of Primary Weapon       (Or just either one of your equips)
  Stun,           // Prevents act during the next turn. (Should never have a duration longer than 1)
  Drench,         // Wards fire damage, but increases vulnerability to volt and ice.
  Oil,            // Wards volt damage, but increases vulnerability to fire.
  Slow,           // Movement range is limited by 1 (and ???)
  Scan,           // Reveals precious information
  
  // Beneficial Status Effect
  POW,
  DEF,
  BAR,
  Reenervate,             // This gives the unit (a spent unit) an extra turn; it reactivates them.
  
  // Properties
  Flying,
  Massive
}