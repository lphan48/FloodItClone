package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;

// Represents a game of flood it
public class FloodItWorld extends World {
    // All the cells of the game
    public ArrayList<ACell> board;
    // Number of guesses the user has made
    public int guesses;
    // Number of guesses the user is allowed
    public int maxGuesses;
    // Length of one side of the board
    public int boardSize;
    // Number of colors in the game
    int colorAmount;
    // Whether or not the board is currently flooding
    public boolean flooding;
    // Index of the block currently being displayed
    public int floodCount;
    // Color of the current flood
    public Color floodColor;
    // Whether or not the game is in play
    boolean gameOn;
    // List of current flooded cells in waterfall order
    public ArrayList<ACell> worklist;
    // All of the possible colors
    public ArrayList<Color> gameColors = new ArrayList<Color>();

    // Constructor for use in real, randomized games
    public FloodItWorld(int boardSize, int colorAmount) {
        // Checks that the input is allowed
        if (boardSize > 26 || boardSize < 2) {
            throw new IllegalArgumentException("Board size must be between 2 and "
                    + "26 cells (inclusive)");
        } else if (colorAmount > 8 || colorAmount < 3) {
            throw new IllegalArgumentException("Must have between 3 "
                    + "and 8 colors (inclusive)");
        } else {
            // Creates a list of colors of size colorAmount by
            // removing from the list allColors
            ArrayList<Color> allColors = new ArrayList<Color>(Arrays.asList(
                    Color.red, Color.blue, Color.green, Color.pink, Color.black,
                    Color.orange, Color.cyan, Color.magenta));
            ArrayList<Color> colors = new ArrayList<Color>();
            for (int idx = 0; idx < colorAmount; idx++) {
                colors.add(allColors.get(idx));
            }
            this.gameColors = colors;
        }
        // Initializes all other default fields
        this.boardSize = boardSize;
        this.colorAmount = colorAmount;
        this.floodCount = 0;
        this.guesses = 0;
        this.gameOn = true;
        this.flooding = false;
        this.worklist = new ArrayList<ACell>();
        this.board = new ArrayList<ACell>();
        // Sets maximum guesses based on boardSize and colorAmount
        if (this.boardSize < 13) {
            this.maxGuesses = this.boardSize + this.colorAmount;
        } else if (this.colorAmount < 5 && this.boardSize >= 13) {
            this.maxGuesses = this.boardSize + this.colorAmount * 2;
        } else if (this.colorAmount >= 5 && this.boardSize >= 13) {
            this.maxGuesses = this.boardSize * 2 + this.colorAmount * 2;
        }
        // Creates list of cells of boardSize squared
        for (int m = 0; m < this.boardSize * this.boardSize; m = m + 1) {
            Color randCol = gameColors.get(new Random().nextInt(colorAmount));
            // Creates the first cell, in the top left corner
            if (m == 0) {
                this.board.add(new Cell(randCol, 10, 10));
                this.floodColor = randCol;
                this.worklist.add(this.board.get(0));
            }
            // Creates the remaining cells across the top row
            else if (m < this.boardSize) {
                this.board.add(new Cell(this.board.get(m - 1), randCol, (m * 20) + 10, 10, true));
            } else {
                // Creates the cells in the left column
                if (m % this.boardSize == 0) {
                    this.board.add(new Cell(this.board.get(m - this.boardSize), randCol,
                            10, (m / this.boardSize * 20) + 10, false));
                }
                // Creates the cells in the right column
                else if ((m + 1) % this.boardSize == 0) {
                    this.board.add(new Cell(this.board.get(m - 1),
                            this.board.get(m - this.boardSize), randCol, ((m % this.boardSize) * 20 + 10),
                            (m / this.boardSize) * 20 + 10));
                }
                // Creates all the remaining middle cells
                else {
                    this.board.add(new Cell(this.board.get(m - 1),
                            this.board.get(m - this.boardSize), randCol, (m % this.boardSize) * 20 + 10,
                            ((m + 1) / this.boardSize) * 20 + 10));
                }
            }
        }
        // Makes cells floodable if they're neighboring a flooded cell,
        // or neighboring a same-colored floodable cell
        for (int n = 0; n < this.board.size(); n = n + 1) {
            for (ACell d : this.board) {
                d.makeFloodable(this.board);
            }
        }
        // Flood all cells that are adjacent to cells previously flooded
        for (int m = 0; m < this.board.size(); m = m + 1) {
            for (ACell b : this.board) {
                b.makeFlooded(this.floodColor);
            }
        }
        // Makes cells floodable if they're neighboring a flooded cell,
        // or neighboring a same-colored floodable cell
        for (int n = 0; n < this.board.size(); n = n + 1) {
            for (ACell d : this.board) {
                d.makeFloodable(this.board);
            }
        }
    }

