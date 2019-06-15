package io.github.plugintemplate.messsagequeue.impl;

import com.google.gson.JsonObject;
import io.github.plugintemplate.messsagequeue.IMessageQueue;
import io.github.plugintemplate.messsagequeue.ISubscription;

public class BungeeMessageQueueImpl implements IMessageQueue {

    public BungeeMessageQueueImpl() {

    }

    @Override
    public void setup() {
//        ModSuitePlugin.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(ModSuitePlugin.getInstance(), "BungeeCord");
//        ModSuitePlugin.getInstance().getServer().getMessenger().registerIncomingPluginChannel(ModSuitePlugin.getInstance(), "BungeeCord", this);
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
