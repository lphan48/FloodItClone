package main;

import java.util.ArrayList;

import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import java.awt.Color;

// Represents any kind of cell
public abstract class ACell {
    // Coordinates of the center of this cell
    int x;
    int y;
    public Color color;
    // Whether or not the cell has been flooded
    public boolean flooded;
    // Whether or not the cell is floodable (neighboring the flood,
    // or neighboring a same-colored floodable cell)
    public boolean floodable;
    // The four neighbors of this
    ACell left;
    public ACell top;
    public ACell right;
    public ACell bottom;

    // EFFECT: Changes the right cell of this to the given
    public abstract void changeRight(ACell that);

    // EFFECT: Changes the bottom cell of this to the given
    public abstract void changeBottom(ACell that);

    // Visualizes this cell
    public WorldImage drawCell() {
        return new RectangleImage(20, 20, "solid", this.color);
    }

    // Makes this cell floodable if it's neighboring a flooded cell,
    // or the same color as a floodable neighbor
    public void makeFloodable(ArrayList<ACell> board) {
        if (((this.top.flooded || this.bottom.flooded || this.left.flooded
                || this.right.flooded) && !this.flooded)
                || ((this.top.floodable && this.top.color == this.color)
                        || (this.bottom.floodable && this.bottom.color == this.color)
                        || (this.right.floodable && this.right.color == this.color)
                        || (this.left.floodable && this.left.color == this.color))) {
            this.flooded = false;
            this.floodable = true;
        }
    }

    // Makes this cell flooded if it's the right color and floodable
    public void makeFlooded(Color floodColor) {
        if (this.floodable && this.color == floodColor) {
            this.flooded = true;
            this.floodable = false;
        }
    }

    // EFFECT: Changes the color of this cell to the given
    public abstract void changeColor(Color floodColor);
}
