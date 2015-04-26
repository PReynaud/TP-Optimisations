import com.polytech4AInfo.Solution.Annealing;
import com.polytech4AInfo.Solution.Solution;
import org.apache.log4j.*;

import java.io.IOException;

/**
 * Created by Pierre on 13/04/2015.
 */
public class ProgramMain {
    /**
     * Logger.
     */
    public static Logger logger = Logger.getLogger(ProgramMain.class);

    public static void main(String args[]){
        defineLogger();
        logger.info("Beginning of the program");

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
            logger.error("Cannot load file");
        } catch (ContextUtils.ContextLoadException e) {
            logger.error("Cannot load file");
        }

        logger.info("------------------");
        logger.info("End of the program");
    }

    private static void defineLogger(){
        HTMLLayout layout = new HTMLLayout();
        DailyRollingFileAppender appender = null;
        try {
            appender = new DailyRollingFileAppender(layout, "./Logs/log.html", "yyyy-MM-dd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsoleAppender ca=new ConsoleAppender();
        ca.setLayout(new SimpleLayout());
        ca.activateOptions();
        logger.addAppender(appender);
        logger.addAppender(ca);
        logger.setLevel(Level.DEBUG);
    }
}
