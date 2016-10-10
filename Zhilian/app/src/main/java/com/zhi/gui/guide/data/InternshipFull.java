package com.zhi.gui.guide.data;

public class InternshipFull {
    private String companyName;
    private String jobTitle;
    private String location;
    private int baseCompetence;
    private String headImageUrl;
    private String salaryRange;
    private Boolean isApplied;

    public Boolean getIsApplied() {
        return isApplied;
    }

    public void setIsApplied(Boolean isApplied) {
        this.isApplied = isApplied;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public InternshipFull() {
    }

    public InternshipFull(String companyName, String jobTitle, String location,
                          int baseCompetence, String imageUrl, String salaryRange) {
        this(companyName, jobTitle, location, baseCompetence, imageUrl, salaryRange, false);
    }

    public InternshipFull(String companyName, String jobTitle, String location,
                          int baseCompetence, String imageUrl, String salaryRange, boolean isApplied) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.location = location;
        this.baseCompetence = baseCompetence;
        this.headImageUrl = imageUrl;
        this.salaryRange = salaryRange;
        this.isApplied = isApplied;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBaseCompetence() {
        return baseCompetence;
    }

    public void setBaseCompetence(int baseCompetence) {
        this.baseCompetence = baseCompetence;
    }

}
