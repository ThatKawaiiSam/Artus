//package io.github.thatkawaiisam.plugintemplate.shared.database.impl;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//import io.github.thatkawaiisam.configs.BukkitConfigHelper;
//import io.github.thatkawaiisam.plugintemplate.shared.database.DBSortDirection;
//import io.github.thatkawaiisam.plugintemplate.shared.database.Database;
//import lombok.Getter;
//import org.bukkit.configuration.Configuration;
//import org.bukkit.configuration.MemorySection;
//import org.bukkit.plugin.java.JavaPlugin;
//
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//
//public class YAMLFileDatabase implements Database {
//
//    @Getter private List<BukkitConfigHelper> files = new ArrayList<>();
//
//    private JavaPlugin plugin;
//
//    public YAMLFileDatabase(JavaPlugin plugin) {
//        this.plugin = plugin;
//    }
//
//    @Override
//    public void setup(List<String> locations) {
//        files.clear();
//        for (String name : locations) {
//            files.add(new BukkitConfigHelper(plugin, name, plugin.getDataFolder().getAbsolutePath()));
//        }
//    }
//
//    @Override
//    public void cleanup() {
//        files.clear();
//    }
//
////    @Override
////    public JsonObject getObject(String location, String identifier, String id) {
////        if (getFileByName(location) == null
////                || getFileByName(location).getConfiguration() == null) {
////            return new JsonObject();
////        }
////
////        Configuration configurationFile = getFileByName(location).getConfiguration();
////
////        if (configurationFile.getConfigurationSection("Objects") == null) {
////            return new JsonObject();
////        }
////
////        for (String ymlID : configurationFile.getConfigurationSection("Objects").getKeys(false)) {
////            if (configurationFile.isConfigurationSection("Objects." + ymlID)) {
////                if (configurationFile.getConfigurationSection("Objects." + ymlID).contains(identifier)) {
////                    JsonObject jsonObject = new JsonObject();
////
////                    Map<String, Object> obj = configurationFile.getConfigurationSection("Objects." + ymlID).getValues(true);
////                    for (String l : obj.keySet()) {
////                        if (obj.get(l) instanceof MemorySection) {
////                            continue;
////                        }
////                        if (obj.get(l) instanceof ArrayList) {
////                            JsonArray array = new JsonArray();
////                            ArrayList ele = (ArrayList) obj.get(l);
////                            for (Object ele1 : ele) {
////                                array.add(new JsonPrimitive(ele1.toString()));
////                            }
////                            jsonObject.add(l, array);
////                        } else {
////                            jsonObject.addProperty(l, obj.get(l).toString());
////                        }
////                    }
////                    return jsonObject;
////                }
////            }
////        }
////        return new JsonObject();
////    }
//
//
//    @Override
//    public CompletableFuture<JsonObject> getObject(String location, String identifier, String id) {
//        return null;
//    }
//
//    @Override
//    public List<JsonObject> getObjects(String location, String identifier, String id) {
//        List<JsonObject> objects = new ArrayList<>();
//
//        if (getFileByName(location) == null
//                || getFileByName(location).getConfiguration() == null) {
//            return objects;
//        }
//
//        Configuration configurationFile = getFileByName(location).getConfiguration();
//
//        if (configurationFile.getConfigurationSection("Objects") == null) {
//            return objects;
//        }
//
//        for (String ymlID : configurationFile.getConfigurationSection("Objects").getKeys(false)) {
//            if (configurationFile.isConfigurationSection("Objects." + ymlID)) {
//                if (configurationFile.getConfigurationSection("Objects." + ymlID).contains(identifier)) {
//                    JsonObject jsonObject = new JsonObject();
//
//                    Map<String, Object> obj = configurationFile.getConfigurationSection("Objects." + ymlID).getValues(true);
//                    for (String l : obj.keySet()) {
//                        if (obj.get(l) instanceof MemorySection) {
//                            continue;
//                        }
//                        if (obj.get(l) instanceof ArrayList) {
//                            JsonArray array = new JsonArray();
//                            ArrayList ele = (ArrayList) obj.get(l);
//                            for (Object ele1 : ele) {
//                                array.add(new JsonPrimitive(ele1.toString()));
//                            }
//                            jsonObject.add(l, array);
//                        } else {
//                            jsonObject.addProperty(l, obj.get(l).toString());
//                        }
//                    }
//                    objects.add(jsonObject);
//                }
//            }
//        }
//
//        return objects;
//    }
//
//    @Override
//    public List<JsonObject> getObjects(String location) {
//        List<JsonObject> objects = new ArrayList<>();
//
//        if (getFileByName(location) == null
//                || getFileByName(location).getConfiguration() == null) {
//            return objects;
//        }
//
//        Configuration configurationFile = getFileByName(location).getConfiguration();
//
//        if (configurationFile.getConfigurationSection("Objects") == null) {
//            return objects;
//        }
//
//        for (String ymlID : configurationFile.getConfigurationSection("Objects").getKeys(false)) {
//            JsonObject jsonObject = new JsonObject();
//
//            Map<String, Object> obj = configurationFile.getConfigurationSection("Objects." + ymlID).getValues(true);
//            for (String l : obj.keySet()) {
//                if (obj.get(l) instanceof MemorySection) {
//                    continue;
//                }
//                if (obj.get(l) instanceof ArrayList) {
//                    JsonArray array = new JsonArray();
//                    ArrayList ele = (ArrayList) obj.get(l);
//                    for (Object ele1 : ele) {
//                        array.add(new JsonPrimitive(ele1.toString()));
//                    }
//                    jsonObject.add(l, array);
//                } else {
//                    jsonObject.addProperty(l, obj.get(l).toString());
//                }
//            }
//            objects.add(jsonObject);
//        }
//
//        return objects;
//    }
//
//    @Override
//    public CompletableFuture<Boolean> saveObject(String location, String identifier, String id, JsonObject object) {
//        return null;
//    }
//
//    //    @Override
////    public void saveObject(String location, String identifier, String id, JsonObject object) {
////        Configuration configurationFile = getFileByName(location).getConfiguration();
////        Gson gson = new Gson();
////        String json = object.toString();
////        Map<String, Object> map = new HashMap<>();
////        map = (Map<String,Object>) gson.fromJson(json, map.getClass());
////        if (!object.entrySet().isEmpty()) {
////            configurationFile.set("Objects." + id, map);
////        } else {
////            configurationFile.set("Objects." + id, null);
////        }
////        getFileByName(location).save();
////    }
//
////    @Override
////    public List<JsonObject> getSortedObjects(String location, DBSortDirection direction, String field, Integer limit) {
////        return null;
////    }
//
//
//    @Override
//    public CompletableFuture<List<JsonObject>> getSortedObjects(String location, DBSortDirection direction, String field, Integer limit) {
//        return null;
//    }
//
//    private BukkitConfigHelper getFileByName(String name) {
//        for (BukkitConfigHelper file : files) {
//            if (file.getName().equalsIgnoreCase(name)) {
//                return file;
//            }
//        }
//        return null;
//    }
//}
