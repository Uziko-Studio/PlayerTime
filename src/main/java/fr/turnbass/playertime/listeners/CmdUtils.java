package fr.turnbass.playertime.listeners;

import fr.turnbass.playertime.PlayerTime;
import fr.turnbass.playertime.db.Database;
import fr.turnbass.playertime.model.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class CmdUtils implements Listener {
    private final Database database;
    private final PlayerTime plugin;

    public CmdUtils(Database database, PlayerTime plugin) {
        this.database = database;
        this.plugin = plugin;
    }
    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByUUID(player.getUniqueId().toString());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getUniqueId().toString(), 1);
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }
    public PlayerStats getPlayerTimeFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerTimeByUUID(player.getUniqueId().toString());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getUniqueId().toString(), 1);
            database.createPlayerTime(playerStats);
        }

        return playerStats;
    }

    public void setGPlayedTime(Player player, Long time) throws SQLException {
        try{
            PlayerStats playerStats = getPlayerTimeFromDatabase(player);
            playerStats.setPlayerTime(time);
            database.updatePlayerTime(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addGPlayedTime(Player player, Long time) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerTimeFromDatabase(player);
            long gt = playerStats.getPlayerTime();
            long total = gt + time;
            playerStats.setPlayerTime(total);
            database.updatePlayerTime(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PlayerStats getGPlayedTime(Player player) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerTimeFromDatabase(player);
            playerStats.getPlayerTime();
            return playerStats;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void resetGPlayedTime(Player player) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerTimeFromDatabase(player);
            playerStats.setPlayerTime(0);
            database.updatePlayerTime(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setSPlayedTime(Player player, Long time) throws SQLException {
        try{
            PlayerStats playerStats = getPlayerStatsFromDatabase(player);
            playerStats.setPlayerTime(time);
            database.updatePlayerStats(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addGSlayedTime(Player player, Long time) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerStatsFromDatabase(player);
            long gt = playerStats.getPlayerTime();
            long total = gt + time;
            playerStats.setPlayerTime(total);
            database.updatePlayerStats(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PlayerStats getSPlayedTime(Player player) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerStatsFromDatabase(player);
            playerStats.getPlayerTime();
            return playerStats;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void resetSPlayedTime(Player player) throws SQLException {
        try {
            PlayerStats playerStats = getPlayerStatsFromDatabase(player);
            playerStats.setPlayerTime(0);
            database.updatePlayerStats(playerStats);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
