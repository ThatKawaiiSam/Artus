package io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.impl;

import com.google.gson.JsonObject;
import io.github.thatkawaiisam.jedis.helper.IJedisSubscription;
import io.github.thatkawaiisam.jedis.helper.JedisCredentials;
import io.github.thatkawaiisam.jedis.helper.JedisHelper;
import io.github.thatkawaiisam.jedis.helper.JedisSubscriber;
import io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.IMessageQueue;
import io.github.thatkawaiisam.plugintemplate.shared.messsagequeue.ISubscription;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class RedisMessageQueueImpl implements IMessageQueue {

    private JedisHelper helper;
    private JedisCredentials credentials;
    private Map<ISubscription, JedisSubscriber> subscriberCache = new ConcurrentHashMap<>();

    public RedisMessageQueueImpl(JedisCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void setup() {
        helper = new JedisHelper(credentials);
    }

    @Override
    public void cleanup() {
        for (ISubscription subscription : getSubscriberCache().keySet()) {
            unregisterSubscription(subscription);
        }
        subscriberCache.clear();
        helper.close();
        helper = null;
    }

    @Override
    public void write(Enum payloadID, JsonObject data, String channel) {
        helper.write(payloadID, data, channel);
    }

    @Override
    public void registerSubscription(ISubscription subscription) {
        JedisSubscriber subscriber = new JedisSubscriber(subscription.channels().toString(), helper, new IJedisSubscription() {
            @Override
            public void handleMessage(String payload, JsonObject data) {
                subscription.handleMessage(payload, data);
            }

            @Override
            public String[] subscriptionChannels() {
                return subscription.channels();
            }
        });
        helper.getSubscribers().add(subscriber);
        subscriberCache.put(subscription, subscriber);
    }

    @Override
    public void unregisterSubscription(ISubscription subscription) {
        JedisSubscriber subscriber = subscriberCache.get(subscription);
        if (subscriber == null || !subscriber.isSubscribed()) {
            return;
        }
        helper.getSubscribers().remove(subscriber);
        subscriberCache.remove(subscription);
    }
}
