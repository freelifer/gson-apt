package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class IntAdapter implements Adapter {
    @Override
    public boolean isType(String type) {
        return "int".equals(type);
    }

    @Override
    public GsonCodeParameter transform(String input) {
        return GsonCodeParameter.createBasicType(input + ".nextInt()");
    }

}
