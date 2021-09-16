/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;
import java.io.IOException;
import java.io.FileWriter;


import java.util.Random;

public class RandomWalk {

    private int x = 0;
    private int y = 0;
    private int[] xCoordinate = {1, 0, -1, 0};
    private int[] yCoordinate = {0, 1, 0, -1};
    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        while ((m --) != 0) {
            move(xCoordinate[random.nextInt(4)], yCoordinate[random.nextInt(4)]);
        }
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("E:\\proj\\random_walk.csv");
            writer.write("n,d\n");
            Random rand = new Random();
            for (int step = 1; step < 6000; step += 10) {
                double meanDistance = (new RandomWalk()).randomWalkMulti(step, 60);
                writer.write(step + "," + meanDistance + "\n");
                System.out.println(step + "," + meanDistance);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
