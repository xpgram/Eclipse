package main;

public enum WeightClass implements ICyclerEnum {
  None ("--"),
  Medium ("Md"),
  Heavy ("Hv"),
  TwoHanded ("2-H"),
  Light ("Lt");

  private final String value;
  
  WeightClass(String value) {
    this.value = value;
  }
  
  @Override
  public ICyclerEnum[] getList() { return values(); }

  @Override
  public String displayString() { return this.value; }

  @Override
  public String displayHeader() { return "Wt."; }
  
  @Override
  public ICyclerEnum next() { return Utils.next(this); }

  @Override
  public ICyclerEnum prev() { return Utils.previous(this); }
}
