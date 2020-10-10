//package io.github.thatkawaiisam.plugintemplate.bungee.handlers;
//
//import io.github.thatkawaiisam.plugintemplate.bungee.BungeeHandler;
//import io.github.thatkawaiisam.plugintemplate.shared.LanguageValue;
//import net.md_5.bungee.api.plugin.Plugin;
//import net.md_5.bungee.config.Configuration;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class BungeeLanguageHandler extends BungeeHandler {
//
//    public BungeeLanguageHandler(Plugin plugin) {
//        super(plugin, "lang", true);
//    }
//
//    @Override
//    public void onEnable() {
//        Configuration c = getConfiguration().getConfiguration();
//        c.getValues(true).forEach((key, object) -> {
//            if (c.isList(key)) {
//                String newValue = "";
//                for (String string : c.getStringList(key)) {
//                    if (string.isEmpty() || string.equalsIgnoreCase(" ")) {
//                        newValue = newValue + ChatColor.BLUE + " " + ChatColor.RESET + "\n";
//                    } else {
//                        newValue = newValue + string + ChatColor.RESET + "\n";
//                    }
//                }
//                values.put(key, new LanguageValue<>(newValue));
//            } else {
//                values.put(key, new LanguageValue<>(c.getString(key)));
//            }
//        });
//    }
//
//    @Override
//    public void onDisable() {
//        values.clear();
//    }
//
//    public String getValue(String key, boolean color) {
//        LanguageValue<String> langValue = values.get(key);
//        return color ? MessageUtility.formatMessage(langValue.getValue()) : langValue.getValue();
//    }
//
//    public List<String> getValueAsList(String key, boolean color) {
//        LanguageValue<String> langValue = values.get(key);
//        List<String> vals = new ArrayList<>();
//        vals.addAll(Arrays.asList(langValue.getValue().split("\n")));
//        return color ? MessageUtility.formatMessages(vals) : vals;
//    }
//}
