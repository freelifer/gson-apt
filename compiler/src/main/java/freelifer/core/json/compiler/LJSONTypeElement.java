package freelifer.core.json.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import freelifer.core.json.annotations.LJSON;

/**
 * @author kzhu on 2017/12/19.
 */
public class LJSONTypeElement {

    private Elements elements;
    private TypeElement typeElement;
    private List<LIMITJSONVariable> variableElements;

    private LJSONTypeElement() {
    }

    public static LJSONTypeElement create(Elements elements, Element element) {
        if (element.getKind() != ElementKind.CLASS) {
            throw new IllegalArgumentException(
                    String.format("Only CLASS can be annotated with @%s", LJSON.class.getSimpleName()));
        }
        TypeElement typeElement = (TypeElement) element;
        LJSONTypeElement ljsonElement = new LJSONTypeElement();
        ljsonElement.typeElement = typeElement;
        ljsonElement.elements = elements;
        ArrayList<LIMITJSONVariable> list = new ArrayList<>();
        List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        if (!Collections.isEmpty(enclosedElements)) {
            for (Element ele : enclosedElements) {
                if (ele instanceof VariableElement) {
                    list.add(LJSONVariableElement.create((VariableElement) ele));
                }
            }
        }
        ljsonElement.variableElements = list;
        return ljsonElement;
    }

    public List<LIMITJSONVariable> getVariableElements() {
        return variableElements;
    }

    public String getTypeName() {
        return typeElement.getSimpleName().toString();
    }

    public String getTypePackageName() {
        return elements.getPackageOf(typeElement).getQualifiedName().toString();
    }

    public String getQualifiedName() {
        return typeElement.getQualifiedName().toString();
    }

    @Override
    public String toString() {
        String result = String.format("LJSONElement class:%s full:%s", typeElement.getSimpleName(), getQualifiedName());
        return result;
    }
}
