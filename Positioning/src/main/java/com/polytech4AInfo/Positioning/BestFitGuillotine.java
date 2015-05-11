package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Toromis on 10/05/2015.
 */
public class BestFitGuillotine implements Guillotine{

    @Override
    public void cut(Sheet sheet, int index, Shape shape) {
        Sheet sheet1 = sheet.clone(), sheet2 = sheet.clone();
        new LengthGuillotine().cut(sheet1, index, shape);
        new BreadthGuillotine().cut(sheet2, index, shape);

        PlacedShape bin1 = sheet1.getBins().get(0),
                bin2 = sheet1.getBins().get(1),
                bin3 = sheet2.getBins().get(0),
                bin4 =sheet2.getBins().get(1),
                bin0 = sheet.getBins().remove(index);
        if (bin0.isLying()) {
            if (shape.isLying()) {
                if (bin0.getLength() - shape.getLength() >= bin0.getBreadth() - shape.getBreadth()) {
                    addBins(sheet,bin1,bin2);
                } else {
                    addBins(sheet, bin3, bin4);
                }
            } else {
                if (bin0.getLength() - shape.getBreadth() >= bin0.getBreadth() - shape.getLength()) {
                    addBins(sheet, bin1, bin2);
                } else {
                    addBins(sheet, bin3, bin4);
                }
            }
        }else{
            if (shape.isLying()) {
                if (bin0.getBreadth() - shape.getLength() >= bin0.getLength() - shape.getBreadth()) {
                    addBins(sheet, bin1, bin2);
                } else {
                    addBins(sheet, bin3, bin4);
                }
            } else {
                if (bin0.getBreadth() - shape.getBreadth() >= bin0.getLength() - shape.getLength()) {
                    addBins(sheet,bin1,bin2);
                } else {
                    addBins(sheet,bin3,bin4);
                }
            }
        }
    }

    public void addBins (Sheet sheet, PlacedShape bin1, PlacedShape bin2){
        if (bin1.getArea()>=bin2.getArea()){
            sheet.getBins().add(bin1);
            sheet.getBins().add(bin2);
        }else{
            sheet.getBins().add(bin2);
            sheet.getBins().add(bin1);
        }
    }
}
