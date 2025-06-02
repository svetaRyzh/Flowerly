package com.example.flowerly;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonParser {
    public static List<Bouquet> parseBouquetsFromJson(Context context) {
        List<Bouquet> bouquets = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.bouquets);
            String json = new Scanner(is).useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("bouquets");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                bouquets.add(new Bouquet(
                        item.getString("name"),
                        item.getInt("price"),
                        item.getString("imageUrl"),
                        item.getString("category")
                ));
            }
        } catch (Exception e) {
            Log.e("JsonParser", "Error parsing JSON", e);
        }
        return bouquets;
    }
}