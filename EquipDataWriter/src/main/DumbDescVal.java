package main;

public class DumbDescVal extends DescVal implements DataField {

  @Override
  public String displayHeader() {
    return "Dumb Description";
  }
  
  @Override
  public FieldType getType() {
    return DataField.FieldType.DumbDescription;
  }
  
}
