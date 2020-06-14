package freelifer.core.json.compiler.gson.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhukun on 2020-06-14.
 */
public class BasicTypeAdapterFactory implements AdapterFactory {
    private List<Adapter> adapters = new ArrayList<>();

    public BasicTypeAdapterFactory() {
        adapters.add(new BooleanAdapter());
        adapters.add(new StringAdapter());
        adapters.add(new FloatAdapter());
        adapters.add(new LongAdapter());
        adapters.add(new DoubleAdapter());
        adapters.add(new IntAdapter());
    }

    @Override
    public Adapter create(String type) {
        for (Adapter adapter : adapters) {
            if (adapter.isType(type)) {
                return adapter;
            }
        }
        return null;
    }
}
