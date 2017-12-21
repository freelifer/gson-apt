package freelifer.core.json.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @author kzhu on 2017/12/19.
 */
public class LJSONAnnotatedClass {

    public static void toWrite(ProcessorHelper helper, LJSONTypeElement ljsonElement, Filer filer) throws IOException {

        // create common ClassName
        ClassName thisObj = ClassName.bestGuess(ljsonElement.getTypeName());
        ClassName jsonObject = ClassName.get("org.json", "JSONObject");

        MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(thisObj)
                .addParameter(ClassName.get(String.class), "json", Modifier.FINAL)
                .addParameter(TypeName.BOOLEAN, "allowNull", Modifier.FINAL)
                .addStatement("$T thisObj = new $T()", thisObj, thisObj)
                .beginControlFlow("try")
                .addStatement("$T root = new $T(json)", jsonObject, jsonObject);

        List<LIMITJSONVariable> ljsonVariableElements = ljsonElement.getVariableElements();
        addLJSONVariable(helper, createMethod, ljsonVariableElements);

        createMethod.nextControlFlow("catch (Exception e)")
                .beginControlFlow("if (allowNull)")
                .addStatement("return null")
                .endControlFlow()
                .endControlFlow();

        createMethod.addStatement("return thisObj");
        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(ljsonElement.getTypeName() + "$$CREATOR")
                .addModifiers(Modifier.PUBLIC)
//                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CORE_PACKAGE_NAME, "Core"), TypeName.get(classElement.asType())))
                .addMethod(createMethod.build())
//                .addMethod(parseIntentBuilder.build())
//                .addMethod(saveInstanceStateBuilder.build())
                .build();

        String packageName = ljsonElement.getTypePackageName();
        // generate file
        JavaFile.builder(packageName, finderClass).build().writeTo(filer);
    }

    private static void addLJSONVariable(ProcessorHelper helper, MethodSpec.Builder builder, List<LIMITJSONVariable> ljsonVariableElements) {
        if (Collections.isEmpty(ljsonVariableElements)) {
            return;
        }

        for (LIMITJSONVariable variable : ljsonVariableElements) {
            LJSONTypeElement ljsonTypeElement = null;
            helper.i(variable.toString());
            TypeMirror typeMirror = variable.typeMirror();
            final TypeKind typeKind = typeMirror.getKind();
            final String typeMirrorStr = typeMirror.toString();
            helper.i(">>>>>>>>>>>>>" + typeKind.name());
            LimitJSONType limitJSONType = null;
            switch (typeKind) {
                case INT:
                    limitJSONType = new LimitJSONType("thisObj.$N = root.optInt($S)");
                    break;
                case BOOLEAN:
                    limitJSONType = new LimitJSONType("thisObj.$N = root.optBoolean($S)");
                    break;
                case ARRAY:
                    if ("int[]".equals(typeMirrorStr)) {
                        helper.i("<<<<<<<<<<<<<<<<<" + typeMirror.getClass());
                    } else if ("String[]".equals(typeMirrorStr)) {

                    }
                    break;
                case DECLARED:
                    if ("java.lang.String".equals(typeMirror.toString())) {
                        limitJSONType = new LimitJSONType("thisObj.$N = root.optString($S)");
                    } else {
                        ljsonTypeElement = findLJSONTypeElement(helper, typeMirror.toString());
                        if (ljsonTypeElement != null) {
                            limitJSONType = new LimitJSONTypeObject("thisObj.$N = $T.create(root.optString($S), allowNull)", ljsonTypeElement.getQualifiedName() + "$$CREATOR");
                        }
                    }
                    break;
                default:
                    break;
            }
            if (limitJSONType != null) {
                limitJSONType.addStatement(builder, variable);
            }
        }
    }

    private static LJSONTypeElement findLJSONTypeElement(ProcessorHelper helper, String className) {
        List<LJSONTypeElement> list = helper.getLimitJSONTypeElements();
        if (Collections.isEmpty(list) || className == null) {
            return null;
        }
        for (LJSONTypeElement element : list) {
            if (className.equals(element.getQualifiedName())) {
                return element;
            }
        }
        return null;
    }
}
