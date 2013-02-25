package fr.ornicare.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.ornicare.util.RandomizerMobsMap;

public abstract class MobStorage {
	
	//Skeleton store
	public static RandomizerMobsMap SKELETONS = new RandomizerMobsMap();

	//Zombies store
	public static RandomizerMobsMap ZOMBIES = new RandomizerMobsMap();
	
	//Mob UUID/spawn on death correlation
	public static Map<UUID,double[]> SPAWNONDEATH = new HashMap<UUID,double[]>();
}
