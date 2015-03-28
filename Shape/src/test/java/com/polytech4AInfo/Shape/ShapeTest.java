package com.polytech4AInfo.Shape;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ShapeTest extends TestCase {
    private Shape shape;

    @Override
    protected void setUp(){
        shape = new Shape(100, 150);
    }

    @Test
    public void testShape() throws Exception {
        Shape shape = new Shape(50, 75);
        assertEquals(75, shape.length);
        assertEquals(50, shape.breadth);

        shape = new Shape(75, 50);
        assertEquals(75, shape.length);
        assertEquals(50, shape.breadth);
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(150, shape.getLength());
    }

    @Test
    public void testGetBreadth() throws Exception {
        assertEquals(100, shape.getBreadth());
    }

    @Test
    public void testGetRatio() throws Exception {
        Shape shape = new Shape(200, 100);
        assertEquals(0.5, shape.getRatio());
    }

    @Test
    public void testGetArea() throws Exception{
        assertEquals(15000 , shape.getArea());
    }

    @Test
    public void testTurnShape() throws Exception {
        shape.turnShape();
        assertTrue(shape.isLying);
        shape.turnShape();
        assertFalse(shape.isLying);
    }

    @Test
    public void testCompareTo() throws Exception {

    }
}