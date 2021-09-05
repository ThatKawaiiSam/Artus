package io.github.thatkawaiisam.artus.bukkit;

import io.github.thatkawaiisam.artus.base.BaseCommand;
import lombok.Getter;

public class BukkitCommand<T extends BukkitModule<?>> extends co.aikar.commands.BaseCommand implements BaseCommand {

    @Getter private T module;

    /**
     * Bukkit Command.
     *
     * @param module instance.
     */
    public BukkitCommand(T module) {
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
