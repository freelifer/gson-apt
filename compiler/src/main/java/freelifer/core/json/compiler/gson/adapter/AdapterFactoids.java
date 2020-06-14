package freelifer.core.json.compiler.gson.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhukun on 2020-06-14.
 */
public class AdapterFactoids {
    private List<AdapterFactory> factories = new ArrayList<>();
    private HashMap<String, Adapter> cachedApaters = new HashMap<>();

    public AdapterFactoids() {
        addAdapterFactory(new BasicTypeAdapterFactory());
    }

    public void addAdapterFactory(AdapterFactory adapterFactory) {
        if (adapterFactory == null) {
            throw new NullPointerException("AdapterFactory setAdapter(Adapter) Adapter is Null.");
        }

        factories.add(adapterFactory);
    }

    public Adapter getAdapter(String type) {
        Adapter adapter = cachedApaters.get(type);
        if (adapter != null) {
            return adapter;
        }
        for (AdapterFactory factory : factories) {
            adapter = factory.create(type);
            if (adapter != null) {
                cachedApaters.put(type, adapter);
                return adapter;
            }
        }
        return null;
    }
}
