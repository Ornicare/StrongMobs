package fr.ornicare.models.mobs;

import net.minecraft.server.v1_4_R1.EntityLiving;


/**
 * Storage class for a generic mob
 * 
 * @author Ornicare
 *
 */
public abstract class MobModel {
	
	/**
	 * Mob's weapon
	 */
	protected ItemStackModel[] items;
	
	/**
	 * Create a new mobModel
	 */
	public MobModel() {
		this.items = new ItemStackModel[5];
		for(int i = 0;i<5;i++) items[i] = new ItemStackModel();
	}
	
	/**
	 * Generate the item at the postition <code>id</code> by using it's name
	 * 
	 * @param id item's position (0=weapon,1=boots,2=leggings,3=chestplate,4=helmet)
	 * @param name item's name (exp : DIAMOND_SWORD)
	 * @param probability When a new mob is created, the chance to the item to be selected.
	 */
	public void setItem(int id, String name, double probability) {
		if(id<5 && id >= 0) {
			this.items[id].setItem(name);
			this.items[id].setProbability(probability);
		}
	}
	
	/**
	 * Generate the item at the postition <code>id</code> by using it's id
	 * 
	 * @param iditem's position (0=weapon,1=boots,2=leggings,3=chestplate,4=helmet)
	 * @param itemId item's id (exp : 276)
	 * @param probability When a new mob is created, the chance to the item to be selected.
	 */
	public void setItem(int id, int itemId, double probability) {
		if(id<5 && id >= 0) {
			this.items[id].setItem(itemId);
			this.items[id].setProbability(probability);
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
		for(int i =0;i<5;i++) {
			entity.setEquipment(i,items[i].getAsItem());
		}
		return entity;
	}


}
