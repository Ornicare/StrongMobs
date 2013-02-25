package fr.ornicare.models;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_4_R1.EntityLiving;


/**
 * Storage class for a generic mob
 * 
 * @author Ornicare
 *
 */
public abstract class MobModel {
	
	/**
	 * Create a new mobModel
	 */
	public MobModel() {
		this.items = new ItemStackModel[5];
		for(int i = 0;i<5;i++) items[i] = new ItemStackModel();
		
		potionEffects = new ArrayList<PotionEffectModel>();
	}
	
	/**
	 * Potion effects to apply to the mob
	 */
	private List<PotionEffectModel> potionEffects;
	
	/**
	 * Mob's items
	 */
	protected ItemStackModel[] items;
	
	/**
	 * Mob's health.
	 * -1 to non-set
	 */
	protected int health = -1;
	
	/**
	 * Mob's spawn multiplicator : chance to a new mob to spawn when a mob of this type spawn
	 */
	protected double spawnmultiplicator = 0;
	
	/**
	 * When the mob die, it has a probability of probability to rebirth in maxnumber mob with healthmultiplicator
	 * probability,  maxnumber, healthmultiplicator
	 */
	protected double[] spawnOnDeath = {0.0, 0.0, 0.0};
	
	public double[] getSpawnOnDeath() {
		return spawnOnDeath;
	}

	public void setSpawnOnDeath(double... spawnOnDeath) {
		if(spawnOnDeath.length>2) this.spawnOnDeath = spawnOnDeath;
	}

	public double getSpawnmultiplicator() {
		return spawnmultiplicator;
	}

	public void setSpawnmultiplicator(double spawnmultiplicator) {
		this.spawnmultiplicator = spawnmultiplicator;
	}
	
	public List<PotionEffectModel> getPotionEffects() {
		return potionEffects;
	}

	public void addPotionEffect(PotionEffectModel potionEffect) {
		this.potionEffects.add(potionEffect);
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Generate the item at the position <code>id</code> by using it's name
	 * 
	 * @param id item's position (0=weapon,1=boots,2=leggings,3=chestplate,4=helmet)
	 * @param name item's name (exp : DIAMOND_SWORD)
	 * @param probability When a new mob is created, the chance to the item to be selected.
	 * @param dropChance item's drop chance
	 */
	public void setItem(int id, String name, double probability, float dropChance) {
		if(id<5 && id >= 0) {
			this.items[id].setItem(name);
			this.items[id].setProbability(probability);
			this.items[id].setDropChance(dropChance);
		}
	}
	
	/**
	 * Generate the item at the postition <code>id</code> by using it's id
	 * 
	 * @param iditem's position (0=weapon,1=boots,2=leggings,3=chestplate,4=helmet)
	 * @param itemId item's id (exp : 276)
	 * @param probability When a new mob is created, the chance to the item to be selected.
	 * @param dropChance item's drop chance
	 */
	public void setItem(int id, int itemId, double probability, float dropChance) {
		if(id<5 && id >= 0) {
			this.items[id].setItem(itemId);
			this.items[id].setProbability(probability);
			this.items[id].setDropChance(dropChance);
		}
	}

	
	public ItemStackModel getItem(int id) {
		return id<5 && id >= 0?items[id]:null;
	}


	/**
	 * Add an enchantment to the the select item by using it's name.
	 * 
	 * @param name enchantment name
	 * @param power enchantment level
	 */
	public void addItemEnchantments(int id,String name, int power) {
		if(id<5 && id >= 0) this.items[id].addEnchantment(new EnchantmentModel(name,power));
	}
	
	/**
	 * Add an enchantment to the the select item by using it's id.
	 * 
	 * @param id enchantment id
	 * @param power enchantment level
	 */
	public void addItemEnchantments(int id, int eid, int power) {
		if(id<5 && id >= 0) this.items[id].addEnchantment(new EnchantmentModel(eid,power));
	}


	protected EntityLiving getMob(EntityLiving entity) {
		//Create items
		for(int i =0;i<5;i++) {
			entity.setEquipment(i,items[i].getAsItem());
			entity.dropChances[i] = items[i].getDropChance();
		}
		
		return entity;
	}


}
