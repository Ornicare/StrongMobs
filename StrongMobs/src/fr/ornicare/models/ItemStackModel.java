package fr.ornicare.models;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_4_R1.Enchantment;
import net.minecraft.server.v1_4_R1.ItemStack;
import net.minecraft.server.v1_4_R1.NBTTagCompound;
import net.minecraft.server.v1_4_R1.NBTTagList;
import net.minecraft.server.v1_4_R1.NBTTagString;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.ornicare.util.MathHelper;

/**
 * ItemStack wrapper (equipement)
 * 
 * @author Ornicare
 *
 */
public class ItemStackModel {
	
	private ItemStack asItem;
	private double probability = 0;
	private float dropChance = -1;
	
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
				if(ench!=null && ench.id == em.getEnchantment().getId() && (Math.random()<em.getProbability())) asItemClone.addEnchantment(ench, MathHelper.randomize(em.getEnchantmentLevel()));
			}
		}
		//asItemClone=addLore(asItemClone, "Ceci n'est pas un test");
		return Math.random()<probability?asItemClone:null;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}


	public float getDropChance() {
		return dropChance;
	}


	public void setDropChance(float dropChance) {
		this.dropChance = dropChance;
	}
	
	
	public ItemStack addLore(ItemStack item, String lore) {
		//ItemStack itemStack = CraftItemStack.asNMSCopy(item);
		
		NBTTagCompound tag = item.tag;
		if (tag == null) {
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			tag.getCompound("display").set("Lore", new NBTTagList());
			item.tag = tag;
		}
		
		tag = item.tag.getCompound("display");
		NBTTagList list = tag.getList("Lore");
		list.add(new NBTTagString("", ChatColor.RESET + lore));
		tag.set("Lore", list);
		item.tag.setCompound("display", tag);
		return item;
		//return CraftItemStack.asCraftMirror(asItem);
	}


	
}
