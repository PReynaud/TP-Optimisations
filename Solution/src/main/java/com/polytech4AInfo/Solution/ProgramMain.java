package com.polytech4AInfo.Solution;

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

    private static int NUMBER_OF_PATTERN = 10;
    private static double TEMPERATURE = 800.0;

    public static void main(String args[]){
        defineLogger();
        logger.info("Beginning of the program");
        try {
            ContextUtils.Context file = ContextUtils.loadContext("Ressources/data_20Lalpha.txt");
            Solution firstSolution = FirstSolution.generateFirstSolution(file, NUMBER_OF_PATTERN);

            Annealing algo = new Annealing();
            Solution finalSolution = algo.simulatedAnnealing(firstSolution, TEMPERATURE);
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
        logger.setLevel(Level.INFO);
    }
}
