/*
 * 
 */

package utils;

public class GridPoint {
  public int x;
  public int y;
  
  
  public GridPoint() {
    setCoords(0,0);
  }
  
  
  public GridPoint(int x, int y) {
    setCoords(x,y);
  }
  
  
  public GridPoint(GridPoint p) {
    setCoords(p.x, p.y);
  }
  
  
  public GridPoint clone(GridPoint p) {
    setCoords(p.x, p.y);
    return this;
  }
  
  
  public GridPoint move(int x, int y) {
    GridPoint p = new GridPoint(this.x + x, this.y + y);
    return p;
  }
  
  
  public GridPoint move(GridPoint p) {
    return move(p.x, p.y);
  }
  
  
  public void setCoords(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  
  public int taxiCabDistance(GridPoint p) {
    return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
  }
  
  
  public boolean equals(GridPoint p) {
    return (this.x == p.x && this.y == p.y);
  }
  
  
  public GridPoint add(GridPoint p) {
    this.x += p.x;
    this.y += p.y;
    return this;
  }
  
  // This doesn't really support division in its current state.
  public GridPoint scale(int n) {
    this.x *= n;
    this.y *= n;
    return this;
  }
  
  
  public String toString() {
    return ("(" + this.x + "," + this.y + ")");
  }
  
  public static GridPoint unitVectorUp() {
    return new GridPoint(0, 1);
  }
  
  public static GridPoint unitVectorDown() {
    return new GridPoint(0, -1);
  }
  
  public static GridPoint unitVectorLeft() {
    return new GridPoint(-1, 0);
  }
  
  public static GridPoint unitVectorRight() {
    return new GridPoint(1, 0);
  }
}