package fr.ornicare.models.mobs;

import fr.ornicare.models.PotionEffectModel;
import net.minecraft.server.v1_4_R1.EntitySkeleton;
import net.minecraft.server.v1_4_R1.MobEffect;

public class SkeletonModel extends MobModel {

	public SkeletonModel() {
		super();
	}
	
	public EntitySkeleton getMob(EntitySkeleton skeleton) {
		//Set the equipment
		skeleton = (EntitySkeleton) super.getMob(skeleton);
		
		//set health
		if(health>0) skeleton.setHealth(this.getHealth());
		
		//Apply potion effect
		for(PotionEffectModel pE : getPotionEffects()) {
			if(pE.getPotionEffectLevel()>0 && (Math.random()<pE.getProbability())) skeleton.addEffect(new MobEffect(pE.getPotionEffect().getId(),2000000000,pE.getPotionEffectLevel()-1));
		}
		
		return skeleton;
	}
}
