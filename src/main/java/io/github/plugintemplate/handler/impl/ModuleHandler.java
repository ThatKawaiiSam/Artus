package io.github.plugintemplate.handler.impl;

import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.module.Module;
import io.github.thatkawaiisam.utils.ClassUtility;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleHandler extends Handler {

    //Modules
    @Getter private List<Module> modules = new ArrayList<>();

    public ModuleHandler(JavaPlugin instance, boolean generateFolder){
        super("modules", true, instance);

        if (generateFolder) {
            new File(String.format("%s%smodules", instance.getDataFolder().getAbsolutePath(), File.separator)).mkdirs();
        }
    }

    public ModuleHandler(String modulePath, JavaPlugin instance, boolean generateFolder) {
        this(instance, generateFolder);
        loadModulesFromPackage(instance, modulePath);
    }

    public void loadModulesFromPackage(JavaPlugin plugin, String packageName) {
        ClassUtility.getClassesInPackage(plugin, packageName).stream()
                .filter(this::isModule)
                .forEach(aClass -> {
                    try {
                        addModule((Module)aClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Module getModule(String moduleName) {
        return modules.stream()
                .filter(module -> module.getModuleName().equalsIgnoreCase(moduleName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void onEnable() {
        this.getModules().stream()
                .filter(module -> !module.isEnabled())
                .filter(module -> getConfiguration().getBoolean(module.getModuleName()))
                .forEach(Module::enable);
    }

    @Override
    public void onDisable() {
        this.getModules().stream()
                .filter(Module::isEnabled)
                .forEach(Module::disable);
    }

}
