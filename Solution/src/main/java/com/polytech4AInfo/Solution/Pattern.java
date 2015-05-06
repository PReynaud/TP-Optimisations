package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Positioning.PositioningStream;
import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
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

    public Sheet getSheet(){ return this.sheet;}

    public Sheet savePatternInFile(){
        return positionings.isPossibleAndSave(sheet, pattern);
    }

    public Pattern clone(){
        Sheet newSheet = sheet.clone();
        ArrayList<ShapeGroup> newArrayList = new ArrayList<ShapeGroup>();
        for(ShapeGroup oneShapeGroup: this.pattern){
            newArrayList.add(oneShapeGroup.clone());
        }

        Pattern newPattern = new Pattern(newArrayList, newSheet);
        return newPattern;
    }

    public float getFillingRatio (){
        return (float)pattern.parallelStream().mapToInt(p->p.getArea()*p.getNumber()).sum()/(float)(sheet.getLength()*sheet.getBreadth());
    }
}
