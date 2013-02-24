package fr.ornicare.models.mobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_4_R1.Enchantment;
import net.minecraft.server.v1_4_R1.ItemStack;

import org.bukkit.Material;

import fr.ornicare.util.MathHelper;

/**
 * ItemStack wrapper
 * 
 * @author Ornicare
 *
 */
public class ItemStackModel {
	
	private ItemStack asItem;
	private double probability = 0;
	
	private List<EnchantmentModel> enchantments = new ArrayList<EnchantmentModel>();
	
	public ItemStackModel() {
	}
	
	
	public void addEnchantment(EnchantmentModel em) {
		enchantments.add(em);
	}
	
	/**
	 * Set the item type by using it's name
	 * 
	 * @param itemnName item name
	 */
	public void setItem(String itemName) {
		Material itemM = Material.getMaterial(itemName);
		asItem=itemM==null?null:new ItemStack(itemM.getId(),1,0);
	}
	
	/**
	 * Set the the item type by using it's id
	 * 
	 * @param itemId item id
	 */
	public void setItem(int itemId) {
		Material itemM = Material.getMaterial(itemId);
		asItem=itemM==null?null:new ItemStack(itemM.getId(),1,0);
	}

	/**
	 * Apply all the enchantment to a copy of the real item and return it
	 * 
	 * @return
	 */
	public ItemStack getAsItem() {
		if(asItem==null) return null;
		ItemStack asItemClone = asItem.cloneItemStack();
		for(EnchantmentModel em : this.enchantments) {
			for(Enchantment ench : Enchantment.byId) {
				if(ench!=null && ench.id == em.getEnchantment().getId()) asItemClone.addEnchantment(ench, MathHelper.randomize(em.getEnchantmentLevel()));
			}
		}
		return Math.random()<probability?asItemClone:null;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}


	
}
