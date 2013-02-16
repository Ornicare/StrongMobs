package fr.ornicare;

import net.minecraft.server.v1_4_R1.Enchantment;
import net.minecraft.server.v1_4_R1.EntitySkeleton;
import net.minecraft.server.v1_4_R1.Item;
import net.minecraft.server.v1_4_R1.MobEffect;

import org.bukkit.craftbukkit.v1_4_R1.entity.CraftSkeleton;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;


public class SkeletonHandler implements Listener {
	//First of all, listen to the CreatureSpawnEvent
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent e) {
	//Now, check if it's a Zombie for example
	    if (e.getEntityType() == EntityType.SKELETON) {
	        //Next, we need to get the Minecraft version of the zombie
	        Skeleton skeleton = (Skeleton)e.getEntity();
	        CraftSkeleton skeletonC = (CraftSkeleton)skeleton;
	        EntitySkeleton skeletonMC = skeletonC.getHandle();

			//Okay, so let's make all zombies have a diamond sword and full diamond armor
	        //item slots: 0=sword, 4=boots, 3=legplate, 2=chestplate, 1=helmet
	        
	        


	        //Potions effects sur le mob
	        //100% des skeletons sautent plus haut.
			skeletonMC.addEffect(new MobEffect(PotionEffectType.JUMP.getId(),2000000000,1));  //jump level 2
			//20% pour tous les skeletons de résister au feu.
			if(Math.random()<0.2) skeletonMC.addEffect(new MobEffect(PotionEffectType.FIRE_RESISTANCE.getId(),2000000000,0)); 
		

	        //20% des skeletons possèdent une hache en fer.
			if(Math.random()<0.2) {
				//100% pour tous les skeletons  avec hache d'avoir speed
				skeletonMC.addEffect(new MobEffect(PotionEffectType.SPEED.getId(),2000000000,2)); 
				skeletonMC.setEquipment(0, new net.minecraft.server.v1_4_R1.ItemStack(net.minecraft.server.v1_4_R1.Item.IRON_AXE));
				//Enchantements de l'arme
				
				//100% des haches tapent plus fort et on du kb
				skeletonMC.getEquipment(0).addEnchantment(Enchantment.DAMAGE_ALL, randomize(5));
				skeletonMC.getEquipment(0).addEnchantment(Enchantment.KNOCKBACK, randomize(2));
				skeletonMC.setHealth(40);
				//30% des haches sont enflammées.
				if(Math.random()<0.3) skeletonMC.getEquipment(0).addEnchantment(Enchantment.FIRE_ASPECT, 2);
				
			}
			else {
				skeletonMC.setEquipment(0, new net.minecraft.server.v1_4_R1.ItemStack(net.minecraft.server.v1_4_R1.Item.BOW));
				//70% des arcs tapent plus fort.
				if(Math.random()<0.7) skeletonMC.getEquipment(0).addEnchantment(Enchantment.ARROW_DAMAGE,randomize(5));
				//80% des arcs ont du kb.
				if(Math.random()<0.4) skeletonMC.getEquipment(0).addEnchantment(Enchantment.ARROW_KNOCKBACK, randomize(2));
				//20% des arcs brulent
				if(Math.random()<0.2) skeletonMC.getEquipment(0).addEnchantment(Enchantment.ARROW_FIRE, randomize(2));
			}
			
			
			
			
			//10% d'avoir chaque pièce d'armure.
			Item[] stuff = {net.minecraft.server.v1_4_R1.Item.CHAINMAIL_BOOTS,net.minecraft.server.v1_4_R1.Item.CHAINMAIL_LEGGINGS,net.minecraft.server.v1_4_R1.Item.CHAINMAIL_CHESTPLATE,net.minecraft.server.v1_4_R1.Item.CHAINMAIL_HELMET};
			for(int i = 1;i<5;i++) {
				if(Math.random()<0.1) {
					skeletonMC.setEquipment(i, new net.minecraft.server.v1_4_R1.ItemStack(stuff[i-1]));
					//Enchantements de l'armure
					
					//50% des armures ont protection
					if(Math.random()<0.5) skeletonMC.getEquipment(i).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, randomize(4));
					//30% des armures  ont thorns
					if(Math.random()<0.3) skeletonMC.getEquipment(i).addEnchantment(Enchantment.THORNS, randomize(3));
				}
			}
	    }
	}
	
	private int randomize(int i) { return (int) (i*Math.random()+1);}
	
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(final EntityDamageEvent e) {
		if (e.getEntityType() == EntityType.ZOMBIE) {
	        Zombie zomb = (Zombie)e.getEntity();
	        //CraftZombie zombC = (CraftZombie)zomb;
	        //EntityZombie zombMC = zombC.getHandle();
	        //zomb.setFireTicks(100000);
	        List<org.bukkit.entity.Entity> entities  = zomb.getNearbyEntities(zomb.getLocation().getX(), zomb.getLocation().getY(), zomb.getLocation().getZ());
	        
	        for(Object entitie: entities) {
	        	if (entitie instanceof Zombie) {
	        		Zombie zombie = ((Zombie) entitie);
					zombie.teleport(zomb);
	        		//((Player) entitie).setFireTicks(100000);
	        	}
	        }
		}
	}*/
	

	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(final ProjectileHitEvent e) {
		if (e.getEntityType() == EntityType.ARROW) {
	        Arrow arrow = (Arrow)e.getEntity();
	        //CraftArrow arrowC = (CraftArrow)arrow;
	        //EntityArrow arrowMC = arrowC.getHandle();
            Location location = arrow.getLocation();
            World world = location.getWorld();
	        if(arrow.getShooter().getType()==EntityType.SKELETON) {
	        	world.createExplosion(location, 0.5F);
	        }
		}
	}*/
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent e) {
		if (e.getEntityType() == EntityType.ZOMBIE) {
	        Zombie zomb = (Zombie)e.getEntity();
	        CraftZombie zombC = (CraftZombie)zomb;
	        EntityZombie zombMC = zombC.getHandle();
	        
	        //Si c'est pas un bébé 40% de chance de renaitre !
	        if(!zomb.isBaby()) {
	        	zomb.setBaby(true);
	        	zomb.setHealth(zomb.getMaxHealth());
	        	zombMC.setInvisible(false);
	        }
		}
	}*/
}