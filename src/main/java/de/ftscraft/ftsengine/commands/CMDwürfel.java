package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftsengine.utils.Gender;
import de.ftscraft.ftsengine.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;


public class CMDwürfel implements CommandExecutor {

    private final Engine plugin;

    private final int DICES = 3;

    public CMDwürfel(Engine plugin) {
        this.plugin = plugin;
        plugin.getCommand("würfel").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player) cs;

        Ausweis ausweis = plugin.getAusweis(p);

        if (ausweis == null || ausweis.getGender() == null || ausweis.getRace() == null) {
            p.sendMessage("§6Bitte lege dir zuerst einen Ausweis an (/ausweis). Für das Würfeln ist folgendes wichtig: §cName, Rasse, Geschlecht");
            return true;
        }

        Gender gender = ausweis.getGender();
        String name = ausweis.getFirstName() + " " + ausweis.getLastName();

        DiceType dice = DiceType.getDiceTypeByName(ausweis.getRace());

        boolean magic = false;

        if (args.length <= 2 && args.length > 0) {
            if (dice == null) {
                dice = DiceType.getDiceTypeByName(args[0]);
                if (args.length == 2)
                    if (args[1].equalsIgnoreCase("magie"))
                        magic = true;
            } else {
                magic = args[0].equalsIgnoreCase("magie");
            }
        }

        if (dice != null) {

            int total = 0;

            StringBuilder sb;

            if (gender == Gender.FEMALE) {
                if (dice.getMName().equalsIgnoreCase(dice.getFName())) {
                    sb = new StringBuilder("§6Der §o" + dice.getMName() + " §r§e" + name + " §6würfelt: §e");
                } else
                    sb = new StringBuilder("§6Die §o" + dice.getFName() + " §r§e" + name + " §6würfelt: §e");
            } else
                sb = new StringBuilder("§6Der §o" + dice.getMName() + " §r§e" + name + " §6würfelt: §e");

            if (magic) {

                total = ThreadLocalRandom.current().nextInt(1, 12 + 1);

                sb.append(((total >= dice.getMagicMin() && total <= dice.getMagicMax()) ? "§2" : "§c") + total + " §5[Magie]");

            } else {

                int value = ThreadLocalRandom.current().nextInt(1, 100 + 1);

                if(value <= dice.getNeeds()) {
                    sb.append("§2" + value + " §6und hat damit den Wurf §2geschafft!");
                } else {
                    sb.append("§c" + value + " §6hätte aber §c" + dice.getNeeds() + " §6oder niedriger würfeln müssen!");
                }

            }

            for (Entity nearbyEntity : p.getLocation().getNearbyEntities(20, 20, 20)) {
                if (nearbyEntity instanceof Player) {
                    nearbyEntity.sendMessage(sb.toString());
                }
            }


        } else {
            cs.sendMessage("§cBitte würfel so: §e/würfel [Mensch/Zwerg/Elf/Ork/Goblin] <Magie>");
        }

        return false;
    }

    public enum DiceType {

        ORK("Ork", "Orkin", 60, 4, 8),
        ZWERG("Zwerg", "Zwergin", 50, 4, 8),
        MENSCH("Mensch", "Mensch", 40, 6, 8),
        ELF("Elf", "Elfin", 25, 2, 10),
        GOBLIN("Goblin", "Goblin", 15, 3, 9);

        private final String mName;
        private final String fName;
        private int pips;
        private final int chance;
        private final int magicMin;
        private final int magicMax;

        DiceType(String mName, String fName, int chance, int magicMin, int magicMax) {
            this.mName = mName;
            this.fName = fName;
            this.pips = pips;
            this.chance = chance;
            this.magicMin = magicMin;
            this.magicMax = magicMax;
        }

        public String getMName() {
            return mName;
        }

        public String getFName() {
            return fName;
        }

        public int getPips() {
            return pips;
        }

        public int getNeeds() {
            return chance;
        }

        public int getMagicMax() {
            return magicMax;
        }

        public int getMagicMin() {
            return magicMin;
        }

        public static DiceType getDiceTypeByName(String race) {
            for (DiceType value : DiceType.values()) {
                if (value.getMName().equalsIgnoreCase(race) || race.toLowerCase().contains(value.getMName().toLowerCase()))
                    return value;
            }
            return null;
        }

    }

}
