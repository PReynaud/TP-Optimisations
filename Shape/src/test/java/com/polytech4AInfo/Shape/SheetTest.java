package com.polytech4AInfo.Shape;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Pierre on 28/03/2015.
 */
public class SheetTest extends TestCase {
    private Sheet instance;

    @Override
    protected void setUp(){
        instance = new Sheet(150, 100);
    }

    @Test
    public void testGetContent() throws Exception {
        ArrayList<PlacedShape> expected = new ArrayList<PlacedShape>();
        assertEquals(expected, instance.getContent());
    }

    @Test
    public void testGetBins() throws Exception {
        ArrayList<PlacedShape> expected = new ArrayList<PlacedShape>();
        expected.add(new PlacedShape(150, 100));
        assertEquals(expected.get(0).getLength(), instance.getBins().get(0).getLength());
        assertEquals(expected.get(0).getBreadth(), instance.getBins().get(0).getBreadth());
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(150, instance.getLength());
    }

    @Test
    public void testGetBreadth() throws Exception {
        assertEquals(100, instance.getBreadth());
    }
}