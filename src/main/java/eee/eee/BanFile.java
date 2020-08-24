package eee.eee;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class BanFile {
    public BanFile(SABans plugin) {}

    public static void makeBanFiles() throws IOException {
        File banFile = new File(SABans.getInstance().getDataFolder(), "banned.yml");
        if (!banFile.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(banFile);
            yamlConfiguration.addDefault("ban-list", Integer.valueOf(0));
            yamlConfiguration.options().copyDefaults(true);
            yamlConfiguration.save(banFile);
        }
        File prevBanFile = new File(SABans.getInstance().getDataFolder(), "prev-banned.yml");
        if (!prevBanFile.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(prevBanFile);
            yamlConfiguration.addDefault("do-not-change", Integer.valueOf(0));
            yamlConfiguration.addDefault("prev-ban-list", Integer.valueOf(0));
            yamlConfiguration.options().copyDefaults(true);
            yamlConfiguration.save(prevBanFile);
        }
    }
}
