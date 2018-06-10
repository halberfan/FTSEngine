package de.ftscraft.ftsengine.utils;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.pferd.Pferd;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class UserIO
{

    private Engine plugin;

    private File folder;

    public UserIO(Engine plugin)
    {
        this.plugin = plugin;
        folder = plugin.getDataFolder();
        getAusweise();
        getPferde();
    }

    public void getAusweise()
    {
        File aFolder = new File(folder + "//ausweise//");
        if (!aFolder.exists())
        {
            aFolder.mkdirs();
        }

        try
        {
            for (File aFile : aFolder.listFiles())
            {
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(aFile);

                String UUID = aFile.getName().replace(".yml", "");
                String lastName = cfg.getString("lastName");
                String firstName = cfg.getString("firstName");
                Gender gender = null;
                if (cfg.isSet("gender"))
                    gender = Gender.valueOf(cfg.getString("gender"));
                String race = cfg.getString("race");
                String religion = cfg.getString("religion");
                String nation = cfg.getString("nation");
                String desc = cfg.getString("desc");
                long millis = cfg.getLong("birthday");
                Integer id = cfg.getInt("id");
                Calendar cal = null;
                Date date = new Date();
                date.setTime(millis);
                cal = Calendar.getInstance();
                cal.setTime(date);


                if (id > plugin.highestId)
                    plugin.highestId = id;

                new Ausweis(plugin, UUID, firstName, lastName, gender, race, nation, desc, religion, cal, id);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getPferde()
    {
        File aFolder = new File(folder + "//pferde//");

        try
        {

            for (File aFile : Objects.requireNonNull(aFolder.listFiles()))
            {
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(aFile);
                String uuid;
                String owner;
                boolean locked;
                World w;
                int price;
                int persID;
                String name;
                boolean chosed;

                uuid = cfg.getString("uuid");
                owner = cfg.getString("owner");
                locked = cfg.getBoolean("locked");
                w = Bukkit.getWorld(cfg.getString("world"));
                price = cfg.getInt("price");
                persID = cfg.getInt("id");
                name = cfg.getString("name");
                chosed = cfg.getBoolean("chosed");

                new Pferd(plugin, UUID.fromString(uuid), w, UUID.fromString(owner), locked, price, persID, name, chosed);
            }

        } catch (NullPointerException e)
        {

        }

    }


}