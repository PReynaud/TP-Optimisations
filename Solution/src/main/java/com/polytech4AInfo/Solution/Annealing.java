package com.polytech4AInfo.Solution;

import org.apache.commons.math.util.FastMath;
//import org.apache.log4j.Logger;

//import com.polytech4AInfo.ProgramMain.ProgramMain;

/**
 * Created by benoitvuillemin on 10/04/2015.
 * RECUIT SIMULE
 */
public class Annealing {
    //private static Logger logger = Logger.getLogger(ProgramMain.class);

    private static int LIMIT = 100;
    private static int LIMITTEMP = 100;
    /**
     * Will count the number of iterations of our algorithm
     */
    private int counter = 0;
    private int counterTemp = 0;

    public Annealing() {
    }

    /**
     * Execute the algorithm
     *
     * @param s
     */
    public Solution simulatedAnnealing(Solution s, double temperature) {
        double startTime = System.currentTimeMillis();
        try {
            s.calculCost();
        } catch (Exception e) {
            System.out.println("Error for the cost of the initial solution");
        }
        Solution currentSolution = s.clone();
        Solution bestSolution = s.clone();

        double deltaCost;
        double p;
        while (counter < LIMIT) {
            while (counterTemp < LIMITTEMP) {
                System.out.println("Number of iteration: " + counter);
                Solution oneSolution = Neighbour.findNeighbour(currentSolution);
                System.out.println("Current solution: " + currentSolution.toString());
                System.out.println("Tested neighbour: " + oneSolution.toString());
                try {
                    deltaCost = currentSolution.getCost() - oneSolution.calculCost();
                    System.out.println("Cost of neighbour: " + oneSolution.getCost());
                    if (deltaCost <= 0) {
                        currentSolution = oneSolution;
                        if (currentSolution.getCost() < bestSolution.getCost()) {
                            bestSolution = currentSolution;
                        }
                    } else {
                        p = Math.random();
                        System.out.println("p: " + p);
                        System.out.println("Exp: " + FastMath.exp(-deltaCost / temperature));
                        if (p < FastMath.exp(-deltaCost / temperature)) {
                            currentSolution = oneSolution;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid cost");
                }
                counterTemp++;
            }
            counter++;
            counterTemp = 0;
            temperature = calculNewTemperature(temperature);
            System.out.println("Temperature: " + temperature);
        }
        System.out.println("-------------------------------");
        System.out.println("Best Solution: " + bestSolution);
        double stopTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (stopTime - startTime));
        return bestSolution;
    }



    /**
     * Calcule the new temperature. It used a geometric progression
     *
     * @param oldTemperature The old temperature
     * @return The new temperature we'll used
     */
    private double calculNewTemperature(double oldTemperature) {
        return 0.99 * oldTemperature;
    }

}
