package freelifer.core.json.compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import freelifer.core.json.annotations.LIMITJSON;
import freelifer.core.json.compiler.gson.GsonTypeElement;
import freelifer.core.json.compiler.json.LJSONTypeElement;
import freelifer.gson.annotations.Gson;

/**
 * @author zhukun on 2017/12/19.
 */
@AutoService(Processor.class)
public class JSONProcessor extends AbstractProcessor {

    private ProcessorHelper processorHelper;
    private Filer filer;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        String content = processingEnv.getOptions().get("GSON_MODULE_NAME");
        processorHelper = ProcessorHelper.create(filer, elements, messager);
        processorHelper.setModuleName(content);

        processorHelper.i("从app module 中获取到的参数: " + content);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        // add annotations type
        annotationTypes.add(LIMITJSON.class.getCanonicalName());
        annotationTypes.add(Gson.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processorHelper.clear();

        processorHelper.i("Printing process............................");
        for (TypeElement te : annotations) {
            processorHelper.i("Printing type: " + te.toString());
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                processorHelper.i("Printing: " + e.toString() + " " + e.getSimpleName());
            }
        }

        try {
            processLimitJSON(roundEnv);
            processGson(roundEnv);

            processorHelper.process();

        } catch (IllegalArgumentException e) {
            processorHelper.e(e.getMessage());
            return true; // stop process
        } catch (IOException e) {
            processorHelper.e(e.getMessage());
            return true; // stop process
        }
        return true;
    }

    private void processLimitJSON(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(LIMITJSON.class)) {
            LJSONTypeElement ljsonElement = LJSONTypeElement.create(elements, element);
            processorHelper.setLimitJSONTypeElements(ljsonElement);
            processorHelper.i("---------------------" + ljsonElement.toString());
//
            TypeElement typeElement = (TypeElement) element;
            Element a = typeElement.getEnclosingElement();
            processorHelper.i("LJSON ++" + a + " " + element.getKind().name());
            List<? extends Element> param = typeElement.getEnclosedElements();
            for (Element p : param) {
                if (p instanceof VariableElement) {
                    processorHelper.i("LJSON ........" + p.getSimpleName() + " " + p.asType().toString());
                }
            }
        }
    }

    private void processGson(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Gson.class)) {
            GsonTypeElement gsonElement = GsonTypeElement.create(elements, element);
            processorHelper.setGsonTypeElements(gsonElement);
            processorHelper.i("---------------------" + gsonElement.toString());
//
            TypeElement typeElement = (TypeElement) element;
            Element a = typeElement.getEnclosingElement();
            processorHelper.i("Gson ++" + a + " " + element.getKind().name());
            List<? extends Element> param = typeElement.getEnclosedElements();
            for (Element p : param) {
                if (p instanceof VariableElement) {
                    processorHelper.i("Gson ........" + p.getSimpleName() + " " + p.asType().toString());
                }
            }
        }
    }
}
