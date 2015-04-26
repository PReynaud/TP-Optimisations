package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Shape.ShapeGroup;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;


/**
 * Created by benoitvuillemin on 26/03/2015.
 */
public class ContextUtilsTest extends TestCase {

    private static final String GOOD_FILE = "src/test/resources/good.txt";
    private static final String BAD_FILE = "src/test/resources/bad.txt";
    private static final String MISSING_FILE = "";
    private ShapeGroup[] pattern;
    private int lx;
    private int ly;

    public void setUp() {
        pattern = new ShapeGroup[3];
        pattern[0] = new ShapeGroup(933, 372, 179);
        pattern[1] = new ShapeGroup(893, 307, 192);
        pattern[2] = new ShapeGroup(727, 333, 121);
        lx = 1400;
        ly = 700;
    }

    @Test
    public void testLoadContext() throws Exception {
        ContextUtils.Context c_good = ContextUtils.loadContext(GOOD_FILE);
        TestCase.assertEquals(lx, c_good.getLx());
        TestCase.assertEquals(ly, c_good.getLy());
        TestCase.assertEquals(pattern.length, c_good.getPattern().length);
        for (int i = 0; i < pattern.length; i++) {
            TestCase.assertEquals(pattern[i], c_good.getPattern()[i]);
        }

        try {
            ContextUtils.Context c_bad = ContextUtils.loadContext(BAD_FILE);
        } catch (ContextUtils.ContextLoadException e) {
            TestCase.assertEquals(e.getMessage(), "Erreur dans la lecture des paramètres à la ligne 1");
        }

        try {
            ContextUtils.Context c_missing = ContextUtils.loadContext(MISSING_FILE);
        } catch (FileNotFoundException e) {
            assertEquals(e.getMessage(), "File '' does not exist");
        }

    }

    /**
     * Compare tous les éléments de deux listes de string non ordonnées
     *
     * @param s1 liste non ordonnée de string
     * @param s2 liste non ordonnée de string
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