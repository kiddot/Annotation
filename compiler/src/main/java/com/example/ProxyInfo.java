package com.example;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by kiddo on 17-4-5.
 */

public class ProxyInfo {
    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;

    public Map<Integer, VariableElement> injectVariables = new HashMap<>();

    public static final String PROXY ="ViewInject";

    public ProxyInfo(Elements elements, TypeElement classElement){
        this.typeElement = classElement;
        PackageElement packageElement = elements.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }

    public String generateJavaCode(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("// Generated code. Do not modify!\n");
        stringBuilder.append("package ").append(packageName).append(";\n\n");
        stringBuilder.append("import com.example.*;\n");
        stringBuilder.append("\n");

        stringBuilder.append("public class ").append(proxyClassName).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        stringBuilder.append(" {\n");

        generateMethods(stringBuilder);
        stringBuilder.append('\n');

        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    private void generateMethods(StringBuilder stringBuilder) {
        stringBuilder.append("@Override\n ");
        stringBuilder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ){\n");

        for (int id : injectVariables.keySet()){
            VariableElement element = injectVariables.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            stringBuilder.append(" if(source instanceof android.app.Activity){\n");
            stringBuilder.append("host." + name).append(" = ");
            stringBuilder.append("(" + type + ")(((android.app.Activity)source).findViewById( " + id + "));\n");
            stringBuilder.append("\n}else{\n");
            stringBuilder.append("host." + name).append(" = ");
            stringBuilder.append("(" + type + ")(((android.view.View)source).findViewById( " + id + "));\n");
            stringBuilder.append("\n};");
        }
        stringBuilder.append("     }\n");

    }

    public String getProxyClassName(){
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement(){
        return typeElement;
    }
}
