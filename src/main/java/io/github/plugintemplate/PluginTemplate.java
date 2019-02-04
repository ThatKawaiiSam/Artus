package io.github.plugintemplate;

import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.handler.impl.LanguageHandler;
import io.github.plugintemplate.handler.impl.ModuleHandler;
import io.github.plugintemplate.module.Module;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class PluginTemplate extends JavaPlugin {

    //Handlers
    @Getter @Setter private List<Handler> handlers;

    public PluginTemplate getPluginInstance() {
        return this;
    }

    public Handler getHandler(String handlerName) {
        return handlers.stream()
                .filter(handler -> handler.getHandlerName().equalsIgnoreCase(handlerName))
                .findFirst()
                .orElse(null);
    }

    public LanguageHandler getLanguage() {
        return (LanguageHandler) getHandler("lang");
    }

    public Module getModule(String module) {
        return ((ModuleHandler)getHandler("modules")).getModule(module);
    }
}
