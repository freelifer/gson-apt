package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public interface Adapter {

    /**
     * 判断类型是否匹配
     *
     * @param type
     * @return
     */
    boolean isType(String type);

    /**
     * 转换
     *
     * @param input 输入
     * @return 返回对应的参数
     */
    GsonCodeParameter transform(String input);
}
