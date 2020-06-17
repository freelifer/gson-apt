package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public class ListAdapter implements Adapter {

    private String type;

    public ListAdapter(String type) {
        this.type = type;
    }
    @Override
    public boolean isType(String type) {
        return true;
    }

    @Override
    public GsonCodeParameter transform(String reader) {
        return GsonCodeParameter.createListType(type);
    }
}
