package io.github.thatkawaiisam.plugintemplate.bukkit.handlers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;
import io.github.thatkawaiisam.utils.ClassUtility;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitCommandHandler extends BukkitHandler {

    @Getter private PaperCommandManager manager;
    Set<BaseCommand> baseCommands = ConcurrentHashMap.newKeySet();

    public BukkitCommandHandler(JavaPlugin javaPlugin, String packageName) {
        super(javaPlugin, "commands", false);
        manager = new PaperCommandManager(javaPlugin);
        loadCommandFromPackage(javaPlugin, packageName);
        loadCustomLanguageFile();
    }

    public BukkitCommandHandler(JavaPlugin javaPlugin, String packageName, ConstructorInject constructorInject) {
        super(javaPlugin, "commands", false);
        manager = new PaperCommandManager(javaPlugin);
        loadCommandFromPackage(javaPlugin, packageName, constructorInject);
        loadCustomLanguageFile();
    }

    private void loadCustomLanguageFile() {
        if (!generateConfiguration) {
            return;
        }
        try {
            manager.getLocales().loadYamlLanguageFile("lang.yml", Locale.ENGLISH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void loadCommandFromPackage(JavaPlugin plugin, String packageName) {
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

    public void loadCommandFromPackage(JavaPlugin plugin, String packageName, ConstructorInject constructorInject) {
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
//        baseCommands.forEach(manager::registerCommand);
        baseCommands.forEach(baseCommand -> {
            manager.registerCommand(baseCommand);
            getJavaPlugin().getLogger().info("[COMMAND] Registered " + baseCommand.getClass().getSimpleName() + ".");
        });
    }

    @Override
    public void onDisable() {
//        baseCommands.forEach(manager::unregisterCommand);
        baseCommands.forEach(baseCommand -> {
            manager.registerCommand(baseCommand);
            getJavaPlugin().getLogger().info("[COMMAND] Unregistered " + baseCommand.getClass().getSimpleName() + ".");
        });
    }
}
