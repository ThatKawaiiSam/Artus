package io.github.plugintemplate;

import io.github.plugintemplate.handlers.AbstractHandler;
import io.github.plugintemplate.handlers.impl.ModuleHandler;
import io.github.plugintemplate.modules.AbstractModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class AbstractPluginTemplate extends JavaPlugin {

    //Handlers
    @Getter @Setter private List<AbstractHandler> handlers;

    public AbstractPluginTemplate getPluginInstance() {
        return this;
    }

    public AbstractHandler getHandler(String handlerName) {
        for (AbstractHandler handler : handlers) {
            if (handler.getHandlerName().equalsIgnoreCase(handlerName)) {
                return handler;
            }
        }
        return null;
    }

    public AbstractModule getModule(String moduleName) {
        return ((ModuleHandler) getHandler("modules")).getModule(moduleName);
    }
}
