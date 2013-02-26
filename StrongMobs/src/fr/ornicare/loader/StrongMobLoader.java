package fr.ornicare.loader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.ornicare.handlers.CreeperHandler;
import fr.ornicare.handlers.SkeletonHandler;
import fr.ornicare.handlers.ZombieHandler;
import fr.ornicare.models.PotionEffectModel;
import fr.ornicare.models.mobs.CreeperModel;
import fr.ornicare.models.mobs.MobModel;
import fr.ornicare.models.mobs.SkeletonModel;
import fr.ornicare.models.mobs.ZombieModel;
import fr.ornicare.storage.MobStorage;
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
	
	/**
	 * Semi paths
	 */
	private String[] idToPath = {"weapon","armor.boots","armor.leggings","armor.chestplate","armor.helmet"};

	/**
	 * Actions to perform on plugin load.
	 */
	public void onEnable() {
		
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
        	 	case "zombie":
        	 		if(loadZombies()) LOGGER.log(Level.INFO,"Zombie modifier loaded !");
        	 		break;
        	 	case "skeleton":
        	 		if(loadSkeletons()) LOGGER.log(Level.INFO,"Skeleton modifier loaded !");
        	 		break;
        	 	case "creeper":
        	 		if(loadCreepers()) LOGGER.log(Level.INFO,"Creeper modifier loaded !");
        	 		break;
        	 }
        }
		
	}
	
	/**
	 * Load creepers models
	 */
	private boolean loadCreepers() {
		//Create the config file manager.
		ConfigAccessor creeperConfigFile = new ConfigAccessor(this, "creepers.yml");
		
		//If the config file doesn't exists, create it.
		creeperConfigFile.saveDefaultConfig();
		
		//get it.
		FileConfiguration creeperConfig = creeperConfigFile.getConfig();
		
		//if the list if empty don't load
		if(creeperConfigFile.getMobList().isEmpty()) return false;
		
		//get the list of creepers and load it
		for(String s : creeperConfigFile.getMobList()) {
			CreeperModel creeper = (CreeperModel) genericMobLoader(new CreeperModel(), s, creeperConfig);

			// /!\ Specific to creeper : probability to be electric.
			creeper.setElectricChance(creeperConfig.getDouble(s+".electricchance"));
			
			// /!\ Specific to creeper : explosion type.
			boolean[] explosionsType = {false,false,false,false};
			if(creeperConfig.getList(s+".explosiontype")!=null) {
				for(Object effectObject : creeperConfig.getList(s+".explosiontype")) {
					if(((String)effectObject).equals("destroyblocs")) explosionsType[0] = true;
					if(((String)effectObject).equals("fire")) explosionsType[1] = true;
					if(((String)effectObject).equals("poisonous")) explosionsType[2] = true;
					if(((String)effectObject).equals("fireentity")) explosionsType[3] = true;
				}	
			}
			else {
				//default creeper explosion
				explosionsType[0] = true;
			}
			creeper.setExplosionTypes(explosionsType);
			
			//Store the new creeper !
			MobStorage.CREEPERS.push(creeperConfig.getInt(s+".spawnweigh"), creeper);	
		}
		
		//Create the handler.
		this.getServer().getPluginManager().registerEvents(new CreeperHandler(), this);
		
		return true;
	}

	/**
	 * Load skeletons models
	 */
	private boolean loadSkeletons() {
		//Create the config file manager.
		ConfigAccessor skeletonConfigFile = new ConfigAccessor(this, "skeletons.yml");
		
		//If the config file doesn't exists, create it.
		skeletonConfigFile.saveDefaultConfig();
		
		//get it.
		FileConfiguration skeletonConfig = skeletonConfigFile.getConfig();
		
		//if the list if empty don't load
		if(skeletonConfigFile.getMobList().isEmpty()) return false;
		
		//get the list of skeleton and load it
		for(String s : skeletonConfigFile.getMobList()) {
			SkeletonModel skeleton = (SkeletonModel) genericMobLoader(new SkeletonModel(), s, skeletonConfig);
	
			//Store the new skeleton !
			MobStorage.SKELETONS.push(skeletonConfig.getInt(s+".spawnweigh"), skeleton);	
		}
		
		//Create the handler.
		this.getServer().getPluginManager().registerEvents(new SkeletonHandler(), this);
		
		return true;
	}

	/**
	 * Load all zombies models.
	 */
	private boolean loadZombies() {
		//Create the config file manager.
		ConfigAccessor zombieConfigFile = new ConfigAccessor(this, "zombies.yml");
		
		//If the config file doesn't exists, create it.
		zombieConfigFile.saveDefaultConfig();
		
		//get it.
		FileConfiguration zombieConfig = zombieConfigFile.getConfig();
		
		//if the list if empty don't load
		if(zombieConfigFile.getMobList().isEmpty()) return false;
		
		//get the list of zombies and load it
		for(String s : zombieConfigFile.getMobList()) {
			ZombieModel zombie = (ZombieModel) genericMobLoader(new ZombieModel(), s, zombieConfig);

			// /!\ Specific to zombie : probability to get a mini zombie.
			zombie.setChildChance(zombieConfig.getDouble(s+".childchance"));
			
			//Store the new zombie !
			MobStorage.ZOMBIES.push(zombieConfig.getInt(s+".spawnweigh"), zombie);	
		}
		
		//Create the handler.
		this.getServer().getPluginManager().registerEvents(new ZombieHandler(), this);
		
		return true;
	}
	
	private MobModel genericMobLoader(MobModel mmodel,String mobName, FileConfiguration fileConfig) {
		
		//load all the items
		for(int i = 0;i<5;i++) mmodel = genericItemLoader(mmodel,mobName,fileConfig, i);
		
		//load the health
		mmodel.setHealth(fileConfig.getInt(mobName+".health")<1?-1:fileConfig.getInt(mobName+".health"));
		
		//load the spawn multiplicator and keep it <0.9
		mmodel.setSpawnmultiplicator(fileConfig.getDouble(mobName+".spawnmultiplicator")<0.9?fileConfig.getDouble(mobName+".spawnmultiplicator"):0.9);

		//load the rebirth stats
		mmodel.setSpawnOnDeath(fileConfig.getDouble(mobName+".spawnondeath.probability"),fileConfig.getDouble(mobName+".spawnondeath.maxnumber"),fileConfig.getDouble(mobName+".spawnondeath.healthmultiplicator"));

		//load effects to apply
		mmodel = potionEffectLoader(mmodel, mobName,fileConfig);
		
		return mmodel;
	}
	
	private MobModel potionEffectLoader(MobModel mmodel, String mobName, FileConfiguration fileConfig) {

		if(fileConfig.getList(mobName+".effects")!=null) {
			for(Object effectObject : fileConfig.getList(mobName+".effects")) {
				String[] effectArray = ((String)effectObject).split(",");
				
				if(effectArray.length>0) {
					String eLevel = effectArray.length>1?effectArray[1].trim():"1";
					String eProba = effectArray.length>2?effectArray[2].trim():"1.0";
					int effectLevel;
					double eProbability;
					
					try {
						eProbability = Double.parseDouble(eProba);
					}
					catch(Exception e) {
						eProbability = 0;
					}
					
					try {
						effectLevel = Integer.parseInt(eLevel);
					}
					catch(Exception e) {
						effectLevel = 0;
					}
					
					try {
						int effectId = Integer.parseInt(effectArray[0].trim());
						
						mmodel.addPotionEffect(new PotionEffectModel(effectId, effectLevel, eProbability));
					}
					catch(Exception e) {
						mmodel.addPotionEffect(new PotionEffectModel(effectArray[0].trim(), effectLevel, eProbability));
					}
				}

			}
		}
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
		
		//Get the dropChance
		float itemDropChance = (float)(fileConfig.getString(mobName+".equipment."+equipmentpiece+".dropchance")!=null?fileConfig.getDouble(mobName+".equipment."+equipmentpiece+".dropchance"):-1);
		
		//Get the bukkit representation of the id.
		int itemId = fileConfig.getInt(mobName+".equipment."+equipmentpiece+".type");
		
		if(itemId!=0){
			mmodel.setItem(id,itemId,fileConfig.getDouble(mobName+".equipment."+equipmentpiece+".probability"),itemDropChance);
		}
		else
		{
			mmodel.setItem(id,fileConfig.getString(mobName+".equipment."+equipmentpiece+".type"),fileConfig.getDouble(mobName+".equipment."+equipmentpiece+".probability"),itemDropChance);
		}
		
		
		
		
		//get it's enchantments
		if(fileConfig.getList(mobName+".equipment."+equipmentpiece+".enchantments")!=null) {
			for(Object enchantmentObject : fileConfig.getList(mobName+".equipment."+equipmentpiece+".enchantments")) {
				String[] enchantmentArray = ((String)enchantmentObject).split(",");
				
				if(enchantmentArray.length>0) {
					String eLevel = enchantmentArray.length>1?enchantmentArray[1].trim():"1";
					String eProba = enchantmentArray.length>2?enchantmentArray[2].trim():"1.0";
					int enchantmentLevel;
					double eProbability;
					
					try {
						eProbability = Double.parseDouble(eProba);
					}
					catch(Exception e) {
						eProbability = 0;
					}
					
					try {
						enchantmentLevel = Integer.parseInt(eLevel);
					}
					catch(Exception e) {
						enchantmentLevel = 0;
					}
					
					
					try {
						int enchantmentId = Integer.parseInt(enchantmentArray[0].trim());
						
						mmodel.addItemEnchantments(id, enchantmentId, enchantmentLevel, eProbability);
					}
					catch(Exception e) {
						mmodel.addItemEnchantments(id, enchantmentArray[0].trim(), enchantmentLevel, eProbability);
					}
				}

			}
		}
		
		return mmodel;
	}
}
