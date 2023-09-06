package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javalib.impworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import main.ACell;
import main.Cell;
import main.OutsideCell;
import tester.Tester;
import main.FloodItWorld;

// Represents example flood its
class ExamplesFloods {
    ACell redCell;
    ACell blueCell;
    ACell greenCell;
    ACell yellowCell;
    ACell outsideCell;
    ArrayList<ACell> examplesCells;
    FloodItWorld base;

    // Resets all cells as having no playable neighbors
    void initCells() {
        redCell = new Cell(Color.red, 10, 10);
        blueCell = new Cell(Color.blue, 0, 0);
        greenCell = new Cell(Color.green, 0, 0);
        yellowCell = new Cell(Color.yellow, 0, 0);
        outsideCell = new OutsideCell();
        examplesCells = new ArrayList<ACell>(Arrays.asList(redCell,
                blueCell, greenCell, yellowCell));
        // A small game for testing with predicted colors
        base = new FloodItWorld(2, 4, 0);
    }

    // Tests the method that modifies this cell's right neighbor
    void testChangeRight(Tester t) {
        this.initCells();
        t.checkExpect(this.redCell.right, new OutsideCell());
        redCell.changeRight(blueCell);
        t.checkExpect(redCell.right, blueCell);
        t.checkExpect(this.greenCell.right, new OutsideCell());
        greenCell.changeRight(yellowCell);
        t.checkExpect(greenCell.right, yellowCell);
        t.checkExpect(this.outsideCell.right, new OutsideCell());
        outsideCell.changeRight(yellowCell);
        t.checkExpect(outsideCell.right, outsideCell);
    }

    // Tests the method that modifies this cell's right neighbor
    void testChangeBottom(Tester t) {
        this.initCells();
        t.checkExpect(this.redCell.bottom, new OutsideCell());
        redCell.changeBottom(blueCell);
        t.checkExpect(redCell.bottom, blueCell);
        t.checkExpect(this.greenCell.bottom, new OutsideCell());
        greenCell.changeBottom(yellowCell);
        t.checkExpect(greenCell.bottom, yellowCell);
        t.checkExpect(this.outsideCell.right, new OutsideCell());
        outsideCell.changeBottom(yellowCell);
        t.checkExpect(outsideCell.right, outsideCell);
    }

    // Tests the method that visualizes a cell
    void testDrawCell(Tester t) {
        this.initCells();
        t.checkExpect(outsideCell.drawCell(),
                new RectangleImage(20, 20, "solid", Color.white));
        t.checkExpect(blueCell.drawCell(),
                new RectangleImage(20, 20, "solid", Color.blue));
        t.checkExpect(yellowCell.drawCell(),
                new RectangleImage(20, 20, "solid", Color.yellow));
        t.checkExpect(outsideCell.drawCell(),
                new RectangleImage(20, 20, "solid", Color.white));
    }

    // Tests the method that makes a cell floodable
    // if it neighbors a flooded cell or a
    // same-colored floodable neighbor
    void testMakeFloodable(Tester t) {
        this.initCells();
        // Checks that the red cell is not currently floodable
        t.checkExpect(this.redCell.floodable, false);
        // Give red cell a neighbor blue cell that is flooded
        blueCell.flooded = true;
        redCell.changeRight(blueCell);
        redCell.makeFloodable(examplesCells);
        // Check that blue cell isn't floodable, but red is
        t.checkExpect(this.redCell.flooded, true);
        t.checkExpect(this.blueCell.floodable, false);
        // Checks that outside cell is not floodable
        t.checkExpect(outsideCell.floodable, false);
        // Checks that the green cell is not currently floodable
        t.checkExpect(this.redCell.floodable, false);
        // Give green cell a neighbor cell that is floodable
        ACell greenCellTwo = new Cell(Color.green, 10, 10);
        greenCellTwo.floodable = true;
        greenCell.makeFloodable(examplesCells);
        // Check that green cell is now floodable
        t.checkExpect(greenCell.floodable, false);
        t.checkExpect(greenCellTwo.floodable, true);
    }

    // Tests the method that makes a cell flooded
    // if it's floodable and the same color as the given
    void testMakeFlooded(Tester t) {
        this.initCells();
        // Checks that red cell will be flooded if the color is red
        // and its flooded
        redCell.floodable = true;
        redCell.makeFlooded(Color.red);
        t.checkExpect(redCell.flooded, true);
        this.initCells();
        // Checks that outside cell is not flooded regardless of color
        outsideCell.makeFlooded(Color.red);
        t.checkExpect(outsideCell.flooded, false);
        outsideCell.makeFlooded(Color.pink);
        t.checkExpect(outsideCell.flooded, false);
    }

