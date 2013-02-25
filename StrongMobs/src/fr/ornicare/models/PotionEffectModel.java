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
	
	public int getEnchantmentLevel() {
		return potionEffectLevel;
	}

	public PotionEffectType getEnchantment() {
		return potionEffect;
	}
	
	/**
	 * Create a new potion effect by using it's name
	 * 
	 * @param pName
	 * @param pLevel
	 */
	public PotionEffectModel(String pName, int pLevel) {
		this.potionEffectLevel = pLevel>0?pLevel:0;
		this.potionEffect = PotionEffectType.getByName(pName);
	}
	
	/**
	 * Create a new potion effect by using it's id
	 * 
	 * @param pId
	 * @param pLevel
	 */
	public PotionEffectModel(int pId, int pLevel) {
		this.potionEffectLevel = pLevel>0?pLevel:0;
		this.potionEffect = PotionEffectType.getById(pId);
	}

}
