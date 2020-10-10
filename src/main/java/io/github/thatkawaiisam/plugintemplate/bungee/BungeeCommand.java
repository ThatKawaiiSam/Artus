package io.github.thatkawaiisam.plugintemplate.bungee;

import co.aikar.commands.BaseCommand;
import io.github.thatkawaiisam.plugintemplate.bungee.handlers.BungeeCommandHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ICommand;
import lombok.Getter;

public class BungeeCommand extends BaseCommand implements ICommand {

    @Getter private BungeeCommandHandler commandHandler;

    /**
     * Bukkit Command.
     *
     * @param commandHandler instance.
     */
    public BungeeCommand(BungeeCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void register() {
        commandHandler.getManager().registerCommand(this);
    }

    @Override
    public void unregister() {
        commandHandler.getManager().unregisterCommand(this);
    }
}
