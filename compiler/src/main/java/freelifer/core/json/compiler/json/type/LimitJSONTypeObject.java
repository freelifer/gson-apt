package freelifer.core.json.compiler.json.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import freelifer.core.json.compiler.json.LIMITJSONVariable;

/**
 * @author zhukun on 2017/12/21.
 */

public class LimitJSONTypeObject extends LimitJSONType {

    private String objClassName;

    public LimitJSONTypeObject(String format, String objClassName) {
        super(format);
        this.objClassName = objClassName;
    }

    @Override
    public void addStatement(MethodSpec.Builder builder, LIMITJSONVariable variable) {
        builder.beginControlFlow("if (root.has($S))", variable.jsonName());
        builder.addStatement(format, variable.variableName(), ClassName.bestGuess(objClassName), variable.jsonName());
        builder.endControlFlow();
    }
}
