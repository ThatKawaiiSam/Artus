package io.github.plugintemplate.handlers.impl;

import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.plugintemplate.handlers.AbstractHandler;
import io.github.plugintemplate.modules.AbstractModule;
import io.github.thatkawaiisam.utils.ClassUtility;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleHandler extends AbstractHandler {

    //Modules
    @Getter private List<AbstractModule> modules = new ArrayList<>();

    public ModuleHandler(JavaPlugin instance){
        super("modules", true, instance);
        new File(instance.getDataFolder().getAbsolutePath() + File.separator + "modules").mkdirs();
    }

    public ModuleHandler(String modulePath, JavaPlugin instance) {
        this(instance);
        loadModulesFromPackage(instance, modulePath);
    }

    public void loadModulesFromPackage(JavaPlugin plugin, String packageName) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin, packageName)) {
            if (isModule(clazz)) {
                 try {
                    addModule((AbstractModule)clazz.newInstance());
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
            }
        }
    }

    private boolean isModule(Class<?> clazz) {
        return AbstractModule.class.isAssignableFrom(clazz);
    }

    public void addModule(AbstractModule module) {
        modules.add(module);
    }

    public AbstractModule getModule(String moduleName) {
        for (AbstractModule module : modules) {
            if (module.getModuleName().equalsIgnoreCase(moduleName)) {
                return module;
            }
        }
        return null;
    }

    @Override
    public void onEnable() {
        for (AbstractModule module : getModules()) {
            if (!module.isEnabled()
                    && getConfiguration().getBoolean(module.getModuleName())) {
                module.enable();
            }
        }
    }

    @Override
    public void onDisable() {
        for (AbstractModule module : getModules()) {
            if (module.isEnabled()) {
                module.disable();
            }
        }
    }

}
