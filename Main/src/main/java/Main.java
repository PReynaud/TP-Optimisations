import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;
import com.polytech4AInfo.Solution.Annealing;
import com.polytech4AInfo.Solution.Pattern;
import com.polytech4AInfo.Solution.Solution;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Pierre on 13/04/2015.
 */
public class Main {
    public static void main(String args[]){
        System.out.println("Beginning of the program");

        /*ArrayList<ShapeGroup> pattern1 = new ArrayList<ShapeGroup>();
        pattern1.add(new ShapeGroup(933,372,1));
        pattern1.add(new ShapeGroup(700,333,1));
        pattern1.add(new ShapeGroup(307,293,2));
        pattern1.add(new ShapeGroup(307,100,5));
        pattern1.add(new ShapeGroup(200, 50, 5));

        int[] order = {10, 4, 6, 8, 10};
        Pattern[] listOfPatterns = new Pattern[2];
        listOfPatterns[0] = new Pattern(pattern1, new Sheet(1500, 700));

        ArrayList<ShapeGroup> pattern2 = new ArrayList<ShapeGroup>();
        pattern2.add(new ShapeGroup(933,372,0));
        pattern2.add(new ShapeGroup(700,333,0));
        pattern2.add(new ShapeGroup(307,293,0));
        pattern2.add(new ShapeGroup(307,100,0));
        pattern2.add(new ShapeGroup(200, 50, 0));
        listOfPatterns[1] = new Pattern(pattern2, new Sheet(1500, 700));

        Solution firstSolution = new Solution(listOfPatterns, order);

        Annealing algo = new Annealing();
        Solution finalSolution = algo.simulatedAnnealing(firstSolution, 500.0);*/

        try {
            ContextUtils.Context file = ContextUtils.loadContext("Ressources/data.txt");
            Solution firstSolution = FirstSolution.generateFirstSolution(file, 10);

            Annealing algo = new Annealing();
            Solution finalSolution = algo.simulatedAnnealing(firstSolution, 1000.0);
        } catch (IOException e) {
            System.out.println("Cannot load file");
        } catch (ContextUtils.ContextLoadException e) {
            System.out.println("Cannot load file");
        }

        System.out.println("------------------");
        System.out.println("End of the program");
    }
}
