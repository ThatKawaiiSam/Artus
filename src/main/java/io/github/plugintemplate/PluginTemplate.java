package io.github.plugintemplate;

import io.github.plugintemplate.handler.Handler;
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
}
