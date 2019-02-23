package io.github.plugintemplate.handler.impl;

import com.google.common.base.Joiner;
import io.github.plugintemplate.handler.Handler;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class LanguageHandler extends Handler {

    private HashMap<String, String> values = new HashMap<>();

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
        final String value = values.get(key);
        if (color) {
            return MessageUtility.formatMessage(value);
        }
        return value;
    }

    public void attemptLoad(Configuration configuration) {
        configuration.getValues(true).forEach((key, object) -> {
            getInstance().getLogger().info("Loaded in language value for '" + key + "'.");
            String value;
            if (configuration.isList(key)) {
                value = Joiner.on("\n").join(configuration.getStringList(key));
            } else {
                value = configuration.getString(key);
            }
            values.put(key, value);
        });
    }

}
