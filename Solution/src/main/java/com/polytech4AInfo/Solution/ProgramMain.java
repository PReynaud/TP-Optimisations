package com.polytech4AInfo.Solution;

import org.apache.log4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Pierre on 13/04/2015.
 */
public class ProgramMain {
    /**
     * Logger.
     */
    public static Logger logger = Logger.getLogger(ProgramMain.class);

    private static int NUMBER_OF_PATTERN = 10;
    public static double TEMPERATURE = 800.0;
    private static String FILE_TO_LOAD = "Ressources/data_20Lalpha.txt";

    public static String PATH_TO_IMAGES = "./OUTPUT/";
    private static String PATH_TO_LOG_FILES = "./OUTPUT/log.html";

    public static int LIMIT = 1000;
    public static int LIMITTEMP = 200;

    private static String DEBUG_LEVEL = "INFO";
    private static String ALGO = "GEN";

    public static double PERCENTAGE_OF_INCREMENTATION = 25;
    public static double PERCENTAGE_OF_ADDING_A_PATTERN = 0.05;
    public static double PERCENTAGE_OF_REMOVING_A_PATTERN = 0.01;
    public static double PERCENTAGE_OF_INVERTING_TWO_SHAPES = 0.1;

    public static int GENERATION = 1000;
    public static int INITIAL_POPULATION_SIZE = 100;
    public static double PERCENTAGE_OF_APPLY_MUTATION = 1;
    public static double PERCENTAGE_OF_APPLY_CROSSOVER = 80;

    public static void main(String args[]){
        defineLogger();
        logger.info("Beginning of the program");
        try{
            defineConfiguration();
            try {
                ContextUtils.Context file = ContextUtils.loadContext(FILE_TO_LOAD);
                logger.info("------------------");
                if(ALGO.equals("GEN")){
                    logger.info("Generating initial population...");
                    ArrayList<Solution> firstPopulation = FirstSolution.generateFirstPopulation(file, NUMBER_OF_PATTERN, INITIAL_POPULATION_SIZE);
                    Genetic algo = new Genetic();
                    Solution lastSolution = algo.simulatedGenetic(firstPopulation);
                    lastSolution.saveSolutionInFiles();
                }
                if(ALGO.equals("ANN")){
                    Solution firstSolution = FirstSolution.generateFirstSolution(file, NUMBER_OF_PATTERN);
                    Annealing algo = new Annealing();
                    Solution lastSolution = algo.simulatedAnnealing(firstSolution, TEMPERATURE);
                    lastSolution.saveSolutionInFiles();
                }

            } catch (IOException e) {
                logger.error("Cannot load data file");
            } catch (ContextUtils.ContextLoadException e) {
                logger.error("Cannot load data file");
            }
        } catch (IOException e) {
            logger.error("Cannot load configuration file");
        }


        logger.info("------------------");
        logger.info("End of the program");
    }

    private static void defineConfiguration() throws IOException {
        Properties prop = new Properties();
        String fileName = "Ressources/default.properties";
        InputStream is = new FileInputStream(fileName);
        prop.load(is);
        NUMBER_OF_PATTERN = Integer.parseInt(prop.getProperty("NUMBER_OF_PATTERN"));
        TEMPERATURE = Double.parseDouble(prop.getProperty("TEMPERATURE"));
        FILE_TO_LOAD = prop.getProperty("FILE_TO_LOAD");
        LIMIT = Integer.parseInt(prop.getProperty("LIMIT"));
        LIMITTEMP = Integer.parseInt(prop.getProperty("LIMITTEMP"));
        PERCENTAGE_OF_INCREMENTATION = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_INCREMENTATION"));
        PERCENTAGE_OF_ADDING_A_PATTERN = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_ADDING_A_PATTERN"));
        PERCENTAGE_OF_REMOVING_A_PATTERN = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_REMOVING_A_PATTERN"));
        PERCENTAGE_OF_INVERTING_TWO_SHAPES = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_INVERTING_TWO_SHAPES"));
        GENERATION = Integer.parseInt(prop.getProperty("GENERATION"));
        INITIAL_POPULATION_SIZE = Integer.parseInt(prop.getProperty("INITIAL_POPULATION_SIZE"));
        PERCENTAGE_OF_APPLY_MUTATION = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_APPLY_MUTATION"));
        PERCENTAGE_OF_APPLY_CROSSOVER = Double.parseDouble(prop.getProperty("PERCENTAGE_OF_APPLY_CROSSOVER"));
        PATH_TO_LOG_FILES = prop.getProperty("PATH_TO_LOG_FILES");
        PATH_TO_IMAGES = prop.getProperty("PATH_TO_IMAGES");
        DEBUG_LEVEL = prop.getProperty("DEBUG_LEVEL");
        ALGO = prop.getProperty("ALGO");

        logger.info("The following properties have been loaded:");
        logger.info("FILE_TO_LOAD: " + FILE_TO_LOAD
                + ", PATH_TO_LOG_FILES: " + PATH_TO_LOG_FILES
                + ", PATH_TO_IMAGES: " + PATH_TO_IMAGES
                + ", NUMBER_OF_PATTERN: " + NUMBER_OF_PATTERN);

        logger.info("Neighbours properties:");
        logger.info("PERCENTAGE_OF_INCREMENTATION: " + PERCENTAGE_OF_INCREMENTATION
                + ", PERCENTAGE_OF_ADDING_A_PATTERN: " + PERCENTAGE_OF_ADDING_A_PATTERN
                + ", PERCENTAGE_OF_REMOVING_A_PATTERN: " + PERCENTAGE_OF_REMOVING_A_PATTERN
                + ", PERCENTAGE_OF_INVERTING_TWO_SHAPES: " + PERCENTAGE_OF_INVERTING_TWO_SHAPES);

        if(ALGO.equals("GEN")){
            logger.info("Genetic algorithm properties:"
                    + ", GENERATION: " + GENERATION
                    + ", INITIAL_POPULATION_SIZE: " + INITIAL_POPULATION_SIZE
                    + ", PERCENTAGE_OF_APPLY_MUTATION: " + PERCENTAGE_OF_APPLY_MUTATION
                    + ", PERCENTAGE_OF_APPLY_CROSSOVER: " + PERCENTAGE_OF_APPLY_CROSSOVER);
        }
        if(ALGO.equals("ANN")){
            logger.info("Annealing algorithm properties: "
                    + ", TEMPERATURE: " + TEMPERATURE
                    + ", LIMIT: " + LIMIT
                    + ", LIMITTEMP: " + LIMITTEMP);
        }
    }

    private static void defineLogger(){
        File file = new File("./Logs/log.html");
        file.delete();
        HTMLLayout layout = new HTMLLayout();
        DailyRollingFileAppender appender = null;
        try {
            appender = new DailyRollingFileAppender(layout, PATH_TO_LOG_FILES, "yyyy-MM-dd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsoleAppender ca=new ConsoleAppender();
        ca.setLayout(new SimpleLayout());
        ca.activateOptions();
        logger.addAppender(appender);
        logger.addAppender(ca);
        if(DEBUG_LEVEL == "DEBUG"){
            logger.setLevel(Level.DEBUG);
        }
        else{
            logger.setLevel(Level.INFO);
        }
    }
}
