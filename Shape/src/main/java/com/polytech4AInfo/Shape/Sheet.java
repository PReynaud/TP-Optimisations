package com.polytech4AInfo.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Dimitri on 16/03/2015.
 */
public class Sheet extends Shape {
    private Map<Shape, Point> content;
    private Map<Shape, Point> blankSquares;

    public Sheet(int height, int width) {
        super(height, width);
        content = new HashMap<Shape, Point>();
        blankSquares = new HashMap<Shape, Point>();
        blankSquares.put(new Shape(height, width), new Point(0, 0));
    }

    public boolean putShape(Shape shape) {
        Iterator iterator = blankSquares.keySet().iterator();
        while (iterator.hasNext()) {
            Shape key = (Shape) iterator.next();
        }
        return false;
    }
}
