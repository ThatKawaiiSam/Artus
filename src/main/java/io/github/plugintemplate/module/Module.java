package io.github.plugintemplate.module;

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
public abstract class Module {

    private BukkitConfigHelper configuration;
    private String moduleName;
    private boolean enabled = false;
    private final Set<Listener> listeners = new HashSet<>();

    private JavaPlugin instance;

    public Module(String moduleName, JavaPlugin instance, boolean generateConfiguration) {
        this.instance = instance;
        this.moduleName = moduleName;

        if (generateConfiguration) {
            //Setup configuration for each module
            this.configuration = new BukkitConfigHelper(
                    instance,
                    "modules/" + moduleName,
                    instance.getDataFolder().getAbsolutePath()
            );
        }

        instance.getLogger().info(String.format("Successfully registered module '%s'.", moduleName));
    }

    public void enable() {
        if (this.configuration != null) {
            getConfiguration().load();
        }

        onEnable();
        listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, instance));

        setEnabled(true);

        instance.getLogger().info(String.format("Successfully enabled module '%s'.", moduleName));
    }

    public void disable() {
        listeners.forEach(HandlerList::unregisterAll);

        listeners.clear();

        onDisable();
        setEnabled(false);

        instance.getLogger().info(String.format("Successfully disabled module '%s'.", moduleName));
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public String getDisplayStatus(){
        return enabled ? "&aEnabled" : "&cDisabled";
    }

}
