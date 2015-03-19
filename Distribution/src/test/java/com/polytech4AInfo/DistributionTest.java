package com.polytech4AInfo;

import org.apache.commons.math.optimization.RealPointValuePair;
import org.junit.Assert;
import junit.framework.TestCase;

public class DistributionTest extends TestCase {
    private Distribution instance;

    @Override
    protected void setUp(){
        instance = new Distribution(2, 2);
    }

    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    public void testAddOrder() throws Exception {
        int[] temp = {1, 1};
        boolean res = instance.addOrder(temp);
        assertTrue(res);
        assertSame(instance.order, temp);

        temp = new int[]{2, 1, 3, 4};
        res = instance.addOrder(temp);
        assertFalse(res);
    }


    public void testAddShapesForOnePattern() throws Exception {
        int[][] temp = new int[2][];
        temp[0] = new int[]{1, 2};
        temp[1] = new int[]{3, 4};
        boolean res;

        res = instance.addShapesForOnePattern(0, temp[0]);
        assertTrue(res);
        res = instance.addShapesForOnePattern(1, temp[1]);
        assertTrue(res);

        Assert.assertArrayEquals(instance.shapesOnPatterns, temp);
    }


    public void testGetSolution() throws Exception {
        //Test 1
        int[][] temp = {{2, 2}, {0, 1}};
        instance.addShapesForOnePattern(0, temp[0]);
        instance.addShapesForOnePattern(1, temp[1]);
        int[] order = {10, 12};
        instance.addOrder(order);
        RealPointValuePair res = instance.getSolution();
        assertEquals(6.0, res.getPoint()[0]);
        assertEquals(0.0, res.getPoint()[1]);


        //Test 2
        instance = new Distribution(2, 3);
        temp = new int[][]{{1, 2, 0}, {0, 2, 2}};
        instance.addShapesForOnePattern(0, temp[0]);
        instance.addShapesForOnePattern(1, temp[1]);
        order = new int[]{80, 250, 50};
        instance.addOrder(order);
        res = instance.getSolution();
        assertEquals(100.0, res.getPoint()[0]);
        assertEquals(25.0, res.getPoint()[1]);


        //Test 3
        instance = new Distribution(3, 3);
        temp = new int[][]{{1, 0, 0}, {0, 2, 0}, {0, 0, 1}};
        instance.addShapesForOnePattern(0, temp[0]);
        instance.addShapesForOnePattern(1, temp[1]);
        instance.addShapesForOnePattern(2, temp[2]);
        order = new int[]{80, 250, 50};
        instance.addOrder(order);
        res = instance.getSolution();
        assertEquals(80.0, res.getPoint()[0]);
        assertEquals(125.0, res.getPoint()[1]);
        assertEquals(50.0, res.getPoint()[2]);
    }
}