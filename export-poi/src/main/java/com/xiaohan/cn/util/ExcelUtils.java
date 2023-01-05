package com.xiaohan.cn.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import static com.xiaohan.cn.constant.ExportContant.LOGGER_ERROR;
import static com.xiaohan.cn.constant.ExportContant.XLS;
import static com.xiaohan.cn.constant.ExportContant.XLSX;

/**
 * Excel工具类
 *
 * @author teddy
 * @since 2022/12/30
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 默认日期格式
     */
    private static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 默认数字格式
     */
    private static final NumberFormat DEFAULT_NUMBER_FORMAT = new DecimalFormat("#.##");

    private ExcelUtils() {
    }

    /**
     * 处理excel文件，返回表格数据
     * @param file excel文件
     * @param sheetNum sheet索引号
     * @return sheet
     */
    public static Sheet handleExcel(MultipartFile file, int sheetNum) {
        return handleExcel(file, sheetNum, null);
    }

    /**
     * 处理excel文件，返回表格数据
     * @param file excel文件
     * @param sheetName sheet名称
     * @return sheet
     */
    public static Sheet handleExcel(MultipartFile file, String sheetName) {
        return handleExcel(file, null, sheetName);
    }

    /**
     * 处理excel文件，返回表格数据
     *
     * @param file excel文件
     * @return sheet
     */
    public static Sheet handleExcle(MultipartFile file) {
        return handleExcel(file, 0, null);
    }

    private static Sheet handleExcel(MultipartFile file, Integer sheetNum, String sheetName) {
        logger.info("handle Excle starts...");
        if (file == null) {
            logger.error("file is not found!");
            return null;
        }

        String filename = file.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            logger.error("file is not found!");
            return null;
        }
        int iIndex = filename.lastIndexOf('.');
        String ext = (iIndex < 0) ? "" : filename.substring(iIndex + 1).toLowerCase();
        if (!(XLSX.equals(ext) || XLS.equals(ext))) {
            logger.error("{} not a Microsoft EXCEL file", filename);
            return null;
        }
        Workbook workBook = null;
        try (InputStream input = file.getInputStream()) {
            if (XLSX.equals(ext)) {
                workBook = new XSSFWorkbook(input);
            } else {
                workBook = new HSSFWorkbook(input);
            }
            if (sheetNum != null) {
                return workBook.getSheetAt(sheetNum);
            } else if (StringUtils.isNotEmpty(sheetName)) {
                return workBook.getSheet(sheetName);
            }

        } catch (IOException e) {
            logger.error(LOGGER_ERROR, e);
        } finally {
            ExcelUtils.closeQuietly(workBook);
        }
        return null;
    }

    /**
     * 读取单元格字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    public static String readCellString(Cell cell) {
        return readCellString(cell, null, null);
    }

    /**
     * 读取单元格字符串
     *
     * @param cell         单元格
     * @param numberFormat 数字格式
     * @return 字符串
     */
    public static String readCellString(Cell cell, NumberFormat numberFormat) {
        return readCellString(cell, null, numberFormat);
    }

    /**
     * 读取单元格字符串
     *
     * @param cell       单元格
     * @param dateFormat 日期格式
     * @return 字符串
     */
    public static String readCellString(Cell cell, DateFormat dateFormat) {
        return readCellString(cell, dateFormat, null);
    }

    /**
     * 读取单元格字符串
     *
     * @param cell         单元格
     * @param dateFormat   日期格式
     * @param numberFormat 数字格式
     * @return 字符串
     */
    public static String readCellString(Cell cell, DateFormat dateFormat, NumberFormat numberFormat) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    boolean booleanCellValue = cell.getBooleanCellValue();
                    return String.valueOf(booleanCellValue);
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date dateCellValue = cell.getDateCellValue();
                        String numeric = (dateFormat == null ? DEFAULT_DATE_FORMAT.format(dateCellValue) : dateFormat.format(dateCellValue));
                        return dateCellValue == null ? "" : numeric;
                    }
                    double numericCellValue = cell.getNumericCellValue();
                    return numberFormat == null ? DEFAULT_NUMBER_FORMAT.format(numericCellValue) : numberFormat.format(numericCellValue);
                case STRING:
                    return StringUtils.trimToEmpty(cell.getStringCellValue());
                case FORMULA:
                    try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date dateCellValue = cell.getDateCellValue();
                            String formula = (dateFormat == null ? DEFAULT_DATE_FORMAT.format(dateCellValue) : dateFormat.format(dateCellValue));
                            return dateCellValue == null ? "" : formula;
                        }

                        numericCellValue = cell.getNumericCellValue();
                        return numberFormat == null ? DEFAULT_NUMBER_FORMAT.format(numericCellValue) : numberFormat.format(numericCellValue);
                    } catch (Exception e1) {
                        try {
                            return StringUtils.trimToEmpty(cell.getStringCellValue());
                        } catch (Exception e2) {
                            try {
                                return String.valueOf(cell.getBooleanCellValue());
                            } catch (Exception e3) {
                                logger.error(LOGGER_ERROR, e3);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return "";
    }

    /**
     * 读取单元格日期
     *
     * @param cell 单元格
     * @return 日期
     */
    public static Date readCellDate(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    }
                    break;
                case STRING:
                    try {
                        return DEFAULT_DATE_FORMAT.parse(cell.getStringCellValue());
                    } catch (ParseException e) {
                        logger.error(LOGGER_ERROR, e);
                    }
                    break;
                case FORMULA:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 读取单元格整数
     *
     * @param cell 单元格
     * @return 整数
     */
    public static Integer readCellInt(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    return cell.getBooleanCellValue() ? 1 : 0;

                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    String intString = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(intString)) {
                        return Integer.valueOf(intString);
                    }
                    break;
                case FORMULA:
                    try {
                        return (int) cell.getNumericCellValue();
                    } catch (Exception e) {
                        try {
                            intString = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(intString)) {
                                return Integer.valueOf(intString);
                            }
                        } catch (Exception ex) {
                            logger.error(LOGGER_ERROR, ex);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 读取单元格浮点数
     *
     * @param cell 单元格
     * @return 浮点数
     */
    public static Double readCellDouble(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    return cell.getBooleanCellValue() ? 1d : 0d;
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    String doubleString = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(doubleString)) {
                        return Double.valueOf(doubleString);
                    }
                    break;
                case FORMULA:
                    try {
                        return cell.getNumericCellValue();
                    } catch (Exception e) {
                        try {
                            doubleString = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(doubleString)) {
                                return Double.valueOf(doubleString);
                            }
                        } catch (Exception ex) {
                            logger.error(LOGGER_ERROR, ex);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 读取单元格布尔值
     *
     * @param cell 单元格
     * @return 布尔值
     */
    public static Boolean readCellBoolean(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case NUMERIC:
                    return cell.getNumericCellValue() > 0;
                case STRING:
                    String booleanString = cell.getStringCellValue();
                    return "true".equalsIgnoreCase(booleanString) || "y".equalsIgnoreCase(booleanString);
                case FORMULA:
                    try {
                        return cell.getBooleanCellValue();
                    } catch (Exception e) {
                        logger.error(LOGGER_ERROR, e);

                    }
                    break;
                default:
                    break;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 单元格空白行检查
     *
     * @param cell 单元格
     * @return true为空白行
     */
    public static Boolean isBankCell(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK || StringUtils.isBlank(readCellString(cell));
    }

    /**
     * close workbook
     *
     * @param workbook workbook
     */
    public static void closeQuietly(final Workbook workbook) {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            logger.error(LOGGER_ERROR, e);
        }
    }
}
