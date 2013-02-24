package fr.ornicare;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener  implements CommandExecutor {
	
	public static boolean BLOODMOON = false;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		@SuppressWarnings("unused")
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		
		
		try {
			
			if (cmd.getName().equalsIgnoreCase("bloodmoon") && args.length>0) {
				
				int difficulty = Integer.valueOf(args[0]);
				
				if(difficulty==0) {

					Bukkit.getServer().broadcastMessage("§1The bloodmoon is gone...");
				}
				else if(difficulty<11){
					
					Bukkit.getServer().broadcastMessage("§4The bloodmoon is rising.");

				}
				else {
					
					return false;
				}
				for(World w : Bukkit.getWorlds()) {
					BLOODMOON = difficulty!=0;
					w.setMonsterSpawnLimit(100*(difficulty+1));
					w.setThundering(difficulty!=0);
					w.setStorm(difficulty!=0);
					
					
				}
				return true;
			} 
			else {

			}
		}
		catch(Exception e) {

		}
		
		return false;
	}

}
