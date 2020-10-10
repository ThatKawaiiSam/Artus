package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.plugintemplate.shared.IListener;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitListener<T extends JavaPlugin> implements IListener, Listener {

    @Getter private T javaPlugin;

    /**
     *
     * @param javaPlugin
     */
    public BukkitListener(T javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void register() {
        this.javaPlugin.getServer().getPluginManager().registerEvents(this, this.javaPlugin);
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
