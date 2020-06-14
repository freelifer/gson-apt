package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class BooleanAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "boolean".equals(type);
    }

    @Override
    public String transform(String reader) {
        return reader + ".nextBoolean()";
    }
}
