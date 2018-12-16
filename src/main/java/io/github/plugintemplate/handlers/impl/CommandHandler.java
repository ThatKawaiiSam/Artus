package io.github.plugintemplate.handlers.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.plugintemplate.handlers.AbstractHandler;

import java.util.HashSet;
import java.util.Set;

public class CommandHandler extends AbstractHandler {

    private PaperCommandManager manager;

    private Set<BaseCommand> baseCommands = new HashSet<>();

    public CommandHandler() {
        super("command", false);
        manager = new PaperCommandManager(
                AbstractPluginTemplate.getPluginInstance()
        );
    }

    @Override
    public void onEnable() {
        for (BaseCommand command : baseCommands) {
            manager.registerCommand(command);
        }
    }

    @Override
    public void onDisable() {
        for (BaseCommand command : baseCommands) {
            manager.unregisterCommand(command);
        }
    }
}
