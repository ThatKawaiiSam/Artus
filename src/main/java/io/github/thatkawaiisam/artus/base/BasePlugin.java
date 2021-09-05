package io.github.thatkawaiisam.artus.base;

import co.aikar.commands.CommandManager;
import io.github.thatkawaiisam.artus.module.Module;
import io.github.thatkawaiisam.artus.module.ModuleFactory;

import java.util.logging.Logger;

public interface BasePlugin<T extends Module> {

    /**
     * Get logger instance in plugin implementation.
     *
     * @return logger instance.
     */
    Logger getPluginLogger();

    /**
     * Get Module Factory declared in plugin.
     *
     * @return factory instance.
     */
    ModuleFactory<T> getModuleFactory();

}
