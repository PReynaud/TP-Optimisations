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
        //TODO
        int[][] temp = {{2, 2}, {0, 1}};
        instance.addShapesForOnePattern(0, temp[0]);
        instance.addShapesForOnePattern(1, temp[1]);

        int[] order = {10, 12};
        instance.addOrder(order);

        RealPointValuePair res = instance.getSolution();
        System.out.println(res);
        assertEquals(6.0, res.getPoint()[0]);
        assertEquals(0.0, res.getPoint()[1]);
    }
}