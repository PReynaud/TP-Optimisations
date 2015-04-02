import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.ShapeGroup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import sun.security.provider.SHA;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by benoitvuillemin on 26/03/2015.
 *
 * @author benoitvuillemin
 * @version 1.0
 *          <p/>
 *          Définit le contexte d'exécution de l'algo Positionning à partir d'un fichier
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
     * @throws IOException                       si problème de lecture dans le fichier
     * @throws ContextUtils.ContextLoadException si une ligne du fichier n'est pas correcte
     */
    public static Context loadContext(String path) throws IOException, ContextLoadException {
        int lx;
        int ly;
        String line_temp;
        String[] line_temp_split;
        final File file = new File(path);
        final LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        ShapeGroup[] pattern;
        Double height_temp;
        Double width_temp;
        int nombre;


        if (it.hasNext()) {
            line_temp = it.nextLine();
            if (line_temp.matches("^LX=\\d+$")) {
                lx = Integer.valueOf(line_temp.split("LX=")[1]);
            } else throw new ContextLoadException(ERREUR_PARAM, 1);
        } else throw new ContextLoadException(ERREUR_LIGNE, 1);
        if (it.hasNext()) {
            line_temp = it.nextLine();
            if (line_temp.matches("^LY=\\d+$")) {
                ly = Integer.valueOf(line_temp.split("LY=")[1]);
            } else throw new ContextLoadException(ERREUR_PARAM, 2);
        } else throw new ContextLoadException(ERREUR_LIGNE, 2);

        if (it.hasNext()) {
            line_temp = it.nextLine();
            if (line_temp.matches("^m=\\d+$")) {
                nombre = Integer.valueOf(line_temp.split("m=")[1]);
            } else throw new ContextLoadException(ERREUR_PARAM, 3);
        } else throw new ContextLoadException(ERREUR_LIGNE, 3);

        pattern = new ShapeGroup[nombre];

        for (int i = 0; i < nombre; i++) {
            line_temp = it.nextLine();
            if (line_temp.matches("^(\\d+.\\d\\s){2}\\d+$")) {
                line_temp_split = line_temp.split("\\s");
                height_temp = Double.parseDouble(line_temp_split[0]);
                width_temp = Double.parseDouble(line_temp_split[1]);
                pattern[i] = new ShapeGroup(height_temp.intValue(), width_temp.intValue(),Integer.parseInt(line_temp_split[2]));
            } else throw new ContextLoadException(ERREUR_SHAPE, i + 4);
        }
        it.close();

        Context c = new Context(lx, ly, pattern);
        return c;

    }

    /**
     * Created by benoitvuillemin on 26/03/2015.
     *
     * @author benoitvuillemin
     * @version 1.0
     *          <p/>
     *          Structure d'un contexte type
     */
    public static class Context {
        int lx;
        int ly;
        ShapeGroup[] pattern;

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

        public ShapeGroup[] getPattern() {
            return pattern;
        }

        public void setPattern(ShapeGroup[] pattern) {
            this.pattern = pattern;
        }

        public Context(int lx, int ly, ShapeGroup[] pattern) {
            this.lx = lx;
            this.ly = ly;
            this.pattern = pattern;
        }
    }

    /**
     * Exception créé à cause d'une erreur de lecture de fichier
     */
    public static class ContextLoadException extends Exception {
        /**
         * Construit une exception pour un problème de lecture de fichier
         *
         * @param s Nature de l'erreur
         * @param i Ligne de l'erreur
         */
        public ContextLoadException(String s, Integer i) {
            super(s + i);
        }
    }
}
