package io.github.plugintemplate.messsagequeue;

import com.google.gson.JsonObject;

public interface IMessageQueue {

    void setup();
    void cleanup();
    void write(Enum payloadID, JsonObject data, String channel);
    void registerSubscription(ISubscription subscription);
    void unregisterSubscription(ISubscription subscription);

}
