package com.ttkegmil.app1.apis;

import com.ttkegmil.app1.fs.JsonStore;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class JsonFileWebApi {
    @GetMapping("/read/{store}")
    public String readStore(@PathVariable String store) throws IOException {
        Map<String, Object> data = JsonStore.getStore(store).enumerate();
        return data.toString();
    }

    @PostMapping("/write/{store}")
    public String writeStore(@PathVariable String store, @RequestBody Map<String, Object> body)
    {
        JsonStore storeObj = JsonStore.getStore(store);

        try{
            storeObj.writeBatch(body);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Write failed";
        }

        return "Done";
    }
}
