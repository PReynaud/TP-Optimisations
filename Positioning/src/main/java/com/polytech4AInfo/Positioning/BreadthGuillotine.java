package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class BreadthGuillotine implements Guillotine {

    public void cut(Sheet sheet, int index, Shape shape) {
        PlacedShape bin = sheet.getBins().remove(index);
        sheet.getBins().add(new PlacedShape(bin.getLength()-shape.getLength(),bin.getBreadth(),bin.getPositionx()+shape.getLength(),bin.getPositiony()));
        sheet.getBins().add(new PlacedShape(shape.getLength(),bin.getBreadth()-shape.getBreadth(), bin.getPositionx(), bin.getPositiony()+shape.getBreadth()));
    }
}
