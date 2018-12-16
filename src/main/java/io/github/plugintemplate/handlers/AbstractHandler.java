package io.github.plugintemplate.handlers;

import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractHandler {

    private String handlerName;
    private BukkitConfigHelper configuration;

    public AbstractHandler(String handlerName, boolean generateConfigurationFile) {
        this.handlerName = handlerName;

        //Whether or not to generate a configuration file.
        if (generateConfigurationFile) {
            configuration = new BukkitConfigHelper(
                    AbstractPluginTemplate.getPluginInstance(),
                    handlerName,
                    AbstractPluginTemplate.getPluginInstance().getDataFolder().getAbsolutePath()
            );
        }

        AbstractPluginTemplate.getPluginInstance().getLogger().info("Registered " + handlerName + " Handler.");
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void enable() {
        onEnable();
        AbstractPluginTemplate.getPluginInstance().getLogger().info("Enabled " + this.handlerName + " Handler.");
    }

    public void disable() {
        onDisable();
        AbstractPluginTemplate.getPluginInstance().getLogger().info("Disabled " + this.handlerName + " Handler.");
    }

}
