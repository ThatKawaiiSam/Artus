package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.plugintemplate.bungee.handlers.BungeeCommandHandler;
import lombok.Getter;

public class BungeeModuleCommand<T extends BungeeModule> extends BungeeCommand {

    @Getter
    private T module;

    /**
     *
     * @param module
     * @param commandHandler
     */
    public BungeeModuleCommand(T module, BungeeCommandHandler commandHandler) {
        super(commandHandler);
        this.module = module;
    }
}
