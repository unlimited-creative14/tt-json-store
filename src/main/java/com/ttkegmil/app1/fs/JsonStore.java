package com.ttkegmil.app1.fs;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonStore {
    File handle;

    private static Map<String, JsonStore> storage = new HashMap<>();
    private static final String PREFIX_PATH = "store";
    public static JsonStore getStore(String storeName)
    {
        if(!storage.containsKey(storeName))
        {
            storage.put(storeName, new JsonStore(PREFIX_PATH + "_" + storeName + ".json"));
        }

        return storage.get(storeName);
    }

    private JsonStore(String filename)
    {
        handle = new File(filename);
        if(!handle.exists())
        {
            try{
                handle.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Object> enumerate() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> data;

        try {
            data = mapper.readValue(handle, HashMap.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            data = new HashMap<>();
        }


        return data;
    }

    public Object read(String key) throws IOException
    {
        Map<String, Object> data = enumerate();
        if(!data.containsKey(key))
        {
            return null;
        }

        return data.get(key);
    }
    public void write(String key, Object value) throws IOException {
        Map<String, Object> data = enumerate();

        data.put(key, value);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(handle, data);
    }

    public void writeBatch(Map<String, Object> entries) throws IOException
    {
        Map<String, Object> data = enumerate();

        data.putAll(entries);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(handle, data);
    }
}
