package fr.ornicare.handlers;

import net.minecraft.server.v1_4_R1.EntityCreeper;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.ornicare.models.mobs.CreeperModel;
import fr.ornicare.storage.MobStorage;



public class CreeperHandler implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) throws Exception {
		if (e.getEntityType() == EntityType.CREEPER) {


            Creeper creep = (Creeper)e.getEntity();
	        CraftCreeper creepC = (CraftCreeper)creep;
	        EntityCreeper creepMC = creepC.getHandle();
     
            //get the a random zombie model
	        CreeperModel creepMod = (CreeperModel)MobStorage.CREEPERS.getRandomMob();
	        creepMC = creepMod.getMob(creepMC); 
	        
	        //Use the spawnmultiplicator
	        if(Math.random() < creepMod.getSpawnmultiplicator()) {
	            spawnAtTheSamePlace(creep);
	        }
	        
	        //store spawnondeath
			MobStorage.SPAWNONDEATH.put(creep.getUniqueId(), creepMod.getSpawnOnDeath());
		}
	}
	
	private EntityCreeper spawnAtTheSamePlace(Creeper creep) {
		Location location = creep.getLocation();
        World world = location.getWorld();
        net.minecraft.server.v1_4_R1.World mcWorld = ((CraftWorld) world).getHandle();

        EntityCreeper childSkem = new EntityCreeper(mcWorld);
        childSkem.setPosition(location.getX(), location.getY(), location.getZ());
    	mcWorld.addEntity(childSkem, SpawnReason.CUSTOM); 
    	return childSkem;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			Creeper creep = (Creeper)e.getEntity();
	        
	        if(MobStorage.SPAWNONDEATH.containsKey(creep.getUniqueId())) {
	        	//use spawn on death
		        double[] spawnondeath = MobStorage.SPAWNONDEATH.get(creep.getUniqueId());
		        if(Math.random()< spawnondeath[0]) {
		        	for(int i = 0; i< (int)(spawnondeath[1])*Math.random()+1;i++) {
		        		EntityCreeper childCreep = spawnAtTheSamePlace(creep);
		        		childCreep.setHealth((int)(childCreep.getHealth()*spawnondeath[0]));
		        	}
		        }
	        }
		}
	}	
}
