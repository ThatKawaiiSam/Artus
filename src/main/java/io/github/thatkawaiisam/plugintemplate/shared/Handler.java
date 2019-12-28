package io.github.thatkawaiisam.plugintemplate.shared;

import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class Handler {

    public String handlerName;
    public IConfiguration configuration;
    public boolean generateConfiguration;
    private Logger logger;
    @Getter @Setter private LoadLevel loadLevel = LoadLevel.NORMAL;

    public Handler(String handlerName, Logger logger) {
        this.handlerName = handlerName;
        this.logger = logger;
    }

    public Handler(String handlerName, Logger logger, boolean generateConfiguration) {
        this.handlerName = handlerName;
        this.generateConfiguration = generateConfiguration;
        this.logger = logger;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void enable() {
        if (generateConfiguration && configuration != null) {
            configuration.loadFile();
        }
        onEnable();
        logger.info(String.format("[HANDLER] Enabled %s Handler.", this.handlerName));
    }

    public void disable() {
        onDisable();
        logger.info(String.format("[HANDLER] Disabled %s Handler.", this.handlerName));
    }
}
