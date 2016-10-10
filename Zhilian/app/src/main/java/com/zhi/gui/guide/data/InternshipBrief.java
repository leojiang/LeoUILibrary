package com.zhi.gui.guide.data;

public class InternshipBrief {
    private String companyName;
    private String jobTitle;
    private String location;
    private int baseCompetence;

    public InternshipBrief() {
    }

    public InternshipBrief(String companyName, String jobTitle, String location,
            int baseCompetence) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.location = location;
        this.baseCompetence = baseCompetence;
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
