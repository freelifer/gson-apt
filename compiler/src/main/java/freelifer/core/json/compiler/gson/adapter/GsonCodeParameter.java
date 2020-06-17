package freelifer.core.json.compiler.gson.adapter;

/**
 * 通过适配器返回对应的生成器
 *
 * @author zhukun on 2020-06-15.
 */
public class GsonCodeParameter {

    /**
     * 参数类型
     * 0: empty
     * 1： 基本类型 next*()
     * 2: Object read*(in)
     * 3: List ???
     * 4: Array ???
     */
    public int type;
    public String value;

    private static final GsonCodeParameter empty = new GsonCodeParameter();

    public static GsonCodeParameter createEmpty() {
        return empty;
    }

    public static GsonCodeParameter createBasicType(String value) {
        GsonCodeParameter parameters = new GsonCodeParameter();
        parameters.type = 1;
        parameters.value = value;
        return parameters;
    }

    public static GsonCodeParameter createObjectType(String value) {
        GsonCodeParameter parameters = new GsonCodeParameter();
        parameters.type = 2;
        parameters.value = value;
        return parameters;
    }

    public static GsonCodeParameter createListType(String value) {
        GsonCodeParameter parameters = new GsonCodeParameter();
        parameters.type = 3;
        parameters.value = value;
        return parameters;
    }

    public static GsonCodeParameter createArrayType(String value) {
        GsonCodeParameter parameters = new GsonCodeParameter();
        parameters.type = 4;
        parameters.value = value;
        return parameters;
    }
}
