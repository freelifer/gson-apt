package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class DoubleAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "double".equals(type);
    }

    @Override
    public GsonCodeParameter transform(String input) {
        return GsonCodeParameter.createBasicType(input + ".nextDouble()");
    }
}
