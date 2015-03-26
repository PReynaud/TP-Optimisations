import com.polytech4AInfo.Shape.Shape;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        Shape[] tab_shape;
        Integer[] tab_order;
        String line_temp;
        String[] line_temp_split;
        final File file = new File(path);
        final LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        int nombre;
        Double height_temp;
        Double width_temp;


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

        tab_shape = new Shape[nombre];
        tab_order = new Integer[nombre];

        for (int i = 0; i < nombre; i++) {
            line_temp = it.nextLine();
            if (line_temp.matches("^(\\d+.\\d\\s){2}\\d+$")) {
                line_temp_split = line_temp.split("\\s");
                height_temp = Double.parseDouble(line_temp_split[0]);
                width_temp = Double.parseDouble(line_temp_split[1]);
                tab_shape[i] = new Shape(height_temp.intValue(), width_temp.intValue());
                tab_order[i] = Integer.parseInt(line_temp_split[2]);
            } else throw new ContextLoadException(ERREUR_SHAPE, i + 4);
        }
        it.close();

        Context c = new Context(lx, ly, tab_shape, tab_order);
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
        Shape[] tab_shape;
        Integer[] tab_order;

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

        public Shape[] getTab_shape() {
            return tab_shape;
        }

        public void setTab_shape(Shape[] tab_shape) {
            this.tab_shape = tab_shape;
        }

        public Integer[] getTab_order() {
            return tab_order;
        }

        public void setTab_order(Integer[] tab_order) {
            this.tab_order = tab_order;
        }

        public Context(int lx, int ly, Shape[] tab_shape, Integer[] tab_order) {
            this.lx = lx;
            this.ly = ly;
            this.tab_shape = tab_shape;
            this.tab_order = tab_order;
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
