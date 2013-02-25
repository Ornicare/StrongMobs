package fr.ornicare.models;

import net.minecraft.server.v1_4_R1.EntityZombie;
import net.minecraft.server.v1_4_R1.MobEffect;

public class ZombieModel extends MobModel {
	
	/**
	 * Chance to be a mini zombie.
	 */
	private double childChance;
	
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
		
		//It is a child ?
		if(Math.random()<childChance) zombie.setBaby(true);
		
		return zombie;
	}
	
	public void setChildChance(double cchance) {
		this.childChance = cchance;	
	}

	public double getChildChance() {
		return childChance;
	}
}
