package io.github.thatkawaiisam.plugintemplate.bungee.handlers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.CommandManager;
import co.aikar.commands.PaperCommandManager;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;
import io.github.thatkawaiisam.utils.ClassUtility;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BungeeCommandHandler extends BungeeHandler {

    @Getter private BungeeCommandManager manager;
    Set<BaseCommand> baseCommands = ConcurrentHashMap.newKeySet();

    public BungeeCommandHandler(Plugin javaPlugin, String packageName) {
        super(javaPlugin, "commands", false);
        manager = new BungeeCommandManager(javaPlugin);
        loadCommandFromPackage(javaPlugin, packageName);
        loadCustomLanguageFile();
    }

    public BungeeCommandHandler(Plugin javaPlugin, String packageName, ConstructorInject constructorInject) {
        super(javaPlugin, "commands", false);
        manager = new BungeeCommandManager(javaPlugin);
        loadCommandFromPackage(javaPlugin, packageName, constructorInject);
        loadCustomLanguageFile();
    }

    private void loadCustomLanguageFile() {
        if (!generateConfiguration) {
            return;
        }
        try {
            manager.getLocales().loadYamlLanguageFile("lang.yml", Locale.ENGLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCommandFromPackage(Plugin plugin, String packageName) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin.getClass(), packageName)) {
            if (isCommand(clazz)) {
                try {
                    baseCommands.add((BaseCommand) clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadCommandFromPackage(Plugin plugin, String packageName, ConstructorInject constructorInject) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin.getClass(), packageName)) {
            if (isCommand(clazz)) {
                try {
                    Constructor constructor = clazz.getConstructor(constructorInject.getClassArray());
                    Object baseCommand = constructor.newInstance(constructorInject.getObjectArray());
                    baseCommands.add((BaseCommand)baseCommand);
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
        baseCommands.forEach(baseCommand -> {
            manager.registerCommand(baseCommand);
            getPlugin().getLogger().info("[COMMAND] Registered " + baseCommand.getClass().getSimpleName() + ".");
        });
    }

    @Override
    public void onDisable() {
        baseCommands.forEach(baseCommand -> {
            manager.registerCommand(baseCommand);
            getPlugin().getLogger().info("[COMMAND] Unregistered " + baseCommand.getClass().getSimpleName() + ".");
        });
    }
}

