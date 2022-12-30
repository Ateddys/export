package com.xiaohan.cn.importer;

import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.i18n.I18nUtils;
import com.xiaohan.cn.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel导入行处理抽象类
 *
 * @author teddy
 * @since 2022/12/30
 */
public abstract class AbstractImportExcelRowHandler implements ImportExcelHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // getHeaderRowNum
    private static final Integer HEADER_ROW_NUM = 1;
    private static final Integer MAX_HEADER_ROW_NUM = 10000;

    @Autowired
    private I18nUtils i18nUtils;

    protected I18nUtils getI18nUtils() {
        return this.i18nUtils;
    }

    /**
     * 表格处理之前
     *
     * @param sheet 表格
     */
    protected void beforeHandleSheet(Sheet sheet) {
    }

    @Override
    public ImportResult handleSheet(Sheet sheet) {
        //表格处理之前
        beforeHandleSheet(sheet);
        ImportResult result = new ImportResult();
        try {
            int rownum = 0;
            //总行数小于表头行数
            if (sheet.getLastRowNum() < getHeaderRowNum()) {
                result.setSuccess(false);
                result.setFailMsg(getI18nUtils().getMessage("emptyData"));
                return result;
            }
            //获取表头行数据
            List<Row> headerRows = new ArrayList<>();
            for (int i = 0; i < getHeaderRowNum(); i++) {
                headerRows.add(sheet.getRow(rownum++));
            }
            //表头检查
            String checkHeaderResult = checkHeaderRows(headerRows);
            if (StringUtils.isNotBlank(checkHeaderResult)) {
                result.setSuccess(false);
                result.setFailMsg(checkHeaderResult);
                return result;
            }
            //在失败原因反馈列表头加上[导出结果]标题
            if (!headerRows.isEmpty() && getFailCellNum() != null) {
                Cell cell = headerRows.get(0).createCell(getFailCellNum());
                cell.setCellValue(getI18nUtils().getMessage("failHeaderName"));
            }
            //如果处理表头数据，行数置为0
            if (handleHeaderRows()) {
                rownum = 0;
            }
            Row row = sheet.getRow(rownum++);
            while (rownum - 1 <= sheet.getLastRowNum()) {
                //检查空白行
                if (row == null || checkBlankRow(row)) {
                    row = sheet.getRow(rownum++);
                    continue;
                }
                //处理行数据
                String handleResult = null;
                //是否处理异常
                if (!handleRowException()) {
                    handleResult = handleRow(row);
                } else {
                    handleResult = handleRowException(row);
                }
                //在失败原因反馈列加上行处理结果
                if (getFailCellNum() != null) {
                    Cell cell = row.createCell(getFailCellNum());
                    cell.setCellValue(handleResult);
                }
                if (StringUtils.isNotBlank(handleResult)) {
                    result.getFailRowNums().add(row.getRowNum());
                    result.getFailRowMsgs().add(handleResult);
                    result.setFailRowCount(result.getFailRowCount() + 1);
                    //是否跳过错误行
                    if (!skipOnRowFail()) {
                        result.setFailMsg(handleResult);
                        result.setSuccess(false);
                        return result;
                    }
                } else {
                    result.setSuccessRowCount(result.getSuccessRowCount() + 1);
                }
                row = sheet.getRow(rownum++);

                //超过最大处理行数, 不处理
                if (rownum > getMaxHandleRowNum()) {
                    break;
                }
            }
            //返回处理结果
            if (result.getSuccessRowCount() > 0) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setFailMsg(getI18nUtils().getMessage("allFail"));
            }
            result.setFeedback(getFailCellNum() != null && result.getFailRowCount() > 0);
        } catch (Exception e) {
            throw new BaseException("00017:导入excel文件失败");
        } finally {
            //表格处理之后
            afterHandleSheet(sheet, result);
        }
        return result;
    }

    /**
     * 处理异常
     *
     * @param row row
     * @return handleResult
     */
    private String handleRowException(Row row) {
        String handleResult;
        try {
            handleResult = handleRow(row);
        } catch (BaseException e) {
            handleResult = e.getMessage();
            logger.error(handleResult, e);
        } catch (Exception e) {
            handleResult = getI18nUtils().getMessage("unknownError");
            logger.error(handleResult, e);
        }
        return handleResult;
    }

    /**
     * 处理行数据
     *
     * @param row Excel行
     * @return 处理结果
     */
    protected abstract String handleRow(Row row);

    /**
     * 表格处理之后,一般进行数据持久化
     *
     * @param sheet  表格
     * @param result 导入结果
     */
    public void doAfterHandleSheet(final Sheet sheet, final ImportResult result) {
        AbstractImportExcelRowHandler handler = (AbstractImportExcelRowHandler) AopContext.currentProxy();
        handler.afterHandleSheet(sheet, result);
    }

    public void afterHandleSheet(Sheet sheet, ImportResult result) {
    }

    /**
     * @return 表头行数
     */
    protected int getHeaderRowNum() {
        return HEADER_ROW_NUM;
    }

    /**
     * 表头检查
     *
     * @param rows 表头行
     * @return 检查不通过信息, 通过返回null或空字符串
     */
    protected String checkHeaderRows(List<Row> rows) {
        return null;
    }

    /**
     * @return 是否处理表头
     */
    protected boolean handleHeaderRows() {
        return false;
    }

    /**
     * 空白行检查
     *
     * @param row 行
     * @return 是否空行
     */
    private boolean checkBlankRow(Row row) {
        boolean isBlank = false;
        if (getFailCellNum() != null) {
            isBlank = true;
            for (int i = 0; i < getFailCellNum(); i++) {
                if (Boolean.FALSE.equals(ExcelUtils.isBankCell(row.getCell(i)))) {
                    isBlank = false;
                    break;
                }
            }
        }
        return isBlank;
    }

    /**
     * @return 是否处理异常
     */
    protected boolean handleRowException() {
        return true;
    }

    /**
     * @return 是否跳过失败行
     */
    protected boolean skipOnRowFail() {
        return true;
    }

    /**
     * 失败原因反馈列, null为不反馈
     *
     * @return 失败原因反馈列号
     */
    protected abstract Integer getFailCellNum();

    /**
     * @return 最大处理行数
     */
    protected int getMaxHandleRowNum() {
        return MAX_HEADER_ROW_NUM;
    }

}
