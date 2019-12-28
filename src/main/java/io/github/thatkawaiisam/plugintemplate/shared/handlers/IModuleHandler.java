package io.github.thatkawaiisam.plugintemplate.shared.handlers;

import io.github.thatkawaiisam.plugintemplate.shared.Module;

import java.util.ArrayList;
import java.util.List;

public interface NewModuleHandler {

    List<Module> modules = new ArrayList<>();

    default boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

    public default void addModule(Module module) {
        getModules().add(module);
    }

    public default Module getModule(String moduleName) {
        return getModules().stream()
                .filter(module -> module.getModuleName().equalsIgnoreCase(moduleName))
                .findFirst()
                .orElse(null);
    }

    boolean shouldEnable(String moduleName);

    List<Module> getModules();
}
