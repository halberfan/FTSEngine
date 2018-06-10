package de.ftscraft.personalausweis.listener;


import de.ftscraft.personalausweis.main.Engine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
    private Engine plugin;

    public PlayerQuitListener(Engine plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(e.getPlayer().getVehicle() != null) {
            e.getPlayer().setSneaking(true);
        }
        plugin.getPlayer().get(e.getPlayer()).leave();
    }

}
