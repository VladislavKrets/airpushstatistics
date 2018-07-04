package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by lollipop on 24.04.2018.
 */
public class CreativeIdDeserializer implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray array = jsonElement.getAsJsonArray();
        return array.get(0).getAsJsonObject().get("creativeid").getAsInt();
    }
}
