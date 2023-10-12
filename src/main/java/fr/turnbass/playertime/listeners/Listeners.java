package fr.turnbass.playertime.listeners;

import fr.turnbass.playertime.PlayerTime;
import fr.turnbass.playertime.db.Database;
import fr.turnbass.playertime.model.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.*;

public class Listeners implements Listener {

    private final Database database;

    public Listeners(Database database) {
        this.database = database;
    }

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByUUID(player.getUniqueId().toString());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getUniqueId().toString(), 1);
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }
    private List<Player> connectedPlayers = new ArrayList<>();
    private Map<Player, Integer> taskIds = new HashMap<>();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        System.out.println("load Stats pour le joueur Player");
        try{
            PlayerStats playerStats = getPlayerStatsFromDatabase(p);
            String UUID = playerStats.getPlayerUUID().toString();
            PlayerTime.getInstance().getLogger().info("Player" +UUID + " joined");
            // Ajoutez le joueur à la liste des joueurs connectés
            connectedPlayers.add(p);

            // Planifiez la tâche périodique pour le joueur
            startPeriodicTask(p);

    }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not update player stats after join.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();

        // Retirez le joueur de la liste des joueurs connectés lors de leur déconnexion
        connectedPlayers.remove(player);

        // Arrêtez la tâche périodique pour ce joueur s'il est déconnecté
        stopPeriodicTask(player);
    }

    private void startPeriodicTask(Player player) {
        // Planifiez la tâche périodique et obtenez son identifiant
        int taskId = new BukkitRunnable() {
            private long timeToAdd = 1; // 1 seconde

            @Override
            public void run() {
                try{
                    PlayerStats playerStats = getPlayerStatsFromDatabase(player);
                    playerStats.setPlayerTime(playerStats.getPlayerTime() + timeToAdd);
                    database.updatePlayerStats(playerStats);
                }catch (SQLException e1){
                    e1.printStackTrace();
                    System.out.println("Could not update player stats after quit.");
                }
            }
        }.runTaskTimer(PlayerTime.getInstance(), 0, 20).getTaskId(); // 20 ticks (1 seconde)

        // Stockez l'identifiant de tâche dans une structure de données pour ce joueur
        // Par exemple, dans une Map<Player, Integer> pour associer le joueur à son identifiant de tâche
        // Cela vous permettra d'annuler la tâche plus tard en utilisant l'identifiant de tâche.
        taskIds.put(player, taskId);
    }

    // Méthode pour arrêter la tâche périodique pour un joueur
    private void stopPeriodicTask(Player player) {
        // Obtenez l'identifiant de tâche associé à ce joueur depuis la structure de données
        Integer taskId = taskIds.get(player);

        if (taskId != null) {
            // Annulez la tâche en utilisant l'identifiant de tâche
            Bukkit.getScheduler().cancelTask(taskId);

            // Retirez l'identifiant de tâche de la structure de données
            taskIds.remove(player);
        }
    }

}
