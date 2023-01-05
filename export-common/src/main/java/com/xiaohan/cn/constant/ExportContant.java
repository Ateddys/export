package com.xiaohan.cn.constant;

import java.util.regex.Pattern;

/**
 * 导出常量
 *
 * @author teddy
 * @since 2022/12/25
 */
public class ExportContant {

    private ExportContant() {
    }

    /**
     * 广场猫（表）
     */
    public static final String T_CAT = "t_cat";

    /**
     * 批量导入的最大参数个数
     */
    public static final int MAX_PARAMS = 2100;

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    public static final String LOGGER_ERROR = "error :{}";
    /**
     * 异常码正则表达式
     */
    public static final Pattern MODEL_ERROR_CODE_PATTERN = Pattern.compile("^[0-9]{5}$");

    private static final String REDIS_IMPORT_PREFIX = "import:";

    public static String getImportRedisKey(String type) {
        return REDIS_IMPORT_PREFIX + type;
    }

    /**
     * 上传结果
     */
    public enum ImportStatusEnum {
        /**
         * 失败
         */
        FAIL(-1, "失败"),

        /**
         * 进行中
         */
        IN_PROGRESS(0, "进行中"),

        /**
         * 成功
         */
        SUCCESS(1, "成功");

        private ImportStatusEnum(Integer key, String name) {
            this.key = key;
            this.name = name;
        }

        private final Integer key;

        private final String name;

        public Integer getKey() {
            return key;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 导入进程
     */
    public enum ImportProgressEnum {
        /**
         * 开始校验
         */
        START_VALIDATE("startValidate","开始校验..."),
        /**
         * 正在校验
         */
        VALIDATING("validating","正在校验: "),
        /**
         * 结束校验
         */
        END_VALIDATE("endValidate","结束校验: "),
        /**
         * 开始入库
         */
        START_SAVE("startSave","开始入库..."),
        /**
         * 正在入库
         */
        SAVING("saving","正在入库..."),
        /**
         * 结束入库
         */
        END_SAVE("endSave","结束入库: ");

        private ImportProgressEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private final String key;

        private final String name;

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public static String getKey(String name) {
            for (ImportProgressEnum e : ImportProgressEnum.values()) {
                if (e.getName().equals(name)) {
                    return e.getKey();
                }
            }
            return null;
        }

        public static String getName(String key) {
            if (key != null) {
                for (ImportProgressEnum e : ImportProgressEnum.values()) {
                    if (e.getKey().equals(key)) {
                        return e.getName();
                    }
                }
            }
            return null;
        }
    }
}
