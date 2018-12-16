package io.github.plugintemplate.handlers.impl;

import io.github.plugintemplate.AbstractPluginTemplate;
import io.github.plugintemplate.handlers.AbstractHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter @Setter
public class ConfigHandler extends AbstractHandler {

    private int configVersion = 0;

    public ConfigHandler() {
        super("config", true);
    }

    public ConfigHandler(int configVersion) {
        this();
        this.configVersion = configVersion;
    }

    @Override
    public void onEnable() {
        checkConfigVersion();
        checkLicense();
    }

    @Override
    public void onDisable() {
        checkConfigVersion();
    }

    private void checkLicense() {
        String license = getConfiguration().getString("License");
//        AzalonAPI.LicenseResult licenseResult = AzalonAPI.checkLicense(license);
//        if (licenseResult.action == AzalonAPI.LicenseAction.DENY) {
//            HubAPI.getLogger().warning("[License] " + "Unable to verify license: " + licenseResult.response);
//            Bukkit.getServer().shutdown();
//        } else {
//            HubAPI.getLogger().info("[License] " + "Successfully verified license.");
//        }
        //TODO finish this
    }

    private void checkConfigVersion() {
        if (getConfiguration().getInt("Config-Version") != getConfigVersion()) {
            String absolutePath = AbstractPluginTemplate.getPluginInstance().getDataFolder().getAbsolutePath();
            File dir = new File(absolutePath);
            File[] filesInDir = dir.listFiles();

            if (filesInDir == null || filesInDir.length < 1) {
                return;
            }

            for (File file : filesInDir) {
                AbstractPluginTemplate.getPluginInstance().getLogger().warning(file.getName() + "|||" + file.getAbsolutePath());
                file.renameTo(new File(file.getAbsolutePath() + ".old"));
            }
        }
    }
}
