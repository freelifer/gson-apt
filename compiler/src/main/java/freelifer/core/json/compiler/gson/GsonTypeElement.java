package freelifer.core.json.compiler.gson;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import freelifer.core.json.compiler.Collections;
import freelifer.gson.annotations.Gson;

/**
 * @author zhukun on 2020-06-13.
 */
public class GsonTypeElement {

    private Elements elements;
    private TypeElement typeElement;
    private List<GsonVariableElement> variableElements;

    private GsonTypeElement() {
    }

    public static GsonTypeElement create(Elements elements, Element element) {
        if (element.getKind() != ElementKind.CLASS) {
            throw new IllegalArgumentException(
                    String.format("Only CLASS can be annotated with @%s", Gson.class.getSimpleName()));
        }
        TypeElement typeElement = (TypeElement) element;
        GsonTypeElement ljsonElement = new GsonTypeElement();
        ljsonElement.typeElement = typeElement;
        ljsonElement.elements = elements;
        ArrayList<GsonVariableElement> list = new ArrayList<>();
        List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        if (!Collections.isEmpty(enclosedElements)) {
            for (Element ele : enclosedElements) {
                if (ele instanceof VariableElement) {
                    list.add(GsonVariableElement.create((VariableElement) ele));
                }
            }
        }
        ljsonElement.variableElements = list;
        return ljsonElement;
    }

    public List<GsonVariableElement> getVariableElements() {
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
        String result = String.format("GSONElement class:%s full:%s", typeElement.getSimpleName(), getQualifiedName());
        return result;
    }
}

