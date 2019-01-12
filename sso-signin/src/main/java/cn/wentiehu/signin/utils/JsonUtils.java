package cn.wentiehu.signin.utils;

import com.google.gson.Gson;

/**
 * @author wentiehu
 * @version 1.0
 * @date 2019/1/11
 */
public class JsonUtils {

    private final static Gson gson = new Gson();

    /**
     * Object转成JSON数据
     */
    public static String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }


    /**
     * JSON数据，转成Object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
