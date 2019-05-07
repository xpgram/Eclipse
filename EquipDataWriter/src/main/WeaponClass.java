package main;

public enum WeaponClass implements ICyclerEnum {
  None ("--"),
  Strength ("Str"),
  Speed ("Spd"),
  Defense ("Def"),
  Mechanical ("Mech"),
  Mana ("Mana");        // TODO I need extensions of the word 'mana', like 'mananical' or '
  
  private final String value;
  
  WeaponClass(String value) {
    this.value = value;
  }

  @Override
  public ICyclerEnum[] getList() { return values(); }

  @Override
  public String displayString() { return this.value; }

  @Override
  public String displayHeader() { return "Class"; }

  @Override
  public ICyclerEnum next() { return Utils.next(this); }

  @Override
  public ICyclerEnum prev() { return Utils.previous(this); }
  
}
