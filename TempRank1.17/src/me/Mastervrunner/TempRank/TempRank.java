package me.Mastervrunner.TempRank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TempRank implements CommandExecutor, Listener {
	
	public Plugin pl;
	public Long experatioooon;
	
	static Main plugin;
	public TempRank(Main main) {
		plugin = main;
	}
		
	 // This method is called, when somebody uses our command
    
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 1) {
			if(args[0].equals("time")) {
				if(sender instanceof Player) {
					Player playerrr = (Player) sender;
					
					FileConfiguration config = plugin.getConfig();
					
					String msg = config.getString("ExpirationTime");
					
					
					
					if(msg.contains(playerrr.getName())) {
					
						if(!playerrr.hasPermission("Tempperm.timeLeft")) {
							playerrr.sendMessage("No permission for getting the time left. Ask somoene for permission or something, bro (Mastervrunner made this)!");

							return true;
						}
						
						RankCheck checkRank = new RankCheck(plugin);
						checkRank.RankChecking(playerrr.getName(), playerrr);
					} else {
						
						
						String msg2 = config.getString("RankNotExpired");
						msg2.replace("%player%", playerrr.getName());
						
						String formattedMEssage = ChatColor.translateAlternateColorCodes('&', msg2);
						
						playerrr.sendMessage(formattedMEssage);
					} 
					
					
				}
			}
		}
		
		
		
		if(sender instanceof Player && args.length >= 2) {
			Player Pplayer = (Player) sender;
			if(!Pplayer.hasPermission("Tempperm.new")) {
				Pplayer.sendMessage("No permission. Ask somoene for permission or something, bro (Mastervrunner made this)!");
				return true;
			}
		}
		
        if(args.length >= 2) {
        	
        	
        	FileConfiguration config = plugin.getConfig();
        	
        	
        	String myCommand = config.getString("ExpirationCommand");
        	
        	Player player = Bukkit.getPlayer(args[0]);
        	
            String playerr = args[0];
            String rank = args[1];
            String time = args[2];
            
            
            if(myCommand.contains("%player%")) {
            	myCommand = myCommand.replace("%player%", playerr);
            }
            
            if(myCommand.contains("%rank%")) {
            	myCommand = myCommand.replace("%rank%", rank);
            }
        	
            String[] myCommands = myCommand.split(" - ");
            	
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

            	
            String command = "pex user " + playerr + " add " + rank;
            Bukkit.dispatchCommand(console, command);
            	
            long timee;
            	
            String time2 = time;
  
            time2 = time2.replaceAll("[^\\d.]", "");
            	
            timee = Long.parseLong(time2);
            
            String expires;
            
            expires = "seconds"; 
            	
            if(time.contains("s")) {
            	timee = timee * 20; //seconds
            	expires = "" + time2 + " seconds";
            } else if(time.contains("m")) {
           		timee = timee * 20;
            	timee = timee * 60; //minutes
            	expires = "" + time2  + " minutes";
            } else if(time.contains("h")) {
            	timee = timee * 20;
           		timee = timee * 60;
           		timee = timee * 60; //hours

           		
           		expires = "" + time2 + " hours";
           		
           	} else if(time.contains("d")) {
            	timee = timee * 20;
            	timee = timee * 60;
           		timee = timee * 60;
           		timee = timee * 24; //days

           		expires = "" + time2 + " days";
           		
           	} else if(time.contains("w")) {
            	timee = timee * 20;
            	timee = timee * 60;
           		timee = timee * 60;
           		timee = timee * 24;
           		timee = timee * 4; //week

           		expires = "" + time2 + " weeks";
           	}

       

            
            long tim = timee/20;
            
            long expirationTime = tim*1000;
            long currenTime = System.currentTimeMillis();
            expirationTime = expirationTime + currenTime;
            
            String configExpirations;
            configExpirations = config.getString("ExpirationTime");
            
            
            if(configExpirations.equals(null)) {
            	configExpirations = "";
            	plugin.getConfig().set("ExpirationTime", playerr+";"+expirationTime+";"+rank);
            } else {
            	plugin.getConfig().set("ExpirationTime", configExpirations+"-"+playerr+";"+expirationTime+";"+rank);
            }

            plugin.saveConfig();
            
            String msg;            
            String gotRank;
            gotRank = config.getString("RankAdded");
            
            gotRank = gotRank.replace("%player%", playerr);
            gotRank = gotRank.replace("%timeLeft%", expires);
            gotRank = gotRank.replace("%rank%", rank);
            
            gotRank = gotRank.replace("%expires%", expires);

            msg = ChatColor.translateAlternateColorCodes('&', gotRank);

            
            player.sendMessage(msg);
            
            
            
            experatioooon = expirationTime;
            	
            String expirTim = expires;
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            		
            	ConsoleCommandSender console2 = Bukkit.getServer().getConsoleSender();    	

            	
            	for(String cmd2: myCommands) {
        		
            		Bukkit.dispatchCommand(console, cmd2);
            	}
               	
               	String command3 = "pex user " + playerr + " group set default ";
               	Bukkit.dispatchCommand(console, command3);
                	
               	
               	String msgRemoved;
               	String ExpirRemoved = config.getString("RankExpired");
 
               	
               	ExpirRemoved = ExpirRemoved.replace("%player%", playerr);
               	ExpirRemoved = ExpirRemoved.replace("%rank%", rank);
               	ExpirRemoved = ExpirRemoved.replace("%timeLeft%", expirTim);

               	msgRemoved = ChatColor.translateAlternateColorCodes('&', ExpirRemoved);
               	
               	
               	String configExpirations2;
                configExpirations2 = config.getString("ExpirationTime");
        
                configExpirations2.replace("-"+playerr+";"+experatioooon+";"+rank,"");
                
                plugin.getConfig().set("ExpirationTime", configExpirations2);
                
                plugin.saveConfig();
                
                player.sendMessage(msgRemoved);	
                
                RankCheck checkRank = new RankCheck(plugin);
                Player pnull = null;

                checkRank.RankChecking(player.getName(), pnull);
                
                	
           	}, timee);
            	
        }
		
       
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
	
	
}
