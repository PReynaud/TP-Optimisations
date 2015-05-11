package com.polytech4AInfo.Solution;

import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;

/**
 * Created by benoitvuillemin on 10/04/2015.
 * RECUIT SIMULE
 */
public class Annealing {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

    /**
     * Will count the number of iterations of our algorithm
     */
    private int counter = 0;
    private int counterTemp = 0;


    private StringWriter stringWriter;
    private BufferedWriter bufferedWriter;

    public Annealing() {
        if(ProgramMain.RECORD_STATS.equals("true")){
            stringWriter  = new StringWriter();
            bufferedWriter = new BufferedWriter(stringWriter);
            try {
                bufferedWriter.write("Progress;Temperature;Current Cost;Current number of patterns;Best Value");
                bufferedWriter.newLine();
            } catch (IOException e) {
                logger.error("Error catching statistics for CSV file");
            }
        }
    }

    /**
     * Execute the algorithm
     *
     * @param s
     */
    public Solution simulatedAnnealing(Solution s, double temperature) {
        double startTime = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            s.calculCost();
            Solution currentSolution = s.clone();
            Solution bestSolution = s.clone();
            Solution oneSolution = s.clone();
            double deltaCost;
            double p;
            while (counter < ProgramMain.LIMIT) {
                double percentage = (double) counter / (double) ProgramMain.LIMIT * 100;
                logger.info("Progress: " + df.format(percentage));

                if(ProgramMain.RECORD_STATS.equals("true")){
                    logger.debug("Best cost: " + bestSolution.getCost()
                            + "\n Temperature: " + temperature
                            + "\n Nb Patterns: " + currentSolution.getPatterns().length
                            + "\n Current cost: " + currentSolution.getCost());

                    recordStatisticsInCSVFile(percentage, temperature, currentSolution.getCost(), currentSolution.getPatterns().length, bestSolution.getCost());
                }

                while (counterTemp < ProgramMain.LIMITTEMP) {
                    oneSolution = Neighbour.findNeighbour(currentSolution);
                    logger.debug("Current solution: " + currentSolution.toString());
                    logger.debug("Tested neighbour: " + oneSolution.toString());
                    try {
                        deltaCost = oneSolution.getCost()- currentSolution.getCost();
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
                        logger.error("Invalid cost" + currentSolution.toString());
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
            logger.info("Final temperature: " + temperature);
            double stopTime = System.currentTimeMillis();
            logger.info("Execution time: " + (stopTime - startTime));

            if(ProgramMain.RECORD_STATS.equals("true")){
                generateCSVFile();
            }

            return bestSolution;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * Calcule the new temperature. It used a geometric progression
     *
     * @param oldTemperature The old temperature
     * @return The new temperature we'll used
     */
    private double calculNewTemperature(double oldTemperature) {
        //return 0.99 * oldTemperature;
        double mu;
        double deltaF = 50.0;
        double pFinal = 1.0/1000000.0;
        mu = Math.exp(Math.log(deltaF/(Math.log(1.0/pFinal)* ProgramMain.TEMPERATURE))/ProgramMain.LIMIT);
        return mu * oldTemperature;
    }

    private void recordStatisticsInCSVFile(double percentage, double temperature, int currentCost, int currentPatterns, int bestValue){
        try {
            bufferedWriter.write(percentage + ";" + temperature + ";" + currentCost + ";" + currentPatterns + ";" + bestValue);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.error("Error catching statistics for CSV file");
        }
    }

    private void generateCSVFile(){
        try {
            FileWriter writer = new FileWriter(ProgramMain.PATH_TO_LOG_FILES + "stats.csv");
            writer.append(stringWriter.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Error writing CSV file");
        }
    }

}
