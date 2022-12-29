package com.xiaohan.cn.poi.importer;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xiaohan.cn.constant.ExportContant.XLS;
import static com.xiaohan.cn.constant.ExportContant.XLSX;

/**
 * 导入Excel文件工具类
 *
 * @author teddy
 * @since 2022/12/20
 */
public class ImportExcelUtils {

    private final Logger logger = LoggerFactory.getLogger(ImportExcelUtils.class);

    /**
     * 读取Excel表，每一行数据作为一个List，string是单元格内容
     *
     * @param file Excel文件
     * @param line 开始行
     * @return 单元格数据
     */
    public List<List<String>> uploadExcle(MultipartFile file, Integer line) {
        logger.info("handle Excle starts...");
        List<List<String>> excleData = new ArrayList<>();
        if (file == null) {
            logger.error("file is not found!");
            return excleData;
        }

        //获取文件名
        String filename = file.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            logger.error("file is not found!");
            return excleData;
        }
        //判断是否为excel文件
        int iIndex = filename.lastIndexOf('.');
        String ext = (iIndex < 0) ? "" : filename.substring(iIndex + 1).toLowerCase();
        if (!(XLSX.equals(ext) || XLS.equals(ext))) {
            logger.error(filename + "not a Microsoft EXCEL file");
        }
        InputStream input = null;
        try {
            input = file.getInputStream();
            if (XLSX.equals(ext)) {
                Workbook workBook = new XSSFWorkbook(input);
                XSSFSheet sheet = (XSSFSheet) workBook.getSheetAt(0);
                excleData = getCellVal(sheet, line);
            } else if (XLS.equals(ext)) {
                Workbook workBook = new HSSFWorkbook(input);
                HSSFSheet sheet = (HSSFSheet) workBook.getSheetAt(0);
                excleData = getCellVal(sheet, line);
            }
        } catch (IOException e) {
            logger.error("export excel error :{}", e);
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("close IO fail");
            }
        }
        return excleData;
    }


    /**
     * 遍历 读取单元格
     *
     * @param sheet 表
     * @param line 开始行
     *
     * @return 读取到的数据
     */
    private List<List<String>> getCellVal(Sheet sheet, Integer line) {
        List<List<String>> excleData = new ArrayList<>();
        Cell cell;
        Row row;
        //读取单元格, 从第line行开始，读取表格
        for (int i = line - 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (null != row) {
                List<String> list = new ArrayList<>();
                // 表头单元格个数
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    String cellValue = getCellValue(cell);
                    list.add(cellValue);
                }
                if (!CollectionUtils.isEmpty(list)) {
                    excleData.add(list);
                }
            }
        }
        return excleData;
    }

    /**
     * 读取单元格值
     *
     * @param cell 单元格
     * @return String
     */
    private String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 判断数据的类型,转换成字符串
            switch (cell.getCellType()) {
                // 数字
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                // 字符串
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                // Boolean
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                // 公式
                case FORMULA:
                    cellValue = cell.getCellFormula() + "";
                    break;
                // 空值
                case BLANK:
                    break;
                // 错误
                case ERROR:
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }

}
