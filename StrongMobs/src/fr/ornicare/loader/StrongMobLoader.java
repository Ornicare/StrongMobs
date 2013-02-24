package fr.ornicare.loader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cconsole.CConsole;
import fr.ornicare.handlers.ZombieHandler;
import fr.ornicare.mobs.MobStorage;
import fr.ornicare.models.mobs.MobModel;
import fr.ornicare.models.mobs.ZombieModel;
import fr.ornicare.yaml.ConfigAccessor;

/**
 * The main class of the plugin
 * 
 * @author Ornicare
 *
 */
public class StrongMobLoader extends JavaPlugin{
	
	public static Logger LOGGER;
	public static FileConfiguration CONFIG;
	
	private String[] idToPath = {"weapon","armor.boots","armor.leggings","armor.chestplate","armor.helmet"};

	/**
	 * Actions to perform on plugin load.
	 */
	public void onEnable() {
		
		///////////////////
		CConsole.load();
		
		//////////////
		
		//If the config file doesn't exists, create it.
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
        	 		LOGGER.log(Level.INFO,"Player modifier loaded !");
        	 		break;
        	 	case "zombie":
        			loadzombies();
        			
        	 		break;
        	 	case "skeleton":
        	        LOGGER.log(Level.INFO,"Skeleton modifier loaded !");
        	 		break;
        	 	case "creeper":
        	        LOGGER.log(Level.INFO,"Creeper modifier loaded !");
        	 		break;
        	 }
        }
		
	}
	
	/**
	 * Load all zombies models.
	 */
	private void loadzombies() {
		//Create the config file manager.
		ConfigAccessor zombieConfigFile = new ConfigAccessor(this, "zombies.yml");
		
		//If the config file doesn't exists, create it.
		zombieConfigFile.saveDefaultConfig();
		
		//get it.
		FileConfiguration zombieConfig = zombieConfigFile.getConfig();
		
		//get the list of zombies and load it
		for(String s : zombieConfigFile.getMobList()) {
			ZombieModel zombie = (ZombieModel) genericMobLoader(new ZombieModel(), s, zombieConfig);

			//Store the new zombie !
			MobStorage.ZOMBIES.push(zombieConfig.getInt(s+".spawnweigh"), zombie);	
		}
		
		//Create the handler.
		this.getServer().getPluginManager().registerEvents(new ZombieHandler(), this);
	}
	
	private MobModel genericMobLoader(MobModel mmodel,String mobName, FileConfiguration fileConfig) {
		
		//load all the items
		for(int i = 0;i<5;i++) mmodel = genericItemLoader(mmodel,mobName,fileConfig, i);
		return mmodel;
	}
	
	/**
	 * Load an item from the fileCOnfig
	 * 
	 * @param mmodel the mob model of the mob
	 * @param mobName mob's name
	 * @param fileConfig source
	 * @param equipmentpiece {weapon, armor.helmet, armor.chest, armor.boots, armor.leggings}
	 * @return
	 */
	private MobModel genericItemLoader(MobModel mmodel,String mobName, FileConfiguration fileConfig, int id) {
		String equipmentpiece = idToPath[id];
		
		//Get the bukkit representation of the id.
		int itemId = fileConfig.getInt(mobName+".equipment."+equipmentpiece+".type");
		if(itemId!=0){
			mmodel.setItem(id,itemId,fileConfig.getDouble(mobName+".equipment."+equipmentpiece+".probability"));
		}
		else
		{
			mmodel.setItem(id,fileConfig.getString(mobName+".equipment."+equipmentpiece+".type"),fileConfig.getDouble(mobName+".equipment."+equipmentpiece+".probability"));
		}
		
		
		
		//get it's enchantments
		if(fileConfig.getList(mobName+".equipment."+equipmentpiece+".enchantments")!=null) {
			for(Object enchantmentObject : fileConfig.getList(mobName+".equipment."+equipmentpiece+".enchantments")) {
				String[] enchantmentArray = ((String)enchantmentObject).split(",");
				
				if(enchantmentArray.length>0) {
					String eLevel = enchantmentArray.length>1?enchantmentArray[1].trim():"1";
					int enchantmentLevel;
					
					try {
						enchantmentLevel = Integer.parseInt(eLevel);
					}
					catch(Exception e) {
						enchantmentLevel = 0;
					}
					
					
					try {
						int enchantmentId = Integer.parseInt(enchantmentArray[0]);
						
						mmodel.addItemEnchantments(id, enchantmentId, enchantmentLevel);
					}
					catch(Exception e) {
						mmodel.addItemEnchantments(id, enchantmentArray[0], enchantmentLevel);
					}
				}

			}
		}
		
		return mmodel;
	}
}
