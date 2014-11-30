package com.scttsc.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.util.Date;

public class ExcelUtil {
    /**
     * 获取workbook
     *
     * @param fis
     * @return
     * @throws IOException
     */
    public static HSSFWorkbook getWorkbook(POIFSFileSystem fis)
            throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        return workbook;
    }

    /**
     * 获取一个已经存在的sheet i,第几个sheet
     *
     * @return
     */
    public static HSSFSheet getSheet(HSSFWorkbook workbook, int i)
            throws IOException {
        HSSFSheet sheet = workbook.getSheetAt(i);
        return sheet;
    }

    public static HSSFSheet createSheet(HSSFWorkbook workbook, int i)
            throws IOException {
        HSSFSheet sheet = workbook.createSheet(i + "");
        return sheet;
    }

    /**
     * 创建行
     *
     * @param i
     */
    public static HSSFRow createRow(HSSFSheet sheet, int i) {
        HSSFRow row = sheet.createRow(i);
        return row;
    }

    /**
     * 创建列
     */

    /**
     * 创建并且返回一个模板列
     *
     * @param i
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int i) {
        HSSFCell cell = row.createCell((short) i);
        // cell.setCellStyle(style);
        return cell;
    }

    /**
     * 创建某列基础上填充值
     */
    public static void createCellValue(HSSFRow row, int i, Object object) {
        HSSFCell cell = createCell(row, i);
        if (object instanceof String) {
            cell.setCellValue(object.toString());
        }
        if (object instanceof Date) {
            cell.setCellValue((Date) object);
        }
    }

    /**
     * 得到行
     */
    public static HSSFRow getRow(HSSFSheet sheet, int i) {
        HSSFRow row = sheet.getRow(i);
        return row;
    }

    /**
     * 得到列
     *
     * @param row
     * @param j
     * @return
     */
    public static HSSFCell getCell(HSSFRow row, int j) {
        HSSFCell cell = row.getCell((short) (j));//Excel
        return cell;
    }

    /**
     * 得到列设置为str
     *
     * @param i
     * @param object
     * @return
     */

    public static HSSFCell setCellValue(HSSFRow row, int i, Object object) {
        HSSFCell cell = row.getCell((short) i);
        if (object instanceof String) {
            cell.setCellValue(object.toString());
        }
        if (object instanceof Date) {
            cell.setCellValue((Date) object);
        }
        return cell;
    }

    /**
     * 合并单元格 Merge Cells
     */
    public static void mergeCells(HSSFSheet sheet, int startrow, int startcol,
                                  int endrow, int endcol) {
        sheet.addMergedRegion(new Region(startrow, (short) startcol, endrow,
                (short) endcol));
    }

    /**
     * @param sheet
     * @param startRow 插入开始行
     * @param rows插入行数 从startRow插入rows行，格式完全与start行相同
     * @author 隆科
     */

    public static void insertRows(HSSFSheet sheet, int startRow, int rows) {
        sheet.shiftRows(startRow, sheet.getLastRowNum(), rows, true, false);
        HSSFRow targetRow = sheet.getRow(startRow + rows);// 创建行的样式与移动目标行格式相同
        for (int i = 0; i < rows; i++) {
            HSSFRow sourceRow = null;// 原始位置
            HSSFCell sourceCell = null;
            HSSFCell targetCell = null;
            sourceRow = sheet.createRow(startRow);// 创建行
            sourceRow.setHeight(targetRow.getHeight());

            for (int m = targetRow.getFirstCellNum(); m < targetRow
                    .getPhysicalNumberOfCells(); m++) {
                sourceCell = sourceRow.createCell((short) m);// 创建列
                targetCell = targetRow.getCell(m);
                sourceCell.setCellStyle(targetCell.getCellStyle());
                sourceCell.setCellType(targetCell.getCellType());
            }
            startRow++;
        }
    }

    /**
     * 设置默认列宽以及列高
     */
    public static void setWidthAndHeight(HSSFSheet sheet, int i, int j) {
        sheet.setDefaultColumnWidth((short) i);
        sheet.setDefaultRowHeight((short) j);
    }

    /**
     * 设置单元格样式 垂直居中
     *
     * @return
     */
    public static HSSFCellStyle setStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置文本自动换行
        style.setWrapText(true);
        // 设置文本局中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFont(getFont(workbook));

        // 设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return style;
    }

    /**
     * 设置单元格样式 垂直靠上
     *
     * @return
     */
    public static HSSFCellStyle setStyle2(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置文本自动换行
        style.setWrapText(true);
        // 设置文本局中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 垂直靠上
        style.setFont(getFont(workbook));

        // 设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return style;
    }

    /**
     * 生成表头样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle setHeaderStype(HSSFWorkbook workbook) {
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样
        style.setFont(font);
        return style;
    }

    /**
     * 设置字体大小，颜色
     */
    public static HSSFFont getFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        return font;
    }

    /**
     * 冻结窗口
     *
     * @param sheet
     * @param colSplit
     * @param rowSplit
     */
    public static void createFreezePane(HSSFSheet sheet, int colSplit,
                                        int rowSplit) {
        sheet.createFreezePane(colSplit, rowSplit);
    }

}
