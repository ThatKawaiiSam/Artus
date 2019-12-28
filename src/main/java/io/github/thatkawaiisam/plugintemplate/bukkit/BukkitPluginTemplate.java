package io.github.thatkawaiisam.plugintemplate.bukkit;

import io.github.thatkawaiisam.plugintemplate.bukkit.handlers.BukkitLanguageHandler;
import io.github.thatkawaiisam.plugintemplate.bukkit.handlers.BukkitModuleHandler;
import io.github.thatkawaiisam.plugintemplate.shared.Handler;
import io.github.thatkawaiisam.plugintemplate.shared.LoadLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BukkitPluginTemplate extends JavaPlugin {

    @Getter @Setter private List<Handler> handlers;

    public <T extends BukkitHandler> T getHandler(String id) {
        for (Handler handler : handlers) {
            if (handler.handlerName.equalsIgnoreCase(id)) {
                return (T) handler;
            }
        }
        return null;
    }

    public BukkitLanguageHandler getLanguage() {
        return getHandler("lang");
    }

    public <T extends BukkitModule> T getModule(String module) {
        BukkitModuleHandler moduleHandler = getHandler("modules");
        return (T) moduleHandler.getModule(module);
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
