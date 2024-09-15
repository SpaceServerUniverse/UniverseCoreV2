package space.yurisi.universecorev2.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigReader{

    private final JavaPlugin plugin;
    private final String name;
    private final String path;
    private File file;
    private File filepath;
    private FileConfiguration config;

    public ConfigReader(JavaPlugin plugin, String pathname, String filename){
        this.plugin = plugin;
        this.path = pathname;
        this.name = filename;
        this.filepath = new File(plugin.getDataFolder(), path);
        this.file = new File(filepath, name);
    }

    public static void save(ConfigReader config){
        config.saveConfig();
        config.reloadConfig();
    }

    public void saveDefaultConfig(){
        if(!filepath.exists()){
            boolean success = filepath.mkdirs();
            if(!success) Bukkit.getLogger().severe("Error creating the config. Please try again.");
        }
        if(!file.exists()) this.plugin.saveResource(path + name, false);
    }

    public FileConfiguration getConfig(){
        if(!filepath.exists()) this.saveDefaultConfig();
        if(config == null) this.reloadConfig();
        return config;
    }

    public void reloadConfig(){
        if(filepath == null) filepath = new File(plugin.getDataFolder(), path);
        if(file == null) file = new File(filepath, name);
        config = YamlConfiguration.loadConfiguration(file);
        InputStream stream = plugin.getResource(name);
        if(stream != null){
            YamlConfiguration YmlFile = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
            config.setDefaults(YmlFile);
        }
    }

    public void saveConfig(){
        try{
            config.save(file);
        }catch(Throwable t){
            Bukkit.getLogger().severe("Error saving the config. Please try again.");
        }
    }
}