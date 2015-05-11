package com.polytech4AInfo.Solution;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;

/**
 * Created by Pierre on 08/05/2015.
 */
public class Genetic {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

    private double percentageOfMutation = 0.1;

    /**
     * Will count the number of iterations of our algorithm
     */
    private int counter = 0;

    private StringWriter stringWriter;
    private BufferedWriter bufferedWriter;

    public Genetic() {
        if(ProgramMain.RECORD_STATS.equals("true")){
            stringWriter  = new StringWriter();
            bufferedWriter = new BufferedWriter(stringWriter);
            try {
                bufferedWriter.write("Generation;Minimum Value;Maximum Value;Average Value;Best Value");
                bufferedWriter.newLine();
            } catch (IOException e) {
                logger.error("Error catching statistics for CSV file");
            }
        }
    }

    /**
     * Execute the algorithm
     */
    public Solution simulatedGenetic(ArrayList<Solution> population) {
        percentageOfMutation = ProgramMain.PERCENTAGE_OF_APPLY_MUTATION;
        double startTime = System.currentTimeMillis();
        int counter = 0;

        /* Look for the first best solution */
        Solution bestSolution = population.stream()
                .min(Comparator.comparing(p -> p.getCost()))
                .get();


        /*Go through the generations*/
        for (int j = 0; j < ProgramMain.GENERATION; j++) {
            logger.info("Current generation: " + j + ", Best cost: " + bestSolution.getCost() + ", Patterns: " + bestSolution.getSolutionArray().length);
            counter++;
            if (counter % 100 == 0) {
                percentageOfMutation += ProgramMain.PERCENTAGE_OF_APPLY_MUTATION;
            }

            /* Selection */
            ArrayList<Solution> selectedPopulation = rouletteWheelSelection(population);
            population = selectedPopulation;
            for (int i = 0; i < population.size(); i++) {
                population.set(i, selectedPopulation.get(i));
            }

            /* Inject best solution into population*/
            injectBestSolutionIntoPopulation(population, bestSolution);

            /* Crossover and mutations */
            applyCrossoverBetweenPatterns(population);
            for (int i = 0; i < population.size(); i++) {
                Solution newSolution = applyCrossoverBetweenShapes(population.get(i));
                population.set(i, newSolution);
            }

            applyMutations(population, percentageOfMutation);

            /*Selection of the best solution for the current generation*/
            Solution bestCurrentSolution = population.stream()
                    .min(Comparator.comparing(p -> p.getCost()))
                    .get();

            /* Record stats */
            if(ProgramMain.RECORD_STATS.equals("true")){
                IntSummaryStatistics stats = population.stream()
                        .mapToInt(p -> p.getCost())
                        .summaryStatistics();
                recordStatisticsInCSVFile(j, stats.getMin(), stats.getMax(), stats.getAverage(), bestCurrentSolution.getCost());
            }

            logger.debug("Best current solution: " + bestCurrentSolution.toString());
            if (bestCurrentSolution.getCost() < bestSolution.getCost()) {
                bestSolution = bestCurrentSolution.clone();
                percentageOfMutation = ProgramMain.PERCENTAGE_OF_APPLY_MUTATION;
                counter = 0;
            }
        }

        logger.info("-----------------------------------------");
        logger.info("Best solution: " + bestSolution.toString());
        double stopTime = System.currentTimeMillis();
        logger.info("Execution time: " + (stopTime - startTime));

        if(ProgramMain.RECORD_STATS.equals("true")){
            generateCSVFile();
        }

        return bestSolution;
    }

