package io.github.plugintemplate;

import io.github.plugintemplate.handlers.AbstractHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class AbstractPluginTemplate extends JavaPlugin {

    //Handlers
    @Getter @Setter private static List<AbstractHandler> handlers;

    public static AbstractPluginTemplate getPluginInstance() {
        return (AbstractPluginTemplate) getProvidingPlugin(AbstractPluginTemplate.class);
    }

    public static AbstractHandler getHandler(String handlerName) {
        for (AbstractHandler handler : handlers) {
            if (handler.getHandlerName().equalsIgnoreCase(handlerName)) {
                return handler;
            }
        }
        return null;
    }
}
