package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class FloatAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "float".equals(type);
    }

    @Override
    public String transform(String reader) {
        return "(float) " + reader + ".nextDouble()";
    }
}
