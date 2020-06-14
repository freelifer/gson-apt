package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class LongAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "long".equals(type);
    }

    @Override
    public String transform(String reader) {
        return reader + ".nextLong()";
    }
}
