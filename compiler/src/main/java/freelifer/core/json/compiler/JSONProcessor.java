package freelifer.core.json.compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.ArrayList;
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

import freelifer.core.json.annotations.LJSON;

/**
 * @author zhukun on 2017/12/19.
 */
@AutoService(Processor.class)
public class JSONProcessor extends AbstractProcessor {

    private ProcessorHelper processorHelper;
    private Filer filer;
    private Elements elements;
    private Messager messager;

    private List<LJSONTypeElement> list = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();

        processorHelper = ProcessorHelper.create(filer, elements, messager);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        // add annotations type
        annotationTypes.add(LJSON.class.getCanonicalName());

        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        list.clear();

        processorHelper.i("Printing process............................");
        for (TypeElement te : annotations) {
            processorHelper.i("Printing type: " + te.toString());
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                processorHelper.i("Printing: " + e.toString() + " " + e.getSimpleName());
            }
        }

        try {
            processLimitJSON(roundEnv);
            if (list != null && list.size() != 0) {
                for (LJSONTypeElement ljsonElement : list) {
                    LJSONAnnotatedClass.toWrite(list, processorHelper, ljsonElement, filer);
                }
            }

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
        for (Element element : roundEnv.getElementsAnnotatedWith(LJSON.class)) {
            LJSONTypeElement ljsonElement = LJSONTypeElement.create(elements, element);
            list.add(ljsonElement);
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
}
