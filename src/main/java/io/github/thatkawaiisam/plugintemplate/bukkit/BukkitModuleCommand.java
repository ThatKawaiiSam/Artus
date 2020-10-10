package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.plugintemplate.bukkit.handlers.BukkitCommandHandler;
import lombok.Getter;

public class BukkitModuleCommand<T extends BukkitModule> extends BukkitCommand {

    @Getter private T module;

    /**
     *
     * @param module
     * @param commandHandler
     */
    public BukkitModuleCommand(T module, BukkitCommandHandler commandHandler) {
        super(commandHandler);
        this.module = module;
    }
}
