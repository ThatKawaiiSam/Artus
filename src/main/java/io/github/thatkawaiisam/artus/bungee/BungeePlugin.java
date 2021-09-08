package io.github.thatkawaiisam.artus.bungee;

import co.aikar.commands.BungeeCommandManager;
import io.github.thatkawaiisam.artus.base.BasePlugin;
import io.github.thatkawaiisam.artus.module.ModuleFactory;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public abstract class BungeePlugin extends Plugin implements BasePlugin {

    private ModuleFactory<BungeeModule> modules = new ModuleFactory<>();
    private BungeeCommandManager commandManager;

    @Override
    public Logger getPluginLogger() {
        return getProxy().getLogger();
    }

    @Override
    public ModuleFactory<BungeeModule> getModuleFactory() {
        return modules;
    }

    public BungeeCommandManager getCommandManager() {
        // Create Command Manager if doesn't exist.
        if (commandManager == null) {
            this.commandManager = new BungeeCommandManager(this);
        }
        return commandManager;
    }

}
