package com.xiaohan.cn.exporter;

import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.result.ApiResultCode;
import com.xiaohan.cn.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * ExportEntity 导出实体
 *
 * @author teddy
 * @since 2022/12/30
 */
public class ExportEntity {
    /**
     * 导出模版
     */
    private Resource template;

    /**
     * 模版列占用行数
     */
    private int headerRowNum = 1;

    /**
     * 自定义导出sheet名称
     */
    private String sheetName;

    /**
     * 自定义导出列名称
     */
    private String[] headerRowNames;

    /**
     * 导出数据
     */
    private List<Object[]> datas;

    /**
     * 列名称单元格样式
     */
    private CellStyle headerCellStyle;

    /**
     * 数据单元格样式
     */
    private CellStyle cellStyle;

    /**
     * 自定义导出默认列宽度
     */
    private int defalutColumnWidth = 18;

    /**
     * 导出名称，如export.xlsx
     */
    private String exportName;

    /**
     * workbook
     */
    protected Workbook workbook;

    /**
     * 自定义导出
     *
     * @param sheetName      sheet名称
     * @param headerRowNames 列名称
     * @param datas          数据
     */
    public ExportEntity(String sheetName, String[] headerRowNames, List<Object[]> datas) {
        this(sheetName, null, headerRowNames, datas);
    }

    /**
     * 自定义导出
     *
     * @param sheetName      sheet名称
     * @param exportName     导出名称
     * @param headerRowNames 列名称
     * @param datas          数据
     */
    public ExportEntity(String sheetName, String exportName, String[] headerRowNames, List<Object[]> datas) {
        if (!(headerRowNames != null && headerRowNames.length > 0)) {
            throw new IllegalArgumentException(String.valueOf("Header row names can not be empty"));
        }
        this.sheetName = sheetName;
        this.exportName = exportName;
        this.headerRowNames = headerRowNames.clone();
        this.datas = datas;

        initWorkbook();
    }

    /**
     * 按模版导出
     *
     * @param template 模版资源
     * @param datas    数据
     */
    public ExportEntity(Resource template, List<Object[]> datas) {
        if (!(template != null && template.exists())) {
            throw new IllegalArgumentException(String.valueOf("Template not exists"));
        }
        this.template = template;
        this.datas = datas;

        initWorkbook();
    }

    /**
     * 初始化workbook
     */
    protected void initWorkbook() {

        String ext = StringUtils.substringAfterLast(getExportName(), ".");
        if (getTemplate() != null) {
            try (InputStream is = getTemplate().getInputStream()) {
                workbook = "xls".equalsIgnoreCase(ext) ? new HSSFWorkbook(is) : new XSSFWorkbook(is);
            } catch (Exception e) {
                throw MessageUtils.buildException(ApiResultCode.EXPORT_EXCEL_FAIL);
            }
        } else {
            workbook = "xls".equalsIgnoreCase(ext) ? new HSSFWorkbook() : new XSSFWorkbook();
            setHeaderCellStyle(createDefaultHeaderStyle(workbook));
            setCellStyle(createDefaultCellStyle(workbook));
        }
    }

    /**
     * 处理标题行
     *
     * @param sheet sheet
     */
    public void handleHeaderRows(Sheet sheet) {
        if (getHeaderRowNames() != null) {
            Row row = sheet.createRow(getHeaderRowNum() - 1);
            for (int i = 0; i < getHeaderRowNames().length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(getHeaderRowNames()[i]);
                if (getHeaderCellStyle() != null) {
                    cell.setCellStyle(getHeaderCellStyle());
                }

                if (getDefalutColumnWidth() > 0) {
                    sheet.setColumnWidth(i, getDefalutColumnWidth() * 256);
                }
            }
        }
    }

    /**
     * 创建默认风格的标题行
     *
     * @param workbook workbook
     * @return CellStyle
     */
    public CellStyle createDefaultHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setFontName("Courier New");

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        return style;
    }

    /**
     * 创建默认风格的单元行
     *
     * @param workbook workbook
     * @return CellStyle
     */
    public CellStyle createDefaultCellStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Courier New");

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(false);
        return style;
    }

    /**
     * 导出
     *
     * @return 工作表
     */
    public Workbook export() throws BaseException {
        Sheet sheet = null;

        if (getTemplate() != null) {
            sheet = workbook.getSheetAt(0);
        } else {
            if (StringUtils.isNotEmpty(getSheetName())) {
                sheet = workbook.createSheet(getSheetName());
            } else {
                sheet = workbook.createSheet();
            }

            handleHeaderRows(sheet);
        }

        int rownum = getHeaderRowNum();

        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle dateCellStyle = workbook.createCellStyle();
        CellStyle defaultCellStyle = workbook.createCellStyle();
        if (getCellStyle() != null) {
            defaultCellStyle.cloneStyleFrom(getCellStyle());
            dateCellStyle.cloneStyleFrom(getCellStyle());
        }
        dateCellStyle.setDataFormat(dataFormat.getFormat("yyyy/mm/dd"));

        setCellValue(getDatas(), sheet, rownum, dateCellStyle, defaultCellStyle);
        return workbook;
    }

    /**
     * 填充单元格数据
     *
     * @param dataList 数据
     * @param sheet sheet
     * @param rownum 行数
     * @param dateCellStyle 填充单元格
     * @param defaultCellStyle 默认单元格风格
     */
    protected void setCellValue(List<Object[]> dataList, Sheet sheet, int rownum, CellStyle dateCellStyle, CellStyle defaultCellStyle) {
        Row row;
        for (Object[] rowData : dataList) {
            row = sheet.createRow(rownum++);
            int column = 0;
            for (Object cellData : rowData) {
                Cell cell = row.createCell(column++);

                CellStyle style = defaultCellStyle;

                if (cellData == null) {
                    cell.setCellValue("");
                } else if (cellData instanceof Number) {
                    cell.setCellValue(((Number) cellData).doubleValue());
                } else if (cellData instanceof Boolean) {
                    cell.setCellValue((Boolean) cellData);
                } else if (cellData instanceof Date) {
                    cell.setCellValue((Date) cellData);
                    style = dateCellStyle;
                } else {
                    cell.setCellValue(Objects.toString(cellData));
                }

                if (style != null) {
                    cell.setCellStyle(style);
                }
            }
        }
    }

    public Resource getTemplate() {
        return template;
    }

    public void setTemplate(Resource template) {
        this.template = template;
    }

    public int getHeaderRowNum() {
        return headerRowNum;
    }

    public void setHeaderRowNum(int headerRowNum) {
        this.headerRowNum = headerRowNum;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaderRowNames() {
        return headerRowNames == null ? null : headerRowNames.clone();
    }

    public void setHeaderRowNames(String[] headerRowNames) {
        this.headerRowNames = headerRowNames == null ? null : headerRowNames.clone();
    }

    public List<Object[]> getDatas() {
        return datas;
    }

    public void setDatas(List<Object[]> datas) {
        this.datas = datas;
    }

    public CellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }

    public void setHeaderCellStyle(CellStyle headerCellStyle) {
        this.headerCellStyle = headerCellStyle;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public int getDefalutColumnWidth() {
        return defalutColumnWidth;
    }

    public void setDefalutColumnWidth(int defalutColumnWidth) {
        this.defalutColumnWidth = defalutColumnWidth;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public Workbook getWorkbook() {
        return workbook;
    }


}
