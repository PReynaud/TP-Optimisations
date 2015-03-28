package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by Dimitri on 28/03/2015.
 */
public abstract class PositioningTest extends TestCase {
    protected Positioning positioning;
    protected Sheet sheet;
    protected Hashtable<PlacedShape, Integer> pattern;

    @Before
    public void setUp() throws Exception {
        sheet = new Sheet(1400,700);
        pattern = new Hashtable<PlacedShape, Integer>();
        pattern.put(new PlacedShape(727,333), 2);
        pattern.put(new PlacedShape(893,307), 1);
        pattern.put(new PlacedShape(933,372), 1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public abstract void testSortShapes() throws Exception;

    @Test
    public abstract void testGuillotine() throws Exception;

    @Test
    public abstract void testIsPossible() throws Exception;

    @Test
    public abstract void testPut() throws Exception;
}