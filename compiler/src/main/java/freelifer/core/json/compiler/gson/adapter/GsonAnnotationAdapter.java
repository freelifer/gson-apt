package freelifer.core.json.compiler.gson.adapter;

import freelifer.core.json.compiler.gson.GsonTypeElement;

/**
 * 已添加@Gson注解处理器
 *
 * @author zhukun on 2020-06-14.
 */
public class GsonAnnotationAdapter implements Adapter {
    private GsonTypeElement element;

    public GsonAnnotationAdapter(GsonTypeElement element) {
        this.element = element;
    }

    @Override
    public boolean isType(String type) {
        return type.equals(element.getQualifiedName());
    }

    @Override
    public String transform(String reader) {
        return "read" + element.getTypeName() + "(" + reader + ")";
    }
}
