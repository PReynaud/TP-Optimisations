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

    @Test
    public void testCaseOfTheBestSolution1(){
        this.sheet = new Sheet(1400, 700);
        this.pattern = new ArrayList<>();
        pattern.add(new ShapeGroup(511, 340, 2));
        pattern.add(new ShapeGroup(708, 199, 2));
        pattern.add(new ShapeGroup(315, 301, 2));
        pattern.add(new ShapeGroup(141, 324, 1));
        pattern.add(new ShapeGroup(161, 182, 1));

        assertTrue("should be able to put this pattern on the shape", positioning.isPossible(this.sheet, this.pattern)!=null);
    }

    @Test
     public void testCaseOfTheBestSolution2(){
        this.sheet = new Sheet(1400, 700);
        this.pattern = new ArrayList<>();
        pattern.add(new ShapeGroup(933, 372, 1));
        pattern.add(new ShapeGroup(893, 307, 1));
        pattern.add(new ShapeGroup(472, 198, 1));
        pattern.add(new ShapeGroup(197, 443, 1));
        pattern.add(new ShapeGroup(463, 144, 1));
        pattern.add(new ShapeGroup(453, 127, 1));

        assertTrue("should be able to put this pattern on the shape", positioning.isPossible(this.sheet, this.pattern)!=null);
    }

    @Test
     public void testCaseOfTheBestSolution3(){
        this.sheet = new Sheet(1400, 700);
        this.pattern = new ArrayList<>();
        pattern.add(new ShapeGroup(727, 333, 1));
        pattern.add(new ShapeGroup(846, 263, 1));
        pattern.add(new ShapeGroup(364, 366, 1));
        pattern.add(new ShapeGroup(234, 399, 1));
        pattern.add(new ShapeGroup(742, 102, 1));
        pattern.add(new ShapeGroup(341, 153, 1));
        pattern.add(new ShapeGroup(141, 324, 1));
        pattern.add(new ShapeGroup(396, 78, 1));

        assertTrue("should be able to put this pattern on the shape", positioning.isPossible(this.sheet, this.pattern)!=null);
    }

    @Test
    public void testCaseOfTheBestSolution4(){
        this.sheet = new Sheet(1400, 700);
        this.pattern = new ArrayList<>();
        pattern.add(new ShapeGroup(571, 408, 1));
        pattern.add(new ShapeGroup(731, 269, 2));
        pattern.add(new ShapeGroup(234, 399, 1));
        pattern.add(new ShapeGroup(742, 102, 1));
        pattern.add(new ShapeGroup(161, 182, 1));

        assertTrue("should be able to put this pattern on the shape", positioning.isPossible(this.sheet, this.pattern)!=null);
    }

    @Test
    public void testCaseOfTheBestSolution5(){
        this.sheet = new Sheet(1400, 700);
        this.pattern = new ArrayList<>();
        pattern.add(new ShapeGroup(571, 408, 2));
        pattern.add(new ShapeGroup(364, 366, 1));
        pattern.add(new ShapeGroup(341, 153, 4));

        assertTrue("should be able to put this pattern on the shape", positioning.isPossible(this.sheet, this.pattern)!=null);
    }
}