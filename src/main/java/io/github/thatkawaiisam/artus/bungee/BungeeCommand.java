package io.github.thatkawaiisam.artus.bungee;

import io.github.thatkawaiisam.artus.base.BaseCommand;
import lombok.Getter;

public class BungeeCommand <T extends BungeeModule<?>> extends co.aikar.commands.BaseCommand implements BaseCommand {

    @Getter private T module;

    /**
     * Bungee Command.
     *
     * @param module instance.
     */
    public BungeeCommand(T module) {
        this.module = module;
    }

    @Override
    public void register() {
        this.module.getPlugin().getCommandManager().registerCommand(this);
    }

    @Override
    public void unregister() {
        this.module.getPlugin().getCommandManager().unregisterCommand(this);
    }

}