package io.github.thatkawaiisam.plugintemplate.shared.handlers;

import io.github.thatkawaiisam.plugintemplate.shared.Module;

import java.util.ArrayList;
import java.util.List;

public interface IModuleHandler {

    default boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

    default void addModule(Module module) {
        getModules().add(module);
    }

    default <T> T getModule(String moduleName) {
        return (T) getModules().stream()
                .filter(module -> module.getModuleName().equalsIgnoreCase(moduleName))
                .findFirst()
                .orElse(null);
    }

    boolean shouldEnable(String moduleName);

    List<Module> getModules();

}
