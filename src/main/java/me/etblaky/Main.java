package me.etblaky;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ETblaky on 04/11/2016.
 */
public class Main extends JavaPlugin implements Listener {

    public static Location loc;

    public void onEnable() {
        loc = getLocationString(this.getConfig().getString("Location"));

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) { sender.sendMessage("Only players can do that!"); return true;}

        if(cmd.getName().equalsIgnoreCase("spawn")){
            ((Player) sender).teleport(loc);
        }

        if(cmd.getName().equalsIgnoreCase("setSpawn")){
            loc = ((Player) sender).getLocation();
            this.getConfig().set("Location", getStringLocation(loc));
            this.saveConfig();
        }

        return true;
    }

    public String getStringLocation(Location l) {
        return l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
    }

    public Location getLocationString(String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 6) {

            final World w = Bukkit.getServer().getWorld(parts[0]);
            final double x = Double.parseDouble(parts[1]);
            final double y = Double.parseDouble(parts[2]);
            final double z = Double.parseDouble(parts[3]);
            final float yaw = Float.parseFloat(parts[4]);
            final float pitch = Float.parseFloat(parts[5]);

            return new Location(w, x, y, z, yaw, pitch);
        }
        return null;
    }

    public static Location getSpawn(){
        return loc;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(Main.getSpawn());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){/*
        if(Game.playerGame(e.getEntity()) != null) return;
        if(Lobby.playerLobby(e.getEntity()) != null) return;*/

        e.getEntity().teleport(Main.getSpawn());
    }

}
