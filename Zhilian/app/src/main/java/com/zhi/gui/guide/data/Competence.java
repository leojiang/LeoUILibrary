package com.zhi.gui.guide.data;

public class Competence {
    private int rank;
    private String nickname;
    private int competence;

    public Competence() {

    }

    public Competence(int rank, String nickname, int competence) {
        this.rank = rank;
        this.nickname = nickname;
        this.competence = competence;
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getCompetence() {
        return competence;
    }
    public void setCompetence(int competence) {
        this.competence = competence;
    }

}
