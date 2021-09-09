package io.github.thatkawaiisam.artus.bungee;

import io.github.thatkawaiisam.artus.base.BaseListener;
import net.md_5.bungee.api.plugin.Listener;

import lombok.Getter;

public class BungeeListener<T extends BungeeModule<?>> implements BaseListener, Listener {

    @Getter private T module;

    /**
     * Bungee Listener.
     *
     * @param module instance.
     */
    public BungeeListener(T module) {
        this.module = module;
    }

    @Override
    public void register() {
        this.module.getPlugin().getProxy().getPluginManager().registerListener(this.module.getPlugin(), this);
    }

    @Override
    public void unregister() {
        this.module.getPlugin().getProxy().getPluginManager().unregisterListener(this);
    }

}