package com.polytech4AInfo.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Dimitri on 16/03/2015.
 */
public class Sheet extends Shape {
    private ArrayList<Shape> content;
    private ArrayList<Shape> blankSquares;

    public ArrayList<Shape> getContent() {
        return content;
    }

    public ArrayList<Shape> getBlankSquares() {
        return blankSquares;
    }

    public Sheet(int height, int width) {
        super(height, width);
        content = new ArrayList<Shape>();
        blankSquares = new ArrayList<Shape>();
        blankSquares.add(new Shape(height, width));
    }

    public boolean putShape(Shape shape, Shape blankSquare) {
        if (shape.getWidth() <= blankSquare.getWidth() && shape.getHeight() <= blankSquare.getHeight()) {
            content.add(shape);
            return true;
        }
        return false;
    }
}
