package fr.ornicare.models.mobs;

import net.minecraft.server.v1_4_R1.EntityZombie;

public class ZombieModel extends MobModel {
	
	public ZombieModel() {
		super();
	}
	
	public EntityZombie getMob(EntityZombie zombie) {
		//Set the weapon
		zombie = (EntityZombie) super.getMob(zombie);
		return zombie;
	}
}
