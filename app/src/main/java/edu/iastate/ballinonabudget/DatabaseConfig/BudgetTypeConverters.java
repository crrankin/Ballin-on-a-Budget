package edu.iastate.ballinonabudget.DatabaseConfig;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import edu.iastate.ballinonabudget.Objects.Items;

public class BudgetTypeConverters {

    /**
     * Converts a string to an item list
     * @param data string
     * @return list
     */
    @TypeConverter
    public static List<Items> stringToItemList(String data) {
        Gson gson = new Gson();
        if(data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Items>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    /**
     * Takes an item list and returns it as a string
     * @param itemList list of items
     * @return string of items
     */
    @TypeConverter
    public static String ItemListToString(List<Items> itemList) {
        Gson gson = new Gson();
        return gson.toJson(itemList);
    }
}
