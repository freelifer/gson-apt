package freelifer.core.json.compiler.json.type;

import com.squareup.javapoet.MethodSpec;

import freelifer.core.json.compiler.json.LIMITJSONVariable;

/**
 * @author zhukun on 2017/12/21.
 */

public class LimitJSONType {

    protected String format;

    public LimitJSONType(String format) {
        this.format = format;
    }

    public void addStatement(MethodSpec.Builder builder, LIMITJSONVariable variable) {
        builder.beginControlFlow("if (root.has($S))", variable.jsonName());
        builder.addStatement(format, variable.variableName(), variable.jsonName());
        builder.endControlFlow();
    }
}
