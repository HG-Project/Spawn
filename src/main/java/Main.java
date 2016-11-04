import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ETblaky on 04/11/2016.
 */
public class Main extends JavaPlugin {

    public Location loc;

    public void onEnable() {
        loc = getLocationString(this.getConfig().getString("Location"));
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
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    public Location getLocationString(String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }

}
