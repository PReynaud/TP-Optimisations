package com.polytech4AInfo.Solution;

import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Pierre on 08/05/2015.
 */
public class Genetic {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

    /**
     * Will count the number of iterations of our algorithm
     */
    private int counter = 0;

    public Genetic() {
    }

    /**
     * Execute the algorithm
     */
    public Solution simulatedGenetic(ArrayList<Solution> initialPopulation) {
        double startTime = System.currentTimeMillis();
        Solution bestSolution = initialPopulation.get(0).clone();
        ArrayList<Solution> population = initialPopulation;

        /*Go through the generations*/
        for(int j=0; j < ProgramMain.GENERATION; j++){
            logger.info("Current generation: " + j + ", Best cost: " + bestSolution.getCost() + ", Patterns: " + bestSolution.getSolutionArray().length);
            /* Selection */
            ArrayList<Solution> selectedPopulation = rouletteWheelSelection(population);

            population = selectedPopulation;

            for(int i = 0; i < population.size(); i++){
                population.set(i, selectedPopulation.get(i));
            }

            /* Crossover and mutations */
            applyCrossoverBetweenPatterns(population);
            for(int i = 0; i < population.size(); i++){
                Solution newSolution = applyCrossoverBetweenShapes(population.get(i));
                population.set(i, newSolution);
            }

            applyMutations(population);

            /*Selection of the best solution for the current generation*/
            Solution bestCurrentSolution = population.stream()
                    .min(Comparator.comparing(p -> p.getCost()))
                    .get();
            logger.debug("Best current solution: " + bestCurrentSolution.toString());
            if(bestCurrentSolution.getCost() < bestSolution.getCost()){
                bestSolution = bestCurrentSolution.clone();
            }
        }

        logger.info("-----------------------------------------");
        logger.info("Best solution: " + bestSolution.toString());
        double stopTime = System.currentTimeMillis();
        logger.info("Execution time: " + (stopTime - startTime));
        return bestSolution;
    }


    /**
     * Select a new population, the solutions with the best cost have a higher probability to be selected
     * @param population
     * @return
     */
    public ArrayList<Solution> rouletteWheelSelection(ArrayList<Solution> population){
        ArrayList<Solution> selectedPopulation = new ArrayList<>();
        double randomValue;
        double sumOfFitness;
        double sumOfAllFitness = 0;
        double probabilityToBeSelected;

        population.sort(new SolutionComparator());

        for(int i = 0; i < population.size(); i++){
            sumOfAllFitness += population.get(i).getCost();
        }

        for(double i = 0; i <= population.size(); i++){
            sumOfFitness = 0;
            randomValue = Math.random();
            for(Solution individual: population){
                probabilityToBeSelected = (individual.getCost() / sumOfAllFitness);
                probabilityToBeSelected = (1 - probabilityToBeSelected) / (population.size() - 1);
                sumOfFitness += probabilityToBeSelected;
                if(sumOfFitness > randomValue){
                    selectedPopulation.add(individual);
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    /**
     * Do a crossover between two patterns from two solutions
     * @param population
     */
    public void applyCrossoverBetweenPatterns(ArrayList<Solution> population){
        for(int i = 0; i < population.size() - 1; i+=2){
            int randomNumberForCrossover = (int)(Math.random() * 100);
            if(randomNumberForCrossover < ProgramMain.PERCENTAGE_OF_APPLY_CROSSOVER){
                Solution firstSolution = population.get(i);
                Solution secondSolution = population.get(i+1);

                Solution firstSon = firstSolution.clone();
                Solution secondSon = secondSolution.clone();

                int randomNumberForFirstCrossover = (int) (Math.random() * (firstSolution.getPatterns().length));
                int randomNumberForSecondCrossover = (int) (Math.random() * (firstSolution.getPatterns().length));

                Pattern buffer;

                if(randomNumberForFirstCrossover < secondSolution.getPatterns().length && randomNumberForSecondCrossover < secondSolution.getPatterns().length){
                    if(randomNumberForFirstCrossover < randomNumberForSecondCrossover){
                        for(int j = randomNumberForFirstCrossover; j < randomNumberForSecondCrossover; j++){
                            buffer = firstSon.getPatterns()[j];
                            firstSon.getPatterns()[j] = secondSolution.getPatterns()[j].clone();
                            secondSon.getPatterns()[j] = buffer.clone();
                        }
                    }
                    else{
                        for(int j = randomNumberForSecondCrossover; j < randomNumberForFirstCrossover; j++){
                            buffer = firstSon.getPatterns()[j];
                            firstSon.getPatterns()[j] = secondSolution.getPatterns()[j].clone();
                            secondSon.getPatterns()[j] = buffer.clone();
                        }
                    }
                    firstSolution.transformPatternArrayInSolutionArray();
                    secondSolution.transformPatternArrayInSolutionArray();

                    firstSolution = Neighbour.findNeighbour(firstSolution);
                    population.set(i, firstSolution);
                    secondSolution = Neighbour.findNeighbour(secondSolution);
                    population.set(i, secondSolution);
                }
            }
        }
    }

    /**
     * Do a crossover between the shapes from two pattern on the same solution
     * @param initialSolution
     */
    public Solution applyCrossoverBetweenShapes(Solution initialSolution){
        int randomNumberForCrossover = (int)(Math.random() * 100);
        if(randomNumberForCrossover < ProgramMain.PERCENTAGE_OF_APPLY_CROSSOVER){
            for(int i = 0; i < initialSolution.getPatterns().length; i++){
                int randomNumberForFirstPattern = (int)(Math.random() * initialSolution.getPatterns().length);
                int randomNumberForSecondPattern = (int)(Math.random() * initialSolution.getPatterns().length);

                int randomNumberForFirstCrossover = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));
                int randomNumberForSecondCrossover = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));

                int buffer1;
                int buffer2;
                if(randomNumberForFirstCrossover < randomNumberForSecondCrossover){
                    for(int j = randomNumberForFirstCrossover; j < randomNumberForSecondCrossover; j++){
                        buffer1 = initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).getNumber();
                        buffer2 = initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).getNumber();
                        initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(j).setNumber(buffer2);
                        initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(j).setNumber(buffer1);
                    }
                }
                else{
                    for(int j = randomNumberForSecondCrossover; j < randomNumberForFirstCrossover; j++){
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
     * @param population
     */
    public void applyMutations(ArrayList<Solution> population){
        int randomNumberForMutation = (int)(Math.random() * 100);
        if(randomNumberForMutation < ProgramMain.PERCENTAGE_OF_APPLY_MUTATION){
            int randomNumberForSolution = (int) (Math.random() * (population.size()));
            population.set(randomNumberForSolution, Neighbour.findNeighbour(population.get(randomNumberForSolution)));
        }
    }
}

class SolutionComparator implements Comparator<Solution> {
    @Override
    public int compare(Solution o1, Solution o2) {
        return o1.getCost() - o2.getCost();
    }
}
