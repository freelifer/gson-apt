package freelifer.core.json.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kzhu on 2017/12/22.
 */
public class LimitJSONs {

    public static int[] a;
    public static int[] arrays(JSONObject root, String key) {

        JSONArray array = root.optJSONArray(key);
        int size = size(array);
        if (size <= 0) {
            return null;
        }
        a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = array.optInt(i);
        }
        return a;
    }

    public static boolean isEmpty(JSONArray jsonArray) {
        return (jsonArray == null || jsonArray.length() <= 0);
    }

    public static int size(JSONArray jsonArray) {
        return jsonArray == null ? 0 : jsonArray.length();
    }

    public static List<String> ofStringList(JSONObject root, String key) {
        List<String> list = null;

        JSONArray array = root.optJSONArray(key);
        int size = size(array);
        if (size <= 0) {
            return null;
        }
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(array.optString(i));
        }
        return list;
    }

    public static <T> List<T> ofList(JSONObject root, String key) {
        List<T> list = null;

        JSONArray array = root.optJSONArray(key);
        int size = size(array);
        if (size <= 0) {
            return null;
        }
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((T) array.opt(i));
        }
        return list;
    }
}