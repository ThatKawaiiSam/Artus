package io.github.thatkawaiisam.plugintemplate.bungee;


import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeModuleListener<T extends BungeeModule<E>, E extends Plugin> extends BungeeListener<E> {

    @Getter
    private T module;

    /**
     *
     * @param module
     */
    public BungeeModuleListener(T module) {
        super(module.getPlugin());
        this.module = module;
    }
}