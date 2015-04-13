package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.PositioningStream;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private ArrayList<ShapeGroup> pattern;
    private Sheet sheet;
    private PositioningStream positionings;

    /**
     * Constructor with default positioning
     * @param pattern : List of shape in a pattern
     * @param sheet : Content for the shapes
     */
    public Pattern(ArrayList<ShapeGroup> pattern,Sheet sheet) {
        this.pattern = pattern;
        this.sheet = sheet;
        this.positionings = new PositioningStream();
    }

    /**
     * Constructor
     * @param pattern : List of shape in a pattern
     * @param sheet : Content for the shapes
     * @param positionings : List of methods to fill a pattern
     */
    public Pattern(ArrayList<ShapeGroup> pattern,Sheet sheet, PositioningStream positionings) {
        this.pattern = pattern;
        this.sheet = sheet;
        this.positionings = positionings;
    }

    /**
     * Wille return if the pattern is valid
     * @return True if valid, else false
     */
    public boolean isPossible() {
        return positionings.isPossible(sheet, pattern);
    }

    public int getNumberOfShapes(){
        return pattern.size();
    }

    public ArrayList<ShapeGroup> getPattern() {
        return pattern;
    }
}
