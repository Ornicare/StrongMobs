package fr.ornicare.handlers;

import net.minecraft.server.v1_4_R1.EntityZombie;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.ornicare.loader.StrongMobLoader;
import fr.ornicare.models.mobs.ZombieModel;
import fr.ornicare.storage.MobStorage;



public class ZombieHandler implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) throws Exception {
		
		/*
		 * Global limit
		 */
		int totalMobs = 0;
		
		for(World w : Bukkit.getWorlds()) {
			totalMobs += w.getEntitiesByClass(Monster.class).size();
		}
		
		if(totalMobs>=StrongMobLoader.MAXMOBS) {
			e.setCancelled(true);
			return;
		}
		
		if (e.getEntityType() == EntityType.ZOMBIE && e.getSpawnReason()!=SpawnReason.CUSTOM) {


            Zombie zomb = (Zombie)e.getEntity();
	        CraftZombie zombC = (CraftZombie)zomb;
	        EntityZombie zombMC = zombC.getHandle();
     
            //get the a random zombie model
	        ZombieModel zombieMod = (ZombieModel)MobStorage.ZOMBIES.getRandomMob();
	        zombMC = zombieMod.getMob(zombMC); 
	        
	        //Use the spawnmultiplicator
	        if(Math.random() < zombieMod.getSpawnmultiplicator()) {
	            spawnAtTheSamePlace(zomb);
	        }
	        
	        //store spawnondeath
			MobStorage.SPAWNONDEATH.put(zomb.getUniqueId(), zombieMod);
		}
	}
	
	private EntityZombie spawnAtTheSamePlace(Zombie zomb) {
		Location location = zomb.getLocation();
        World world = location.getWorld();
        net.minecraft.server.v1_4_R1.World mcWorld = ((CraftWorld) world).getHandle();

        EntityZombie childZombie = new EntityZombie(mcWorld);
        childZombie.setPosition(location.getX(), location.getY(), location.getZ());
    	mcWorld.addEntity(childZombie, SpawnReason.NATURAL); 
    	return childZombie;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent e) {
		if (e.getEntityType() == EntityType.ZOMBIE) {
	        Zombie zomb = (Zombie)e.getEntity();
	        
	        if(MobStorage.SPAWNONDEATH.containsKey(zomb.getUniqueId())) {
	        	//use spawn on death
		        double[] spawnondeath = MobStorage.SPAWNONDEATH.get(zomb.getUniqueId()).getSpawnOnDeath();
		        if(Math.random()< spawnondeath[0]) {
		        	for(int i = 0; i< (int)(spawnondeath[1])*Math.random()+1;i++) {
		        		EntityZombie childZombie = spawnAtTheSamePlace(zomb);
		        		childZombie.setHealth((int)(childZombie.getHealth()*spawnondeath[0]));
		        	}
		        }
	        }
		}
	}	
}
