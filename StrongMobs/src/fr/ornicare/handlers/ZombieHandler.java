package fr.ornicare.handlers;

import net.minecraft.server.v1_4_R1.EntityZombie;

import org.bukkit.craftbukkit.v1_4_R1.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import fr.ornicare.mobs.MobStorage;
import fr.ornicare.models.mobs.ZombieModel;

/*import net.minecraft.server.v1_4_R1.Enchantment;
import net.minecraft.server.v1_4_R1.EntityWitch;
import net.minecraft.server.v1_4_R1.EntityZombie;
import net.minecraft.server.v1_4_R1.Item;
import net.minecraft.server.v1_4_R1.MobEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;*/


public class ZombieHandler implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) throws Exception {
		if (e.getEntityType() == EntityType.ZOMBIE) {


            Zombie zomb = (Zombie)e.getEntity();
	        CraftZombie zombC = (CraftZombie)zomb;
	        EntityZombie zombMC = zombC.getHandle();
     
            
	        zombMC = ((ZombieModel)MobStorage.ZOMBIES.getRandomMob()).getMob(zombMC); 
            
		}
	}

	
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) throws Exception {
		
		
			 
		    if (e.getEntityType() == EntityType.ZOMBIE) {
		        //Next, we need to get the Minecraft version of the zombie
		        Zombie zomb = (Zombie)e.getEntity();
		        CraftZombie zombC = (CraftZombie)zomb;
		        EntityZombie zombMC = zombC.getHandle();
		        

				//Okay, so let's make all zombies have a diamond sword and full diamond armor
		        //item slots: 0=sword, 4=boots, 3=legplate, 2=chestplate, 1=helmet
		        
		        
		       
		        
		        //10% d'�tre mimi
		        if(Math.random()<0.1) zombMC.setBaby(true);
		        
		        //spawn a witche !
		        if(Math.random()<0.05)  {
		        	Entity entity1 = e.getEntity();
		            Location location1 = entity1.getLocation();
		            World world1 = location1.getWorld();
		     
		            net.minecraft.server.v1_4_R1.World mcWorld1 = ((CraftWorld) world1).getHandle();
		     
	                EntityWitch bloodMoonEntityZombie1 = new EntityWitch(mcWorld1);
	     
	                bloodMoonEntityZombie1.setPosition(location1.getX(), location1.getY(), location1.getZ());
	     
	                mcWorld1.addEntity(bloodMoonEntityZombie1, SpawnReason.CUSTOM);
	  
		        }
		        

		        //Potions effects sur le mob
		        //100% des zombies vont plus vite et sautent plus haut.
		        if(!zomb.isBaby()) {
		        	zombMC.addEffect(new MobEffect(PotionEffectType.SPEED.getId(),2000000000,1));  //speed level 2 sauf si b�b�
		        }
		        else {
		        	zombMC.addEffect(new MobEffect(PotionEffectType.SPEED.getId(),2000000000,0));  //speed level 2 sauf si b�b�
		        }

				zombMC.addEffect(new MobEffect(PotionEffectType.JUMP.getId(),2000000000,1));  //jump level 2
				//20% pour tous les zombies de r�sister au feu.
				if(Math.random()<0.2) zombMC.addEffect(new MobEffect(PotionEffectType.FIRE_RESISTANCE.getId(),2000000000,0)); 

		        //50% des zombies poss�dent une �p�e en fer.
				if(Math.random()<0.5) {
					zombMC.setEquipment(0, new net.minecraft.server.v1_4_R1.ItemStack(net.minecraft.server.v1_4_R1.Item.IRON_SWORD));
					//Enchantements de l'arme
					
					//50% des �p�es tapent plus fort. suaf si b�b�
					if(!zomb.isBaby()) if(Math.random()<0.5) zombMC.getEquipment(0).addEnchantment(Enchantment.DAMAGE_ALL, randomize(5));
					//20% des �p�es sont enflamm�es.
					if(Math.random()<0.2) zombMC.getEquipment(0).addEnchantment(Enchantment.FIRE_ASPECT, randomize(2));
					//40% des �p�es ont du kb.
					if(Math.random()<0.4) zombMC.getEquipment(0).addEnchantment(Enchantment.KNOCKBACK, randomize(2));
					
				}
				
				//10% d'avoir chaque pi�ce d'armure.
				Item[] stuff = {net.minecraft.server.v1_4_R1.Item.IRON_BOOTS,net.minecraft.server.v1_4_R1.Item.IRON_LEGGINGS,net.minecraft.server.v1_4_R1.Item.IRON_CHESTPLATE,net.minecraft.server.v1_4_R1.Item.IRON_HELMET};
				for(int i = 1;i<5;i++) {
					if(Math.random()<0.1) {
						zombMC.setEquipment(i, new net.minecraft.server.v1_4_R1.ItemStack(stuff[i-1]));
						//Enchantements de l'armure
						
						//50% des armures ont protection
						if(Math.random()<0.5) zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, randomize(4));
						//30% des armures  ont thorns
						if(Math.random()<0.3) zombMC.getEquipment(i).addEnchantment(Enchantment.THORNS, randomize(3));
					}
				}
				
				//0.1% d'�tre un superzombie
				if(Math.random()<0.01) {
					//Location zLoc = zomb.getLocation();
					//Bukkit.broadcastMessage(ChatColor.RED + "Un superZombie est n� ! [x="+zLoc.getX()+";"+zLoc.getY()+";"+zLoc.getZ()+"]");
					
					zombMC.addEffect(new MobEffect(PotionEffectType.FIRE_RESISTANCE.getId(),2000000000,0)); 
					zombMC.addEffect(new MobEffect(PotionEffectType.SPEED.getId(),2000000000,3)); 
					
					zombMC.setEquipment(0, new net.minecraft.server.v1_4_R1.ItemStack(net.minecraft.server.v1_4_R1.Item.DIAMOND_SWORD));
					zombMC.getEquipment(0).addEnchantment(Enchantment.DAMAGE_ALL, 6);
					zombMC.getEquipment(0).addEnchantment(Enchantment.FIRE_ASPECT, 3);
					zombMC.getEquipment(0).addEnchantment(Enchantment.KNOCKBACK, 3);
					
					
					Item[] stuffUltra = {net.minecraft.server.v1_4_R1.Item.DIAMOND_BOOTS,net.minecraft.server.v1_4_R1.Item.DIAMOND_LEGGINGS,net.minecraft.server.v1_4_R1.Item.DIAMOND_CHESTPLATE,net.minecraft.server.v1_4_R1.Item.DIAMOND_HELMET};
					for(int i = 1;i<5;i++) {
						zombMC.setEquipment(i, new net.minecraft.server.v1_4_R1.ItemStack(stuffUltra[i-1]));
						//Enchantements de l'armure
						zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, randomize(5));
						zombMC.getEquipment(i).addEnchantment(Enchantment.THORNS, randomize(3));
						zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, randomize(5));
						if(i==1) zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_FALL, randomize(5));
						zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_FIRE, randomize(5));
						zombMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_PROJECTILE, randomize(5));
						zombMC.getEquipment(i).addEnchantment(Enchantment.DURABILITY, randomize(5));
					}
					zomb.setMaxHealth(100);
					zomb.setHealth(100);
					for(int i = 0;i< zombMC.dropChances.length;i++) zombMC.dropChances[i] = 0.01f;
					
					
					
				}
				
				EntityZombie temp = new EntityZombie(null);
				
				
				//multispawn
				if(Math.random() < 0.3 && zomb.getMaxHealth()==temp.getMaxHealth()) {
		        	for(int i = 0; i< 2;i++) {
		        		Entity entity = e.getEntity();
			            Location location = entity.getLocation();
			            World world = location.getWorld();
			     
			            net.minecraft.server.v1_4_R1.World mcWorld = ((CraftWorld) world).getHandle();
			     
		                EntityZombie bloodMoonEntityZombie = new EntityZombie(mcWorld);
		                location.setX(location.getX()+(Math.random()-0.5)*0.5);
		                location.setZ(location.getZ()+(Math.random()-0.5)*0.5);
		                if(world.getBlockAt(location).getLightLevel()<7) {
		                	bloodMoonEntityZombie.setPosition(location.getX(), location.getY(), location.getZ());
		                	mcWorld.addEntity(bloodMoonEntityZombie, SpawnReason.CUSTOM);
		  	                
		  	                bloodMoonEntityZombie.setHealth(zomb.getMaxHealth());
		                }
		     
		              
		        	}
		        }
				
				
		    }
		
		   */
	
	
	
}
