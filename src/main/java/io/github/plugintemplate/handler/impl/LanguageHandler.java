package io.github.plugintemplate.handler.impl;

import com.google.common.base.Joiner;
import io.github.plugintemplate.handler.Handler;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class LanguageHandler extends Handler {

    private ConcurrentHashMap<String, String> values = new ConcurrentHashMap<>();

    public LanguageHandler(JavaPlugin instance) {
        super("lang", true, instance);
    }

    @Override
    public void onEnable() {
        attemptLoad(getConfiguration());
    }

    @Override
    public void onDisable() {
        values.clear();
    }

    public String getValue(String key, boolean color) {
        String string = values.get(key);
        if (string == null) {
            return "";
        }
        return color ? MessageUtility.formatMessage(string) : string;
    }

    public void attemptLoad(Configuration configuration) {
        configuration.getValues(true).forEach((key, object) -> {
            getInstance().getLogger().info("Loaded in language value for '" + key + "'.");
            String value = configuration.isList(key) ? Joiner.on("\n").join(configuration.getStringList(key)) : configuration.getString(key);
            values.put(key, value);
        });
    }

}
