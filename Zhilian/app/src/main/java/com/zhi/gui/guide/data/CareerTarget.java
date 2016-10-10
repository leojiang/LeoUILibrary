package com.zhi.gui.guide.data;

public class CareerTarget {
    public final static int LEVEL_PRIOR = 1;
    public final static int LEVEL_SECONDARY = 2;
    public final static int LEVEL_NONE = 0;

    private int index;
    private String industry;
    private String percentage;
    private int careerLevel = LEVEL_NONE;

    public int getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(int careerLevel) {
        this.careerLevel = careerLevel;
    }

    public CareerTarget(int index, String industry, String percentage) {
        this.index = index;
        this.industry = industry;
        this.percentage = percentage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}
