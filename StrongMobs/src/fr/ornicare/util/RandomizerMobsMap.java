package fr.ornicare.util;

import java.util.HashMap;
import java.util.Map;

import fr.ornicare.models.MobModel;

/**
 * Wrapper for a  Map<weigh,MobModel>
 * Probability to get choose is proportionnal to the weigh
 * 
 * @author Ornicare
 *
 */
public class RandomizerMobsMap{

	private int totalWeigh = 0;
	private Map<Integer[],MobModel> map = new HashMap<Integer[],MobModel>();
	
	public int getTotalWeigh() {
		return totalWeigh;
	}

	public void push(Integer key, MobModel value) {
		Integer[] definitionValue = new Integer[2];
		definitionValue[0] = new Integer(this.totalWeigh);
		this.totalWeigh += key.intValue();
		definitionValue[1] = new Integer(this.totalWeigh);
		
		map.put(definitionValue, value);
	}
	
	public void push(int key, MobModel value) {
		Integer[] definitionValue = new Integer[2];
		definitionValue[0] = new Integer(this.totalWeigh);
		this.totalWeigh += key;
		definitionValue[1] = new Integer(this.totalWeigh);
		
		map.put(definitionValue, value);
	}
	
	public void remove(Integer[] key) {
		map.remove(key);
		this.totalWeigh -= key[0].intValue();
	}
	
	/**
	 * Choose a random MobModel according to his weigh
	 * 
	 * @return MobModel
	 */
	public MobModel getRandomMob() {
		int mobInt = MathHelper.randomize(totalWeigh);
		for(Integer[] definitionDomain : map.keySet()) {
			if(mobInt>definitionDomain[0] && mobInt<=definitionDomain[1]) return map.get(definitionDomain);
		}
		return null;
	}

}
