package io.github.thatkawaiisam.plugintemplate.shared;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public abstract class Module {

    @Getter public String moduleName;
    public IConfiguration configuration;
    public boolean generateConfiguration;
    public Set<IListener> listeners = new HashSet<>();
    public Set<ICommand> commands = new HashSet<>();
    @Getter public boolean enabled = false;
    private Logger logger;

    /**
     *
     * @param moduleName
     * @param logger
     * @param generateConfiguration
     */
    public Module(String moduleName, Logger logger, boolean generateConfiguration) {
        this.moduleName = moduleName;
        this.logger = logger;
        this.generateConfiguration = generateConfiguration;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void enable() {
        if (generateConfiguration) {
            configuration.loadFile();
        }
        enabled = true;
        onEnable();
        if (!enabled) {
            return;
        }
        listeners.forEach(IListener::register);
        commands.forEach(ICommand::register);
        logger.info(String.format("[MODULE] Enabled %s Module.", this.moduleName));
    }

    public void disable() {
        listeners.forEach(IListener::unregister);
        commands.forEach(ICommand::unregister);

        listeners.clear();
        commands.clear();

        onDisable();

        enabled = false;
        logger.info(String.format("[MODULE] Disabled %s Module.", this.moduleName));
    }

    /**
     *
     * @param listener
     */
    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    /**
     *
     * @param command
     */
    public void addCommand(ICommand command) {
        commands.add(command);
    }

}
