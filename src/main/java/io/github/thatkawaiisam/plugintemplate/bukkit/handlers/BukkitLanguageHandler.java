package io.github.thatkawaiisam.plugintemplate.bukkit.handlers;

import io.github.thatkawaiisam.plugintemplate.bukkit.BukkitHandler;
import io.github.thatkawaiisam.plugintemplate.shared.LanguageValue;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitLanguageHandler extends BukkitHandler {

    public ConcurrentHashMap<String, LanguageValue> values = new ConcurrentHashMap<>();

    public BukkitLanguageHandler(JavaPlugin javaPlugin) {
        super(javaPlugin, "lang", true);
    }

    @Override
    public void onEnable() {
        Configuration c = getConfiguration();
        c.getValues(true).forEach((key, object) -> {
            if (c.isList(key)) {
                String newValue = "";
                for (String string : c.getStringList(key)) {
                    if (string.isEmpty() || string.equalsIgnoreCase(" ")) {
                        newValue = newValue + ChatColor.BLUE + " " + ChatColor.RESET + "\n";
                    } else {
                        newValue = newValue + string + ChatColor.RESET + "\n";
                    }
                }
                values.put(key, new LanguageValue<>(newValue));
            } else {
                values.put(key, new LanguageValue<>(c.getString(key)));
            }
        });
    }

    @Override
    public void onDisable() {
        values.clear();
    }

    public String getValue(String key, boolean color) {
        LanguageValue<String> langValue = values.get(key);
        return color ? MessageUtility.formatMessage(langValue.getValue()) : langValue.getValue();
    }

    public List<String> getValueAsList(String key, boolean color) {
        LanguageValue<String> langValue = values.get(key);
        List<String> vals = new ArrayList<>();
        vals.addAll(Arrays.asList(langValue.getValue().split("\n")));
        return color ? MessageUtility.formatMessages(vals) : vals;
    }
}
