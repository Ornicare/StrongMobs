StrongMobs
==========

A simple minecraft plugin to parametrize all the mobs.

TODO : finish the rewritting.

Actully take the following format : 

zombie.yml
----------

    superzombie:  
      spawnweigh: 10  
      equipment:  
        weapon:  
          probability: 1.0  
          type: 293  
          enchantments:  
            - FIRE_ASPECT, 10  
            - DAMAGE_ALL, 5  
        armor:  
          chestplate:  
            probability: 1.0  
            type: DIAMOND_CHESTPLATE  
            enchantments:  
              - FIRE_ASPECT, 10  
              - 0, 20  
    simplezomb:  
      spawnweigh: 100  
      equipment:  
        weapon:  
          probability: 0.2  
          type: DIAMOND_SWORD  
          enchantments:  
            - FIRE_ASPECT, 10  
            - 0, 20  
