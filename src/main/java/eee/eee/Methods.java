package eee.eee;

import java.util.concurrent.TimeUnit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Methods implements Listener {
    public Methods(SABans plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
    }

    public static String getRemainingTime(String time, long seconds) {
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        String sDay = String.valueOf(day) + "d ", sHour = String.valueOf(hours) + "h ", sMinute = String.valueOf(minute) + "m ", sSecond = String.valueOf(second) + "s ";
        if (day == 0)
            sDay = "";
        if (hours == 0L)
            sHour = "";
        if (minute == 0L)
            sMinute = "";
        if (second == 0L)
            sSecond = "";
        time = String.valueOf(sDay) + sHour + sMinute + sSecond;
        time = time.trim();
        if (time.equals(""))
            time = SABans.getInstance().getConfig().getString("strings.no-time");
        return time;
    }
}
