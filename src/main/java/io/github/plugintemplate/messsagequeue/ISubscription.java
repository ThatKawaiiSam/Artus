package io.github.plugintemplate.messsagequeue;

import com.google.gson.JsonObject;

public interface ISubscription {

    void handleMessage(String payload, JsonObject data);

    String[] channels();
}