    // Constructor for use in testing, where the generation of colors
    // will follow the order in the allColors list
    public FloodItWorld(int boardSize, int colorAmount, int colorIndex) {
        // Checks that the input is allowed
        if (boardSize > 26 || boardSize < 2) {
            throw new IllegalArgumentException("Board size must be between 2 and "
                    + "26 cells (inclusive)");
        } else if (colorAmount > 8 || colorAmount < 3) {
            throw new IllegalArgumentException("Must have between 3 "
                    + "and 8 colors (inclusive)");
        } else {
            // Creates a list of colors of size colorAmount by
            // removing from the list allColors
            ArrayList<Color> allColors = new ArrayList<Color>(Arrays.asList(
                    Color.red, Color.blue, Color.green, Color.pink, Color.black,
                    Color.orange, Color.cyan, Color.magenta));
            ArrayList<Color> colors = new ArrayList<Color>();
            for (int idx = 0; idx < colorAmount; idx++) {
                colors.add(allColors.get(idx));
            }
            this.gameColors = colors;
        }
        // Initializes all other default fields
        this.boardSize = boardSize;
        this.colorAmount = colorAmount;
        this.floodCount = 0;
        this.guesses = 0;
        this.gameOn = true;
        this.flooding = false;
        this.worklist = new ArrayList<ACell>();
        this.board = new ArrayList<ACell>();
        // Sets maximum guesses based on boardSize and colorAmount
        if (this.boardSize < 13) {
            this.maxGuesses = this.boardSize + this.colorAmount;
        } else if (this.colorAmount < 5 && this.boardSize >= 13) {
            this.maxGuesses = this.boardSize + this.colorAmount * 2;
        } else if (this.colorAmount >= 5 && this.boardSize >= 13) {
            this.maxGuesses = this.boardSize * 2 + this.colorAmount * 2;
        }
        // Creates list of cells of boardSize squared
        for (int m = 0; m < this.boardSize * this.boardSize; m = m + 1) {
            // Creates top left cell and resets colorIndex if needed
            if (m == 0) {
                this.board.add(new Cell(gameColors.get(colorIndex), 10, 10));
                this.floodColor = gameColors.get(colorIndex);
                colorIndex = 1;
                this.worklist.add(this.board.get(0));
            }
            // Creates top row cells and resets colorIndex if needed
            else if (m < this.boardSize && colorIndex < colorAmount - 1) {
                this.board.add(new Cell(this.board.get(m - 1),
                        gameColors.get(colorIndex), (m * 20) + 10, 10, true));
                colorIndex = colorIndex + 1;
            } else if (m < this.boardSize) {
                this.board.add(new Cell(this.board.get(m - 1),
                        gameColors.get(colorIndex), (m * 20) + 10, 10, true));
                colorIndex = 0;
            } else {
                // Creates left column cells and resets colorIndex if needed
                if (m % this.boardSize == 0 && colorIndex != colorAmount - 1) {
                    this.board.add(new Cell(this.board.get(m - this.boardSize),
                            gameColors.get(colorIndex), 10,
                            (m / this.boardSize) * 20 + 10, false));
                    colorIndex = colorIndex + 1;
                } else if (m % this.boardSize == 0) {
                    this.board.add(new Cell(this.board.get(m - this.boardSize),
                            gameColors.get(colorIndex), 10,
                            (m / this.boardSize) * 20 + 10, false));
                    colorIndex = 0;
                }
                // Creates right column cells and resets colorIndex if needed
                else if ((m + 1) % this.boardSize == 0 && colorIndex != colorAmount - 1) {
                    this.board.add(new Cell(this.board.get(m - 1),
                            this.board.get(m - this.boardSize), gameColors.get(colorIndex),
                            (m % this.boardSize) * 20 + 10, (m / this.boardSize) * 20 + 10));
                    colorIndex = colorIndex + 1;
                } else if ((m + 1) % this.boardSize == 0) {
                    this.board.add(new Cell(this.board.get(m - 1),
                            this.board.get(m - this.boardSize), gameColors.get(colorIndex),
                            (m % this.boardSize) * 20 + 10, (m / this.boardSize) * 20 + 10));
                    colorIndex = 0;
                }
                // Creates remaining cells and resets colorIndex if needed
                else {
                    if (colorIndex < colorAmount - 1) {
                        this.board.add(new Cell(this.board.get(m - 1),
                                this.board.get(m - this.boardSize), gameColors.get(colorIndex),
                                (m % this.boardSize) * 20 + 10, ((m + 1) / this.boardSize) * 20 + 10));
                        colorIndex = colorIndex + 1;
                    } else {
                        this.board.add(new Cell(this.board.get(m - 1),
                                this.board.get(m - this.boardSize), gameColors.get(colorIndex),
                                (m % this.boardSize) * 20 + 10, ((m + 1) / this.boardSize) * 20 + 10));
                        colorIndex = 0;
                    }
                }
            }
            // Makes cells floodable if they're neighboring a flooded cell,
            // or neighboring a same-colored floodable cell
            for (int n = 0; n < this.board.size(); n = n + 1) {
                for (ACell d : this.board) {
                    d.makeFloodable(this.board);
                }
            }
            // Flood all cells that are adjacent to cells previously flooded
            for (int y = 0; y < this.board.size(); y = y + 1) {
                for (ACell b : this.board) {
                    b.makeFlooded(this.floodColor);
                }
            }
            // Makes cells floodable if they're neighboring a flooded cell,
            // or neighboring a same-colored floodable cell
            for (int n = 0; n < this.board.size(); n = n + 1) {
                for (ACell d : this.board) {
                    d.makeFloodable(this.board);
                }
            }
        }
    }

