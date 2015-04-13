package com.polytech4AInfo.Positioning.Pattern;

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
            Solution s2 = findNeighbour(s);
            deltaCost = s.getCost() - s2.getCost();
            if (deltaCost <= 0){
                currentSolution = s2;
                if(bestSolution.getCost() < currentSolution.getCost()){
                    bestSolution = currentSolution;
                }
            }
            else{
                p = Math.random();
                if(p < Math.exp(-deltaCost / temperature)){
                    currentSolution = s2;
                }
            }
            counter++;
            temperature = calcNewTemperature(temperature);
        }
        return currentSolution;
    }

    /**
     * Will look for a neighnour and return it
     * @param s
     * @return A clone of the initial solution but which has been modified
     */
    private Solution findNeighbour(Solution s) {
        // TODO un test dessus pourrait être utile je pense, la nouvelle solution doit aussi absolument etre
        // un CLONE de la solution de départ
        Solution s2;
        do {
            s2 = s.clone();
            int a = (int) (Math.random() * (s.getSolution().length));
            int b = (int) (Math.random() * (s.getSolution()[a].length));
            s2.getSolution()[a][b] -= 1;
        }
        while(s2.isPossible());
        return s2;
    }

    /**
     * Calcule the new temperature. It used a geometric progression
     * @param oldTemperature The old temperature
     * @return The new temperature we'll used
     */
    private double calcNewTemperature(double oldTemperature){
        return 0.99 * oldTemperature;
    }

}