    // Tests the method that changes a cell's color to the given
    void testChangeColor(Tester t) {
        this.initCells();
        // Checks that redCell is changed from red to pink
        t.checkExpect(redCell.color, Color.red);
        redCell.changeColor(Color.pink);
        t.checkExpect(redCell.color, Color.pink);
        // Checks that outsideCell doesn't change color
        outsideCell.changeColor(Color.pink);
        t.checkExpect(outsideCell.color, Color.white);
        outsideCell.changeColor(Color.cyan);
        t.checkExpect(outsideCell.color, Color.white);
    }

    // Tests the list of colors generated by the constructor
    void testColors(Tester t) {
        // Tests invalid inputs for colorAmount
        t.checkConstructorException(new IllegalArgumentException("Must "
                + "have between 3 and 8 colors (inclusive)"), "FloodItWorld",
                10, 2);
        t.checkConstructorException(new IllegalArgumentException("Must "
                + "have between 3 and 8 colors (inclusive)"), "FloodItWorld",
                10, 9);
        t.checkConstructorException(new IllegalArgumentException("Must "
                + "have between 3 and 8 colors (inclusive)"), "FloodItWorld",
                10, 0);
        // Tests the colors when the user inputs 4
        FloodItWorld testOne = new FloodItWorld(10, 4, 0);
        t.checkExpect(testOne.gameColors.size(), 4);
        t.checkExpect(testOne.gameColors.contains(Color.green), true);
        t.checkExpect(testOne.gameColors.contains(Color.black), false);
        t.checkExpect(testOne.gameColors.contains(Color.pink), true);
        t.checkExpect(testOne.board.size(), 100);
        // Tests the colors when the user inputs 8
        FloodItWorld testTwo = new FloodItWorld(10, 8, 0);
        t.checkExpect(testTwo.gameColors.size(), 8);
        t.checkExpect(testTwo.gameColors.contains(Color.red), true);
        t.checkExpect(testTwo.gameColors.contains(Color.cyan), true);
        t.checkExpect(testTwo.gameColors.contains(Color.magenta), true);
        t.checkExpect(testTwo.board.size(), 100);
        t.checkExpect(testTwo.board.get(20).top == testTwo.board.get(20 - testTwo.boardSize), true);
    }

    // Tests the list of cells generated by the constructor
    void testBoard(Tester t) {
        // Tests invalid inputs for colorAmount
        t.checkConstructorException(new IllegalArgumentException("Board size must be "
                + "between 2 and 26 cells (inclusive)"), "FloodItWorld", 28, 2);
        t.checkConstructorException(new IllegalArgumentException("Board size must be "
                + "between 2 and 26 cells (inclusive)"), "FloodItWorld",
                35, 2);
        // Tests the valid boards
        FloodItWorld testOne = new FloodItWorld(10, 4);
        t.checkExpect(testOne.board.size(), 100);
        FloodItWorld testTwo = new FloodItWorld(26, 4);
        t.checkExpect(testTwo.board.size(), 676);
    }

    // Tests the field maxGuesses initialized by the constructor
    void testConstructorFields(Tester t) {
        FloodItWorld testOne = new FloodItWorld(10, 3);
        t.checkExpect(testOne.maxGuesses, 13);
        FloodItWorld testTwo = new FloodItWorld(14, 5);
        t.checkExpect(testTwo.maxGuesses, 38);
    }

