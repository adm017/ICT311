package au.edu.usc.adm017.myactivities.activity;

/**
 * Created by Alex on 28/10/2017.
 */

public enum ActivityType {

    WORK("Work"),
    LEISURE("Leisure"),
    STUDY("Study"),
    SPORT("Sport");

    private String humanReadable;

    ActivityType(String humanReadable) {
        this.humanReadable = humanReadable;
    }

    public String getHumanReadable() {
        return humanReadable;
    }

    public static ActivityType fromHumanReadable(String value) {
        for (ActivityType type : values()) {
            if (type.getHumanReadable().equalsIgnoreCase(value)) {
                return type;
            }
        }

        return null;
    }
}
