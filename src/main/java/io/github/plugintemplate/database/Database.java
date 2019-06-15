package io.github.plugintemplate.database;

import com.google.gson.JsonObject;

import java.util.List;

public interface Database {

    void setup(List<String> locations);
    void cleanup();

    List<JsonObject> getObjects(String location);
    List<JsonObject> getObjects(String location, String identifier, String id);
    JsonObject getObject(String location, String identifier, String id);
    void saveObject(String location, String identifier, String id, JsonObject object);
}
