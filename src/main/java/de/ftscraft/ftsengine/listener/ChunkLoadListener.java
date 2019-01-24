package de.ftscraft.ftsengine.listener;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.pferd.Pferd;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    private Engine plugin;

    public ChunkLoadListener(Engine plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {

        try {
            for (Pferd a : plugin.getPferde().values()) {
                a.updateLocation();
                if(a.location == null) {
                    return;
                }
                if (a.location.getChunk().equals(e.getChunk())) {
                    a.spawnHorse(null);
                }
            }
        } catch (Exception ignored) {

        }

    }

}