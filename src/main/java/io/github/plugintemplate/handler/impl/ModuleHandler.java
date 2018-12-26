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

    public ModuleHandler(JavaPlugin instance){
        super("modules", true, instance);

        new File(String.format("%s%smodules", instance.getDataFolder().getAbsolutePath(), File.separator)).mkdirs();
    }

    public ModuleHandler(String modulePath, JavaPlugin instance) {
        this(instance);
        loadModulesFromPackage(instance, modulePath);
    }

    public void loadModulesFromPackage(JavaPlugin plugin, String packageName) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin, packageName)) {
            if (isModule(clazz)) {
                 try {
                    addModule((Module)clazz.newInstance());
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
            }
        }
    }

    private boolean isModule(Class<?> clazz) {
        return Module.class.isAssignableFrom(clazz);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Module getModule(String moduleName) {
        for (Module module : modules) {
            if (module.getModuleName().equalsIgnoreCase(moduleName)) {
                return module;
            }
        }
        return null;
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
