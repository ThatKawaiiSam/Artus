package io.github.thatkawaiisam.artus.module;

import io.github.thatkawaiisam.filare.BaseConfiguration;
import io.github.thatkawaiisam.artus.base.BaseCommand;
import io.github.thatkawaiisam.artus.base.BaseListener;
import io.github.thatkawaiisam.artus.base.BasePlugin;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public abstract class Module<T extends BaseConfiguration, E extends BasePlugin> {

    private E plugin;

    @Getter private String id;
    @Getter private boolean enabled = false;
    @Getter private ModuleOptions options = new ModuleOptions();

    private BaseConfiguration configuration;
    private Set<BaseListener> listeners = new HashSet<>();
    private Set<BaseCommand> commands = new HashSet<>();
//    private Set<Module<F, T, E>> submodules = new HashSet<>();

    /**
     * Module.
     *
     * @param plugin instance
     * @param id of module.
     */
    public Module(E plugin, String id) {
        this.plugin = plugin;
        this.id = id;
    }

    /**
     * Module enable.
     */
    public void enable() {
        // Generate Configuration file.
        if (this.options.isGenerateConfiguration()) {
            this.configuration = this.createConfiguration();
        }

        if (this.options.isGenerateConfiguration() && this.configuration != null) {
            this.configuration.load();
        }

        // Set module to enabled.
        this.enabled = true;

        // Call on enable.
        this.onEnable();

        // Check if disable has been called upon enabling.
        // If so, prevent everything else from registering.
        if (!this.enabled) {
            return;
        }

        // Register all.
        this.listeners.forEach(BaseListener::register);
        this.commands.forEach(BaseCommand::register);

        this.plugin.getPluginLogger().info(String.format("[MODULE] Enabled %s Module.", this.id));
    }

    /**
     * Module disable.
     */
    public void disable() {
        // Unregister all.
        this.listeners.forEach(BaseListener::unregister);
        this.commands.forEach(BaseCommand::unregister);

        // Clear all.
        this.listeners.clear();
        this.commands.clear();

        // Call on disable.
        this.onDisable();

        // Set module to disabled.
        this.enabled = false;
        this.plugin.getPluginLogger().info(String.format("[MODULE] Disabled %s Module.", this.id));
    }

    /**
     * On module enable.
     */
    public abstract void onEnable();

    /**
     * On module disable.
     */
    public abstract void onDisable();

    /**
     * Create Configuration Implementation and attach to module.
     *
     * @return configuration with specific implementation.
     */
    public abstract BaseConfiguration createConfiguration();

    /**
     * Provides a default value of the file name.
     * By default, all files create by a module are stored
     * in a directory named 'modules' within the plugin
     * directory. This is designed to be overwritten for
     * specific modules - like the lang.yml which can be in
     * the root folder.
     *
     * @return name of file.
     */
    public String getFileName() {
        return "modules/" + getId();
    }

    /**
     * Check to see if the module is ready to be enabled via custom implementation (i.e Config File).
     *
     * @return whether the module should be enabled.
     */
    public boolean shouldEnable() {
        return true;
    }

    /**
     * Add Listener to module.
     *
     * @param listener to be added.
     */
    public void addListener(BaseListener listener) {
        this.listeners.add(listener);
        getPlugin().getPluginLogger().info("ADDED LISTENER TO LIST!");
    }

    /**
     * Remove Listener from module.
     *
     * @param listener to be removed.
     */
    public void removeListener(BaseListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Add Command to module.
     *
     * @param command to be added.
     */
    public void addCommand(BaseCommand command) {
        this.commands.add(command);
    }

    /**
     * Remove Command from module.
     *
     * @param command to be removed.
     */
    public void removeCommand(BaseCommand command) {
        this.commands.remove(command);
    }

    /**
     * Get Configuration attached to module.
     *
     * @return configuration file.
     */
    public T getConfiguration() {
        return (T) this.configuration;
    }

    /**
     * Get Plugin attached to module.
     *
     * @return plugin instance.
     */
    public E getPlugin() {
        return plugin;
    }

    /**
     * Get Load Weight of module.
     *
     * @return weight associated with allocated load level.
     */
    public int getLoadWeight() {
        return this.options.getLoadLevel().getWeight();
    }

}
