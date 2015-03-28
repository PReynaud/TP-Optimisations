import com.polytech4AInfo.Shape.Shape;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;


/**
 * Created by benoitvuillemin on 26/03/2015.
 */
public class ContextUtilsTest extends TestCase {

    private static final String GOOD_FILE = "src/test/resources/good.txt";
    private static final String BAD_FILE = "";
    private static final String MISSING_FILE = "";
    private Hashtable<Shape, Integer> pattern = new Hashtable<Shape, Integer>();
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
        assertEquals(pattern.toString(), c_good.getPattern().toString());

        //ContextUtils.Context c_bad = ContextUtils.loadContext(BAD_FILE);
        //TODO Faire test mauvais fichier

    }

    private void testShape(Shape a, Shape b) {
        assertEquals(a.getPositionx(), b.getPositionx());
        assertEquals(a.getPositiony(), b.getPositiony());
        assertEquals(a.getWidth(), b.getWidth());
        assertEquals(a.getHeight(), b.getHeight());
    }
}