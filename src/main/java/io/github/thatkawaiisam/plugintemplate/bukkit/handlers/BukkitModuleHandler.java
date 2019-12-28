package io.github.thatkawaiisam.plugintemplate.bukkit.handlers;

import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitHandler;
import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitPluginTemplate;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;
import io.github.thatkawaiisam.plugintemplate.shared.IListener;
import io.github.thatkawaiisam.plugintemplate.shared.Module;
import io.github.thatkawaiisam.plugintemplate.shared.handlers.IModuleHandler;
import io.github.thatkawaiisam.utils.ClassUtility;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class BukkitModuleHandler extends BukkitHandler implements IModuleHandler {

    List<Module> modules = new ArrayList<>();

    public BukkitModuleHandler(JavaPlugin javaPlugin, String packageName) {
        super(javaPlugin, "modules", true);
        loadModulesFromPackage(javaPlugin, packageName);
    }
    public BukkitModuleHandler(JavaPlugin javaPlugin, String packageName, boolean generateConfiguration) {
        super(javaPlugin, "modules", generateConfiguration);
        loadModulesFromPackage(javaPlugin, packageName);
    }


    public BukkitModuleHandler(JavaPlugin javaPlugin, String packageName, ConstructorInject constructorInject) {
        super(javaPlugin, "modules", true);
        loadModulesFromPackage(javaPlugin, packageName, constructorInject);
    }

    public BukkitModuleHandler(JavaPlugin javaPlugin, String packageName, boolean generateConfiguration, ConstructorInject constructorInject) {
        super(javaPlugin, "modules", generateConfiguration);
        loadModulesFromPackage(javaPlugin, packageName, constructorInject);
    }

    public void loadModulesFromPackage(JavaPlugin plugin, String packageName) {
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

    public void loadModulesFromPackage(JavaPlugin plugin, String packageName, ConstructorInject constructorInject) {
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
        Configuration configuration = getConfiguration();
        return configuration.getBoolean(moduleName);
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }
}
