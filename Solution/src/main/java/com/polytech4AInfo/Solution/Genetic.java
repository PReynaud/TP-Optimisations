package com.polytech4AInfo.Solution;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Pierre on 08/05/2015.
 */
public class Genetic {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

    private static int GENERATION = 5000;

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
        for(int j=0; j < GENERATION; j++){
            logger.info("Current generation: " + j);
            ArrayList<Solution> selectedPopulation = rouletteWheelSelection(population);
            for(int i = 0; i < population.size(); i++){
                population.set(i, selectedPopulation.get(i));
            }

            //TODO croisements et mutations
            applyCrossoverBetweenPatterns(population);
            applyMutations(population);

            /*Selection of the best solution for the current generation*/
            Solution bestCurrentSolution = population.stream()
                    .min(Comparator.comparing(p -> p.getCost()))
                    .get();
            logger.debug("Best current solution: " + bestCurrentSolution.toString());
            if(bestCurrentSolution.getCost() < bestSolution.getCost()){
                bestSolution = bestCurrentSolution;
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
                probabilityToBeSelected = individual.getCost() / sumOfAllFitness;
                sumOfFitness += probabilityToBeSelected;
                if(sumOfFitness > randomValue){
                    selectedPopulation.add(individual);
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    public void applyCrossoverBetweenPatterns(ArrayList<Solution> population){
        for(int i = 0; i < population.size(); i+=2){
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
            }
        }
    }

    /**
     * Call the find neighbour function that will apply a random mutation and send a valid solution
     * @param population
     */
    public void applyMutations(ArrayList<Solution> population){
        for(int i = 0; i< 10; i++){
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
