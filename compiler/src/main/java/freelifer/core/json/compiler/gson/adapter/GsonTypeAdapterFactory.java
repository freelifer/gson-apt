package freelifer.core.json.compiler.gson.adapter;

import java.util.List;

import freelifer.core.json.compiler.Collections;
import freelifer.core.json.compiler.gson.GsonTypeElement;

/**
 * @author zhukun on 2020-06-14.
 */
public class GsonTypeAdapterFactory implements AdapterFactory {
    private List<GsonTypeElement> list;

    public GsonTypeAdapterFactory(List<GsonTypeElement> list) {
        this.list = list;
    }

    @Override
    public Adapter create(String type) {
        if (Collections.isEmpty(list)) {
            return null;
        }
        for (GsonTypeElement element : list) {
            if (type.equals(element.getQualifiedName())) {
                return new GsonAnnotationAdapter(element);
            }
        }
        return null;
    }
}
