package io.github.thatkawaiisam.artus.base;

import io.github.thatkawaiisam.artus.module.Module;
import io.github.thatkawaiisam.artus.module.ModuleFactory;

import java.util.logging.Logger;

public interface BasePlugin<T extends Module> {

    /**
     *
     * @return
     */
    Logger getPluginLogger();

    /**
     *
     * @return
     */
    ModuleFactory<T> getModuleFactory();

}
