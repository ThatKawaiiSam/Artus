package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.plugintemplate.shared.Module;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeModule<T extends Plugin> extends Module {

    @Getter private T plugin;

    public BungeeModule(String moduleName, T plugin, boolean generateConfiguration) {
        super(moduleName, plugin.getLogger(), generateConfiguration);

        this.plugin = plugin;

        if (generateConfiguration) {
            this.configuration = new BungeeConfiguration(
                    plugin,
                    "modules/" + moduleName,
                    plugin.getDataFolder().getAbsolutePath()
            );
        }
    }

    public BungeeConfiguration getConfiguration() {
        return this.configuration.getAmbig();
    }
}
