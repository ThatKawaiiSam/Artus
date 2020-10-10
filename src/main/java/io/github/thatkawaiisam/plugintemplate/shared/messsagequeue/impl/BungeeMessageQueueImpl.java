package io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.impl;

import com.google.gson.JsonObject;
import io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.IMessageQueue;
import io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.ISubscription;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeMessageQueueImpl implements IMessageQueue {

    private JavaPlugin javaPlugin;

    public BungeeMessageQueueImpl(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void setup() {
        javaPlugin.getServer().getMessenger().registerOutgoingPluginChannel(javaPlugin, "BungeeCord");
        javaPlugin.getServer().getMessenger().registerIncomingPluginChannel(javaPlugin, "BungeeCord", null);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void write(Enum payloadID, JsonObject data, String channel) {

    }

    @Override
    public void registerSubscription(ISubscription subscription) {

    }

    @Override
    public void unregisterSubscription(ISubscription subscription) {

    }
}
