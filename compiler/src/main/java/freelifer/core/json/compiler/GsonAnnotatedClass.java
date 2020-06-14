package freelifer.core.json.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import freelifer.core.json.compiler.gson.GsonTypeElement;
import freelifer.core.json.compiler.gson.GsonVariableElement;
import freelifer.core.json.compiler.gson.adapter.Adapter;

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

    public static void toWrite(ProcessorHelper helper, GsonTypeElement gsonTypeElement, Filer filer) throws IOException {

        // create common ClassName
        ClassName thisObj = ClassName.bestGuess(gsonTypeElement.getTypeName());
        ClassName jsonObject = ClassName.get("org.json", "JSONObject");

        MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(thisObj)
                .addParameter(ClassName.get(String.class), "json", Modifier.FINAL)
                .addParameter(TypeName.BOOLEAN, "allowNull", Modifier.FINAL)
                .addStatement("$T thisObj = new $T()", thisObj, thisObj)
                .beginControlFlow("try")
                .addStatement("$T root = new $T(json)", jsonObject, jsonObject);

        List<GsonVariableElement> gsonVariableElements = gsonTypeElement.getVariableElements();
        helper.i("<<<<<<<<<<<<<<GsonVariableElement " + gsonVariableElements.get(1));

        createMethod.nextControlFlow("catch (Exception e)")
                .beginControlFlow("if (allowNull)")
                .addStatement("return null")
                .endControlFlow()
                .endControlFlow();

        createMethod.addStatement("return thisObj");

        // read
//        public T read(com.google.gson.stream.JsonReader reader) throws IOException
        MethodSpec.Builder readMethod = MethodSpec.methodBuilder("read")
                .addModifiers(Modifier.PUBLIC)
                .returns(thisObj)
                .addParameter(ClassName.get("com.google.gson.stream", "JsonReader"), "reader")
                .addException(ClassName.get("java.io", "IOException"));

        // switch
        CodeBlock.Builder caseBlock = CodeBlock.builder().beginControlFlow("switch (reader.nextName())");
        addGsonVariable(helper, caseBlock, gsonVariableElements);
        caseBlock.endControlFlow();

        readMethod.addStatement("$T thisObj = new $T()", thisObj, thisObj)
                .addStatement("reader.beginObject()")
                .addStatement("String fieldname = null")
                .beginControlFlow("while (reader.hasNext())")
                .addCode(caseBlock.build())
//                .addStatement("$T token = reader.peek()", ClassName.get("com.google.gson.stream", "JsonToken"))
                .endControlFlow();


        readMethod.addStatement("reader.endObject()")
                .addStatement("return null");

        // write
//        public void write(JsonWriter writer, T student) throws IOException {
        MethodSpec.Builder writeMethod = MethodSpec.methodBuilder("write")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("com.google.gson.stream", "JsonWriter"), "writer")
                .addParameter(thisObj, "value")
                .addException(ClassName.get("java.io", "IOException"));


        // TypeAdapter
        ClassName typeAdapter = ClassName.get("com.google.gson", "TypeAdapter");
        ParameterizedTypeName typeAdapterParameterizedTypeName = ParameterizedTypeName.get(typeAdapter, thisObj);

        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(gsonTypeElement.getTypeName() + "$$TypeAdapter")
                .addModifiers(Modifier.PUBLIC)
                .superclass(typeAdapterParameterizedTypeName)
//                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CORE_PACKAGE_NAME, "Core"), TypeName.get(classElement.asType())))
                .addMethod(createMethod.build())
                .addMethod(readMethod.build())
                .addMethod(writeMethod.build())
//                .addMethod(parseIntentBuilder.build())
//                .addMethod(saveInstanceStateBuilder.build())
                .build();

        String packageName = gsonTypeElement.getTypePackageName();
        // generate file
        JavaFile.builder("a.b.c", finderClass).build().writeTo(filer);
    }

    private static void addGsonVariable(ProcessorHelper helper, CodeBlock.Builder codeBlock, List<GsonVariableElement> gsonVariableElements) {
        if (Collections.isEmpty(gsonVariableElements)) {
            return;
        }

        for (GsonVariableElement element : gsonVariableElements) {
            TypeMirror typeMirror = element.typeMirror();
            codeBlock.add("case $S:\n", element.jsonName())
                    .addStatement("target.$N = $N", element.variableName(), transform(helper, typeMirror))
                    .addStatement("break");
        }
    }

    private static String transform(ProcessorHelper helper, TypeMirror typeMirror) {
        final TypeKind typeKind = typeMirror.getKind();
        final String typeMirrorStr = typeMirror.toString();
        helper.i(">>>>>>>>>>>>><<<<<<<<<<" + typeKind.name() + " " + typeMirrorStr);
        Adapter adapter = helper.getAdapter(typeMirrorStr);
        if (adapter != null) {
            return adapter.transform("in");
        }
        helper.i(">>>>>>>NOT FOUND Adapter<<<<<<" + typeKind.name() + " " + typeMirrorStr);
        String result = "";
        switch (typeKind) {
//            case ARRAY:
//                if ("int[]".equals(typeMirrorStr)) {
//                    limitJSONType = new LimitJSONTypeArray("optInt", "int");
//                } else if ("java.lang.String[]".equals(typeMirrorStr)) {
//                    limitJSONType = new LimitJSONTypeArray("optString", "String");
//                } else if ("long[]".equals(typeMirrorStr)) {
//                    limitJSONType = new LimitJSONTypeArray("optLong", "long");
//                } else if ("double[]".equals(typeMirrorStr)) {
//                    limitJSONType = new LimitJSONTypeArray("optDouble", "double");
//                } else if ("boolean[]".equals(typeMirrorStr)) {
//                    limitJSONType = new LimitJSONTypeArray("optBoolean", "boolean");
//                } else {
//                    // Object[]
//                    String className = typeMirrorStr.substring(0, typeMirrorStr.length() - 2);
//                    ljsonTypeElement = findLJSONTypeElement(helper, className);
//                    if (ljsonTypeElement != null) {
//                        limitJSONType = new LimitJSONTypeArrayObject("optString", className, ljsonTypeElement.getQualifiedName() + "$$CREATOR");
//                    }
//                }
//                break;
            case DECLARED:
                if ("java.lang.String".equals(typeMirror.toString())) {
                    result = "in.nextString()";
                } else if (typeMirrorStr.startsWith("java.util.List") || typeMirrorStr.startsWith("java.util.ArrayList")) {
                    String regEx = "\\<(\\S*)\\>";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher mat = pattern.matcher(typeMirrorStr);
                    if (mat.find()) {
                        helper.i("<<<<<<<<<<<<<<List DECLARED " + mat.group(1));
                        try {
                            String className = mat.group(1);
                            result = transform(helper, className);
//                            GsonTypeElement gsonTypeElement = findGsonTypeElement(helper, className);
//                            if (gsonTypeElement != null) {
//                                limitJSONType = new LimitJSONTypeList("optString", "", gsonTypeElement.getQualifiedName() + "$$CREATOR");
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }

    private static String transform(ProcessorHelper helper, String typeName) {
        Adapter adapter = helper.getAdapter(typeName);
        if (adapter != null) {
            return adapter.transform("in");
        }
        helper.i(">>>>>>>NOT FOUND Adapter<<<<<<" + typeName);
        return "";
    }
}
