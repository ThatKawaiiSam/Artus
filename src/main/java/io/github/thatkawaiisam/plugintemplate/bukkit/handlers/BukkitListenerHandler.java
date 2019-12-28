package io.github.thatkawaiisam.plugintemplate.bukkit.handlers;

import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;
import io.github.thatkawaiisam.plugintemplate.shared.IListener;
import io.github.thatkawaiisam.utils.ClassUtility;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitListenerHandler extends BukkitHandler {

    Set<IListener> listeners = ConcurrentHashMap.newKeySet();

    public BukkitListenerHandler(JavaPlugin javaPlugin, String packageName) {
        super(javaPlugin, "listeners", false);
        loadListenersFromPackage(javaPlugin, packageName);
    }

    public BukkitListenerHandler(JavaPlugin javaPlugin, String packageName, ConstructorInject constructorInject) {
        super(javaPlugin, "listeners", false);
        loadListenersFromPackage(javaPlugin, packageName, constructorInject);
    }

    public void loadListenersFromPackage(Plugin plugin, String packageName) {
        ClassUtility.getClassesInPackage(plugin.getClass(), packageName).stream()
                .filter(this::isListener)
                .forEach(aClass -> {
                    try {
                        listeners.add((IListener) aClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void loadListenersFromPackage(JavaPlugin plugin, String packageName, ConstructorInject constructorInject) {
        for (Class<?> clazz : ClassUtility.getClassesInPackage(plugin.getClass(), packageName)) {
            if (isListener(clazz)) {
                try {
                    Constructor constructor = clazz.getConstructor(constructorInject.getClassArray());
                    Object listener = constructor.newInstance(constructorInject.getObjectArray());
                    listeners.add((IListener) listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isListener(Class<?> clazz) {
        return IListener.class.isAssignableFrom(clazz);
    }


    @Override
    public void onEnable() {
//        this.listeners.forEach(IListener::register);
        this.listeners.forEach(listener -> {
            listener.register();
            getJavaPlugin().getLogger().info("[LISTENER] Registered " + listener.getClass().getSimpleName() + ".");
        });
    }

    @Override
    public void onDisable() {
//        this.listeners.forEach(IListener::unregister);
        this.listeners.forEach(listener -> {
            listener.unregister();
            getJavaPlugin().getLogger().info("[LISTENER] Unregistered " + listener.getClass().getSimpleName() + ".");
        });
    }

}
