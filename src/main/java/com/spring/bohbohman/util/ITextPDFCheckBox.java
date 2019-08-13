package com.spring.bohbohman.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Auther: xueyb
 * @Date: 2019/1/9 16:03
 * @Description:
 */
public class ITextPDFCheckBox {
    public static final String DEST = "//Users//sqbj0042//school-system//20181217//testCheck.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ITextPDFCheckBox().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        //定义字体
        BaseFont bfChinese = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", false );
        Font fNormal = new Font(bfChinese, 11, Font.NORMAL);
        //新建Table两列
        PdfPTable table = new PdfPTable(2);
        //定义每列的宽度
        table.setWidths(new int[]{100,200});
        //第一列start
        PdfPCell head = new PdfPCell(new Phrase("状态:",fNormal));
        head.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        head.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        head.setMinimumHeight(30);
        table.addCell(head);
        //第一列end
        //第二列start
        //新建table t1
        PdfPTable t1 = new PdfPTable(4);
        t1.setWidths(new int[]{30,60,30,60});

        PdfPCell c0 = new PdfPCell();
        c0.setCellEvent(new ITextPDFCheckboxCellEvent("1", true));
        c0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c0.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c0.setFixedHeight(10);
        c0.setPadding(10);


        PdfPCell c1 = new PdfPCell(c0);
//        c1.setIndent(15);
//        c1.setPadding(10);
        c1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        t1.addCell(c1);

        PdfPCell c11 = new PdfPCell(new Phrase("是",fNormal));
        c11.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c11.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c11.setMinimumHeight(30);
        t1.addCell(c11);

        PdfPCell c2 = new PdfPCell();
        c2.setCellEvent(new ITextPDFCheckboxCellEvent("0", false));
        c2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c2.setFixedHeight(10);
        c2.setMinimumHeight(30);
        t1.addCell(c2);

        PdfPCell c21 = new PdfPCell(new Phrase("否",fNormal));
        c21.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c21.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c21.setMinimumHeight(30);
        t1.addCell(c21);
        //将t1嵌入到 第二列中
        PdfPCell checkbox = new PdfPCell(t1);
        table.addCell(checkbox);
        //第二列end
        document.add(table);
        document.close();
        System.out.println("===========:end");
    }

}
