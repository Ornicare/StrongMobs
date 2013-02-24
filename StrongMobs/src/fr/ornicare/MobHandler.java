package fr.ornicare;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cconsole.CConsole;
import fr.ornicare.yaml.ConfigAccessor;




public class MobHandler extends JavaPlugin{
	
	public static Logger LOGGER;
	



	public static FileConfiguration CONFIG;
	
	private CommandListener myExecutor = new CommandListener();
	
	private static int normalSpawnLimit;
	
	
	public static Logger getLOGGER() {
		return LOGGER;
	}
	
	public static int getNormalSpawnLimit() {
		return normalSpawnLimit;
	}


	public void onEnable() {
		
		CConsole.load();
		
		
		////////////////////////
		ConfigAccessor zombieConfigFile = new ConfigAccessor(this, "zombieDefinition2.yml");
		zombieConfigFile.saveDefaultConfig();

		FileConfiguration zombieConfig = zombieConfigFile.getConfig();
		
		
		
		zombieConfig.set("zombies.superzombie.spawnweigh", 10);
		zombieConfig.set("zombies.superzombie.equipement.weapon.type", "DIAMOND_SWORD");
		
		zombieConfig.set("zombies.simplezomb.spawnweigh", 10);
		zombieConfig.set("zombies.simplezomb.equipement.weapon.type", "DIAMOND_SWORD");
		CConsole.println(zombieConfig.saveToString());

		for(String s : ((MemorySection) zombieConfig.get("zombies")).getKeys(true)) {
			if(!s.contains(".")) CConsole.println(s);
		}
		
		zombieConfigFile.saveConfig();

		
		
		////////////////////////
	
		// Save a copy of the default config.yml if one is not there
		this.saveDefaultConfig();

		// Registering the logger
		LOGGER = this.getLogger();
		
		// Registering the config file
		CONFIG = this.getConfig();
		
		
		//Loading the activated part of the plugin
		LOGGER.log(Level.INFO,CONFIG.getString("Loading_Message"));
		
		//LOGGER.log(Level.SEVERE,""+Bukkit.getWorlds().get(0).getTicksPerMonsterSpawns());
		//Bukkit.getWorlds().get(0).setTicksPerMonsterSpawns(1);
		LOGGER.log(Level.INFO,""+Bukkit.getWorlds().get(0).getAmbientSpawnLimit());
		//Bukkit.getWorlds().get(0).setAmbientSpawnLimit(400);
		
		//get normal spawn limit
		normalSpawnLimit = Bukkit.getWorlds().get(0).getMonsterSpawnLimit();
		
		
		//register commands
		//getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
	
		 
		
			myExecutor = new CommandListener();
			this.getCommand("bloodmoon").setExecutor(myExecutor);
			
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