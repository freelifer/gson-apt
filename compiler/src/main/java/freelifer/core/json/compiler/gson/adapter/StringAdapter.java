package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class StringAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "java.lang.String".equals(type);
    }

    @Override
    public GsonCodeParameter transform(String input) {
        return GsonCodeParameter.createObjectType(input + ".nextString()");
    }
}
