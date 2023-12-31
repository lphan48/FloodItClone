<h3 align="center">Flood It Clone</h3>
Completed Fall 2022

## About The Project

This project mimics the Flood It game, where users have limited chances to click on cells and fill a square grid with one color. Although this is a replica, both the design and implementation are uniquely developed.

### Dependencies

To run the game, you must have a JRE that supports class file version 61.0 or higher.

### Installation

1. Clone the repo:
   ```sh
   git clone https://github.com/lphan48/FloodItClone
   ```
2. Navigate to project directory:
   ```sh
   cd FloodItClone
   ```
3. Run the game by executing the main JAR file, located in the project's root directory:
   ```sh
   java -jar FloodItClone.jar
   ```

## Usage
### The project begins with a randomly generated board of cells:

<img width="300" alt="Screenshot 2023-09-06 at 7 52 41 PM" src="https://github.com/lphan48/FloodItClone/assets/116211528/ea2a0644-5216-469e-ad2e-473fa326f327">




### Starting from the top leftmost cell, clicking any cell will flood the board to that color (the following image is several turns ahead from the beginning):

<img width="300" alt="Screenshot 2023-09-06 at 7 53 43 PM" src="https://github.com/lphan48/FloodItClone/assets/116211528/8bddeea4-dd74-421b-ae1c-9c8bc9c8a91a">




### The game ends when either the grid is entirely one color, or the user exceeds the amount of turns indicated in the bottom right corner:

<img width="300" alt="Screenshot 2023-09-06 at 7 55 42 PM" src="https://github.com/lphan48/FloodItClone/assets/116211528/9c19d917-8dda-4575-a63c-332d89b35534">



