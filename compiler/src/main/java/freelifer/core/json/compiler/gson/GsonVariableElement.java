package freelifer.core.json.compiler.gson;

import com.google.gson.annotations.SerializedName;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * @author kzhu on 2017/12/19.
 */
public class GsonVariableElement implements GsonVariable {

    private Elements elements;
    private VariableElement variableElement;
    private SerializedName nameVariable;

    private GsonVariableElement() {
    }

    public static GsonVariableElement create(VariableElement element) {
        GsonVariableElement ljsonElement = new GsonVariableElement();
        ljsonElement.variableElement = element;
        ljsonElement.nameVariable = element.getAnnotation(SerializedName.class);

        return ljsonElement;
    }

    @Override
    public String variableName() {
        return variableElement.getSimpleName().toString();
    }

    @Override
    public String jsonName() {
        if (nameVariable != null) {
            return nameVariable.value();
        }
        return variableElement.getSimpleName().toString();
    }

    @Override
    public TypeMirror typeMirror() {
        return variableElement.asType();
    }

    @Override
    public String toString() {
        return String.format("GsonVariableElement name:%s, json:%s, type:%s", variableName(), jsonName(), typeMirror().toString());
    }

}
