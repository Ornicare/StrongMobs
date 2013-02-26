package fr.ornicare.models;

import org.bukkit.potion.PotionEffectType;


/**
 * Storage class for a potion effect
 * 
 * @author Ornicare
 *
 */
public class PotionEffectModel {
	private int potionEffectLevel = 0;
	private PotionEffectType potionEffect;
	private double probability = 1.0;
	
	public int getPotionEffectLevel() {
		return potionEffectLevel;
	}

	public PotionEffectType getPotionEffect() {
		return potionEffect;
	}
	
	/**
	 * Create a new potion effect by using it's name
	 * 
	 * @param pName
	 * @param pLevel
	 */
	public PotionEffectModel(String pName, int pLevel, double eProbability) {
		this.potionEffectLevel = pLevel>0?pLevel:0;
		this.potionEffect = PotionEffectType.getByName(pName);
		this.probability = eProbability;
	}
	
	/**
	 * Create a new potion effect by using it's id
	 * 
	 * @param pId
	 * @param pLevel
	 * @param eProbability 
	 */
	public PotionEffectModel(int pId, int pLevel, double eProbability) {
		this.potionEffectLevel = pLevel>0?pLevel:0;
		this.potionEffect = PotionEffectType.getById(pId);
		this.probability = eProbability;
	}

	public double getProbability() {
		return probability;
	}

}
