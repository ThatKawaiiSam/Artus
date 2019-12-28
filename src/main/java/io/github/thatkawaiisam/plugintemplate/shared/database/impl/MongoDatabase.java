package io.github.thatkawaiisam.plugintemplate.shared.database.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import io.github.thatkawaiisam.plugintemplate.shared.database.DBSortDirection;
import io.github.thatkawaiisam.plugintemplate.shared.database.Database;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Getter @Setter
public class MongoDatabase implements Database {

    private MongoClient client;
    private com.mongodb.client.MongoDatabase db;
    private String dbName;
    private MongoCredential credential;
    private ServerAddress address;
    private List<MongoCollection<Document>> collections = new ArrayList<>();

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public MongoDatabase(String dbName, MongoCredential credential, ServerAddress address) {
        this.dbName = dbName;
        this.credential = credential;
        this.address = address;
    }

    @Override
    public void setup(List<String> locations) {
        try {
            long time = System.currentTimeMillis();
            if (credential == null || credential.getUserName().isEmpty()) {
                client = new MongoClient(address);
            } else {
                client = new MongoClient(address, Collections.singletonList(credential));
            }
            long duration = System.currentTimeMillis() - time;
            Logger.getAnonymousLogger().info("[MONGO] Successfully connected to database. (" + duration + "ms)");
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("[MONGO] Unable to connect to database.");
        }
        db = client.getDatabase(dbName);
        for (String loc : locations) {
            collections.add(db.getCollection(loc));
        }
    }

    @Override
    public void cleanup() {
        client.close();
    }

    @Override
    public CompletableFuture<JsonObject> getObject(String location, String identifier, String id) {
        MongoCollection<Document> collection = getCollectionByName(location);
        if (collection == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
                Document document = collection.find(Filters.eq(identifier, id)).first();
                if (document == null) {
                    return null;
                }
                return (JsonObject) new JsonParser().parse(document.toJson());
        }, executorService);
    }

    @Override
    public CompletableFuture<List<JsonObject>> getObjects(String location, String identifier, String id) {
        MongoCollection<Document> collection = getCollectionByName(location);
        if (collection == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<JsonObject> objects = new ArrayList<>();
            for (Document document : collection.find(Filters.eq(identifier, id))) {
                objects.add((JsonObject) new JsonParser().parse(document.toJson()));
            }
            return objects;
        }, executorService);
    }

    @Override
    public CompletableFuture<List<JsonObject>> getObjects(String location) {
        MongoCollection<Document> collection = getCollectionByName(location);
        if (collection == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<JsonObject> objects = new ArrayList<>();
            for (Document document : collection.find()) {
                objects.add((JsonObject) new JsonParser().parse(document.toJson()));
            }
            return objects;
        });
    }

    @Override
    public CompletableFuture<Boolean> saveObject(String location, String identifier, String id, JsonObject object) {
        MongoCollection<Document> collection = getCollectionByName(location);
        if (collection == null) {
            return CompletableFuture.completedFuture(false);
        }
        if (object == null || object.entrySet().isEmpty()) {
            collection.deleteOne(new Document(identifier, id));
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.supplyAsync(() -> {
            collection.replaceOne(
                    Filters.eq(identifier, id),
                    Document.parse(object.toString()),
                    new ReplaceOptions().upsert(true)
            );
            return true;
        });
    }

    @Override
    public CompletableFuture<List<JsonObject>> getSortedObjects(String location, DBSortDirection direction, String field, Integer limit) {
        MongoCollection<Document> collection = getCollectionByName(location);
        if (collection == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<JsonObject> objects = new ArrayList<>();
            for (Document document : collection.find().sort(new BasicDBObject(field, direction.getValue())).limit(limit != null ? limit : (int) collection.count())) {
                objects.add((JsonObject) new JsonParser().parse(document.toJson()));
            }
            return objects;
        });
    }

    private MongoCollection<Document> getCollectionByName(String name) {
        for (MongoCollection<Document> collection : collections) {
            if (collection.getNamespace().getCollectionName().equalsIgnoreCase(name)) {
                return collection;
            }
        }
        return null;
    }
}
