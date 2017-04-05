package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by kiddo on 17-4-5.
 */

public class ClassValidator {
    static boolean isPrivate(Element element){
        return element.getModifiers().contains(Modifier.PRIVATE);
    }

    static String getClassName(TypeElement element, String packageName){
        int packageLength = packageName.length() + 1;
        return element.getQualifiedName().toString().substring(packageLength).replace('.', '$');
    }
}
