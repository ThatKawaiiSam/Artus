package io.github.plugintemplate.handlers.impl;

import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.plugintemplate.handlers.AbstractHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ListenerHandler extends AbstractHandler {

    private static Set<Listener> listeners = new HashSet<>();

    public ListenerHandler() {
        super("listeners", false);
    }

    public static void registerListeners() {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, AbstractPluginTemplate.getPluginInstance());
        }
    }

    public static void unregisterListeners() {
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
    }

    @Override
    public void onEnable() {
        registerListeners();
    }

    @Override
    public void onDisable() {
        unregisterListeners();
    }
}
