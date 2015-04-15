package com.polytech4AInfo.Positioning.Pattern;

import org.apache.commons.math.util.FastMath;

/**
 * Created by benoitvuillemin on 10/04/2015.
 * RECUIT SIMULE
 */
public class Annealing {
    private static int LIMIT = 1000;
    /**
     * Will count the number of iterations of our algorithm
     */
    private int counter = 0;

    public Annealing(){}

    /**
     * Execute the algorithm
     * @param s
     */
    public Solution simulatedAnnealing(Solution s, double temperature) {
        Solution currentSolution = s.clone();
        Solution bestSolution = s.clone();

        double deltaCost;
        double p;
        while(counter < LIMIT){
            System.out.println("Number of iteration: " + counter);
            Solution oneSolution = findNeighbour(currentSolution);
            deltaCost = currentSolution.getCost() - oneSolution.calculCost();
            if (deltaCost <= 0){
                currentSolution = oneSolution;
                if(bestSolution.getCost() < currentSolution.getCost()){
                    bestSolution = currentSolution;
                }
            }
            else{
                p = Math.random();
                if(p < FastMath.exp(-deltaCost / temperature)){
                    currentSolution = oneSolution;
                }
            }
            counter++;
            temperature = calculeNewTemperature(temperature);
        }
        return currentSolution;
    }

    /**
     * Will look for a neighnour and return it
     * @param s
     * @return A clone of the initial solution but which has been modified
     */
    private Solution findNeighbour(Solution s) {
        // TODO un test dessus pourrait �tre utile je pense, la nouvelle solution doit aussi absolument etre
        // un CLONE de la solution de d�part
        Solution s2;
        do {
            s2 = s.clone();
            int a = (int) (Math.random() * (s.getSolution().length));
            int b = (int) (Math.random() * (s.getSolution()[a].length));
            int plus_or_minus = (int) (Math.random() * 2);
            if(plus_or_minus == 0) {
                s2.getSolution()[a][b] -= 1;
            }
            else{
                s2.getSolution()[a][b] += 1;
            }
        }
        while(!s2.isPossible());
        return s2;
    }

    /**
     * Calcule the new temperature. It used a geometric progression
     * @param oldTemperature The old temperature
     * @return The new temperature we'll used
     */
    private double calculeNewTemperature(double oldTemperature){
        return 0.99 * oldTemperature;
    }

}
