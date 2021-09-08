package io.github.thatkawaiisam.artus.bukkit;

import co.aikar.commands.BukkitCommandManager;
import io.github.thatkawaiisam.artus.base.BasePlugin;
import io.github.thatkawaiisam.artus.module.ModuleFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class BukkitPlugin extends JavaPlugin implements BasePlugin {

    private ModuleFactory<BukkitModule> modules = new ModuleFactory<>();
    private BukkitCommandManager commandManager = null;

    @Override
    public Logger getPluginLogger() {
        return getServer().getLogger();
    }

    @Override
    public ModuleFactory<BukkitModule> getModuleFactory() {
        return modules;
    }

    public BukkitCommandManager getCommandManager() {
        // Create Command Manager if doesn't exist.
        if (commandManager == null) {
            this.commandManager = new BukkitCommandManager(this);
        }
        return commandManager;
    }

}
