package com.polytech4AInfo.Solution;

import org.apache.commons.math.util.FastMath;
import org.apache.log4j.Logger;

/**
 * Created by benoitvuillemin on 10/04/2015.
 * RECUIT SIMULE
 */
public class Annealing {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

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
            logger.error("Error for the cost of the initial solution");
        }
        Solution currentSolution = s.clone();
        Solution bestSolution = s.clone();

        double deltaCost;
        double p;
        while (counter < LIMIT) {
            logger.info("Percentage of iteration: " + (int)((double)counter/(double)LIMIT * 100));
            while (counterTemp < LIMITTEMP) {
                Solution oneSolution = Neighbour.findNeighbour(currentSolution);
                logger.debug("Current solution: " + currentSolution.toString());
                logger.debug("Tested neighbour: " + oneSolution.toString());
                try {
                    deltaCost = currentSolution.getCost() - oneSolution.calculCost();
                    logger.debug("Cost of tested neighbour: " + oneSolution.getCost());
                    if (deltaCost <= 0) {
                        currentSolution = oneSolution;
                        if (currentSolution.getCost() < bestSolution.getCost()) {
                            bestSolution = currentSolution;
                        }
                    } else {
                        p = Math.random();
                        if (p < FastMath.exp(-deltaCost / temperature)) {
                            currentSolution = oneSolution;
                        }
                    }
                } catch (Exception e) {
                    logger.error("Invalid cost");
                }
                counterTemp++;
            }
            counter++;
            counterTemp = 0;
            temperature = calculNewTemperature(temperature);
            logger.debug("Temperature: " + temperature);
        }
        logger.info("-------------------------------");
        logger.info("Best Solution: " + bestSolution);
        double stopTime = System.currentTimeMillis();
        logger.info("Execution time: " + (stopTime - startTime));
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
