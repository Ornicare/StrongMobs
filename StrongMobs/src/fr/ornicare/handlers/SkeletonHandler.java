package fr.ornicare.handlers;

import net.minecraft.server.v1_4_R1.EntitySkeleton;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftSkeleton;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.ornicare.models.SkeletonModel;
import fr.ornicare.storage.MobStorage;



public class SkeletonHandler implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) throws Exception {
		if (e.getEntityType() == EntityType.SKELETON) {


            Skeleton skel = (Skeleton)e.getEntity();
	        CraftSkeleton skelC = (CraftSkeleton)skel;
	        EntitySkeleton skelMC = skelC.getHandle();
     
            //get the a random zombie model
	        SkeletonModel skelMod = (SkeletonModel)MobStorage.SKELETONS.getRandomMob();
	        skelMC = skelMod.getMob(skelMC); 
	        
	        //Use the spawnmultiplicator
	        if(Math.random() < skelMod.getSpawnmultiplicator()) {
	            spawnAtTheSamePlace(skel);
	        }
	        
	        //store spawnondeath
			MobStorage.SPAWNONDEATH.put(skel.getUniqueId(), skelMod.getSpawnOnDeath());
		}
	}
	
	private EntitySkeleton spawnAtTheSamePlace(Skeleton skel) {
		Location location = skel.getLocation();
        World world = location.getWorld();
        net.minecraft.server.v1_4_R1.World mcWorld = ((CraftWorld) world).getHandle();

        EntitySkeleton childSkem = new EntitySkeleton(mcWorld);
        childSkem.setPosition(location.getX(), location.getY(), location.getZ());
    	mcWorld.addEntity(childSkem, SpawnReason.CUSTOM); 
    	return childSkem;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent e) {
		if (e.getEntityType() == EntityType.SKELETON) {
			Skeleton skel = (Skeleton)e.getEntity();
	        
	        if(MobStorage.SPAWNONDEATH.containsKey(skel.getUniqueId())) {
	        	//use spawn on death
		        double[] spawnondeath = MobStorage.SPAWNONDEATH.get(skel.getUniqueId());
		        if(Math.random()< spawnondeath[0]) {
		        	for(int i = 0; i< (int)(spawnondeath[1])*Math.random()+1;i++) {
		        		EntitySkeleton childSkel = spawnAtTheSamePlace(skel);
		        		childSkel.setHealth((int)(childSkel.getHealth()*spawnondeath[0]));
		        	}
		        }
	        }
		}
	}	
}
