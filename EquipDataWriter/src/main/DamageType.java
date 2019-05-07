package main;

public enum DamageType implements ICyclerEnum {
  None ("--"),
  Physical ("Phys"),
  Mannial ("Mana"),     // Meaning "of mana". I don't like it. 'Manial', with one n, could be pronounced 'may-nial', which fuck dat boi.
  Explosive ("Expl.");

  private final String value;
  
  DamageType(String value) {
    this.value = value;
  }
  
  @Override
  public ICyclerEnum[] getList() { return values(); }

  @Override
  public String displayString() { return this.value; }

  @Override
  public String displayHeader() { return "Dmg"; }

  @Override
  public ICyclerEnum next() { return Utils.next(this); }

  @Override
  public ICyclerEnum prev() { return Utils.previous(this); }
  
}