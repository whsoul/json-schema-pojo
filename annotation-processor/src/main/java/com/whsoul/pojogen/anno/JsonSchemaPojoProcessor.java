package com.whsoul.pojogen.anno;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes(
        "com.whsoul.anno.POJOGEN")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class JsonSchemaPojoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        System.out.println("pojo gen process : ");

        for(Element element : roundEnv.getElementsAnnotatedWith(POJOGEN.class)){
            POJOGEN pojogen = element.getAnnotation(POJOGEN.class);
            System.out.println("annotation a : " + pojogen.schemeUrl());

            try {
                FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", "test.json");

                try(
                    InputStreamReader isr = new InputStreamReader(resource.openInputStream());
                ) {
                    ObjectMapper om = new ObjectMapper();
                    Map<String, Object> test = om.readValue(isr, new TypeReference<Map<String, Object>>(){});

                    System.out.println(test);
                }
            } catch (IOException e) {
                System.out.println("no");
                e.printStackTrace();
            }

        }

//        for (TypeElement annotation : annotations) {
//
//
//            System.out.println("annotation : " + annotation);
//            System.out.println("annotation enclosed s : " + annotation.getEnclosedElements().size());
//            System.out.println("annotation enclosed  1 : " + annotation.getEnclosingElement().asType());
//
//            System.out.println("RootElement : " + roundEnv.getRootElements());
//            System.out.println("ElementAnnotatedWith : " + roundEnv.getElementsAnnotatedWith(annotation));
//
//        }

        return true;
    }

    private void writeBuilderFile(String className, Map<String, String> setterMap) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Builder";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");
            out.println();

            setterMap.entrySet().forEach(setter -> {
                String methodName = setter.getKey();
                String argumentType = setter.getValue();

                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(methodName);

                out.print("(");

                out.print(argumentType);
                out.println(" value) {");
                out.print("        object.");
                out.print(methodName);
                out.println("(value);");
                out.println("        return this;");
                out.println("    }");
                out.println();
            });

            out.println("}");

        }
    }
}