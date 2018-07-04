package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 24.04.2018.
 */
public class JsonCampaignStatisticsDeserializer implements JsonDeserializer<List<JsonCampaignStatisticsEntity>>{
    @Override
    public List<JsonCampaignStatisticsEntity>
    deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<JsonCampaignStatisticsEntity> jsonCampaignStatisticsEntities = new ArrayList<>();
        JsonCampaignStatisticsEntity jsonCampaignStatisticsEntity;
        try {
            JsonArray array = jsonElement.getAsJsonObject().get("advertiser_data").getAsJsonArray();
            for (JsonElement element : array) {
                jsonCampaignStatisticsEntity = new JsonCampaignStatisticsEntity();
                jsonCampaignStatisticsEntity.setCampaignName(element.getAsJsonObject().get("campaignname").getAsString());
                jsonCampaignStatisticsEntity.setCampaignId(element.getAsJsonObject().get("campaignid").getAsInt());
                jsonCampaignStatisticsEntity.setClicks(element.getAsJsonObject().get("clicks").getAsInt());
                jsonCampaignStatisticsEntity.setCtr(element.getAsJsonObject().get("ctr").getAsDouble());
                jsonCampaignStatisticsEntity.setAvgCPC(element.getAsJsonObject().get("avgCPC").getAsDouble());
                jsonCampaignStatisticsEntity.setConversion(element.getAsJsonObject().get("conversion").getAsInt());
                jsonCampaignStatisticsEntity.setConversionRate(element.getAsJsonObject().get("conversionrate").getAsDouble());
                jsonCampaignStatisticsEntity.setCpa(element.getAsJsonObject().get("CPA").getAsDouble());
                jsonCampaignStatisticsEntity.setCurrentBid(element.getAsJsonObject().get("currentBid").getAsDouble());
                jsonCampaignStatisticsEntity.setDailyBudget(element.getAsJsonObject().get("dailybudget").getAsInt());
                jsonCampaignStatisticsEntity.setImpression(element.getAsJsonObject().get("impression").getAsInt());
                jsonCampaignStatisticsEntity.setSpent(element.getAsJsonObject().get("Spent").getAsDouble());
                jsonCampaignStatisticsEntities.add(jsonCampaignStatisticsEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return jsonCampaignStatisticsEntities;
        }

        return jsonCampaignStatisticsEntities;
    }
}
