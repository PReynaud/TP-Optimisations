package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;


/**
 * Created by Dimitri on 28/03/2015.
 */
public class BreadthPositioningTest extends PositioningTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        positioning = new BreadthPositioning();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSortShapes() throws Exception {
    }

    @Test
    public void testGuillotine() throws Exception {

    }

    @Override
    public void testIsPossible() throws Exception {
        assert (positioning.isPossible(sheet,pattern));
    }

    @Override
    public void testPut() throws Exception {

    }
}