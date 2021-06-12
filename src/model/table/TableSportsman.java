package model.table;

import model.Sportsman;

public class TableSportsman extends Sportsman {
    private String sportName;
    private String titleName;

    public TableSportsman(int id, String fullName, int sportId, String phoneNumber, int sportTitleId, String coachName) {
        super(id, fullName, sportId, phoneNumber, sportTitleId, coachName);
    }

    public TableSportsman(Sportsman sportsman) {
        this(sportsman.getId(), sportsman.getFullName(), sportsman.getSportId(), sportsman.getPhoneNumber(), sportsman.getSportTitleId(), sportsman.getCoachName());
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public String getPhoneNumber() {
        String phoneNumber = super.getPhoneNumber();
        if (phoneNumber.isEmpty()) {
            return "-";
        }
        int d = 1;
        if (phoneNumber.startsWith("8")) {
            d = 0;
        }

        return phoneNumber.substring(0, 1 + d) +
                "(" + phoneNumber.substring(1 + d, 4 + d) + ")" +
                phoneNumber.substring(4 + d, 7 + d) + "-" + phoneNumber.substring(7 + d, 9 + d) +
                "-" + phoneNumber.substring(9 + d, 11 + d);
    }

    public String getSimplePhoneNumber() {
        return super.getPhoneNumber();
    }
}