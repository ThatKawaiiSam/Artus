package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.plugintemplate.shared.IListener;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeListener<T extends Plugin> implements IListener, Listener {

    @Getter private T plugin;

    public BungeeListener(T plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @Override
    public void unregister() {
        ProxyServer.getInstance().getPluginManager().unregisterListener(this);
    }
}
