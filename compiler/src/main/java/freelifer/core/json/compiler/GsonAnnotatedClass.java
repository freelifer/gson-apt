package freelifer.core.json.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import freelifer.core.json.compiler.gson.GsonTypeElement;
import freelifer.core.json.compiler.gson.GsonVariableElement;
import freelifer.core.json.compiler.gson.adapter.GsonCodeParameter;

/**
 * @author kzhu on 2017/12/19.
 */
public class GsonAnnotatedClass {

    /**
     * 生成叶子类静态方法 read 和 write
     *
     * @param helper 工具类
     * @param filer  写入文件
     * @throws IOException
     */
    public static void toWriteAllGsonClass(ProcessorHelper helper, Filer filer) throws IOException {
        List<GsonTypeElement> gsonTypeElements = helper.getGsonTypeElements();
        if (Collections.isEmpty(gsonTypeElements)) {
            return;
        }

        // 生成方法
        List<MethodSpec> methodSpecs = new ArrayList<>(gsonTypeElements.size() * 2);
        for (GsonTypeElement element : gsonTypeElements) {
            List<MethodSpec> methods = generateGsonReadAndWriteMethod(helper, element);
            methodSpecs.addAll(methods);
        }

        // 生成类 freelifer.gson.{moduleName}.GsonTypeHelper
        TypeSpec finderClass = TypeSpec.classBuilder("GsonTypeHelper")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CORE_PACKAGE_NAME, "Core"), TypeName.get(classElement.asType())))
                .addMethods(methodSpecs)
