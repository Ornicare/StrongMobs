package fr.ornicare;

import net.minecraft.server.v1_4_R1.EntityCreeper;
import net.minecraft.server.v1_4_R1.MobEffect;

import org.bukkit.craftbukkit.v1_4_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;


public class CreeperHandler implements Listener {
	

	//First of all, listen to the CreatureSpawnEvent
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) {
	//Now, check if it's a Zombie for example
	    if (e.getEntityType() == EntityType.CREEPER) {
	        //Next, we need to get the Minecraft version of the zombie
	        Creeper creeper = (Creeper)e.getEntity();
	        CraftCreeper creeperC = (CraftCreeper)creeper;
	        EntityCreeper creeperMC = creeperC.getHandle();

			//Okay, so let's make all zombies have a diamond sword and full diamond armor
	        //item slots: 0=sword, 4=boots, 3=legplate, 2=chestplate, 1=helmet
	        
	        


	        //Potions effects sur le mob
	        //100% des creepers sautent plus haut.
			creeperMC.addEffect(new MobEffect(PotionEffectType.JUMP.getId(),2000000000,1));  //jump level 2
			//10% pour tous les creepers d'être invisible ET électrque (faut pas être trop vicieux quoi).
			if(Math.random()<0.1){
				creeperMC.addEffect(new MobEffect(PotionEffectType.INVISIBILITY.getId(),2000000000,0)); 
				creeper.setPowered(true);
			}
			//50% pour tous les creepers de se déplacer plus vite.
			if(Math.random()<0.5) creeperMC.addEffect(new MobEffect(PotionEffectType.SPEED.getId(),2000000000,0)); 

	    }	
	}
	
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			Entity entity = e.getEntity();
            Location location = entity.getLocation();
            World world = location.getWorld();
            for(int i = 0;i<100;i++)  world.createExplosion(location, i);
           

	        
	        //CraftZombie zombC = (CraftZombie)zomb;
	        //EntityZombie zombMC = zombC.getHandle();
	        //zomb.setFireTicks(100000);
	        
		}
	}*/
}