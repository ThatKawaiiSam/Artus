package io.github.plugintemplate.handler;

import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public abstract class Handler {

    private String handlerName;
    private BukkitConfigHelper configuration;

    private JavaPlugin instance;

    public Handler(String handlerName, boolean generateConfigurationFile, JavaPlugin instance) {
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

        instance.getLogger().info(String.format("Registered %s Handler.", handlerName));
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void enable() {
        onEnable();
        instance.getLogger().info(String.format("Enabled %s Handler.", this.handlerName));
    }

    public void disable() {
        onDisable();
        instance.getLogger().info(String.format("Disabled %s Handler.", this.handlerName));
    }

}
