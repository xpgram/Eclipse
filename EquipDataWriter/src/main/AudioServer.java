package main;

import java.util.ArrayList;

public class AudioServer {

  private static ArrayList<int[]> sfxList;
  private static int bgm;
  private static int bgmQueue;
  
  public static final int MAX_SFX = 100;      // IDs loop around this number: 0 -> 100 -> 0 -> 100 -> 0 -> ...
  
  public static int play(String sfx) {
    int id = 0;
    return id;
  }
  
  public static void stop(int id) { }
  
  public static void playMusic() { }
  public static void pauseMusic() { }
  public static void queueMusic() { }
  public static void transitionMusic() { }   // Swap main and queued music.
}
