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
    Sheet sheet;
    ArrayList<ShapeGroup> pattern;

    @Before
    public void setUp() throws Exception {
        this.positioning = new Positioning(new BreadthGuillotine(), Shape.Comparators.LENGTH );
        this.sheet = new Sheet(1400,700);
        this.pattern = new ArrayList<ShapeGroup>();
        pattern.add(new ShapeGroup(933,372,1));
        pattern.add(new ShapeGroup(700,333,1));
        pattern.add(new ShapeGroup(307,293,3));
    }

    @Test
    public void testIsPossible() throws Exception {
        assertEquals("Test isPossible : should return true",positioning.isPossible(sheet,pattern),true);
    }
}