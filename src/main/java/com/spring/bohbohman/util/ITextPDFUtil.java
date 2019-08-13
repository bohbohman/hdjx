package com.spring.bohbohman.util;

import com.google.common.collect.Lists;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.spring.bohbohman.bean.common.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: xueyb
 * @Date: 2019/1/9 11:30
 * @Description:
 */
public class ITextPDFUtil {

    public static void main(String[] args) throws DocumentException, IOException {
        String filePath = "//Users//sqbj0042//school-system//20181217//poi.PDF";
        createPDF(filePath);
    }

    public static File createPDF(String filePath) throws DocumentException, IOException  {
        List<User> users = Lists.newArrayList();
        // 循环添加对象
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(i);
            user.setInfo("开发者测试"+i);
            user.setName("测试"+i);
            users.add(user);
        }

        // 创建Document对象(页面的大小为A4,左、右、上、下的页边距为10)
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        // 建立书写器
        //生成文件位置
        File outFile = new File(filePath);
        //如果不存在临时文件夹，则创建文件夹
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(outFile);
        //PDFWriter可以将文档存成PDF文件；HtmlWriter可以将文档存成html文件
        PdfWriter.getInstance(document, outputStream);
        // 设置相关的参数
        ITextPDFUtil.setParameters(document, "彩虹城工单受理单", "受理单", "彩虹城 工单 受理单", "xueyb", "xueyb");
        // 打开文档
        document.open();

        // 使用iTextAsian.jar中的字体
        //BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        // 设置pdf的基础样式；参数1:(1)使用iText中的字体,字体类行为宋体，（2）使用资源字体classpath：/SIMYOU.TTF （3）使用windows系统字体C:/Windows/Fonts/SIMYOU.TTF
        BaseFont baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
        // 文档标题对应的样式 参数1：相当于字体（宋体） 参数2：字体大小 参数3：是否加粗，斜体 参数4：字体颜色
        Font headerFont = new Font(baseFont, 16, Font.NORMAL, BaseColor.BLACK);
        // headerParagraph  文档标题
        Paragraph headerParagraph = new Paragraph("彩虹城工单受理单", headerFont);
        //文字居中
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        //将header添加至文档中
        document.add(headerParagraph);

        //添加空行
        Paragraph emptyLine = new Paragraph(" ");
        emptyLine.setLeading(20);
        document.add(emptyLine);

        PdfPTable table = ITextPDFUtil.setTable(users);

        document.add(table);

