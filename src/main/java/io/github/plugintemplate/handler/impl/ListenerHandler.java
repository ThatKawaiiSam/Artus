package io.github.plugintemplate.handler.impl;

import com.google.common.collect.Sets;
import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.module.Module;
import io.github.thatkawaiisam.utils.ClassUtility;
import io.netty.util.internal.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerHandler extends Handler {

    private final Set<Listener> listeners = Sets.newConcurrentHashSet();

    public ListenerHandler(JavaPlugin instance) {
        super("listeners", false, instance);
    }

    public ListenerHandler(String listenerPath, JavaPlugin instance) {
        this(instance);
        loadListenersFromPackage(instance, listenerPath);
    }

    public void loadListenersFromPackage(Plugin plugin, String packageName) {
        ClassUtility.getClassesInPackage(plugin, packageName).stream()
                .filter(this::isListener)
                .forEach(aClass -> {
                    try {
                        listeners.add((Listener) aClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
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
        this.listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, getInstance()));
    }

    public void unregisterListeners() {
        this.listeners.forEach(HandlerList::unregisterAll);
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
