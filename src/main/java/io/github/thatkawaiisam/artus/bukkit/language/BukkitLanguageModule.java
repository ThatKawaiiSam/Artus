package io.github.thatkawaiisam.artus.bukkit.language;

import io.github.thatkawaiisam.artus.bukkit.BukkitModule;
import io.github.thatkawaiisam.artus.bukkit.BukkitPlugin;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitLanguageModule<T extends BukkitPlugin> extends BukkitModule<T> {

    private ConcurrentHashMap<String, LanguageValue> values = new ConcurrentHashMap<>();

    /**
     * Bukkit Language Module.
     *
     * @param plugin instance.
     */
    public BukkitLanguageModule(T plugin) {
        super(plugin, "lang");
        getOptions().setGenerateConfiguration(true);
    }

    @Override
    public void onEnable() {
        // Get configuration.
        Configuration c = getConfiguration().getImplementation();

        // Loop through each key in configuration.
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

    @Override
    public boolean shouldEnable() {
        return true;
    }

    @Override
    public String getFileName() {
        return getId();
    }

    /**
     * Get language value.
     *
     * @param key of language value.
     * @param color whether to translate color codes.
     * @return language result.
     */
    public String getValue(String key, boolean color) {
        LanguageValue<String> langValue = values.get(key);
        return color ? MessageUtility.formatMessage(langValue.getValue()) : langValue.getValue();
    }

    /**
     * Get language value as list.
     *
     * @param key of language value.
     * @param color whether to translate color codes.
     * @return language result as list.
     */
    public List<String> getValueAsList(String key, boolean color) {
        LanguageValue<String> langValue = values.get(key);
        List<String> vals = new ArrayList<>();
        vals.addAll(Arrays.asList(langValue.getValue().split("\n")));
        return color ? MessageUtility.formatMessages(vals) : vals;
    }
}
