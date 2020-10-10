package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import io.github.thatkawaiisam.plugintemplate.shared.Handler;
import io.github.thatkawaiisam.plugintemplate.shared.IConfiguration;
import lombok.Getter;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitHandler<T extends JavaPlugin> extends Handler {

    @Getter private T javaPlugin;

    /**
     *
     * @param plugin
     * @param handlerName
     * @param generateConfiguration
     */
    public BukkitHandler(T plugin, String handlerName, boolean generateConfiguration) {
        super(handlerName, plugin.getLogger(), generateConfiguration);

        this.javaPlugin = plugin;

        if (generateConfiguration) {
            this.configuration = new BukkitConfiguration(
                    javaPlugin,
                    handlerName,
                    javaPlugin.getDataFolder().getAbsolutePath()
            );
        }
    }

    public BukkitConfigHelper getConfiguration() {
        return this.configuration.getAmbig();
    }

}
