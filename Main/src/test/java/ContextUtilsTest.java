import com.polytech4AInfo.Shape.Shape;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;



/**
 * Created by benoitvuillemin on 26/03/2015.
 */
public class ContextUtilsTest extends TestCase {

    private static final String GOOD_FILE = "src/test/resources/good.txt";
    private static final String BAD_FILE = "src/test/resources/bad.txt";
    private static final String MISSING_FILE = "";
    private Hashtable<Shape, Integer> pattern = new Hashtable<Shape, Integer>();
    String[] l1_temp;
    String[] l2_temp;
    private int lx;
    private int ly;

    public void setUp() {
        pattern.put(new Shape(933, 372), 179);
        pattern.put(new Shape(893, 307), 192);
        pattern.put(new Shape(727, 333), 121);
        lx = 1400;
        ly = 700;
    }

    @Test
    public void testLoadContext() throws Exception {
        ContextUtils.Context c_good = ContextUtils.loadContext(GOOD_FILE);
        assertEquals(lx, c_good.getLx());
        assertEquals(ly, c_good.getLy());
        assertEquals(pattern.size(), c_good.getPattern().size());
        l1_temp = (pattern.toString().substring(1, pattern.toString().length() - 1)).split(", ");
        l2_temp = (pattern.toString().substring(1, c_good.getPattern().toString().length() - 1)).split(", ");
        System.out.print(l1_temp[0]);
        assertEquals(true, compareArrays(l1_temp, l2_temp));

        try {
            ContextUtils.Context c_bad = ContextUtils.loadContext(BAD_FILE);
        } catch (ContextUtils.ContextLoadException e) {
            assertEquals(e.getMessage(), "Erreur dans la lecture des paramètres à la ligne 1");
        }

        try {
            ContextUtils.Context c_missing = ContextUtils.loadContext(MISSING_FILE);
        } catch (FileNotFoundException e) {
            assertEquals(e.getMessage(), "File '' does not exist");
        }

    }

    /**
     * Compare tous les éléments de deux listes de string non ordonnées
     * @param s1 liste non ordinnée de string
     * @param s2 liste non ordinnée de string
     * @return true si tout est équivalent, false sinon
     */
    private boolean compareArrays(String[] s1, String[] s2) {
        if (s1.length != s2.length)
            return false;
        int length = s1.length;
        boolean[] b = new boolean[length];
        boolean resultat = true;

        for (int a = 0; a < length; a++) {
            b[a] = false;
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (s1[i].equals(s2[j]))
                    b[i] = true;
            }
        }
        for (int a = 0; a < length; a++) {
            if (b[a] == false)
                resultat = false;
        }
        return resultat;
    }
}