package io.github.thatkawaiisam.plugintemplate.shared.database;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void setup(List<String> locations);
    void cleanup();

    CompletableFuture<List<JsonObject>> getObjects(String location);
    CompletableFuture<List<JsonObject>> getObjects(String location, String identifier, String id);
    CompletableFuture<List<JsonObject>> getSortedObjects(String location, DBSortDirection direction, String field, Integer limit);
    CompletableFuture<JsonObject> getObject(String location, String identifier, String id);
    CompletableFuture<Boolean> saveObject(String location, String identifier, String id, JsonObject object);

}
