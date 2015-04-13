package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class BreadthGuillotine implements Guillotine {

    /**
     * {@inheritDoc}
     */
    public void cut(Sheet sheet, int index, Shape shape) {
        PlacedShape bin = sheet.getBins().remove(index);
        if (shape.isLying()&& bin.isLying()){
            sheet.getBins().add(new PlacedShape(shape.getLength(),bin.getBreadth()-shape.getBreadth(), bin.getPositionx(), bin.getPositiony()+shape.getBreadth()));
            sheet.getBins().add(new PlacedShape(bin.getLength()-shape.getLength(),bin.getBreadth(),bin.getPositionx()+shape.getLength(),bin.getPositiony()));
        }
        else if(!shape.isLying()&&bin.isLying()){
            sheet.getBins().add(new PlacedShape(shape.getBreadth(),bin.getBreadth()-shape.getLength(),bin.getPositionx(),bin.getPositiony()+shape.getLength()));
            sheet.getBins().add(new PlacedShape(bin.getLength()-shape.getBreadth(),bin.getBreadth(), bin.getPositionx()+shape.getBreadth(), bin.getPositiony()));
        }
        else if (shape.isLying()){
            sheet.getBins().add(new PlacedShape(shape.getLength(),bin.getLength()-shape.getBreadth(), bin.getPositionx(), bin.getPositiony()+shape.getBreadth()));
            sheet.getBins().add(new PlacedShape(bin.getBreadth()-shape.getLength(),bin.getLength(),bin.getPositionx()+shape.getLength(),bin.getPositiony()));
        }
        else {
            sheet.getBins().add(new PlacedShape(shape.getBreadth(),bin.getLength()-shape.getLength(),bin.getPositionx(),bin.getPositiony()+shape.getLength()));
            sheet.getBins().add(new PlacedShape(bin.getBreadth()-shape.getBreadth(),bin.getLength(), bin.getPositionx()+shape.getBreadth(), bin.getPositiony()));
        }
    }
}
