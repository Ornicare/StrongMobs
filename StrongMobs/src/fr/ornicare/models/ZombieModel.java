package fr.ornicare.models;

import net.minecraft.server.v1_4_R1.EntityZombie;
import net.minecraft.server.v1_4_R1.MobEffect;

public class ZombieModel extends MobModel {
	
	public ZombieModel() {
		super();
	}
	
	public EntityZombie getMob(EntityZombie zombie) {
		//Set the weapon
		zombie = (EntityZombie) super.getMob(zombie);
		
		//set health
		if(health>0) zombie.setHealth(this.getHealth());
		
		//Apply potion effect
		for(PotionEffectModel pE : getPotionEffects()) {
			zombie.addEffect(new MobEffect(pE.getEnchantment().getId(),2000000000,pE.getEnchantmentLevel()));
		}
		
		return zombie;
	}
}
