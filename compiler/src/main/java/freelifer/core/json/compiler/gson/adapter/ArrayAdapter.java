package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class ArrayAdapter implements Adapter {

    private String type;

    public ArrayAdapter(String type) {
        this.type = type;
    }
    @Override
    public boolean isType(String type) {
        return true;
    }

    @Override
    public GsonCodeParameter transform(String reader) {
        return GsonCodeParameter.createArrayType(type);
    }
}
