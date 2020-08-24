package eee.eee;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
    public Events(SABans plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
        YamlConfiguration yamlConfiguration1 = YamlConfiguration.loadConfiguration(banFile);
        File prevBanFile = new File(SABans.getInstance().getDataFolder(), "prev-banned.yml");
        YamlConfiguration yamlConfiguration2 = YamlConfiguration.loadConfiguration(prevBanFile);
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        if (!yamlConfiguration1.contains("ban-list." + uuid))
            return;
        String reason = yamlConfiguration1.getString("ban-list." + uuid + ".reason");
        reason = reason.substring(0, reason.length() - 1);
        String time = ChatColor.translateAlternateColorCodes('&', SABans.getInstance().getConfig().getString("strings.default-perm"));
        boolean type = yamlConfiguration1.getBoolean("ban-list." + uuid + ".permanent");
        if (!type) {
            long banTime = yamlConfiguration1.getLong("ban-list." + uuid + ".ban-length");
            if (banTime < System.currentTimeMillis()) {
                String pName = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".name");
                String bannedBy = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".banned-by");
                String banLength = yamlConfiguration1.getString("ban-list." + uuid.toString() + ".ban-length");
                boolean permanent = yamlConfiguration1.getBoolean("ban-list." + uuid.toString() + ".permanent");
                int s = yamlConfiguration2.getInt("do-not-change") + 1;
                yamlConfiguration2.set("do-not-change", Integer.valueOf(s));
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".name", pName);
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".banned-by", bannedBy);
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".unbanned-by", ChatColor.stripColor(SABans.getInstance().getConfig().getString("strings.expired-ban")));
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".ban-length", banLength);
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".permanent", Boolean.valueOf(permanent));
                yamlConfiguration2.set("prev-ban-list." + s + "." + uuid.toString() + ".reason", reason);
                yamlConfiguration1.set("ban-list." + uuid, null);
                try {
                    yamlConfiguration1.save(banFile);
                    yamlConfiguration2.save(prevBanFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
            long timeLeft = banTime - System.currentTimeMillis();
            long seconds = timeLeft / 1000L;
            time = Methods.getRemainingTime(time, seconds);
        }
        String kickMsg = ChatColor.translateAlternateColorCodes('&', SABans.getInstance().getConfig().getString("strings.ban-message")
                .replaceAll("%NL%", "\n")
                .replaceAll("%banned-by%", yamlConfiguration1.getString("ban-list." + uuid + ".banned-by"))
                .replaceAll("%ban-length%", time)
                .replaceAll("%reason%", yamlConfiguration1.getString("ban-list." + uuid + ".reason")));
        e.disallow(PlayerLoginEvent.Result.KICK_BANNED, kickMsg);
    }
}

