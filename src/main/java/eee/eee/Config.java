package eee.eee;

public class Config {
    public static void makeConfig() {
        SABans pl = SABans.getInstance();
        pl.getConfig().addDefault("strings.already-banned", "&c%player% is already banned!");
        pl.getConfig().addDefault("strings.ban-check-message", "&4%player% is banned!%NL%&cBan issued by: &6%banned-by%%NL%&cBan length: &6%ban-length% %NL%&cBan reason: &6%reason%");
        pl.getConfig().addDefault("strings.ban-exempt", "&c%player% is not bannable!");
        pl.getConfig().addDefault("strings.ban-message", "&4You have been banned!%NL% %NL%&cBan issued by: &6%banned-by%%NL%&cBan length: &6%ban-length% %NL%&cBan reason: &6%reason%%NL% %NL%&cYou may appeal on our site if your ban is permanent.%NL%&bhttp://mywebsite.com/unbans");
        pl.getConfig().addDefault("strings.default-perm", "&6Forever");
        pl.getConfig().addDefault("strings.default-reason", "&6None");
        pl.getConfig().addDefault("strings.expired-ban", "Ban expired!");
        pl.getConfig().addDefault("strings.no-time", "&6None");
        pl.getConfig().addDefault("strings.player-not-found", "&cPlayer %player% not found!");
        pl.getConfig().addDefault("strings.player-not-banned", "&c%player% is not banned!");
        pl.getConfig().addDefault("strings.perm-banned-notif", "&6%player% &cwas permanently banned by &6%banned-by%&c.%NL%&cReason: &6%reason%");
        pl.getConfig().addDefault("strings.temp-banned-notif", "&6%player% &cwas temporarily banned by &6%banned-by%&c for &6%ban-length%&c.%NL%&cReason: &6%reason%");
        pl.getConfig().addDefault("strings.unban-notif", "&6%player% &cwas unbanned by &6%unbanned-by%&c!");
        pl.getConfig().options().copyDefaults(true);
        pl.saveConfig();
    }
}