    // Visualizes the current scene
    public WorldScene makeScene() {
        // Determines scene length based on boardSize
        int sceneLength = this.boardSize * 20 + 80;
        WorldScene scene = new WorldScene(600, sceneLength);
        // Displays the score
        scene.placeImageXY(new TextImage("Click cells. "
                + "Fill the board with a single color.", 17, Color.black),
                410, sceneLength - 30);
        scene.placeImageXY(new TextImage(this.guesses + "/" + this.maxGuesses,
                24, Color.black), 550, sceneLength - 60);
        // Uses a boolean to track if the user just won
        int amountFlooded = 0;
        for (ACell cell : this.board) {
            if (cell.color == this.floodColor) {
                amountFlooded = amountFlooded + 1;
            }
        }
        // Displays winning scene
        if (amountFlooded == this.board.size()) {
            scene.placeImageXY(new TextImage("You won! Congrats :)",
                    30, Color.black), 300,
                    sceneLength / 2);
            this.gameOn = false;
            return scene;
        }

        // Displays losing scene
        else if (this.guesses == this.maxGuesses) {
            scene.placeImageXY(new TextImage("You lose! Sorry :(",
                    30, Color.black), 300,
                    sceneLength / 2);
            this.gameOn = false;
            return scene;
        } else {
            // For every column
            for (int c = 0; c < this.boardSize; c++) {
                // For every row
                for (int r = 0; r < this.boardSize; r++) {
                    scene.placeImageXY(this.board.get(c + this.boardSize * r).drawCell(),
                            this.board.get(c + this.boardSize * r).x,
                            this.board.get(c + this.boardSize * r).y);
                }
            }
            return scene;
        }
    }

    // Handles mouse clicks if the board isn't flooding
    public void onMouseClicked(Posn posn) {
        if (!flooding && gameOn) {
            for (ACell cell : this.board) {
                // If the coordinates match this cell and it isn't
                // already flooded
                if ((!cell.flooded)
                        && (posn.x >= cell.x - 10 && posn.x <= cell.x + 10)
                        && (posn.y >= cell.y - 10 && posn.y <= cell.y + 10)) {
                    // This cell's color is the new floodColor
                    this.floodColor = cell.color;
                    // Flood all cells that are adjacent to cells previously flooded
                    for (int m = 0; m < this.board.size(); m = m + 1) {
                        for (ACell b : this.board) {
                            b.makeFlooded(this.floodColor);
                        }
                    }
                    // Makes cells floodable if they're neighboring a flooded cell,
                    // or neighboring a same-colored floodable cell
                    for (int n = 0; n < this.board.size(); n = n + 1) {
                        for (ACell d : this.board) {
                            d.makeFloodable(this.board);
                        }
                    }
                    // Update flooding and guess count
                    this.flooding = true;
                    this.floodCount = 0;
                    this.guesses = guesses + 1;
                }
            }
        }
    }

    // Handles the passing of time
    public void onTick() {
        if (flooding) {
            // If all the cells have been flooded, stop flooding
            // and reset the count to 1
            if (floodCount == this.worklist.size()) {
                this.floodCount = 0;
                this.flooding = false;
            } else {
                // Current cell being visualized
                ACell currentCell = this.worklist.get(floodCount);
                // Change its color if it's flooded
                if (currentCell.flooded) {
                    currentCell.changeColor(this.floodColor);
                }
                // Add its bottom and right neighbors if they
                // arent already in the list
                if (!this.worklist.contains(currentCell.bottom)) {
                    this.worklist.add(currentCell.bottom);
                }
                if (!this.worklist.contains(currentCell.right)) {
                    this.worklist.add(currentCell.right);
                }
                this.floodCount = this.floodCount + 1;
            }
        }
    }
}
