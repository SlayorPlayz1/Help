package eee.eee;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class CmdCheck implements CommandExecutor {

    public CmdCheck() {

    }

    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("check")) {
            if (!sender.hasPermission("smartbans.check") && sender instanceof org.bukkit.entity.Player) {
                msg(sender, "&4You do not have access to that command.");
                return true;
            }
            if (args.length == 0 || args.length > 1) {
                msg(sender, "&cCorrect usage: /check <username>");
                return true;
            }
            if (Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-found").replaceAll("%player%", args[0]));
                return true;
            }
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
            String uuid = p.getUniqueId().toString();
            File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(banFile);
            if (!yamlConfiguration.contains("ban-list." + uuid)) {
                msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-banned").replaceAll("%player%", p.getName()));
                return true;
            }
            String reason = yamlConfiguration.getString("ban-list." + uuid + ".reason");
            String time = SABans.getInstance().getConfig().getString("strings.default-perm");
            boolean type = yamlConfiguration.getBoolean("ban-list." + uuid + ".permanent");
            if (!type) {
                long banTime = yamlConfiguration.getLong("ban-list." + uuid + ".ban-length");
                if (banTime < System.currentTimeMillis()) {
                    yamlConfiguration.set("ban-list." + uuid, null);
                    try {
                        yamlConfiguration.save(banFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    msg(sender, SABans.getInstance().getConfig().getString("strings.player-not-banned").replaceAll("%player%", p.getName()));
                    return true;
                }
                long timeLeft = banTime - System.currentTimeMillis();
                long seconds = timeLeft / 1000L;
                time = Methods.getRemainingTime(time, seconds);
            }
            msg(sender, SABans.getInstance().getConfig().getString("strings.ban-check-message")
                    .replaceAll("%NL%", "\n")
                    .replaceAll("%banned-by%", yamlConfiguration.getString("ban-list." + uuid + ".banned-by"))
                    .replaceAll("%ban-length%", time)
                    .replaceAll("%player%", p.getName())
                    .replaceAll("%reason%", reason));
            return true;
        }
        return false;
    }
}

