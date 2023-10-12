package fr.turnbass.playertime.model;

import java.util.Date;

public class PlayerStats {

    private String playerUUID;

    //random stats on each player
    private int playerTime;

    //last login and logout times
    private Date lastLogin;
    private Date lastLogout;

    public PlayerStats(String playerUUID,int playerTime) {
        this.playerUUID = playerUUID;
        this.playerTime = playerTime;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setPlayerTime(int playerTime) {this.playerTime = playerTime;}

    public int getPlayerTime() {return playerTime;}


}