    // Tests the method that visualizes the world scene
    void testMakeScene(Tester t) {
        this.initCells();
        // Make the game lose
        base.maxGuesses = 1;
        base.onMouseClicked(new Posn(30, 10));
        base.onTick();
        // Check that the losing screen is displayed
        WorldScene losingScene = new WorldScene(600, 120);
        losingScene.placeImageXY(new TextImage("Click cells. Fill the board with a "
                + "single color.", 17, Color.black), 410, 90);
        losingScene.placeImageXY(new TextImage("1/1", 24, Color.black), 550, 60);
        losingScene.placeImageXY(new TextImage("You lose! Sorry :(",
                30, Color.black), 300, 60);
        t.checkExpect(base.makeScene(), losingScene);
        // Make it so that the user just won
        this.initCells();
        for (ACell cell : base.board) {
            cell.changeColor(Color.blue);
        }
        base.floodColor = Color.blue;
        // Check that the winning screen is displayed
        WorldScene winning = new WorldScene(600, 120);
        winning.placeImageXY(new TextImage("Click cells. Fill the board with a "
                + "single color.", 17, Color.black), 410, 90);
        winning.placeImageXY(new TextImage("0/6", 24, Color.black), 550, 60);
        winning.placeImageXY(new TextImage("You won! Congrats :)", 30, Color.black), 300, 60);
        t.checkExpect(base.makeScene(), winning);
        // Tests the screen if the game is still in play
        this.initCells();
        WorldScene baseScene = new WorldScene(600, 120);
        baseScene.placeImageXY(new TextImage("Click cells. Fill the board with a "
                + "single color.", 17, Color.black), 410, 90);
        baseScene.placeImageXY(new TextImage("0/6", 24, Color.black), 550, 60);
        baseScene.placeImageXY(new RectangleImage(20, 20, "solid", Color.red), 10, 10);
        baseScene.placeImageXY(new RectangleImage(20, 20, "solid", Color.green), 10, 30);
        baseScene.placeImageXY(new RectangleImage(20, 20, "solid", Color.blue), 30, 10);
        baseScene.placeImageXY(new RectangleImage(20, 20, "solid", Color.pink), 30, 30);
        t.checkExpect(base.makeScene(), baseScene);
    }

    // Tests the method that handles mouse clicks
    void testOnMouseClicked(Tester t) {
        this.initCells();
        // Check that the top left cell is currently flooded,
        // its neighbor is floodable, and all fields are at default
        t.checkExpect(base.floodColor, Color.red);
        t.checkExpect(base.guesses, 0);
        t.checkExpect(base.board.get(0).flooded, true);
        t.checkExpect(base.board.get(1).floodable, true);
        t.checkExpect(base.flooding, false);
        // Check that after clicking the top right (blue) cell,
        // it's now flooded, its neighbor is floodable,
        // and the necessary fields are updated
        base.onMouseClicked(new Posn(30, 10));
        t.checkExpect(base.board.get(1).flooded, true);
        t.checkExpect(base.board.get(3).floodable, true);
        t.checkExpect(base.floodColor, Color.blue);
        t.checkExpect(base.guesses, 1);
        t.checkExpect(base.flooding, true);
        // Check that clicking outside the range does nothing
        base.onMouseClicked(new Posn(100, 100));
        t.checkExpect(base.board.get(1).flooded, true);
        t.checkExpect(base.board.get(3).floodable, true);
        t.checkExpect(base.floodColor, Color.blue);
        t.checkExpect(base.guesses, 1);
        t.checkExpect(base.flooding, true);
        // Check that clicking an already flooded cell does nothing
        base.onMouseClicked(new Posn(10, 10));
        t.checkExpect(base.board.get(0).flooded, true);
        t.checkExpect(base.board.get(2).flooded, false);
        t.checkExpect(base.floodColor, Color.blue);
        t.checkExpect(base.guesses, 1);
        t.checkExpect(base.flooding, true);
    }

    // Tests the method that handles the passing of time
    // by changing the colors of cells in waterfall order
    void testOnTick(Tester t) {
        this.initCells();
        // Check that there's only one flooded cell in worklist (top left),
        // its color is red, and floodCount is 0
        t.checkExpect(base.worklist.size(), 1);
        t.checkExpect(base.worklist.get(0).color, Color.red);
        t.checkExpect(base.floodCount, 0);
        // Click on the top right (blue) cell
        base.onMouseClicked(new Posn(30, 10));
        // Check that onTick changes color of all flooded cells,
        // adds the neighbors of cell at index floodCount,
        // and increases the floodCount by one
        base.onTick();
        t.checkExpect(base.board.get(0).color, Color.blue);
        t.checkExpect(base.board.get(1).color, Color.blue);
        t.checkExpect(base.board.get(2).color, Color.green);
        t.checkExpect(base.board.get(3).color, Color.pink);
        t.checkExpect(base.worklist.size(), 3);
        t.checkExpect(base.floodCount, 1);
        // Check that onTick continues to add cell's neighbors
        // to worklist in waterfall order
        base.onTick();
        t.checkExpect(base.worklist.size(), 5);
        t.checkExpect(base.floodCount, 2);
        // Check that onTick continues to add cell's neighbors
        // to worklist in waterfall order
        base.onTick();
        t.checkExpect(base.worklist.size(), 6);
        t.checkExpect(base.floodCount, 3);
        // Check that if the game is not flooding, nothing happens
        base.flooding = false;
        t.checkExpect(base.worklist.size(), 6);
        t.checkExpect(base.floodCount, 3);
    }
}
