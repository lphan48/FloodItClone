package main;

import java.awt.Color;

// Represents a playable cell of the game area
public class Cell extends ACell {

  // Creates the first cell, in the top left corner
  public Cell(Color color, int x, int y) {
    this.x = x;
    this.y = y;
    this.flooded = true;
    this.color = color;
    this.floodable = false;
    this.left = new OutsideCell();
    this.top = new OutsideCell();
    this.right = new OutsideCell();
    this.bottom = new OutsideCell();
  }

  // Creates a cell in the top row or left column depending on the boolean
  public Cell(ACell otherCell, Color color, int x, int y, boolean top) {
    this.x = x;
    this.y = y;
    this.flooded = false;
    this.floodable = false;
    this.color = color;
    this.right = new OutsideCell();
    this.bottom = new OutsideCell();
    if (top) {
      this.top = new OutsideCell();
      this.left = otherCell;
      this.left.changeRight(this);
    } else {
      this.top = otherCell;
      this.top.changeBottom(this);
      this.left = new OutsideCell();
    }
  }

  // Creates a cell in the middle of the board
  public Cell(ACell left, ACell top, Color color, int x, int y) {
    this.left = left;
    this.top = top;
    this.x = x;
    this.y = y;
    this.flooded = false;
    this.floodable = false;
    this.top.changeBottom(this);
    this.left.changeRight(this);
    this.right = new OutsideCell();
    this.bottom = new OutsideCell();
    this.color = color;
  }

  // EFFECT: Changes the right cell of this to the given
  public void changeRight(ACell that) {
    this.right = that;
  }

  // EFFECT: Changes the botton cell of this to the given
  public void changeBottom(ACell that) {
    this.bottom = that;
  }

  // EFFECT: Changes the color of this cell to the given
  public void changeColor(Color floodColor) {
    this.color = floodColor;
  }
}
