package io.github.plugintemplate.database.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import io.github.plugintemplate.database.Database;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class MongoDatabase implements Database {

    private MongoClient client;
    private com.mongodb.client.MongoDatabase db;
    private String dbName;
    private MongoCredential credential;
    private ServerAddress address;
    private List<MongoCollection<Document>> collections = new ArrayList<>();

    public MongoDatabase(String dbName, MongoCredential credential, ServerAddress address) {
        this.dbName = dbName;
        this.credential = credential;
        this.address = address;
    }

    @Override
    public void setup(List<String> locations) {
        client = new MongoClient(address, Collections.singletonList(credential));
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
    public JsonObject getObject(String location, String identifier, String id) {
        Document document = getCollectionByName(location).find(Filters.eq(identifier, id)).first();
        if (document == null) {
            return null;
        }
        return (JsonObject) new JsonParser().parse(document.toJson());
    }

    @Override
    public List<JsonObject> getObjects(String location, String identifier, String id) {
        List<JsonObject> objects = new ArrayList<>();
        for (Document document : getCollectionByName(location).find(Filters.eq(identifier, id))) {
            objects.add((JsonObject) new JsonParser().parse(document.toJson()));
        }
        return objects;
    }

    @Override
    public List<JsonObject> getObjects(String location) {
        List<JsonObject> objects = new ArrayList<>();
        for (Document document : getCollectionByName(location).find()) {
            objects.add((JsonObject) new JsonParser().parse(document.toJson()));
        }
        return objects;
    }

    @Override
    public void saveObject(String location, String identifier, String id, JsonObject object) {
        if (object.entrySet().isEmpty()) {
            getCollectionByName(location).deleteOne(new Document(identifier, id));
            return;
        }
        getCollectionByName(location).replaceOne(
                Filters.eq(identifier, id),
                Document.parse(object.toString()),
                new ReplaceOptions().upsert(true)
        );
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
