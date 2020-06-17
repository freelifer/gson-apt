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
    public GsonCodeParameter transform(String input) {
        return GsonCodeParameter.createObjectType("read" + element.getTypeName() + "(" + input + ")");
    }
}
