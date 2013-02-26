package fr.ornicare.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.ornicare.models.mobs.MobModel;
import fr.ornicare.util.RandomizerMobsMap;

public abstract class MobStorage {
	
	//Creeper store
	public static RandomizerMobsMap CREEPERS = new RandomizerMobsMap();
	
	//Skeleton store
	public static RandomizerMobsMap SKELETONS = new RandomizerMobsMap();

	//Zombies store
	public static RandomizerMobsMap ZOMBIES = new RandomizerMobsMap();
	
	//Mob UUID/spawn on death correlation
	public static Map<UUID,MobModel> SPAWNONDEATH = new HashMap<UUID,MobModel>();
}
