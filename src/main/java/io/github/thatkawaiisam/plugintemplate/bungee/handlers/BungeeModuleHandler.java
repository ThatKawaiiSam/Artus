package io.github.thatkawaiisam.plugintemplate.bungee.handlers;

import io.github.thatkawaiisam.plugintemplate.bungee.BungeeHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;
import io.github.thatkawaiisam.plugintemplate.shared.Module;
import io.github.thatkawaiisam.plugintemplate.shared.handlers.IModuleHandler;
import io.github.thatkawaiisam.utils.ClassUtility;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class BungeeModuleHandler extends BungeeHandler implements IModuleHandler {

    List<Module> modules = new ArrayList<>();

    public BungeeModuleHandler(Plugin javaPlugin, String packageName) {
        super(javaPlugin, "modules", true);
        loadModulesFromPackage(javaPlugin, packageName);
    }
    public BungeeModuleHandler(Plugin javaPlugin, String packageName, boolean generateConfiguration) {
        super(javaPlugin, "modules", generateConfiguration);
        loadModulesFromPackage(javaPlugin, packageName);
    }

    public BungeeModuleHandler(Plugin javaPlugin, String packageName, ConstructorInject constructorInject) {
        super(javaPlugin, "modules", true);
        loadModulesFromPackage(javaPlugin, packageName, constructorInject);
    }

    public BungeeModuleHandler(Plugin javaPlugin, String packageName, boolean generateConfiguration, ConstructorInject constructorInject) {
        super(javaPlugin, "modules", generateConfiguration);
        loadModulesFromPackage(javaPlugin, packageName, constructorInject);
    }

    public void loadModulesFromPackage(Plugin plugin, String packageName) {
        ClassUtility.getClassesInPackage(plugin.getClass(), packageName).stream()
                .filter(this::isModule)
                .forEach(aClass -> {
                    try {
                        addModule((Module)aClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void loadModulesFromPackage(Plugin plugin, String packageName, ConstructorInject constructorInject) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin.getClass(), packageName)) {
            if (isModule(clazz)) {
                try {
                    Constructor constructor = clazz.getConstructor(constructorInject.getClassArray());
                    Object module = constructor.newInstance(constructorInject.getObjectArray());
                    addModule((Module) module);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.modules.stream()
                .filter(module -> !module.enabled)
                .filter(module -> shouldEnable(module.moduleName))
                .forEach(Module::enable);
    }

    @Override
    public void onDisable() {
        this.modules.stream()
                .filter(module -> module.enabled)
                .forEach(Module::disable);
    }

    @Override
    public boolean shouldEnable(String moduleName) {
        if (!generateConfiguration) {
            return true;
        }
        Configuration configuration = getConfiguration().getConfiguration();
        return configuration.getBoolean(moduleName);
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }
}
