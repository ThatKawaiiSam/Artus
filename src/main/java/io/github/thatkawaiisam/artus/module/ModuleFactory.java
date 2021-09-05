package io.github.thatkawaiisam.artus.module;

import io.github.thatkawaiisam.artus.ConstructorInject;
import io.github.thatkawaiisam.utils.ClassUtility;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleFactory<T extends Module> {

    @Getter private Set<T> modules = new HashSet<>();

    /**
     * Get module in factory by ID.
     *
     * @param id of module.
     * @return module if present.
     */
    public <G extends T> G getModule(String id) {
        for (Module module : this.modules) {
            if (module.getId().equalsIgnoreCase(id)) {
                return (G) module;
            }
        }
        return null;
    }

    /**
     * Add module to factory.
     *
     * @param module to be added.
     */
    public void addModule(T module) {
        this.modules.add(module);
    }

    /**
     * Enable all modules in factory.
     */
    public void enableModules() {
        this.getSortedModules(true).forEach(Module::enable);
    }

    /**
     * Disable all modules in factory.
     */
    public void disableModules() {
        this.getSortedModules(false).forEach(Module::disable);
    }

    /**
     * Gets a sorted list of the modules in factory.
     *
     * @return list of modules.
     */
    private List<T> getSortedModules(boolean enable) {
        // Define comparator.
        Comparator<T> comparator = Comparator.comparing(Module::getLoadWeight);
        if (!enable) {
            comparator = comparator.reversed();
        }

        // Define filter.
        Stream<T> stream = this.modules.stream().sorted(comparator);
        if (enable) {
            stream = stream.filter(module -> !module.isEnabled()).filter(Module::shouldEnable);
        }

        // Collect and return.
        return stream.collect(Collectors.toList());
    }

    /**
     *
     * @param clazz
     * @param packageName
     */
    public void loadModulesFromPackage(Class<T> clazz, String packageName) {
        this.loadModulesFromPackage(clazz, packageName, null);
    }

    /**
     *
     * @param clazz
     * @param packageName
     */
    public void loadModulesFromPackage(Class<T> clazz, String packageName, ConstructorInject inject) {
        ClassUtility.getClassesInPackage(clazz, packageName).stream()
                .filter(this::isModule)
                .forEach(module -> {
                    try {
                        T instance;
                        if (inject != null) {
                            Constructor<T> constructor = clazz.getConstructor(inject.getClassArray());
                            instance = constructor.newInstance(inject.getObjectArray());
                        } else {
                            instance = (T) module.newInstance();
                        }
                        addModule(instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * Checks whether a class is a declared module.
     *
     * @param clazz to check.
     * @return whether class is a module.
     */
    public boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

}
