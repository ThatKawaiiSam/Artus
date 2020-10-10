package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import io.github.thatkawaiisam.plugintemplate.shared.IConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitConfiguration extends BukkitConfigHelper implements IConfiguration {

    /**
     *
     * @param plugin
     * @param name
     * @param directory
     */
    public BukkitConfiguration(JavaPlugin plugin, String name, String directory) {
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
        return (T) this.getConfiguration();
    }
}
