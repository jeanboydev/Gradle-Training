package com.jeanboy.module.compiler;

import com.google.auto.service.AutoService;
import com.jeanboy.module.annotation.Field;
import com.jeanboy.module.annotation.From;
import com.jeanboy.module.annotation.To;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @author caojianbo
 * @since 2020/1/16 17:59
 */
@SupportedAnnotationTypes({
        "com.jeanboy.module.annotation.From",
        "com.jeanboy.module.annotation.To",
        "com.jeanboy.module.annotation.Field"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class TransformProcessor extends AbstractProcessor {

    private Messager messager; // 输出日志
    private Elements elements;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elements = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();

        messager.printMessage(Diagnostic.Kind.WARNING, "------TransformProcessor init------");

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.WARNING, "------TransformProcessor process------");
        System.out.println("===========TransformProcessor process=========");

        Set<? extends Element> fromElements = roundEnv.getElementsAnnotatedWith(From.class);
        Set<? extends Element> toElements = roundEnv.getElementsAnnotatedWith(To.class);
        Set<? extends Element> fieldElements = roundEnv.getElementsAnnotatedWith(Field.class);

        for (Element element : fromElements) {
            if (ElementKind.CLASS == element.getKind()) {
                // 类泛型
                TypeElement typeElement = (TypeElement) element;
                typeElement.getQualifiedName();


//                // 获取该注解的类的包名
//                PackageElement packageElement = (PackageElement) element.getEnclosingElement();
//                String packageName = packageElement.getQualifiedName().toString();
//
//                // 获取该注解的类的类名
//                TypeElement classElement = (TypeElement) element;
//                String className = classElement.getSimpleName().toString();
//                String classFullName = classElement.getQualifiedName().toString();
//
//                // 获取该注解的方法名
//                VariableElement methodElement = (VariableElement) element.getEnclosingElement();
//                String methodName = methodElement.getSimpleName().toString();


            }
        }
        return false;
    }
}
