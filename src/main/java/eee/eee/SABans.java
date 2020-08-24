package eee.eee;

import java.io.IOException;
import MoreCommands.cmds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import eee.eee.*;
import org.slf4j.Logger;


import java.util.Objects;


public final class SABans extends JavaPlugin implements Listener {

    private static SABans instance;

    public Permission permBan = new Permission("smartbans.ban");

    public Permission permCheck = new Permission("smartbans.check");

    public Permission permExempt = new Permission("smartbans.exempt");

    public Permission permNotify = new Permission("smartbans.notify");

    public Permission permoverride = new Permission("smartbans.override");

    public Permission permTemp = new Permission("smartbans.tempban");

    public Permission permUnban = new Permission("smartbans.unban");

    public static SABans getInstance() {
        return instance;
    }

    public static Permission perm = null;



    @Override
    public Logger getSLF4JLogger() {
        return null;
    }

    private boolean setupPermissions() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null)
            return false;
        perm = (Permission)rsp.getProvider();
        return (perm != null);
    }


    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());

        Objects.requireNonNull(getCommand("tpall")).setExecutor(new Tpall());

        Objects.requireNonNull(getCommand("serveraddons")).setExecutor(new MainCommand());

        Objects.requireNonNull(getCommand("chatmsgs")).setExecutor(new cmds());
        Objects.requireNonNull(getCommand("helpme")).setExecutor(new cmds());
        Objects.requireNonNull(getCommand("oof")).setExecutor(new cmds());
        Objects.requireNonNull(getCommand("idk")).setExecutor(new cmds());

        instance = this;
        BanFile banfile = new BanFile(this);
        Events events = new Events(this);
        try {
            BanFile.makeBanFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config.makeConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)this);
        PluginManager pm = getServer().getPluginManager();
        pm.addPermission(this.permBan);
        pm.addPermission(this.permNotify);
        pm.addPermission(this.permTemp);
        pm.addPermission(this.permUnban);
        if (!setupPermissions()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }

        getCommand("ban").setExecutor(new CmdBan());
        getCommand("check").setExecutor(new CmdCheck());
        getCommand("tempban").setExecutor(new CmdTempban());
        getCommand("unban").setExecutor(new CmdUnban());

        getServer().getConsoleSender().sendMessage("Server Addons Enabled");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("gms")){
            Player player = (Player) sender;
            if (args.length == 0) {
            if (sender instanceof Player) {
                if (player.hasPermission("sa.gms")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                }
            }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
            }
        }else{
            if (args.length == 1){
                if (sender.hasPermission("sa.gms.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                    sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }
        }

        if(command.getName().equals("gmc")){
            Player player = (Player) sender;
            if (args.length == 0) {
            if (sender instanceof Player) {
                if (player.hasPermission("sa.gmc")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }
            }else{
                if (args.length == 1){
                    if (player.hasPermission("sa.gmc.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        assert target != null;
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                        sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }
        }

        if(command.getName().equals("gma")){
            Player player = (Player) sender;
            if (sender instanceof Player){
            if (args.length == 0) {
                if (player.hasPermission("sa.gma")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                }
            }
                }
            }else{
                if (args.length == 1){
                    Player player = (Player) sender;
                    if (player.hasPermission("sa.gma.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        assert target != null;
                        target.setGameMode(GameMode.ADVENTURE);
                        target.sendMessage(ChatColor.GOLD + "Gamemode updated!");
                        sender.sendMessage(ChatColor.GOLD + "Target's gamemode has been updated!");
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    }
                }
            }

        if (command.getName().equals("discord")){
            if (sender.hasPermission("sa.discord")){
                String discord = getConfig().getString("Discord_Link");
                sender.sendMessage(ChatColor.DARK_BLUE + "Join our discord server!");
                sender.sendMessage(ChatColor.DARK_BLUE + discord);
            }
        }

        Player player = (Player) sender;

        if (command.getName().equals("gg")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.gg")) {
                    player.chat(ChatColor.GOLD + "GG");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }

        if (command.getName().equals("no")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.no")) {
                    player.chat(ChatColor.RED + "No");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }

        if (command.getName().equals("yes")) {
            if (sender instanceof Player){
                if (sender.hasPermission("sa.yes")) {
                    player.chat(ChatColor.GREEN + "Yes");
                }sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");

            }
        }




        return true;
    }


    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage("Server Addons Disabled");

    }
}

