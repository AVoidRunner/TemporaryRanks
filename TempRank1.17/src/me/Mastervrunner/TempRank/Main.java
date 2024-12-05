package me.Mastervrunner.TempRank;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

//public class Main implements JavaPlugin{
public class Main extends JavaPlugin{
	
	private static Main instance;
	
	FileConfiguration config = getConfig();
	
	Plugin plugin = this;

	public void getTimeLeftBad(String player) {
		
		FileConfiguration config = plugin.getConfig();
		
		String timeLeft = config.getString("ExpirationTime");
		
		String[] timesLeft =  {timeLeft};
		
		if(timeLeft.contains("-")) {
			timesLeft = timeLeft.split("-");
		}
		
		long currenTime = System.currentTimeMillis();
		
		for(String timee: timesLeft) {
			
			String time = timee;
			
			if(time.startsWith(player+";")) {
				time = time.replace(player, "");
				
				String timess[] = time.split(";");
				time = timess[1];
				String rank = timess[2];
				
				Bukkit.getLogger().info(timess[0]);
				Bukkit.getLogger().info(timess[1]);
				Bukkit.getLogger().info(timess[2]);
				
				
				Long millisecondTime = Long.parseLong(time);
				
				Long maxVal = (millisecondTime);
				Long current = (currenTime);
				Long timeBetween = maxVal-current;
				
				if(timeBetween < 1) {
					String newConf = timeLeft;
					newConf = newConf.replace("-"+player+";"+maxVal, "");
					
					plugin.getConfig().set("ExpirationTime", newConf);
					plugin.saveConfig();
					
					timeLeft = newConf;
					
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					
					String myCommand = config.getString("ExpirationCommand");
					
					if(myCommand.contains("%player%")) {
						myCommand = myCommand.replace("%player%", player);
		            }
		            
		            if(myCommand.contains("%rank%")) {
		            	myCommand = myCommand.replace("%rank%", rank); 
		            }
		        	
		            String[] myCommands = myCommand.split(" - ");
		            
		            
					for(String cmd2: myCommands) {
						
						Bukkit.getLogger().info("CMD 2 String cmd2: myCommands : " + cmd2 + " : RANK!!!! " + rank);
		        		
						Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
						Bukkit.dispatchCommand(console, cmd2);
						},20);

	            	}
	               	
					String command3 = "pex user " + player + " group set default ";
	               	Bukkit.dispatchCommand(console, command3);
	               	
	               	Bukkit.getLogger().info("Comamnd 3 pex user player stuff group set default : " + command3);
	               	
				} else {
					 Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
						 
						 ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
							
						String myCommand = config.getString("ExpirationCommand");
							
						if(myCommand.contains("%player%")) {
							myCommand = myCommand.replace("%player%", player);
				           }
				            
				           
				           if(myCommand.contains("%rank%")) {
				           	myCommand = myCommand.replace("%rank%", rank);
				           }
				        	
				           String[] myCommands = myCommand.split(" - ");
				            
							
						for(String cmd2: myCommands) {
				        		
			            	Bukkit.dispatchCommand(console, cmd2);
			            }
			               	
						String command3 = "pex user " + player + " group set default ";
			            Bukkit.dispatchCommand(console, command3);
			               
			        	Bukkit.getLogger().info("Comamnd 3 pex user player stuff group set default (2.0): " + command3);
			               
						 
					 },timeBetween);
				}
				
			}
		}
	}
	
	@Override
    public void onEnable() {
		
		config.addDefault("ExpirationCommand", "pex user %player% remove %rank% - bending remove %player% all");
		config.addDefault("ExpirationTime", "");
		config.addDefault("ExpirationPlayerList", "");
		config.addDefault("RankAdded", "&8&l[&c&lHob&8&l]&f:&7 Your rank has been added, %player%, and it will expire in &5&l%expires%&7.");
		config.addDefault("RankExpired", "&8&l[&c&lHob&8&l]&f: &7Your rank %rank% has expired, %player%.");
		config.addDefault("RankTime", "%player%, your time Left for Rank: %rank% is %timeLeft%");
		config.addDefault("RankNotExpired", "&8&l[&c&lHob&8&l]&f:&7 %player%, you do not have any rank that will expire!!!! &5&l%Why would you ask, now I had to make this feature%&7");
		
        config.options().copyDefaults(true);
        saveConfig();
        
        FileConfiguration config = plugin.getConfig();
		
		
		String timeLeft = config.getString("ExpirationTime");		
		String[] timesLeft =  {timeLeft};
		ArrayList<String> players = new ArrayList<String>();
		
		
		if(timeLeft.contains("-")) {
			timesLeft = timeLeft.split("-");
			
		} 
		
		for(String left: timesLeft) {
			if(left.contains(";")) {
				
				String playA = left.split(";")[0];

				players.add(playA);				
			}
			
		}
		
		for(String player: players) {
			RankCheck checkRank = new RankCheck(this);
			Player nulPlay = null;
			
			checkRank.RankChecking(player, nulPlay);
		}
        
		
		this.getCommand("tempperm").setExecutor(new TempRank(this));
    }
	
	public static Main getInstance() {
		return instance;
	}

}
