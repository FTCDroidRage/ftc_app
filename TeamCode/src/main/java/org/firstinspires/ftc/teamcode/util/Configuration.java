package org.firstinspires.ftc.teamcode.util;

public class Configuration {

    public enum Alliance {
        BLUE,
        RED,
        NULL
    }

    public enum Team {
        TEAM_12285,
        TEAM_10863,
        NULL
    }

    public enum Beacon {
        LEFT,
        RIGHT,
        NULL
    }

    private Alliance alliance;
    private Team team;
    private Beacon beacon1Side;
    private Beacon beacon2Side;

    public Configuration(Alliance alliance, Team team, Beacon beacon1Side, Beacon beacon2Side) {
        this.alliance = alliance;
        this.team = team;
        this.beacon1Side = beacon1Side;
        this.beacon2Side = beacon2Side;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public Team getTeam() {
        return team;
    }

    public Beacon getBeacon1Side() {
        return beacon1Side;
    }

    public Beacon getBeacon2Side() {
        return beacon2Side;
    }

}