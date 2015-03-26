import com.polytech4AInfo.Shape.Shape;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benoitvuillemin on 26/03/2015.
 * @author benoitvuillemin
 * @version 1.0
 *
 * Définit le contexte d'exécution de l'algo Positionning à partir d'un fichier
 */
public class ContextUtils {

    private static final String ERREUR_PARAM = "Erreur dans la lecture des paramètres à la ligne ";
    private static final String ERREUR_LIGNE = "Ligne manquante : ";
    private static final String ERREUR_SHAPE = "Erreur lors de la lecture du Shape à la ligne ";
    /**
     * Lit un fichier donné en paramètre et retourne un contexte à partir de ce fichier
     *
     * @param path chemin absolu du fichier
     * @return Contexte à partir d'un fichier
     * @throws IOException si problème de lecture dans le fichier
     * @throws ContextUtils.ContextLoadException si une ligne du fichier n'est pas correcte
     */
    public Context loadContext(String path) throws IOException, ContextLoadException {
        int lx;
        int ly;
        List<Shape> shapeList = new ArrayList();
        String line_temp;
        String[] line_temp_split;
        final File file = new File(path);
        final LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        int numero_ligne = 3;


        if(it.hasNext()){
            line_temp = it.nextLine();
            if(line_temp.matches("^LX=\\d+$")){
                lx = Integer.valueOf(it.nextLine().split("LX=")[1]);
            }
            else throw new ContextLoadException(ERREUR_PARAM, 1);
        }
        else throw new ContextLoadException(ERREUR_LIGNE, 1);
        if(it.hasNext()){
            line_temp = it.nextLine();
            if(line_temp.matches("^LY=\\d+$")){
                ly = Integer.valueOf(it.nextLine().split("LY=")[1]);
            }
            else throw new ContextLoadException(ERREUR_PARAM, 2);
        }
        else throw new ContextLoadException(ERREUR_LIGNE, 2);

        //TODO NOMBRE IMPRESSIONS PAR IMAGES
        line_temp = it.nextLine();


        while(it.hasNext()){
            numero_ligne++;
            line_temp = it.nextLine();
            if(line_temp.matches("^(\\d+\\s){2}\\d$")){
                line_temp_split = line_temp.split("\\s");
                shapeList.add(new Shape(Integer.parseInt(line_temp_split[0]), Integer.parseInt(line_temp_split[1])));
            }
            else throw new ContextLoadException(ERREUR_SHAPE, numero_ligne);
        }
        it.close();
        final Context c = new Context(lx, ly, shapeList);
        return c;

    }

    /**
     * Created by benoitvuillemin on 26/03/2015.
     * @author benoitvuillemin
     * @version 1.0
     *
     * Structure d'un contexte type
     */
    public class Context{
        int lx;
        int ly;
        List<Shape> shapeList = new ArrayList();

        public int getLx() {
            return lx;
        }

        public void setLx(int lx) {
            this.lx = lx;
        }

        public int getLy() {
            return ly;
        }

        public void setLy(int ly) {
            this.ly = ly;
        }

        public List<Shape> getShapeList() {
            return shapeList;
        }

        public void setShapeList(List<Shape> shapeList) {
            this.shapeList = shapeList;
        }

        public Context(int lx, int ly, List<Shape> shapeList) {
            this.lx = lx;
            this.ly = ly;
            this.shapeList = shapeList;
        }
    }

    /**
     *
     * Exception créé à cause d'une erreur de lecture de fichier
     */
    public static class ContextLoadException extends Exception{
        /**
         * Construit une exception pour un problème de lecture de fichier
         * @param s Nature de l'erreur
         * @param i Ligne de l'erreur
         */
        public ContextLoadException(String s, Integer i){
            super(s + i);
        }
    }
}
