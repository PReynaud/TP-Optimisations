package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.Positioning;
import com.polytech4AInfo.Positioning.PositioningStream;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private ArrayList<ShapeGroup> pattern;

    /**
     * Constructor
     * @param pattern List of shapes in a pattern
     */
    public Pattern(ArrayList<ShapeGroup> pattern) {
        this.pattern = pattern;
    }

    /**
     * Wille return if the pattern is valid
     * @param positioning
     * @param sheet
     * @return True if valid, else false
     */
    public boolean isPossible(PositioningStream positioning,Sheet sheet) {
        return positioning.isPossible(sheet,pattern);
    }
}
