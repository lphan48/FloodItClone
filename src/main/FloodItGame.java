package main;

public class FloodItGame {
    public static void main(String[] args) throws Exception {
        FloodItWorld starterWorld = new FloodItWorld(26, 8);
        starterWorld.bigBang(600, 600, 0.001);
    }
}