//                .addMethod(saveInstanceStateBuilder.build())
                .build();

        String packageName = String.format("freelifer.gson.%s", helper.getModuleName());
        // generate file
        JavaFile.builder(packageName, finderClass).build().writeTo(filer);
    }

    private static List<MethodSpec> generateGsonReadAndWriteMethod(ProcessorHelper helper, GsonTypeElement element) {
        List<MethodSpec> methodSpecs = new ArrayList<>(2);
        MethodSpec readMethodSpec = generateGsonReadMethod(helper, element);
        MethodSpec writeMethodSpec = generateGsonWriteMethod(helper, element);
        methodSpecs.add(readMethodSpec);
        methodSpecs.add(writeMethodSpec);
        return methodSpecs;
    }

    //        public T read(com.google.gson.stream.JsonReader reader) throws IOException
    private static MethodSpec generateGsonReadMethod(ProcessorHelper helper, GsonTypeElement element) {
        String typeName = element.getTypeName();
        ClassName thisObj = ClassName.get(element.getTypePackageName(), typeName);
        List<GsonVariableElement> gsonVariableElements = element.getVariableElements();

        MethodSpec.Builder readMethod = MethodSpec.methodBuilder("read" + typeName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(thisObj)
                .addParameter(ClassName.get("com.google.gson.stream", "JsonReader"), "in")
                .addException(ClassName.get("java.io", "IOException"));

        // new 一个对象
        readMethod.addStatement("$T target = new $T()", thisObj, thisObj)
                .addStatement("in.beginObject()");

        CodeBlock.Builder caseBlock = CodeBlock.builder().beginControlFlow("switch (in.nextName())");
        addGsonVariable(helper, caseBlock, gsonVariableElements);
        caseBlock.endControlFlow();

        readMethod.beginControlFlow("while (in.hasNext())")
                .addCode(caseBlock.build())
                .endControlFlow();

        // 返回对象
        readMethod.addStatement("in.endObject()")
                .addStatement("return target");
        return readMethod.build();
    }

    //        public void write(JsonWriter writer, T student) throws IOException {
    private static MethodSpec generateGsonWriteMethod(ProcessorHelper helper, GsonTypeElement element) {
        String typeName = element.getTypeName();
        ClassName thisObj = ClassName.get(element.getTypePackageName(), typeName);

        MethodSpec.Builder writeMethod = MethodSpec.methodBuilder("write" + typeName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get("com.google.gson.stream", "JsonWriter"), "out")
                .addParameter(thisObj, "value")
                .addException(ClassName.get("java.io", "IOException"));
        return writeMethod.build();
    }

    private static void addGsonVariable(ProcessorHelper helper, CodeBlock.Builder codeBlock, List<GsonVariableElement> gsonVariableElements) {
        if (Collections.isEmpty(gsonVariableElements)) {
            return;
        }

        for (GsonVariableElement element : gsonVariableElements) {
            TypeMirror typeMirror = element.typeMirror();
            codeBlock.add("case $S:\n", element.jsonName());

            // 开始在case内容体 添加代码
            addCodeBlockStatement(helper, codeBlock, element.variableName(), typeMirror.toString());
            codeBlock.addStatement("break");
        }
    }

    private static void addCodeBlockStatement(ProcessorHelper helper, CodeBlock.Builder codeBlock, String variableName, String type) {
        GsonCodeParameter parameters = handleCodeParameter(helper, type);
        if (parameters.type == 0) {
            helper.i(">>>>>>>NOT FOUND Type[%s] for %s<<<<<<", type, variableName);
            return;
        }

        if (parameters.type == 1 || parameters.type == 2) {
            codeBlock.addStatement("target.$N = $N", variableName, parameters.value);
        } else if (parameters.type == 3) {
            addCodeBlockStatementForList(helper, codeBlock, variableName, parameters.value);
        } else if (parameters.type == 4) {
            addCodeBlockStatementForArray(helper, codeBlock, variableName, parameters.value);
        }
    }

    private static GsonCodeParameter handleCodeParameter(ProcessorHelper helper, String type) {
        return helper.getAdapter(type).transform("in");
    }

    // 数组第一次 第二次 逻辑不一样
    private static void addCodeBlockStatementForList(ProcessorHelper helper, CodeBlock.Builder codeBlock, String variableName, String type) {
        GsonCodeParameter parameters = handleCodeParameter(helper, type);
        helper.i(">>>>>>>parameters [%d] for %s<<<<<<", parameters.type, parameters.value);
        if (parameters.type ==  0) {
            helper.i(">>>>>>>NOT FOUND Class[%s] for %s<<<<<<", type, variableName);
            return;
        }

        codeBlock.addStatement("target.$N = new $T()", variableName, ClassName.get(ArrayList.class));
        codeBlock.addStatement("in.beginArray()");
        codeBlock.beginControlFlow("while (in.hasNext())");

        // 递归
        if (parameters.type == 1 || parameters.type == 2) {
            codeBlock.addStatement("target.$N.add($N)", variableName, parameters.value);
        } else if (parameters.type == 3) {
            // 特殊处理
            addCodeBlockStatementForList(helper, codeBlock, variableName, parameters.value);
        }

        codeBlock.endControlFlow();
        codeBlock.addStatement("in.endArray()");
    }

    private static void addCodeBlockStatementForArray(ProcessorHelper helper, CodeBlock.Builder codeBlock, String variableName, String type) {
        GsonCodeParameter parameters = handleCodeParameter(helper, type);
        helper.i(">>>>>>>parameters [%d] for %s<<<<<<", parameters.type, parameters.value);
        if (parameters.type == 0) {
            helper.i(">>>>>>>NOT FOUND Class[%s] for %s<<<<<<", type, variableName);
            return;
        }

        // type 基本类型 -> 对象
        ClassName typeClassName = ClassName.bestGuess(mapping(type));
        codeBlock.addStatement("ArrayList<$T> a1 = new $T()", typeClassName, ClassName.get(ArrayList.class));
        codeBlock.addStatement("in.beginArray()");
        codeBlock.beginControlFlow("while (in.hasNext())");

        // 递归
        if (parameters.type == 1 || parameters.type == 2) {
            codeBlock.addStatement("a1.add($N)", parameters.value);
        }

        codeBlock.endControlFlow();

        // 基本类型数组 赋值需要特殊处理
        if (parameters.type == 1) {
            codeBlock.addStatement("target.$N = new $N[a1.size()]", variableName, type);
            codeBlock.beginControlFlow(" for (int i = 0; i < a1.size(); i++)");
            codeBlock.addStatement("target.$N[i] = a1.get(i)", variableName);
            codeBlock.endControlFlow();
        } else {
            codeBlock.addStatement("target.$N = a1.toArray(new $T[0])", variableName, typeClassName);
        }
        codeBlock.addStatement("in.endArray()");
    }

    private static String mapping(String type) {
        if ("int".equalsIgnoreCase(type)) {
            return Integer.class.getCanonicalName();
        } else if ("boolean".equalsIgnoreCase(type)) {
            return Boolean.class.getCanonicalName();
        } else if ("float".equalsIgnoreCase(type)) {
            return Float.class.getCanonicalName();
        } else if ("long".equalsIgnoreCase(type)) {
            return Long.class.getCanonicalName();
        } else if ("double".equalsIgnoreCase(type)) {
            return Double.class.getCanonicalName();
        }
        return type;
    }

    // target.$N = in.next*()/read*(in) 单表达式
    //
//    private static String transform(ProcessorHelper helper, TypeMirror typeMirror) {
//        final TypeKind typeKind = typeMirror.getKind();
//        final String typeMirrorStr = typeMirror.toString();
//        helper.i(">>>>>>>>>>>>><<<<<<<<<<" + typeKind.name() + " " + typeMirrorStr);
//        Adapter adapter = helper.getAdapter(typeMirrorStr);
//        if (adapter != null) {
//            return adapter.transform("in");
//        }
//        GsonCodeParameter parameters = null;
//        if (parameters.type == 1) {
//
//        }
//        helper.i(">>>>>>>NOT FOUND Adapter<<<<<<" + typeKind.name() + " " + typeMirrorStr);
//        String result = "";
//        switch (typeKind) {
////            case ARRAY:
////                if ("int[]".equals(typeMirrorStr)) {
////                    limitJSONType = new LimitJSONTypeArray("optInt", "int");
////                } else if ("java.lang.String[]".equals(typeMirrorStr)) {
////                    limitJSONType = new LimitJSONTypeArray("optString", "String");
////                } else if ("long[]".equals(typeMirrorStr)) {
////                    limitJSONType = new LimitJSONTypeArray("optLong", "long");
////                } else if ("double[]".equals(typeMirrorStr)) {
////                    limitJSONType = new LimitJSONTypeArray("optDouble", "double");
////                } else if ("boolean[]".equals(typeMirrorStr)) {
////                    limitJSONType = new LimitJSONTypeArray("optBoolean", "boolean");
////                } else {
////                    // Object[]
////                    String className = typeMirrorStr.substring(0, typeMirrorStr.length() - 2);
////                    ljsonTypeElement = findLJSONTypeElement(helper, className);
////                    if (ljsonTypeElement != null) {
////                        limitJSONType = new LimitJSONTypeArrayObject("optString", className, ljsonTypeElement.getQualifiedName() + "$$CREATOR");
////                    }
////                }
////                break;
//            case DECLARED:
//                if ("java.lang.String".equals(typeMirror.toString())) {
//                    result = "in.nextString()";
//                } else if (typeMirrorStr.startsWith("java.util.List") || typeMirrorStr.startsWith("java.util.ArrayList")) {
////                    java.util.(Array)?List\<(\S*)\>
//                    String regEx = "\\<(\\S*)\\>";
//                    Pattern pattern = Pattern.compile(regEx);
//                    Matcher mat = pattern.matcher(typeMirrorStr);
//                    if (mat.find()) {
//                        helper.i("<<<<<<<<<<<<<<List DECLARED " + mat.group(1));
//                        try {
//                            String className = mat.group(1);
//                            result = transform(helper, className);
//                            GsonTypeElement gsonTypeElement = findGsonTypeElement(helper, className);
//                            if (gsonTypeElement != null) {
//                                limitJSONType = new LimitJSONTypeList("optString", "", gsonTypeElement.getQualifiedName() + "$$CREATOR");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        return result;
//    }

//    private static String transform(ProcessorHelper helper, String typeName) {
//        Adapter adapter = helper.getAdapter(typeName);
//        if (adapter != null) {
//            return adapter.transform("in");
//        }
//        helper.i(">>>>>>>NOT FOUND Adapter<<<<<<" + typeName);
//        return "";
//    }
}
