package main;

public class DisplaySettings {

  public static final int Screen_Width = 1200;
  public static final int Screen_Height = 800;
  
  public static final int Scale_Factor = 4;     // TODO This should derive from width/height and is used to determine Cell_Size and other things.
  
  public static final String Typeface_32 = "TecTacP3-48.vlw";
  public static final int Text_Size = 24;
  public static final int Text_Size_Map = 16;
  
  
  public static final int Pixel_Size = 3;
  public static final int Border = 2 * Pixel_Size;
  public static final int Text_Height = 7 * Pixel_Size;
  public static final int Text_Width = 4 * Pixel_Size;
  public static final int Newline = Text_Height + Pixel_Size;
  public static final int Map_Newline = 5 * Pixel_Size;
  public static final int Small_Indent = Text_Width*8;
  public static final int Big_Indent = Text_Width*15;
  
  // Chunk or Field Sizes (Width of Input Box)
  public static final int Std_Chunk = Text_Width*6;
  public static final int Name_Chunk = Text_Width*22;
  public static final int Small_Chunk = Text_Width*5;
  public static final int Value_Chunk = Text_Width*8;
  public static final int Attr_Chunk = Text_Width*16;
  public static final int Desc_Chunk = Text_Width*70;
  public static final int Combo_Chunk = Text_Width*14;
  public static final int XP_Chunk = Text_Width*7;
  public static final int MapCell_Chunk = Text_Width*2 + Pixel_Size*3;
  public static final int Map_Chunk = MapCell_Chunk*11;
  
  // CoreStats View Columns
  public static final int Col_EID = Border;
  public static final int Col_Name = Col_EID + Std_Chunk;
  public static final int Col_AP = Col_Name + Name_Chunk;
  public static final int Col_POW = Col_AP + Small_Chunk;
  public static final int Col_DEF = Col_POW + Small_Chunk;
  public static final int Col_BAR = Col_DEF + Small_Chunk;
  public static final int Col_Mov = Col_BAR + Small_Chunk;
  public static final int Col_Range = Col_Mov + Small_Chunk;
  public static final int Col_Radius = Col_Range + Std_Chunk;
  public static final int Col_Targ = Col_Radius + Small_Chunk;
  public static final int Col_Value = Col_Targ + Std_Chunk;
  public static final int Col_AttrCS = Col_Value + Value_Chunk;
  
  // Attribute View Columns
  public static final int Col_Attr1 = Col_Name + Name_Chunk;
  public static final int Col_Attr2 = Col_Attr1 + Attr_Chunk;
  public static final int Col_Attr3 = Col_Attr2 + Attr_Chunk;
  public static final int Col_Attr4 = Col_Attr3 + Attr_Chunk;
  public static final int Col_Attr5 = Col_Attr4 + Attr_Chunk;
  
  // Requirements View Columns
  public static final int Col_Req1 = Col_Name + Name_Chunk;
  public static final int Col_Req2 = Col_Req1 + Std_Chunk;
  public static final int Col_XP1 = Col_Req2 + Std_Chunk;
  public static final int Col_XP2 = Col_XP1 + XP_Chunk;
  public static final int Col_Combo1 = Col_XP2 + XP_Chunk;
  public static final int Col_Combo2 = Col_Combo1 + Combo_Chunk;
  public static final int Col_WType = Col_Combo2 + Combo_Chunk;
  
  // Description View Column
  public static final int Col_Description = Col_Name + Name_Chunk;
  
  // AoEMap View Column
  public static final int Col_BlastMap = Col_Name + Name_Chunk;
  public static final int Col_RangeMap = Col_BlastMap + Map_Chunk + Text_Width;
  public static final int Col_RadiusVal = Col_BlastMap + Big_Indent;
  
  // General View Columns
  public static final int Read_Line_1 = Newline*6 + Border;
  public static final int Read_Line_2 = Read_Line_1 - Newline;
  public static final int Read_Line_3 = Read_Line_2 - Newline;
  public static final int Read_Line_4 = Read_Line_3 - Newline;
  public static final int Read_Col_1 = Border;
  public static final int Read_Col_2 = Read_Col_1 + Big_Indent;
  public static final int Read_Col_3 = Read_Col_2 + Big_Indent;
  public static final int Read_Col_4 = Read_Col_3 + Big_Indent;
  public static final int Read_Col_5 = Read_Col_4 + Big_Indent;
  public static final int Read_Col_6 = Read_Col_5 + Big_Indent;
  public static final int Read_Col_7 = Read_Col_6 + Big_Indent;
  
  public static final int DataBookLine = Border;
  public static final int EditWindowLine = Border + Newline*21;
}
