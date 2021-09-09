package io.github.thatkawaiisam.artus.bungee;

import io.github.thatkawaiisam.artus.module.Module;
import io.github.thatkawaiisam.filare.BaseConfiguration;
import io.github.thatkawaiisam.filare.BungeeConfiguration;

public abstract class BungeeModule<E extends BungeePlugin> extends Module<BungeeConfiguration, E> {

    /**
     * Bungee Module.
     *
     * @param plugin instance.
     * @param id of module.
     */
    public BungeeModule(E plugin, String id) {
        super(plugin, id);
    }

    @Override
    public BaseConfiguration createConfiguration() {
        return new BungeeConfiguration(
                this.getPlugin(),
                this.getFileName(),
                this.getPlugin().getDataFolder().getAbsolutePath()
        );
    }

}
