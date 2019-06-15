package io.github.plugintemplate.messsagequeue.impl;

import com.google.gson.JsonObject;
import io.github.plugintemplate.messsagequeue.IMessageQueue;
import io.github.plugintemplate.messsagequeue.ISubscription;

import java.util.HashSet;
import java.util.Set;

public class LocalMessageQueueImpl implements IMessageQueue {

    private Set<ISubscription> subscriptions;

    @Override
    public void setup() {
        subscriptions = new HashSet<>();
    }

    @Override
    public void cleanup() {
        subscriptions.clear();
    }

    @Override
    public void write(Enum payloadID, JsonObject data, String channel) {
        for (ISubscription subscription : subscriptions) {
            for (String loopChannels : subscription.channels()) {
                if (loopChannels.equalsIgnoreCase(channel)) {
                    subscription.handleMessage(payloadID.name(), data);
                }
            }
        }
    }

    @Override
    public void registerSubscription(ISubscription subscription) {
        subscriptions.add(subscription);
    }

    @Override
    public void unregisterSubscription(ISubscription subscription) {
        subscriptions.remove(subscription);
    }

}
