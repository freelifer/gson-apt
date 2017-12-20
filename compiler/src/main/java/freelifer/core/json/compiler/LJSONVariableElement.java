package freelifer.core.json.compiler;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import freelifer.core.json.annotations.LJSONVariable;

/**
 * @author kzhu on 2017/12/19.
 */
public class LJSONVariableElement implements LIMITJSONVariable {

    private Elements elements;
    private VariableElement variableElement;
    private LJSONVariable ljsonVariable;

    private LJSONVariableElement() {
    }

    public static LJSONVariableElement create(VariableElement element) {
        LJSONVariableElement ljsonElement = new LJSONVariableElement();
        ljsonElement.variableElement = element;
        ljsonElement.ljsonVariable = element.getAnnotation(LJSONVariable.class);

        return ljsonElement;
    }

    @Override
    public String variableName() {
        return variableElement.getSimpleName().toString();
    }

    @Override
    public String jsonName() {
        if (ljsonVariable != null) {
            return ljsonVariable.value();
        }
        return variableElement.getSimpleName().toString();
    }

    @Override
    public TypeMirror typeMirror() {
        return variableElement.asType();
    }

    @Override
    public String toString() {
        return String.format("LJSONVariableElement name:%s, json:%s, type:%s", variableName(), jsonName(), typeMirror().toString());
    }

}
