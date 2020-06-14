package freelifer.core.json.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import freelifer.core.json.compiler.gson.GsonTypeElement;
import freelifer.core.json.compiler.gson.adapter.Adapter;
import freelifer.core.json.compiler.gson.adapter.AdapterFactoids;
import freelifer.core.json.compiler.gson.adapter.GsonTypeAdapterFactory;
import freelifer.core.json.compiler.json.LJSONTypeElement;

/**
 * @author kzhu on 2017/12/19.
 */
public class ProcessorHelper {

    private final static boolean DBG = true;

    private final Filer filer;

    private final Elements elements;

    private final Messager messager;

    private String moduleName;

    private AdapterFactoids adapterFactory;

    /**
     * LimitJSON 注解的类对象列表
     */
    private List<LJSONTypeElement> limitJSONTypeElements = Collections.newArrayList();
    private List<GsonTypeElement> gsonTypeElements = Collections.newArrayList();

    private ProcessorHelper(final Filer filer, final Elements elements, final Messager messager) {
        this.filer = filer;
        this.elements = elements;
        this.messager = messager;
        this.adapterFactory = new AdapterFactoids();
    }

    public static ProcessorHelper create(final Filer filer, final Elements elements, final Messager messager) {
        return new ProcessorHelper(filer, elements, messager);
    }

    public void process() throws IOException {
        this.adapterFactory.addAdapterFactory(new GsonTypeAdapterFactory(this.gsonTypeElements));

        if (!Collections.isEmpty(limitJSONTypeElements)) {
            for (LJSONTypeElement ljsonElement : limitJSONTypeElements) {
                LJSONAnnotatedClass.toWrite(this, ljsonElement, filer);
            }
        }

        if (!Collections.isEmpty(gsonTypeElements)) {
            GsonAnnotatedClass.toWriteAllGsonClass(this, filer);
//            for (GsonTypeElement gsonTypeElement : gsonTypeElements) {
//                GsonAnnotatedClass.toWrite(this, gsonTypeElement, filer);
//            }
        }
    }

    public List<LJSONTypeElement> getLimitJSONTypeElements() {
        return limitJSONTypeElements;
    }

    public void setLimitJSONTypeElements(LJSONTypeElement element) {
        limitJSONTypeElements.add(element);
    }

    public void setGsonTypeElements(GsonTypeElement element) {
        gsonTypeElements.add(element);
    }

    public List<GsonTypeElement> getGsonTypeElements() {
        return gsonTypeElements;
    }

    public void clear() {
        limitJSONTypeElements.clear();
        gsonTypeElements.clear();
    }

    public String getPackageOf(Element element) {
        return elements.getPackageOf(element).getQualifiedName().toString();
    }

    public void toWrite(String packageName, TypeSpec typeSpec) throws IOException {
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }


    public Adapter getAdapter(String type) {
        return this.adapterFactory.getAdapter(type);
    }
    //-----------------------------日志
    public void i(String format, Object... args) {
        if (!DBG) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(format, args));
    }

    public void e(String format, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(format, args));
    }
}
