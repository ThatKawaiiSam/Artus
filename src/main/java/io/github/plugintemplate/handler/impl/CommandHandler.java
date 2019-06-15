package io.github.plugintemplate.handler.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.Sets;
import io.github.plugintemplate.handler.Handler;
import io.github.thatkawaiisam.utils.ClassUtility;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class CommandHandler extends Handler {

    private PaperCommandManager manager;
    private Set<BaseCommand> baseCommands = Sets.newConcurrentHashSet();

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
            if (isCommand(clazz)) {
                try {
                    baseCommands.add((BaseCommand) clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isCommand(Class<?> clazz) {
        return BaseCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void onEnable() {
        baseCommands.forEach(manager::registerCommand);
    }

    @Override
    public void onDisable() {
        baseCommands.forEach(manager::unregisterCommand);
    }
}
