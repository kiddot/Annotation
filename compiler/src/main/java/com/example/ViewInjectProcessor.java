package com.example;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by kiddo on 17-4-5.
 */

public class ViewInjectProcessor extends AbstractProcessor {
    private Filer mFileUtils;
    private Messager messager;
    private Elements mElementUtils;
    private Map<String, ProxyInfo> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Bind.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE , " process...");
        mProxyMap.clear();
        //获取所有用到注解的地方
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Bind.class);
        for(Element element : elements){
            checkAnnotationValid(element, Bind.class);//检验用到注解的地方是否符合规定

            VariableElement variableElement = (VariableElement) element;

            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();//类的类型

            String fullClassName = classElement.getQualifiedName().toString();//类的全名

            ProxyInfo proxyInfo = mProxyMap.get(fullClassName);
            if (proxyInfo == null){
                proxyInfo = new ProxyInfo(mElementUtils, classElement);
                mProxyMap.put(fullClassName, proxyInfo);
            }

            Bind bindAnnotation = variableElement.getAnnotation(Bind.class);
            int id = bindAnnotation.value();
            proxyInfo.injectVariables.put(id, variableElement);
        }

        for (String key : mProxyMap.keySet()){
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try{
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(
                        proxyInfo.getProxyClassName(),
                        proxyInfo.getTypeElement()
                );
                Writer writer = javaFileObject.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            }catch (IOException e){
                error(proxyInfo.getTypeElement(), "Unable to write injector for type %s:%s", proxyInfo.getTypeElement(), e.getMessage());
            }
        }
        return true;
    }

    private boolean checkAnnotationValid(Element element, Class clazz){
        if (element.getKind() != ElementKind.FIELD){
            error(element, "%s must be declared on field.", clazz.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(element)){
            error(element, "%s() must be can not be private.", element.getSimpleName());
        }
        return true;
    }

    private void error(Element element, String message, Object... args)
    {
        if (args.length > 0)
        {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