    /**
     * Select a new population, the solutions with the best cost have a higher probability to be selected
     *
     * @param population
     * @return
     */
    private ArrayList<Solution> rouletteWheelSelection(ArrayList<Solution> population) {
        ArrayList<Solution> selectedPopulation = new ArrayList<>();
        double randomValue;
        double sumOfFitness;
        double sumOfAllFitness = 0;
        double probabilityToBeSelected;

        population.sort(new SolutionComparator());

        for (int i = 0; i < population.size(); i++) {
            sumOfAllFitness += population.get(i).getCost();
        }

        for (double i = 0; i <= population.size(); i++) {
            sumOfFitness = 0;
            randomValue = Math.random();
            for (Solution individual : population) {
                probabilityToBeSelected = (individual.getCost() / sumOfAllFitness);
                probabilityToBeSelected = (1 - probabilityToBeSelected) / (population.size() - 1);
                sumOfFitness += probabilityToBeSelected;
                if (sumOfFitness > randomValue) {
                    selectedPopulation.add(individual);
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    /**
     * Do a crossover between two patterns from two solutions
     *
     * @param population
     */
    private void applyCrossoverBetweenPatterns(ArrayList<Solution> population) {
        for (int i = 0; i < population.size() - 1; i += 2) {
            int randomNumberForCrossover = (int) (Math.random() * 100);
            if (randomNumberForCrossover < ProgramMain.PERCENTAGE_OF_APPLY_CROSSOVER) {
                Solution firstSolution = population.get(i);
                Solution secondSolution = population.get(i + 1);

                Solution firstSon = firstSolution.clone();
                Solution secondSon = secondSolution.clone();

                int randomNumberForFirstCrossover = (int) (Math.random() * (firstSolution.getPatterns().length));
                int randomNumberForSecondCrossover = (int) (Math.random() * (firstSolution.getPatterns().length));

                Pattern buffer;

                if (randomNumberForFirstCrossover < secondSolution.getPatterns().length && randomNumberForSecondCrossover < secondSolution.getPatterns().length) {
                    if (randomNumberForFirstCrossover < randomNumberForSecondCrossover) {
                        for (int j = randomNumberForFirstCrossover; j < randomNumberForSecondCrossover; j++) {
                            buffer = firstSon.getPatterns()[j];
                            firstSon.getPatterns()[j] = secondSolution.getPatterns()[j].clone();
                            secondSon.getPatterns()[j] = buffer.clone();
                        }
                    } else {
                        for (int j = randomNumberForSecondCrossover; j < randomNumberForFirstCrossover; j++) {
                            buffer = firstSon.getPatterns()[j];
                            firstSon.getPatterns()[j] = secondSolution.getPatterns()[j].clone();
                            secondSon.getPatterns()[j] = buffer.clone();
                        }
                    }
                    firstSolution.transformPatternArrayInSolutionArray();
                    secondSolution.transformPatternArrayInSolutionArray();

                    population.set(i, Neighbour.findNeighbour(firstSolution));
                    population.set(i + 1, Neighbour.findNeighbour(secondSolution));
                }
            }
        }
    }

    /**
     * Do a crossover between the shapes from two pattern on the same solution
     *
     * @param initialSolution
     */
    private Solution applyCrossoverBetweenShapes(Solution initialSolution) {
        int randomNumberForCrossover = (int) (Math.random() * 100);
        if (randomNumberForCrossover < ProgramMain.PERCENTAGE_OF_APPLY_CROSSOVER) {
            for (int i = 0; i < initialSolution.getPatterns().length; i++) {
                int randomNumberForFirstPattern = (int) (Math.random() * initialSolution.getPatterns().length);
                int randomNumberForSecondPattern = (int) (Math.random() * initialSolution.getPatterns().length);

                int randomNumberForFirstCrossover = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));
                int randomNumberForSecondCrossover = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));

                int buffer1;
                int buffer2;
                if (randomNumberForFirstCrossover < randomNumberForSecondCrossover) {
                    for (int j = randomNumberForFirstCrossover; j < randomNumberForSecondCrossover; j++) {
                        buffer1 = initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).getNumber();
                        buffer2 = initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).getNumber();
                        initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).setNumber(buffer2);
                        initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).setNumber(buffer1);
                    }
                } else {
                    for (int j = randomNumberForSecondCrossover; j < randomNumberForFirstCrossover; j++) {
                        buffer1 = initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).getNumber();
                        buffer2 = initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).getNumber();
                        initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).setNumber(buffer2);
                        initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).setNumber(buffer1);
                    }
                }
            }
            initialSolution.transformPatternArrayInSolutionArray();
            initialSolution = Neighbour.findNeighbour(initialSolution);
        }
        return initialSolution;
    }

    /**
     * Call the find neighbour function that will apply a random mutation and send a valid solution
     *
     * @param population
     */
    private void applyMutations(ArrayList<Solution> population, double percentageOfMutation) {
        int randomNumberForMutation = (int) (Math.random() * 100);
        if (randomNumberForMutation < percentageOfMutation) {
            int randomNumberForSolution = (int) (Math.random() * (population.size()));
            population.set(randomNumberForSolution, Neighbour.findNeighbour(population.get(randomNumberForSolution)));
        }
    }


    private void injectBestSolutionIntoPopulation(ArrayList<Solution> population, Solution bestSolution) {
        int randomNumberForPosition = (int) (Math.random() * population.size());
        population.set(randomNumberForPosition, bestSolution);
    }

    private void recordStatisticsInCSVFile(int generation, int min, int max, double avg, int bestValue){
        try {
            bufferedWriter.write(generation + ";" + min + ";" + max + ";" + avg + ";" + bestValue);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.error("Error catching statistics for CSV file");
        }
    }

    private void generateCSVFile(){
        try {
            FileWriter writer = new FileWriter(ProgramMain.PATH_TO_LOG_FILES + "stats.csv");
            bufferedWriter.close();
            writer.append(stringWriter.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Error writing CSV file");
        }
    }
}


class SolutionComparator implements Comparator<Solution> {
    @Override
    public int compare(Solution o1, Solution o2) {
        return o1.getCost() - o2.getCost();
    }
}
