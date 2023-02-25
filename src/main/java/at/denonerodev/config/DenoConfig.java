package at.denonerodev.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DenoConfig {

    String path;
    File file;
    Gson gson;
    JsonObject cache;

    public DenoConfig(String path) {

        this.path = path;
        this.file = new File(path);
        this.gson = new GsonBuilder().create();

        /* Create Config File */
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created Config in: " + path);

        this.cache = load();

    }

    JsonObject load() {

        String json = "{}";

        try {
            json = new String(Files.readAllBytes(Paths.get(this.path)));
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        if(!json.startsWith("{") || !json.endsWith("}"))
            json = "{}";
        return this.gson.fromJson(json, JsonObject.class);

    }
    public void prettify() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    public void prettify(boolean bool) {
        this.gson = (bool) ? new GsonBuilder().setPrettyPrinting().create() : new GsonBuilder().create();
    }
    public void set(String key, Object value) {

        List<String> keys = Arrays.asList(key.split("\\."));
        JsonObject currentObject = this.cache;

        for (int i = 0; i < keys.size() - 1; i++) {

            String currentKey = keys.get(i);
            if (!currentObject.has(currentKey))
                currentObject.add(currentKey, new JsonObject());
            currentObject = currentObject.getAsJsonObject(currentKey);

        }

        String lastKey = keys.get(keys.size() - 1);

        currentObject.add(lastKey, gson.toJsonTree(value));

    }
    public Object get(String key) {

        List<String> keys = Arrays.asList(key.split("\\."));
        JsonObject currentObject = this.cache;

        for (String currentKey : keys) {

            JsonElement element = currentObject.get(currentKey);

            if (element == null)
                return null;
            if (element.isJsonObject())
                currentObject = element.getAsJsonObject();
            else
                return this.gson.fromJson(element, Object.class);

        }
        return this.gson.fromJson(currentObject, Object.class);

    }
    public String getString(String key) {
        return (String) this.get(key);
    }
    public int getInt(String key) {
        return (int) (double) this.get(key);
    }
    public double getDouble(String key) {
        return Double.parseDouble(this.get(key).toString());
    }
    public float getFloat(String key) {
        return Float.parseFloat(this.get(key).toString());
    }
    public boolean getBoolean(String key) {
        return (boolean) this.get(key);
    }
    public List getList(String key) {
        return (List) this.get(key);
    }
    public Map getMap(String key) {
        return (Map) this.get(key);
    }
    public void remove(String key) {

        List<String> keys = Arrays.asList(key.split("\\."));
        JsonObject currentObject = this.cache;

        for (int i = 0; i < keys.size() - 1; i++) {

            String currentKey = keys.get(i);

            if (!currentObject.has(currentKey))
                return;

            currentObject = currentObject.getAsJsonObject(currentKey);

        }

        String lastKey = keys.get(keys.size() - 1);

        currentObject.remove(lastKey);

    }
    public void clear() {
        this.cache = new JsonObject();
    }
    public void save() {

        try (FileWriter writer = new FileWriter(this.file)) {
            this.gson.toJson(this.cache, writer);
        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

}