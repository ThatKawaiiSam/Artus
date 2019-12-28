package io.github.thatkawaiisam.plugintemplate.shared.handlers;

import io.github.thatkawaiisam.plugintemplate.shared.Module;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public interface IModuleHandler {
=======
public interface NewModuleHandler {

    List<Module> modules = new ArrayList<>();
>>>>>>> master

    default boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

<<<<<<< HEAD
    default void addModule(Module module) {
        getModules().add(module);
    }

    default <T> T getModule(String moduleName) {
        return (T) getModules().stream()
=======
    public default void addModule(Module module) {
        getModules().add(module);
    }

    public default Module getModule(String moduleName) {
        return getModules().stream()
>>>>>>> master
                .filter(module -> module.getModuleName().equalsIgnoreCase(moduleName))
                .findFirst()
                .orElse(null);
    }

    boolean shouldEnable(String moduleName);

    List<Module> getModules();
<<<<<<< HEAD

=======
>>>>>>> master
}
