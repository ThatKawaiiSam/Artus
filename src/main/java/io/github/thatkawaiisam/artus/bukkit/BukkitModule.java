package io.github.thatkawaiisam.artus.bukkit;

import io.github.thatkawaiisam.filare.BaseConfiguration;
import io.github.thatkawaiisam.filare.BukkitConfiguration;
import io.github.thatkawaiisam.artus.module.Module;

public abstract class BukkitModule<E extends BukkitPlugin> extends Module<BukkitConfiguration, E> {

    /**
     * Bukkit Module.
     *
     * @param plugin instance.
     * @param id of module.
     */
    public BukkitModule(E plugin, String id) {
        super(plugin, id);
    }

    @Override
    public BaseConfiguration createConfiguration() {
        return new BukkitConfiguration(
                this.getPlugin(),
                this.getFileName(),
                this.getPlugin().getDataFolder().getAbsolutePath()
        );
    }

}
