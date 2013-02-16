package fr.ornicare;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PlayerHandler implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(final EntityDamageEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
	        Player player = (Player)e.getEntity();
	        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1)); //10 sec de speed 2
	        //Bukkit.broadcastMessage(player.getLastDamage()+" " +EntityType.SKELETON+" "+player.get);
	        
	        //if(e.getCause().equals(DamageCause.PROJECTILE)) {
	        	//player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 1));
	        //}
	        //CraftZombie zombC = (CraftZombie)zomb;
	        //EntityZombie zombMC = zombC.getHandle();
	        //zomb.setFireTicks(100000);
	        
		}
	}
	
}