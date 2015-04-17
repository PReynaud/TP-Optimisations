package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Dimitri on 09/04/2015.
 */
public class PositioningTest {
    Positioning positioning;
    Positioning positioning2;
    Sheet sheet;
    ArrayList<ShapeGroup> pattern;

    @Before
    public void setUp() throws Exception {
        this.positioning = new Positioning(new RatioGuillotine(), Shape.Comparators.RATIO );
        this.positioning2 = new Positioning(new LengthGuillotine(),Shape.Comparators.BREADTH);
    }

    @Test
    public void testIsPossible() throws Exception {
        this.sheet = new Sheet(1500,750);
        this.pattern = new ArrayList<ShapeGroup>();
        pattern.add(new ShapeGroup(933,372,1));
        pattern.add(new ShapeGroup(700,333,1));
        pattern.add(new ShapeGroup(307,293,2));
        pattern.add(new ShapeGroup(307, 100, 3));
        pattern.add(new ShapeGroup(200, 50, 10));
        assertEquals("Test isPossible : should return true", positioning.isPossible(sheet, pattern), true);
    }

    /*@Test
    public void testIsPossible2() throws Exception{
        this.sheet = new Sheet(1500,750);
        this.pattern = new ArrayList<ShapeGroup>();
        pattern.add(new ShapeGroup(933,372,1));
        pattern.add(new ShapeGroup(700,333,1));
        pattern.add(new ShapeGroup(307,293,2));
        pattern.add(new ShapeGroup(307, 100, 3));
        pattern.add(new ShapeGroup(200, 50, 10));

        assertEquals("Test isPossible2 : should return true", positioning2.isPossible(sheet, pattern), true);
    }*/
}