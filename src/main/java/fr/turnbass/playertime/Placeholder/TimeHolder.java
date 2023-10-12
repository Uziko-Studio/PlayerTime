package fr.turnbass.playertime.Placeholder;

import fr.turnbass.playertime.Utils.TimeUtils;
import fr.turnbass.playertime.db.Database;
import fr.turnbass.playertime.model.PlayerStats;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TimeHolder extends PlaceholderExpansion {
        private final Database database;

        public TimeHolder(Database database) {
            this.database = database;
        }

        @Override
        public String getAuthor() {
            return "turnbass";
        }

        @Override
        public String getIdentifier() {
            return "Pt";
        }

        @Override
        public String getVersion() {
            return "1.0.0";
        }

        @Override
        public boolean persist() {
            return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
        }
    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByUUID(player.getUniqueId().toString());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getUniqueId().toString(), 1);
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }

    public long getPlayerTime(Player player) throws SQLException {

        PlayerStats playerStats = getPlayerStatsFromDatabase(player);
        long time = Long.parseLong(String.valueOf(playerStats.getPlayerTime()));
        return time;
    }
        @Override
        public String onRequest(OfflinePlayer player, String params) {
            if(params.equalsIgnoreCase("PlayerTime")){
                long time = 0;
                try {
                    time = getPlayerTime((Player) player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return TimeUtils.secondsToHHMMSS(time);
            }

            return null; // Placeholder is unknown by the Expansion
        }
}
