package io.github.thatkawaiisam.plugintemplate.bukkit;

import lombok.Getter;

public class BukkitModuleListener<T extends BukkitModule> extends BukkitListener {

    @Getter private T module;

    public BukkitModuleListener(T module) {
        super(module.getJavaPlugin());
        this.module = module;
    }
}
