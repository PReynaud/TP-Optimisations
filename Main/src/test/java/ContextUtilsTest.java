import com.polytech4AInfo.Shape.Shape;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * Created by benoitvuillemin on 26/03/2015.
 */
public class ContextUtilsTest extends TestCase {

    private static final String GOOD_FILE = "src/test/resources/good.txt";
    private static final String BAD_FILE = "";
    private static final String MISSING_FILE = "";
    private Integer[] tab_order;
    private Shape[] tab_shape;
    private int lx;
    private int ly;

    public void setUp() {
        tab_order = new Integer[3];
        tab_order[0] = 179;
        tab_order[1] = 192;
        tab_order[2] = 121;

        tab_shape = new Shape[3];
        tab_shape[0] = new Shape(933, 372);
        tab_shape[1] = new Shape(893, 307);
        tab_shape[2] = new Shape(727, 333);

        lx = 1400;
        ly = 700;
    }

    @Test
    public void testLoadContext() throws Exception {
        ContextUtils.Context c_good = ContextUtils.loadContext(GOOD_FILE);
        assertEquals(lx, c_good.getLx());
        assertEquals(ly, c_good.getLy());
        assertEquals(tab_order.length, c_good.getTab_order().length);
        for(int i=0; i<tab_order.length; i++){
            assertEquals(tab_order[i], c_good.getTab_order()[i]);
        }
        assertEquals(tab_shape.length, c_good.getTab_shape().length);
        for(int i=0; i<tab_shape.length; i++){
            assertEquals(tab_shape[i].getHeight(), c_good.getTab_shape()[i].getHeight());
            assertEquals(tab_shape[i].getWidth(), c_good.getTab_shape()[i].getWidth());
            assertEquals(tab_shape[i].getPositionx(), c_good.getTab_shape()[i].getPositionx());
            assertEquals(tab_shape[i].getPositiony(), c_good.getTab_shape()[i].getPositiony());
        }

        ContextUtils.Context c_bad = ContextUtils.loadContext(BAD_FILE);
        //TODO Faire test mauvais fichier

    }
}