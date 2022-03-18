package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CMDplaytime implements CommandExecutor {


    public CMDplaytime(Engine plugin){
        plugin.getCommand("spielzeit").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender cs, Command cmd,String label,String[] args) {

        if (cs instanceof Player) {
            Player p = (Player) cs;
//Komisches Rechen Ding
            int second = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
            int minute = second / 60;
            int hour = minute / 60;

            if (cmd.getName().equalsIgnoreCase("spielzeit")) ;
            p.sendMessage("§bDu spielst schon §c" + hour + " Stunden§b. \n§bAb §c50 Stunden §b solltest du eine Caraktervorstellung im Forum schreiben");
        } else {
            cs.sendMessage("Du musst ein Spieler sein");
        }
        return false;
    }
}