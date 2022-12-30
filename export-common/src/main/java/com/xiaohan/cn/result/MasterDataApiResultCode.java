package com.xiaohan.cn.result;

/**
 * <br>
 * date：2021/7/28 5:25 下午
 *
 * @author teddy
 */
public class MasterDataApiResultCode extends ApiResultCode {

    public static final ApiResultCode CODE_IS_ALREADY_IN_USE = buildApiResultCode(getBaseResultCode(), 1, "{0}已被使用");

    public static final ApiResultCode REUSE_THIS_IMPORT = buildApiResultCode(getBaseResultCode(), 2, "本次导入{0}重复");

    public static final ApiResultCode IMPORT_IN_PROGRESS = buildApiResultCode(getBaseResultCode(), 3, "{0}用户正在进行{1}导入，请等待导入结束再进行操作");

    public static final ApiResultCode IMPORT_MAX = buildApiResultCode(getBaseResultCode(), 4, "一次性最多导入{0}条数据");

    /**
     * 模块基础码
     */
    private static final int BASE_RESULT_CODE = 18;

    private static int getBaseResultCode() {
        return BASE_RESULT_CODE;
    }

}
