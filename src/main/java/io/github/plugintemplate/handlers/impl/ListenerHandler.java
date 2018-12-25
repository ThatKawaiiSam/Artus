package io.github.plugintemplate.handlers.impl;

import io.github.plugintemplate.handlers.AbstractHandler;
import io.github.plugintemplate.modules.AbstractModule;
import io.github.thatkawaiisam.utils.ClassUtility;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class ListenerHandler extends AbstractHandler {

    private Set<Listener> listeners = new HashSet<>();

    public ListenerHandler(JavaPlugin instance) {
        super("listeners", false, instance);
    }

    public ListenerHandler(String listenerPath, JavaPlugin instance) {
        this(instance);
        loadListenersFromPackage(instance, listenerPath);
    }

    public void loadListenersFromPackage(Plugin plugin, String packageName) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin, packageName)) {
            if (isListener(clazz)) {
                try {
                    listeners.add((Listener) clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isListener(Class<?> clazz) {
        for (Class<?> interfaze : clazz.getInterfaces()) {
            if (interfaze == Listener.class) {
                return true;
            }
        }

        return false;
    }

    public void registerListeners() {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, getInstance());
        }
    }

    public void unregisterListeners() {
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
