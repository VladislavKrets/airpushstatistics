package online.omnia.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 24.04.2018.
 */
public class Main {
    public static int days;
    public static long deltaTime = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        if (!args[0].matches("\\d+")) return;
        if (Integer.parseInt(args[0]) == 0) {
            deltaTime = 0;
        }
        days = Integer.parseInt(args[0]);

        List<AccountsEntity> accountsEntities = MySQLDaoImpl.getInstance().getAccountsEntities("airpush");
        String token = "a50a507948642c2fae008394";
        String answer;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new JsonCampaignStatisticsDeserializer());
        builder.registerTypeAdapter(Integer.TYPE, new CreativeIdDeserializer());
        builder.registerTypeAdapter(String.class, new UrlJsonDeserializer());
        Gson gson = builder.create();
        List<JsonCampaignStatisticsEntity> jsonCampaignStatisticsEntities;
        SourceStatisticsEntity sourceStatisticsEntity;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String url;
        Integer id;
        int afid;
        Map<String, String> parameters;
        SourceStatisticsEntity entity;
        for (AccountsEntity accountsEntity : accountsEntities) {
            token = accountsEntity.getApiKey();
            for (int i = 0; i <= days; i++) {
                answer = HttpMethodUtils.getMethod("http://openapi.airpush.com/getAdvertiserReports?apikey="
                        + token +
                        "&startDate="
                        + simpleDateFormat.format(new Date(System.currentTimeMillis() - deltaTime - i * 24L * 60 * 60 * 1000)) +
                        "&endDate="
                        + simpleDateFormat.format(new Date(System.currentTimeMillis() - deltaTime - i * 24L * 60 * 60 * 1000)));
                jsonCampaignStatisticsEntities = gson.fromJson(answer, List.class);
                for (JsonCampaignStatisticsEntity jsonCampaignStatisticsEntity : jsonCampaignStatisticsEntities) {
                    sourceStatisticsEntity = new SourceStatisticsEntity();
                    sourceStatisticsEntity.setDate(new java.sql.Date(System.currentTimeMillis() - deltaTime - i * 24L * 60 * 60 * 1000));
                    sourceStatisticsEntity.setClicks(jsonCampaignStatisticsEntity.getClicks());
                    sourceStatisticsEntity.setCampaignId(String.valueOf(jsonCampaignStatisticsEntity.getCampaignId()));
                    sourceStatisticsEntity.setCampaignName(jsonCampaignStatisticsEntity.getCampaignName());
                    sourceStatisticsEntity.setBuyerId(accountsEntity.getBuyerId());
                    sourceStatisticsEntity.setAccount_id(accountsEntity.getAccountId());
                    sourceStatisticsEntity.setReceiver("API");
                    sourceStatisticsEntity.setSpent(jsonCampaignStatisticsEntity.getSpent());
                    sourceStatisticsEntity.setCpc(jsonCampaignStatisticsEntity.getAvgCPC());
                    sourceStatisticsEntity.setConversions(jsonCampaignStatisticsEntity.getConversion());
                    answer = HttpMethodUtils.getMethod("http://openapi.airpush.com/getCreativesByCampaignId?apikey=a50a507948642c2fae008394&creatives=%7B%20%22campaignId%22:%20%22"
                            + sourceStatisticsEntity.getCampaignId() + "%22%20%7D");
                    id = gson.fromJson(answer, Integer.TYPE);
                    answer = HttpMethodUtils.getMethod("http://openapi.airpush.com/getCreativeDetailsById?apikey=a50a507948642c2fae008394&creatives=%7B%20%22creativeId%22:%20%22"
                            + id + "%22%20%7D");
                    url = gson.fromJson(answer, String.class);
                    parameters = Utils.getUrlParameters(url);
                    if (parameters.containsKey("cab")) {
                        if (parameters.get("cab").matches("\\d+")
                                && MySQLDaoImpl.getInstance().getAffiliateByAfid(Integer.parseInt(parameters.get("cab"))) != null) {
                            afid = Integer.parseInt(parameters.get("cab"));
                        } else {
                            afid = 0;
                        }
                    } else afid = 2;
                    sourceStatisticsEntity.setAfid(afid);
                    if (Main.days != 0) {
                        entity = MySQLDaoImpl.getInstance().getSourceStatistics(sourceStatisticsEntity.getAccount_id(),
                                sourceStatisticsEntity.getCampaignName(), sourceStatisticsEntity.getDate(), sourceStatisticsEntity.getAdsetId());
                        if (entity != null) {
                            sourceStatisticsEntity.setId(entity.getId());
                            MySQLDaoImpl.getInstance().updateSourceStatistics(sourceStatisticsEntity);
                            entity = null;
                        } else MySQLDaoImpl.getInstance().addSourceStatistics(sourceStatisticsEntity);
                    } else {
                        if (MySQLDaoImpl.getInstance().isDateInTodayAdsets(sourceStatisticsEntity.getDate(), sourceStatisticsEntity.getAccount_id(),
                                sourceStatisticsEntity.getCampaignId())) {
                            MySQLDaoImpl.getInstance().updateTodayAdset(Utils.getAdset(sourceStatisticsEntity));
                        } else MySQLDaoImpl.getInstance().addTodayAdset(Utils.getAdset(sourceStatisticsEntity));

                    }
                }

            }
        }
        MySQLDaoImpl.getSessionFactory().close();
    }
}
