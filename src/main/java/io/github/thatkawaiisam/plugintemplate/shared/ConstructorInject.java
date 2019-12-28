package io.github.thatkawaiisam.plugintemplate.shared;

import java.util.ArrayList;
import java.util.List;

public class ConstructorInject {

    private List<Class> classes = new ArrayList<>();
    private List<Object> objects = new ArrayList<>();

    public ConstructorInject chain(Class clazz, Object object) {
        classes.add(clazz);
        objects.add(object);
        return this;
    }

    public Class[] getClassArray() {
        return classes.stream().toArray(Class[]::new);
    }

    public Object[] getObjectArray() {
        return objects.stream().toArray(Object[]::new);
    }
}
