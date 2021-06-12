package model;

public class Sportsman {
    private int id;
    private String fullName;
    private int sportId;
    private String phoneNumber;
    private int sportTitleId;
    private String coachName;

    public Sportsman(int id, String fullName, int sportId, String phoneNumber, int sportTitleId, String coachName) {
        this.id = id;
        this.fullName = fullName;
        this.sportId = sportId;
        this.phoneNumber = phoneNumber;
        this.sportTitleId = sportTitleId;
        this.coachName = coachName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSportTitleId() {
        return sportTitleId;
    }

    public void setSportTitleId(int sportTitleId) {
        this.sportTitleId = sportTitleId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
}
