package io.github.plugintemplate.modules;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public abstract class AbstractModule {

    private BukkitConfigHelper configuration;
    private String moduleName;
    private boolean enabled = false;
    private Set<Listener> listeners = new HashSet<>();

    private JavaPlugin instance;

    public AbstractModule(String moduleName, JavaPlugin instance) {
        this.instance = instance;
        this.moduleName = moduleName;

        //Setup configuration for each module
        this.configuration = new BukkitConfigHelper(
                instance,
                "modules/" + moduleName,
                instance.getDataFolder().getAbsolutePath()
        );

        instance.getLogger().info("Successfully registered module '" + moduleName + "'.");
    }

    public void enable() {
        onEnable();
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, instance);
        }
        setEnabled(true);
        instance.getLogger().info("Successfully enabled module '" + moduleName + "'.");
    }

    public void disable() {
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
        listeners.clear();
        onDisable();
        setEnabled(false);
        instance.getLogger().info("Successfully disabled module '" + moduleName + "'.");
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public String displayStatus(){
        return enabled ? "&aEnabled" : "&cDisabled";
    }

}
