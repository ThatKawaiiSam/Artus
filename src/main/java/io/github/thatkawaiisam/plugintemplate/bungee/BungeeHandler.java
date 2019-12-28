package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.configs.BungeeConfigHelper;
import io.github.thatkawaiisam.plugintemplate.shared.Handler;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeHandler<T extends Plugin> extends Handler {

    @Getter private T plugin;

    public BungeeHandler(T plugin, String handlerName, boolean generateConfiguration) {
        super(handlerName, plugin.getLogger(), generateConfiguration);

        this.plugin = plugin;

        if (generateConfiguration) {
            this.configuration = new BungeeConfiguration(
                    plugin,
                    handlerName,
                    plugin.getDataFolder().getAbsolutePath()
            );
        }
    }

    public BungeeConfigHelper getConfiguration() {
        return this.configuration.getAmbig();
    }

}
