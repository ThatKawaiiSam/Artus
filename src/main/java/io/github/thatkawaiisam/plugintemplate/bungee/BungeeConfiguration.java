package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.configs.BungeeConfigHelper;
import io.github.thatkawaiisam.plugintemplate.shared.IConfiguration;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeConfiguration extends BungeeConfigHelper implements IConfiguration {

    public BungeeConfiguration(Plugin plugin, String name, String directory) {
        super(plugin, name, directory);
    }

    @Override
    public void loadFile() {
        this.load();
    }

    @Override
    public void saveFile() {
        this.save();
    }

    @Override
    public <T> T getAmbig() {
        return (T) this;
    }
}
