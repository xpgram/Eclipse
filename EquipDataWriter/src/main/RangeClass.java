package main;

public enum RangeClass implements ICyclerEnum {
  None ("--"),
  Limited ("Lim"),
  Projectile ("Shot"),
  Missile ("L.Shot"),   // Not a rocket, just a big projectile. Slower, too, but typically more powerful. Depends on the application, I guess.
  Area ("Area");

  private final String value;
  
  RangeClass(String value) {
    this.value = value;
  }
  
  @Override
  public ICyclerEnum[] getList() { return values(); }

  @Override
  public String displayString() { return this.value; }

  @Override
  public String displayHeader() { return "Reach"; }

  @Override
  public ICyclerEnum next() { return Utils.next(this); }

  @Override
  public ICyclerEnum prev() { return Utils.previous(this); }
}