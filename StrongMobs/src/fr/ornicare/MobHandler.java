package fr.ornicare;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import fr.ornicare.yaml.ConfigAccessor;




public class MobHandler extends JavaPlugin{
	
	public static Logger LOGGER;
	public static FileConfiguration CONFIG;
	
	public void onEnable() {
	
		// Save a copy of the default config.yml if one is not there
		this.saveDefaultConfig();

		// Registering the logger
		LOGGER = this.getLogger();
		
		// Registering the config file
		CONFIG = this.getConfig();
		
		
		//Loading the activated part of the plugin
		LOGGER.log(Level.INFO,CONFIG.getString("Loading_Message"));

        List<String> rules = CONFIG.getStringList("Activated");
        for (String s : rules) {
        	 switch(s) {
        	 	case "player":
        	 		getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
        	 		LOGGER.log(Level.INFO,"Player modifier loaded !");
        	 		break;
        	 	case "zombie":
        	        getServer().getPluginManager().registerEvents(new ZombieHandler(), this);
        	        LOGGER.log(Level.INFO,"Zombie modifier loaded !");
        	 		break;
        	 	case "skeleton":
        	        getServer().getPluginManager().registerEvents(new SkeletonHandler(), this);
        	        LOGGER.log(Level.INFO,"Skeleton modifier loaded !");
        	 		break;
        	 	case "creeper":
        	        getServer().getPluginManager().registerEvents(new CreeperHandler(), this);
        	        LOGGER.log(Level.INFO,"Creeper modifier loaded !");
        	 		break;
        	 }
        }
    }
	
 
    public void onDisable() {}

}