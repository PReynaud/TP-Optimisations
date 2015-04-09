package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 08/04/2015.
 */
public class PositioningStream {
    private ArrayList<Positioning> positionings;

    public PositioningStream(){
        this.positionings = new ArrayList<Positioning>();
        this.positionings.add(new Positioning(new LengthGuillotine(),Shape.Comparators.BREADTH));
        this.positionings.add(new Positioning(new BreadthGuillotine(),Shape.Comparators.LENGTH));
        this.positionings.add(new Positioning(new RatioGuillotine(),Shape.Comparators.AREA));
        this.positionings.add(new Positioning(new RatioGuillotine(),Shape.Comparators.AREA));
    }

    public PositioningStream(ArrayList<Positioning> positionings){
        this.positionings=positionings;
    }

    public boolean isPossible(Sheet sheet, ArrayList<ShapeGroup> shapes){
        return positionings
                .parallelStream()
                .filtg
        return false;
    }
}
