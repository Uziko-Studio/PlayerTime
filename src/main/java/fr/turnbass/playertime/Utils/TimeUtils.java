package fr.turnbass.playertime.Utils;

public class TimeUtils {
    public static String secondsToHHMMSS(long totalSeconds) {
        long hours = totalSeconds / 3600; // 3600 secondes dans une heure
        long minutes = (totalSeconds % 3600) / 60; // Le reste de la division par 3600 donne les secondes restantes, que nous divisons ensuite par 60 pour obtenir les minutes.
        long seconds = totalSeconds % 60; // Le reste de la division par 60 donne les secondes.

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return formattedTime;
    }
}