package io.github.thatkawaiisam.artus.bukkit;

import io.github.thatkawaiisam.artus.base.BasePlugin;
import io.github.thatkawaiisam.artus.module.ModuleFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class BukkitPlugin extends JavaPlugin implements BasePlugin {

    private ModuleFactory<BukkitModule> modules = new ModuleFactory<>();

    /**
     * Get Logger from Bukkit Server.
     *
     * @return logger instance.
     */
    public Logger getPluginLogger() {
        return getServer().getLogger();
    }

    @Override
    public ModuleFactory<BukkitModule> getModuleFactory() {
        return modules;
    }
}
