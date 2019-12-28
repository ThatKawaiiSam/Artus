package io.github.thatkawaiisam.plugintemplate.bungee;

import io.github.thatkawaiisam.plugintemplate.bungee.handlers.BungeeModuleHandler;
import io.github.thatkawaiisam.plugintemplate.shared.Handler;
import io.github.thatkawaiisam.plugintemplate.shared.LoadLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BungeePluginTemplate extends Plugin {

    @Getter @Setter private List<Handler> handlers;

    public <T extends BungeeHandler> T getHandler(String id) {
        for (Handler handler : handlers) {
            if (handler.handlerName.equalsIgnoreCase(id)) {
                return (T) handler;
            }
        }
        return null;
    }

//    public  getLanguage() {
//        return getHandler("lang");
//    }

    public <T extends BungeeModule> T getModule(String module) {
        BungeeModuleHandler moduleHandler = getHandler("modules");
        return moduleHandler.getModule(module);
//        return null;
    }

    public void enableHandlers() {
        for (LoadLevel loadLevel : LoadLevel.values()) {
            for (Handler handler : handlers) {
                if (handler.getLoadLevel() == loadLevel) {
                    handler.enable();
                }
            }
        }
    }

    public void disableHandlers() {
        List<LoadLevel> loads = Arrays.asList(LoadLevel.values());
        Collections.reverse(loads);
        loads.forEach(loadLevel -> {
            for (Handler handler : handlers) {
                if (handler.getLoadLevel() == loadLevel) {
                    handler.disable();
                }
            }
        });
    }
}
