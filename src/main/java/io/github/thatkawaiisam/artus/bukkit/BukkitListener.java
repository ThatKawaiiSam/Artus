package io.github.thatkawaiisam.artus.bukkit;

import io.github.thatkawaiisam.artus.base.BaseListener;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class BukkitListener<T extends BukkitModule<?>> implements BaseListener, Listener {

    @Getter private T module;

    /**
     * Bukkit Listener.
     *
     * @param module instance.
     */
    public BukkitListener(T module) {
        this.module = module;
    }

    @Override
    public void register() {
        this.module.getPlugin().getServer().getPluginManager().registerEvents(this, this.module.getPlugin());
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
    }

}
