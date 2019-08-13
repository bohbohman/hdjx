package com.spring.bohbohman.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

/**
 * @Auther: xueyb
 * @Date: 2019/1/9 16:09
 * @Description:
 */
public class ITextPDFCheckboxCellEvent implements PdfPCellEvent {

    // The name of the check box field
    protected String name;
    protected boolean flag ;
    // We create a cell event
    public ITextPDFCheckboxCellEvent(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }
    // We create and add the check box field
    public void cellLayout(PdfPCell cell, Rectangle position,
                           PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        // define the coordinates of the middle
        float x = (position.getLeft() + position.getRight()) / 2;
        float y = (position.getTop() + position.getBottom()) / 2;
        // define the position of a check box that measures 20 by 20
        //画勾
        Rectangle rect = new Rectangle(x - 9, y - 5, x - 1, y + 4);
        RadioCheckField checkbox = new RadioCheckField(writer, rect, name, "On");
        checkbox.setCheckType(RadioCheckField.TYPE_CHECK);
        //checkbox.setBackgroundColor(new GrayColor(0.8f));

        if(flag){
            //设置为选中状态
            checkbox.setChecked(true);
        }
        else{
            checkbox.setChecked(false);
        }
        //画框
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setColorStroke(BaseColor.BLACK);
        canvas.setLineDash(1f);
        canvas.rectangle(x - 10, y - 6, 10,10);
        canvas.stroke();

        try {
            writer.addAnnotation(checkbox.getCheckField());
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
}
