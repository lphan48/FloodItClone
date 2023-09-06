package main;

import java.awt.Color;

// Represents a holder for the neighbor of an edge cell
public class OutsideCell extends ACell {
  public OutsideCell() {
    this.x = 0;
    this.y = 0;
    flooded = false;
    floodable = false;
    this.color = Color.white;
    this.left = this;
    this.top = this;
    this.right = this;
    this.bottom = this;
  }

  // EFFECT: Changes the right cell of this to the given
  public void changeRight(ACell that) {
    this.right = new OutsideCell();
  }

  // EFFECT: Changes the bottom cell of this to the given
  public void changeBottom(ACell that) {
    this.bottom = new OutsideCell();
  }

  // EFFECT: Changes the color of this cell to the given
  public void changeColor(Color floodColor) {
    this.color = Color.white;
  }
}
