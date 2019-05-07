package main;

public class CycleVal<T extends ICyclerEnum> implements DataField {

  private ICyclerEnum value;
  private T enumHandle;
  
  protected CycleVal(T handle) {
    enumHandle = handle;
    reset();
  }
  
  @Override
  public void reset() {
    this.value = enumHandle.getList()[0];
  }

  @Override
  public void submit(String str) {
    if (valid(str))
      this.value = ICyclerEnum.Utils.valueOf(enumHandle.getList(), str);
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    ICyclerEnum e = ICyclerEnum.Utils.valueOf(enumHandle.getList(), str);
    if (e != null)
      r = true;
    
    return r;
  }

  @Override
  public String value() {
    return this.value.toString();
  }

  @Override
  public void tap() { }

  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
  }

  @Override
  public void increment() {
    this.value = this.value.prev();
  }

  @Override
  public void decrement() {
    this.value = this.value.next();
  }

  @Override
  public void incrementAlt() { }

  @Override
  public void decrementAlt() { }

  @Override
  public String displayString() {
    return this.value.displayString();
  }

  @Override
  public String displayHeader() {
    return this.value.displayHeader();
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Value_Chunk;
  }

  @Override
  public String contextEnter() {
    return "None";
  }

  @Override
  public String contextDel() {
    return "Reset";
  }

  @Override
  public String contextArrows() {
    return "Cycle";
  }

  @Override
  public boolean editable() {
    return false;
  }

  @Override
  public boolean strictlyNumerical() {
    return false;
  }

  @Override
  public FieldType getType() {
    return FieldType.CycleList;
  }

}
