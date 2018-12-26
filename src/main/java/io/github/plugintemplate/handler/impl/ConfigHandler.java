package io.github.plugintemplate.handler.impl;

import io.github.plugintemplate.handler.Handler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

@Getter @Setter
public class ConfigHandler extends Handler {

    private int configVersion = 0;

    public ConfigHandler(JavaPlugin instance) {
        super("config", true, instance);
    }

    public ConfigHandler(int configVersion, JavaPlugin instance) {
        this(instance);

        this.configVersion = configVersion;
    }

    @Override
    public void onEnable() {
        checkConfigVersion();
        //checkLicense();
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
            String absolutePath = getInstance().getDataFolder().getAbsolutePath();
            File dir = new File(absolutePath);
            File[] filesInDir = dir.listFiles();

            if (filesInDir == null || filesInDir.length < 1) {
                return;
            }

            Arrays.stream(filesInDir).forEach(file -> {
                getInstance().getLogger().warning(file.getName() + "|||" + file.getAbsolutePath());

                file.renameTo(new File(file.getAbsolutePath() + ".old"));
            });

        }
    }
}
