package io.github.plugintemplate.messsagequeue.impl;

import com.google.gson.JsonObject;
import io.github.plugintemplate.messsagequeue.IMessageQueue;
import io.github.plugintemplate.messsagequeue.ISubscription;
import io.github.thatkawaiisam.jedis.helper.IJedisSubscription;
import io.github.thatkawaiisam.jedis.helper.JedisCredentials;
import io.github.thatkawaiisam.jedis.helper.JedisHelper;
import io.github.thatkawaiisam.jedis.helper.JedisSubscriber;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RedisMessageQueueImpl implements IMessageQueue {

    private JedisHelper helper;
    private JedisCredentials credentials;

    public RedisMessageQueueImpl(JedisCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void setup() {
        helper = new JedisHelper(credentials);
    }

    @Override
    public void cleanup() {
        helper.close();
        helper = null;
    }

    @Override
    public void write(Enum payloadID, JsonObject data, String channel) {
        helper.write(payloadID, data, channel);
    }

    @Override
    public void registerSubscription(ISubscription subscription) {
        helper.getSubscribers().add(new JedisSubscriber(subscription.channels().toString(), helper, new IJedisSubscription() {
            @Override
            public void handleMessage(String payload, JsonObject data) {
                subscription.handleMessage(payload, data);
            }

            @Override
            public String[] subscriptionChannels() {
                return subscription.channels();
            }
        }));
    }

    @Override
    public void unregisterSubscription(ISubscription subscription) {
    }
}
