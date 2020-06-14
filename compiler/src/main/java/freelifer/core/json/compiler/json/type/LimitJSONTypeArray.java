package freelifer.core.json.compiler.json.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import freelifer.core.json.compiler.json.LIMITJSONVariable;

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

public class LimitJSONTypeArray extends LimitJSONType {

    private String type;

    public LimitJSONTypeArray(String format, String type) {
        super(format);
        this.type = type;
    }


    @Override
    public void addStatement(MethodSpec.Builder builder, LIMITJSONVariable variable) {
        builder.beginControlFlow("if (root.has($S))", variable.jsonName());
        builder.addStatement("$T array = root.optJSONArray($S)", ClassName.bestGuess("org.json.JSONArray"), variable.jsonName());
        builder.addStatement("int size = array.length()");
        builder.addStatement("thisObj.$N = new $N[size]", variable.variableName(), type);
        builder.beginControlFlow("for (int i = 0; i < size; i++)");
        builder.addStatement(String.format("thisObj.$N[i] = array.%s(i)", format), variable.variableName());
        builder.endControlFlow();
        builder.endControlFlow();
    }
}
