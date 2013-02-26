package fr.ornicare.models.mobs;

import fr.ornicare.models.PotionEffectModel;
import net.minecraft.server.v1_4_R1.EntityCreeper;
import net.minecraft.server.v1_4_R1.MobEffect;

public class CreeperModel extends MobModel {

	private double electricChance = 0.0;
	private boolean[] explosionTypes = {false,false,false,false};
	
	public CreeperModel() {
		super();
	}
	
	public EntityCreeper getMob(EntityCreeper creeper) {
		//Don't set the equipment (Creeper doesn't have arms.
		
		//set health
		if(health>0) creeper.setHealth(this.getHealth());
		
		//Is it electric ?
		if(Math.random()<this.getElectricChance()) creeper.setPowered(true);
		
		//Apply potion effect
		for(PotionEffectModel pE : getPotionEffects()) {
			if(pE.getPotionEffectLevel()>0 && (Math.random()<pE.getProbability())) creeper.addEffect(new MobEffect(pE.getPotionEffect().getId(),2000000000,pE.getPotionEffectLevel()-1));
		}
		
		return creeper;
	}

	public double getElectricChance() {
		return electricChance;
	}

	public void setElectricChance(double electricChance) {
		this.electricChance = electricChance;
	}

	public boolean[] getExplosionTypes() {
		return explosionTypes;
	}

	public void setExplosionTypes(boolean[] explosionTypes) {
		this.explosionTypes = explosionTypes;
	}
}
