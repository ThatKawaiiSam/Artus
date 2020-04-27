package io.github.thatkawaiisam.plugintemplate.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitModuleListener<T extends BukkitModule<E>, E extends JavaPlugin> extends BukkitListener<E> {

    @Getter private T module;

    public BukkitModuleListener(T module) {
        super(module.getJavaPlugin());
        this.module = module;
    }
}
