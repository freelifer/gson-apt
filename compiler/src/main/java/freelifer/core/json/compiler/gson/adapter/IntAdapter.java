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
    public String transform(String reader) {
        return reader + ".nextInt()";
    }
}
