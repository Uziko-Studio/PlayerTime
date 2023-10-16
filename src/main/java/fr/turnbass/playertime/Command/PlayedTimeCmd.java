package fr.turnbass.playertime.Command;

import fr.turnbass.playertime.PlayerTime;
import fr.turnbass.playertime.listeners.CmdUtils;
import fr.turnbass.playertime.model.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayedTimeCmd implements CommandExecutor , TabCompleter {
    private final CmdUtils cmdUtils;

    public PlayedTimeCmd(CmdUtils cmdUtils) {
        this.cmdUtils = cmdUtils;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("Global")) {
            if (args[1].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage("Usage : /votreCommande Global set <joueur> <valeur in seconde>");
                    return true;
                }
                String playerName = args[2];
                long valeur = Long.parseLong(args[3]);
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.setGPlayedTime(targetPlayer, valeur);
                        sender.sendMessage(playerName + "Value set to" + valeur + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("reset")) {
                String playerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.resetGPlayedTime(targetPlayer);
                        sender.sendMessage(playerName + "Value reset to 0s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    sender.sendMessage("Usage : /votreCommande Global set <joueur> <valeur in seconde>");
                    return true;
                }
                String playerName = args[2];
                long valeur = Long.parseLong(args[3]);
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.addGPlayedTime(targetPlayer, valeur);
                        sender.sendMessage(playerName + "Value add to" + valeur + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("get")) {
                String playerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        PlayerStats playerStats = cmdUtils.getGPlayedTime(targetPlayer);
                        sender.sendMessage(playerName + "Value set to" + playerStats.getPlayerTime() + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            }
        } else if (args[0].equalsIgnoreCase("Server")) {
            if (args[1].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage("Usage : /votreCommande Global set <joueur> <valeur in seconde>");
                    return true;
                }
                String playerName = args[2];
                long valeur = Long.parseLong(args[3]);
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.setSPlayedTime(targetPlayer, valeur);
                        sender.sendMessage(playerName + "Value set to" + valeur + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("reset")) {
                String playerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.resetSPlayedTime(targetPlayer);
                        sender.sendMessage(playerName + "Value reset to 0s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    sender.sendMessage("Usage : /votreCommande Global set <joueur> <valeur in seconde>");
                    return true;
                }
                String playerName = args[2];
                long valeur = Long.parseLong(args[3]);
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        cmdUtils.addGSlayedTime(targetPlayer, valeur);
                        sender.sendMessage(playerName + "Value add to" + valeur + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            } else if (args[1].equalsIgnoreCase("get")) {
                String playerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    try {
                        PlayerStats playerStats = cmdUtils.getSPlayedTime(targetPlayer);
                        sender.sendMessage(playerName + "Value set to" + playerStats.getPlayerTime() + "s");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Le joueur n'a pas été trouvé, vous pouvez envoyer un message d'erreur
                    sender.sendMessage("No player found");
                }
            }
        } else {
            // Afficher l'aide si la sous-commande n'est pas reconnue
            return false;
        }

        // Retournez true pour indiquer que la commande a été gérée avec succès
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            // Autocomplete the first argument (Global, Server, etc.)
            List<String> completions = new ArrayList<>();
            completions.add("Global");
            completions.add("Server");
            // Add more options as needed.
            return completions;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("Global")) {
            // Autocomplete the second argument (set, reset, add, get, etc.)
            List<String> completions = new ArrayList<>();
            completions.add("set");
            completions.add("reset");
            completions.add("add");
            completions.add("get");
            // Add more options as needed.
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Global") && args[1].equalsIgnoreCase("set")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Global") && args[1].equalsIgnoreCase("add")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Global") && args[1].equalsIgnoreCase("reset")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Global") && args[1].equalsIgnoreCase("get")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("Server")) {
            // Autocomplete the second argument (set, reset, add, get, etc.)
            List<String> completions = new ArrayList<>();
            completions.add("set");
            completions.add("reset");
            completions.add("add");
            completions.add("get");
            // Add more options as needed.
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Server") && args[1].equalsIgnoreCase("set")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Server") && args[1].equalsIgnoreCase("add")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Server") && args[1].equalsIgnoreCase("reset")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("Server") && args[1].equalsIgnoreCase("get")) {
            // Autocomplete the player's name for the 'set' subcommand.
            List<String> completions = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
            return completions;
        }

        // If no suggestions, return an empty list.
        return new ArrayList<>();
    }
}