        // 关闭文档
        document.close();
        return outFile;
    }

    public static PdfPTable setTable(List<User> users) throws BadElementException, IOException {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        //无边框表头
        PdfPTable table1 = new PdfPTable(3);
        PdfPCell headerCell1 = ITextPDFUtil.setTableCell("业务工单号：   110110110110", 7);
        headerCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(headerCell1);
        PdfPCell headerCell2 = ITextPDFUtil.setTableCell("受理人：   李元芳", 7);
        headerCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(headerCell2);
        PdfPCell headerCell3 = ITextPDFUtil.setTableCell("受理时间：   2019-01-08 8:00:00", 7);
        headerCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(headerCell3);

        table.addCell(table1);

        //受理单正文表
        float[] cellWidths = {0.1f, 0.2f, 0.1f, 0.2f, 0.1f, 0.3f};
        PdfPTable table2 = new PdfPTable(cellWidths);
        //第一行
        table2.addCell(ITextPDFUtil.setTableCell("姓名", 1));
        table2.addCell(ITextPDFUtil.setTableCell("刘文杰", 2));
        table2.addCell(ITextPDFUtil.setTableCell("电话", 2));
        table2.addCell(ITextPDFUtil.setTableCell("151101157669", 2));
        table2.addCell(ITextPDFUtil.setTableCell("住址", 2));
        table2.addCell(ITextPDFUtil.setTableCell("彩虹城一区1号楼2单元302室", 3));

        //第二行
        table2.addCell(ITextPDFUtil.setTableCell("分类", 4));
        table2.addCell(ITextPDFUtil.setTableCell("报事报修", 5));
        table2.addCell(ITextPDFUtil.setTableCell("紧急程度", 5));
        table2.addCell(ITextPDFUtil.setTableCell("紧急", 5));
        table2.addCell(ITextPDFUtil.setTableCell("预约时间", 5));
        table2.addCell(ITextPDFUtil.setTableCell("2019-01-08 8:00:00", 6));

        //第三行
        table2.addCell(ITextPDFUtil.setTableCell("地址", 4));
        PdfPCell cell3_2 = ITextPDFUtil.setTableCell("彩虹城一区1号楼2单元302室", 5);
        cell3_2.setColspan(3);
        cell3_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3_2.setPadding(15f);
        table2.addCell(cell3_2);
        table2.addCell(ITextPDFUtil.setTableCell("接待方式", 5));
        table2.addCell(ITextPDFUtil.setTableCell("电话预约", 6));

        //第四行
        table2.addCell(ITextPDFUtil.setTableCell("受理内容", 11));
        PdfPCell cell4_2 = ITextPDFUtil.setTableCell("房屋下水道管道漏水", 6);
        cell4_2.setColspan(5);
        cell4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4_2.setPadding(15f);
        table2.addCell(cell4_2);

        //第五行
        table2.addCell(ITextPDFUtil.setTableCell("成本材料", 4));
        PdfPCell cell5_2 = ITextPDFUtil.setTableCell("下水管2米", 5);
        cell5_2.setColspan(3);
        cell5_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell5_2.setPadding(15f);
        table2.addCell(cell5_2);
        table2.addCell(ITextPDFUtil.setTableCell("合计", 5));
        table2.addCell(ITextPDFUtil.setTableCell("20元", 6));

        //第六行
        table2.addCell(ITextPDFUtil.setTableCell("派单时间", 4));
        table2.addCell(ITextPDFUtil.setTableCell("2019-01-08 8:00:00", 5));
        table2.addCell(ITextPDFUtil.setTableCell("开工时间", 5));
        table2.addCell(ITextPDFUtil.setTableCell("2019-01-08 8:00:00", 5));
        table2.addCell(ITextPDFUtil.setTableCell("完工时间", 5));
        table2.addCell(ITextPDFUtil.setTableCell("2019-01-08 8:00:00", 6));

        //第七行
        table2.addCell(ITextPDFUtil.setTableCell("处理结果", 4));
        PdfPCell cell7_2 = ITextPDFUtil.setTableCell("修理完毕，并且更换了管道", 5);
        cell7_2.setColspan(3);
        cell7_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell7_2.setPadding(15f);
        table2.addCell(cell7_2);
        table2.addCell(ITextPDFUtil.setTableCell("处理人", 5));
        table2.addCell(ITextPDFUtil.setTableCell("秦康康", 6));

        //第八行
        table2.addCell(ITextPDFUtil.setTableCell("满意度", 4));
        float[] widths = {0.05f, 0.03f, 0.14f, 0.03f, 0.1f, 0.03f, 0.1f, 0.03f, 0.12f, 0.03f, 0.34f};
        PdfPTable table3 = new PdfPTable(widths);
        table3.setWidthPercentage(50);
        table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        //一个空cell
        table3.addCell(ITextPDFUtil.setTableCell("", 12));

        Image image1 = Image.getInstance("src/main/resources/static/img/box1.png");
        Image image2 = Image.getInstance("src/main/resources/static/img/box2.png");

        PdfPCell cellBox = new PdfPCell(image1, true);
        //cellBox.setCellEvent(new ITextPDFCheckboxCellEvent("0", true));
        cellBox.setBorder(Rectangle.NO_BORDER);
        cellBox.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBox.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cellBox);
        table3.addCell(ITextPDFUtil.setTableCell("非常满意", 12));
        PdfPCell cellBox1 = new PdfPCell(image2, true);
        //cellBox1.setCellEvent(new ITextPDFCheckboxCellEvent("1", true));
        cellBox1.setBorder(Rectangle.NO_BORDER);
        cellBox1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBox1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cellBox1);
        table3.addCell(ITextPDFUtil.setTableCell("满意", 12));
        PdfPCell cellBox2 = new PdfPCell(image1, true);
        //cellBox2.setCellEvent(new ITextPDFCheckboxCellEvent("2", true));
        cellBox2.setBorder(Rectangle.NO_BORDER);
        cellBox2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBox2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cellBox2);
        table3.addCell(ITextPDFUtil.setTableCell("一般", 12));
        PdfPCell cellBox3 = new PdfPCell(image1, true);
        //cellBox3.setCellEvent(new ITextPDFCheckboxCellEvent("3", true));
        cellBox3.setBorder(Rectangle.NO_BORDER);
        cellBox3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBox3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cellBox3);
        table3.addCell(ITextPDFUtil.setTableCell("不满意", 12));
        PdfPCell cellBox4 = new PdfPCell(image1, true);
        //cellBox4.setCellEvent(new ITextPDFCheckboxCellEvent("3", true));
        cellBox4.setBorder(Rectangle.NO_BORDER);
        cellBox4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBox4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cellBox4);
        table3.addCell(ITextPDFUtil.setTableCell("非常不满意", 12));
        PdfPCell cell8_2 = new PdfPCell(table3);
        cell8_2.disableBorderSide(5);
        cell8_2.setBorderColorRight(BaseColor.BLACK);
        cell8_2.setBorderColorBottom(BaseColor.GRAY);
        cell8_2.setColspan(5);
        /*PdfPCell cell8 = ITextPDFUtil.setTableCell("口 非常满意  口 满意   口 一般   口 不满意");
        cell8.setColspan(5);*/

        table2.addCell(cell8_2);

        //第九行
        table2.addCell(ITextPDFUtil.setTableCell("回访人", 4));
        table2.addCell(ITextPDFUtil.setTableCell("韩磊", 5));
        table2.addCell(ITextPDFUtil.setTableCell("回访方式", 5));
        table2.addCell(ITextPDFUtil.setTableCell("电话回访", 5));
        table2.addCell(ITextPDFUtil.setTableCell("回访时间", 5));
        table2.addCell(ITextPDFUtil.setTableCell("2019-01-09 8:00:00", 6));

        //第十行
        table2.addCell(ITextPDFUtil.setTableCell("回访记录", 11));
        PdfPCell cell10_2 = ITextPDFUtil.setTableCell("记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录记录", 6);
        cell10_2.setColspan(5);
        cell10_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell10_2.setPadding(15f);

        table2.addCell(cell10_2);

        //第十一行
        table2.addCell(ITextPDFUtil.setTableCell("财务签字", 8));
        PdfPCell cell11_2 = ITextPDFUtil.setTableCell("", 9);
        cell11_2.setColspan(3);
        table2.addCell(cell11_2);
        table2.addCell(ITextPDFUtil.setTableCell("业主签字", 9));
        table2.addCell(ITextPDFUtil.setTableCell("", 10));

        table.addCell(table2);
        return table;

    }
    /**
     * 设置字体编码格式
     * @return
     */
    public static Font setFont(){
        BaseFont baseFont = null;
        try {
            //baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(baseFont, 8, Font.NORMAL, BaseColor.BLACK);
        return font;
    }
    /**
     * 设置cell
     * @param name
     * @return
     * @throws BadElementException
     */
    public static PdfPCell setTableCell(String name, Integer type) {

        Phrase phrase = new Phrase(name, ITextPDFUtil.setFont());
        //Paragraph paragraph = new Paragraph(name, ITextPDFUtil.setFont());
        //paragraph.setAlignment(1);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setUseAscender(true);
        //单元格水平对齐方式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //单元格垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //设置最小高度
        cell.setMinimumHeight(30f);
        //设置行间距
        cell.setLeading(5f, 1f);

        if (type == 1) {
            //设置右下边框灰色，左上黑色
            cell.setBorderColorLeft(BaseColor.BLACK);
            cell.setBorderColorTop(BaseColor.BLACK);
            cell.setBorderColorRight(BaseColor.GRAY);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 2) {
            //上边框黑色 右下边框灰色 左边框隐藏
            cell.disableBorderSide(4);
            cell.setBorderColorTop(BaseColor.BLACK);
            cell.setBorderColorRight(BaseColor.GRAY);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 3) {
            //上右边框黑色 下边框灰色 左边框隐藏
            cell.disableBorderSide(4);
            cell.setBorderColorTop(BaseColor.BLACK);
            cell.setBorderColorRight(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 4) {
            //左边框黑色 右下边框灰色 上边框隐藏
            cell.disableBorderSide(1);
            cell.setBorderColorLeft(BaseColor.BLACK);
            cell.setBorderColorRight(BaseColor.GRAY);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 5) {
            //设置右下边框灰色 左上边框隐藏
            cell.disableBorderSide(5);
            cell.setBorderColorRight(BaseColor.GRAY);
            cell.setBorderColorBottom(BaseColor.GRAY);
        }  else if (type == 6) {
            //右边框黑色 下边框灰色 左上边框隐藏
            cell.disableBorderSide(5);
            cell.setBorderColorRight(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 7) {
            //无边框
            cell.setBorder(Rectangle.NO_BORDER);
        } else if (type == 8) {
            //左下黑色 右上隐藏
            cell.disableBorderSide(9);
            cell.setBorderColorLeft(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.BLACK);
        } else if (type == 9) {
            //下黑色 左右上隐藏
            cell.disableBorderSide(13);
            cell.setBorderColorBottom(BaseColor.BLACK);
        } else if (type == 10) {
            //右下黑色 左上隐藏
            cell.disableBorderSide(5);
            cell.setBorderColorRight(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.BLACK);
        } else if (type == 11) {
            //左黑色 下灰色 右上隐藏
            cell.disableBorderSide(9);
            cell.setBorderColorLeft(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.GRAY);
        } else if (type == 12) {
            //无边框 水平居左
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        return cell;
    }

    /**
     * 设置相关参数
     * @param document
     * @return
     */
    public static Document setParameters(Document document,String title,String subject,String keywords,String author,
                                         String creator){
        // 设置标题
        document.addTitle(title);
        // 设置主题
        document.addSubject(subject);
        // 设置关键字
        document.addKeywords(keywords);
        // 设置作者
        document.addAuthor(author);
        // 设置创建者
        document.addCreator(creator);
        // 设置生产者
        document.addProducer();
        // 设置创建日期
        document.addCreationDate();

        return document;
    }

}
