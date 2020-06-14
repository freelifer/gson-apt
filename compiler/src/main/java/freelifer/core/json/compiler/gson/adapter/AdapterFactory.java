package freelifer.core.json.compiler.gson.adapter;

/**
 * @author zhukun on 2020-06-14.
 */
public interface AdapterFactory {
    Adapter create(String type);
}
