package fr.ornicare.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_4_R1.EntityCreeper;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
			MobStorage.SPAWNONDEATH.put(creep.getUniqueId(), creepMod);
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
		        double[] spawnondeath = MobStorage.SPAWNONDEATH.get(creep.getUniqueId()).getSpawnOnDeath();
		        if(Math.random()< spawnondeath[0]) {
		        	for(int i = 0; i< (int)(spawnondeath[1])*Math.random()+1;i++) {
		        		EntityCreeper childCreep = spawnAtTheSamePlace(creep);
		        		childCreep.setHealth((int)(childCreep.getHealth()*spawnondeath[0]));
		        	}
		        }
	        }
		}
	}	 
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityExplodeEvent(final EntityExplodeEvent e) {
		if (e.getEntity()!=null && (e==null?null:e.getEntityType()) == EntityType.CREEPER) {
			Creeper creep = (Creeper)e.getEntity();
			List<String> explosionTypes;
			
			double explMult = 1;
			if(MobStorage.SPAWNONDEATH.containsKey(creep.getUniqueId())) {
				explosionTypes = ((CreeperModel)MobStorage.SPAWNONDEATH.get(creep.getUniqueId())).getExplosionTypes();
				explMult = ((CreeperModel)MobStorage.SPAWNONDEATH.get(creep.getUniqueId())).getExplosionRadiusMultiplier();
				
			}
			else {
				explosionTypes = new ArrayList<String>();
			}
			
			
			for(PotionEffect pEffect : creep.getActivePotionEffects()) {
				//if a creeper is poisoned, add poison effect
				if(pEffect.getType().getName().equals(PotionEffectType.POISON.getName())) {
					explosionTypes.add("poisonous");
				}
				//if a creeper is weak, add poison effect (base config)
				if(pEffect.getType().getName().equals(PotionEffectType.WEAKNESS.getName())) {
					explosionTypes.add("poisonous");
				}
				//if a creeper is fire resistant, add fire effect
				if(pEffect.getType().getName().equals(PotionEffectType.FIRE_RESISTANCE.getName())) {
					explosionTypes.add("fireentity");
				}
			}
			
			if(creep.getFireTicks()>0) {
				explosionTypes.add("fireentity");
			}
			
			for(String exp : explosionTypes) {
				parseExplosionAndCreateIt(creep,exp,explMult);
			}
			
			e.setCancelled(true);
		}
	}

	private void parseExplosionAndCreateIt(Creeper creep, String exp, double radiusmult) {
		Location location = creep.getLocation();
		radiusmult = (radiusmult==-1?1:radiusmult);
		
		//Physical
		if(exp.equals("destroyblocs")) ((CraftWorld)creep.getWorld()).createExplosion(location.getX(), location.getY(), location.getZ(), (float) ((creep.isPowered()?6:3)*radiusmult), false);
		
		//fire
		if(exp.equals("fire"))((CraftWorld)creep.getWorld()).getHandle().createExplosion(null, location.getX(), location.getY(), location.getZ(), (float) ((creep.isPowered()?6:3)*radiusmult), true, false);
		
		//poisonous
		if(exp.equals("poisonous")) {
			int power = creep.isPowered()?1:0;
			double radius = 10*(power+1)*radiusmult;
			for(Entity ent : creep.getNearbyEntities(radius,radius,radius)) {
				if(ent instanceof LivingEntity) {
					LivingEntity enti = (LivingEntity) ent;
					enti.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (creep.isPowered()?6:3)*100, power));
				}
			}
			//Visual effect
			for(int i = 0;i<10;i++) creep.getWorld().playEffect(creep.getLocation().add(new Vector(Math.random(), Math.random(), Math.random())), Effect.POTION_BREAK, 20, 50);
		}
		
		//fireentity
		if(exp.equals("fireentity")) {
			int power = creep.isPowered()?1:0;
			double radius = 10*(power+1)*radiusmult;
			for(Entity ent : creep.getNearbyEntities(radius,radius,radius)) {
				ent.setFireTicks((creep.isPowered()?6:3)*100);
			}
			//Visual effect (http://wiki.sk89q.com/wiki/CraftBook/MC0210       http://www.lb-stuff.com/Minecraft/PotionDataValues1.9pre3.txt)
			for(int i = 0;i<10;i++) creep.getWorld().playEffect(creep.getLocation().add(new Vector(Math.random(), Math.random(), Math.random())), Effect.POTION_BREAK, 19, 50);
		}
		
	}
}
