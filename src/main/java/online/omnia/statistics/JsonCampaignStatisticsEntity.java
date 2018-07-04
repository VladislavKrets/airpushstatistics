package online.omnia.statistics;

import java.util.Date;

/**
 * Created by lollipop on 24.04.2018.
 */
public class JsonCampaignStatisticsEntity {
    private String campaignName;
    private int campaignId;
    private int dailyBudget;
    private double currentBid;
    private int impression;
    private int clicks;
    private double spent;
    private int conversion;
    private double ctr;
    private double avgCPC;
    private double conversionRate;
    private double cpa;

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(int dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public int getImpression() {
        return impression;
    }

    public void setImpression(int impression) {
        this.impression = impression;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public int getConversion() {
        return conversion;
    }

    public void setConversion(int conversion) {
        this.conversion = conversion;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public double getAvgCPC() {
        return avgCPC;
    }

    public void setAvgCPC(double avgCPC) {
        this.avgCPC = avgCPC;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public double getCpa() {
        return cpa;
    }

    public void setCpa(double cpa) {
        this.cpa = cpa;
    }

    @Override
    public String toString() {
        return "JsonCampaignStatisticsEntity{" +
                "campaignName='" + campaignName + '\'' +
                ", campaignId=" + campaignId +
                ", dailyBudget=" + dailyBudget +
                ", currentBid=" + currentBid +
                ", impression=" + impression +
                ", clicks=" + clicks +
                ", spent=" + spent +
                ", conversion=" + conversion +
                ", ctr=" + ctr +
                ", avgCPC=" + avgCPC +
                ", conversionRate=" + conversionRate +
                ", cpa=" + cpa +
                '}';
    }
}
