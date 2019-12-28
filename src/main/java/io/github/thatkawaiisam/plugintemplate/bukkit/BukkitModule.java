package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import io.github.thatkawaiisam.plugintemplate.shared.Module;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitModule<T extends JavaPlugin> extends Module {

    @Getter private T javaPlugin;

    public BukkitModule(String moduleName, T plugin, boolean generateConfiguration) {
        super(moduleName, plugin.getLogger(), generateConfiguration);

        this.javaPlugin = plugin;

        if (generateConfiguration) {
            this.configuration = new BukkitConfiguration(
                    plugin,
                    "modules/" + moduleName,
                    plugin.getDataFolder().getAbsolutePath()
            );
        }
    }

    public BukkitConfigHelper getConfiguration() {
        return this.configuration.getAmbig();
    }

}
