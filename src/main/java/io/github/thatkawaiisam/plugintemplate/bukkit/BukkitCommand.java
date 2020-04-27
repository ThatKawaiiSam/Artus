package io.github.thatkawaiisam.plugintemplate.bukkit;

import co.aikar.commands.BaseCommand;
import io.github.thatkawaiisam.plugintemplate.bukkit.handlers.BukkitCommandHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ICommand;
import lombok.Getter;

public class BukkitCommand extends BaseCommand implements ICommand {

    @Getter private BukkitCommandHandler commandHandler;

    /**
     * Bukkit Command.
     *
     * @param commandHandler instance.
     */
    public BukkitCommand(BukkitCommandHandler commandHandler) {
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
