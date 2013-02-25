package fr.ornicare.models;

import net.minecraft.server.v1_4_R1.EntitySkeleton;
import net.minecraft.server.v1_4_R1.MobEffect;

public class SkeletonModel extends MobModel {

	public SkeletonModel() {
		super();
	}
	
	public EntitySkeleton getMob(EntitySkeleton skeleton) {
		//Set the weapon
		skeleton = (EntitySkeleton) super.getMob(skeleton);
		
		//set health
		if(health>0) skeleton.setHealth(this.getHealth());
		
		//Apply potion effect
		for(PotionEffectModel pE : getPotionEffects()) {
			skeleton.addEffect(new MobEffect(pE.getEnchantment().getId(),2000000000,pE.getEnchantmentLevel()));
		}
		
		return skeleton;
	}
}
