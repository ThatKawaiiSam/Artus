package io.github.plugintemplate.handlers;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public abstract class AbstractHandler {

    private String handlerName;
    private BukkitConfigHelper configuration;

    private JavaPlugin instance;

    public AbstractHandler(String handlerName, boolean generateConfigurationFile, JavaPlugin instance) {
        this.instance = instance;
        this.handlerName = handlerName;

        //Whether or not to generate a configuration file.
        if (generateConfigurationFile) {
            configuration = new BukkitConfigHelper(
                    instance,
                    handlerName,
                    instance.getDataFolder().getAbsolutePath()
            );
        }

        instance.getLogger().info("Registered " + handlerName + " Handler.");
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void enable() {
        onEnable();
        instance.getLogger().info("Enabled " + this.handlerName + " Handler.");
    }

    public void disable() {
        onDisable();
        instance.getLogger().info("Disabled " + this.handlerName + " Handler.");
    }

}
