package io.github.plugintemplate.handlers.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.plugintemplate.handlers.AbstractHandler;
import io.github.plugintemplate.modules.AbstractModule;
import io.github.thatkawaiisam.utils.ClassUtility;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class CommandHandler extends AbstractHandler {

    private PaperCommandManager manager;
    private Set<BaseCommand> baseCommands = new HashSet<>();

    public CommandHandler(JavaPlugin instance) {
        super("command", false, instance);
        manager = new PaperCommandManager(instance);
    }

    public CommandHandler(String commandPath, JavaPlugin instance) {
        this(instance);
        loadCommandFromPackage(instance, commandPath);
    }

    public void loadCommandFromPackage(JavaPlugin plugin, String packageName) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin, packageName)) {
            if (isModule(clazz)) {
                try {
                    baseCommands.add((BaseCommand) clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isModule(Class<?> clazz) {
        return BaseCommand.class.isAssignableFrom(clazz);
        //return clazz.isAssignableFrom(AbstractModule.class);
    }

    @Override
    public void onEnable() {
        for (BaseCommand command : baseCommands) {
            manager.registerCommand(command);
        }
    }

    @Override
    public void onDisable() {
        for (BaseCommand command : baseCommands) {
            manager.unregisterCommand(command);
        }
    }
}
