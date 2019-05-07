package main;

public enum TargetType implements ICyclerEnum {
  None ("--"),
  Enemy ("Foe"),
  Ally ("Ally"),
  Self ("Self"),
  Any ("Any"),
  AnyButSelf ("NotS"),
  Location ("Loc"),
  EmptyLocation ("ELoc"),
  Passive ("Pasv");

  private final String value;
  
  TargetType(String value) {
    this.value = value;
  }
  
  @Override
  public ICyclerEnum[] getList() { return values(); }

  @Override
  public String displayString() { return this.value; }

  @Override
  public String displayHeader() { return "Targ"; }

  @Override
  public ICyclerEnum next() { return Utils.next(this); }

  @Override
  public ICyclerEnum prev() { return Utils.previous(this); }
}
