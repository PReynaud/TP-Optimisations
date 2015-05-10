package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Dimitri on 08/04/2015.
 */
public class PositioningStream {
    private ArrayList<Positioning> positionings;

    public PositioningStream(){
        this.positionings = new ArrayList<Positioning>();
        this.positionings.add(new Positioning(new LengthGuillotine(),Shape.Comparators.BREADTH));
        this.positionings.add(new Positioning(new BreadthGuillotine(),Shape.Comparators.LENGTH));
        this.positionings.add(new Positioning(new RatioGuillotine(),Shape.Comparators.RATIO));
        this.positionings.add(new Positioning(new BestFitGuillotine(),Shape.Comparators.AREA));
    }

    public PositioningStream(ArrayList<Positioning> positionings){
        this.positionings=positionings;
    }

    /**
     * Test if a pattern fits in a sheet
     * @param sheet content for the shapes
     * @param shapes pattern to put on the sheet
     * @return boolean : true if the pattern fits the sheet
     */
    public boolean isPossible(Sheet sheet, ArrayList<ShapeGroup> shapes){
        //Parallel execution catch the first match
        try{ positionings
                .parallelStream()
                .filter(p->p.isPossible(sheet,shapes)!=null)
                .findAny()
                .get();
            return true;
        }catch(NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Test if a pattern fits in a sheet
     * @param sheet content for the shapes
     * @param shapes pattern to put on the sheet
     * @return boolean : true if the pattern fits the sheet
     */
    public Sheet isPossibleAndSave(Sheet sheet, ArrayList<ShapeGroup> shapes){
        for(int i = 0; i < positionings.size(); i++){
            Sheet testedSheet=positionings.get(i).isPossible(sheet, shapes);
            if (testedSheet!=null){
                return testedSheet;
            }
        }
        return null;
    }
}
