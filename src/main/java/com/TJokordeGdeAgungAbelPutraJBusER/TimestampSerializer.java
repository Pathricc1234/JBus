package com.TJokordeGdeAgungAbelPutraJBusER;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimestampSerializer untuk melakukan formating pada Timestamp
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class TimestampSerializer implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(new Date(timestamp.getTime())));
    }

    @Override
    public Timestamp deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            Date parsedDate = dateFormat.parse(jsonElement.getAsString());
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}

