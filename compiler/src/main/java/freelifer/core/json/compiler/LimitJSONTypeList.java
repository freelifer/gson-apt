package freelifer.core.json.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

/**
 * JSONArray array = root.optJSONArray(key);
 * int size = size(array);
 * if (size <= 0) {
 * return null;
 * }
 * a = new int[size];
 * for (int i = 0; i < size; i++) {
 * a[i] = array.optInt(i);
 * }
 * return a;
 *
 * @author zhukun on 2017/12/21.
 */

public class LimitJSONTypeList extends LimitJSONType {

    private String type;
    private String objClassName;

    public LimitJSONTypeList(String format, String type, String objClassName) {
        super(format);
        this.type = type;
        this.objClassName = objClassName;
    }


    public void addStatement(MethodSpec.Builder builder, LIMITJSONVariable variable) {
        builder.beginControlFlow("if (root.has($S))", variable.jsonName());
        builder.addStatement("$T array = root.optJSONArray($S)", ClassName.bestGuess("org.json.JSONArray"), variable.jsonName());
        builder.addStatement("int size = array == null ? 0 : array.length()");
        builder.beginControlFlow("if (size > 0)");
        builder.addStatement("thisObj.$N = new $T<>()", variable.variableName(), ClassName.bestGuess("java.util.ArrayList"));
        builder.beginControlFlow("for (int i = 0; i < size; i++)");
        builder.addStatement(String.format("thisObj.$N.add($N.create(array.%s(i), allowNull))", format), variable.variableName(), objClassName);
        builder.endControlFlow();
        builder.endControlFlow();
        builder.endControlFlow();
    }
}
