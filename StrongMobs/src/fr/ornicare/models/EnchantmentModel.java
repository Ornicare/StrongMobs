package fr.ornicare.models;

import org.bukkit.enchantments.Enchantment;

/**
 * Storage class for an enchantment
 * 
 * @author Ornicare
 *
 */
public class EnchantmentModel {
	private int enchantmentLevel = 0;
	private Enchantment enchantment;
	private double probability = 1.0;
	
	public int getEnchantmentLevel() {
		return enchantmentLevel;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}
	
	/**
	 * Create a new enchantment by using it's name
	 * 
	 * @param eName enchantment name
	 * @param eLevel enchantment id
	 * @param eProbability 
	 */
	public EnchantmentModel(String eName, int eLevel, double eProbability) {
		this.enchantmentLevel = eLevel>0?eLevel:0;
		this.enchantment = Enchantment.getByName(eName);
		this.probability = eProbability;
	}
	
	/**
	 * Create a new enchantment by using it's id
	 * 
	 * @param eId enchantment name
	 * @param eLevel enchantment level
	 */
	public EnchantmentModel(int eId, int eLevel, double eProbability) {
		this.enchantmentLevel = eLevel>0?eLevel:0;
		this.enchantment = Enchantment.getById(eId);
		this.probability = eProbability;
	}

	public double getProbability() {
		return probability;
	}

}
