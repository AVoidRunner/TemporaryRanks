package me.Mastervrunner.TempRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RankCheck {
	
	static Main plugin;
	public RankCheck(Main main) {
		plugin = main;
	}
	
	private String timeLefted;

	public void RankChecking(String player, Player play) {
		
		Logger log = Bukkit.getLogger();
		FileConfiguration config = plugin.getConfig();
		
		
		String timeLeft = config.getString("ExpirationTime");
		
		log.info("Player: " + player);
		
		String[] timesLeft =  {timeLeft};
		
		
		if(timeLeft.contains("-")) {
			timesLeft = timeLeft.split("-");
			
		} else {
			
			
		}
		
		log.info("ExpirationTime Config: " + timeLeft);
		
		long currenTime = System.currentTimeMillis();
		
		for(String timee: timesLeft) {
			
			String time = timee;
			
			if(time.startsWith(player+";")) {
				time = time.replace(player, "");
				String timess[] = time.split(";");
				
				time = timess[1];
				String rank = timess[2];
				
				Long millisecondTime = Long.parseLong(time);
				
				Long maxVal = (millisecondTime);
				Long current = (currenTime);
				Long timeBetween = maxVal-current;
				
				log.info("Max Val: " + maxVal + " current: " + current + " timeBetween: " + timeBetween);
				
				if(play != null) {
					
					Long secBetween = timeBetween;
					secBetween = secBetween/1000;
					
					int min = 60;
					int hour = 60*min;
					int day = 24*hour;
					int week = 7*day;
					
					String msg = config.getString("RankTime");
					msg = msg.replace("%player%", player);
					msg = msg.replace("%rank%", rank);
					
					if(secBetween >= min && secBetween < hour) {
						secBetween = secBetween/min;
						
						msg.replace("timeLeft", secBetween.toString());
						
						play.sendMessage(msg + " minutes");
						
					} else if(secBetween >= hour && secBetween < day) {
						secBetween = secBetween/hour;
						
						msg.replace("timeLeft", secBetween.toString());
						play.sendMessage(msg + " hours");
					} else if(secBetween >= day && secBetween < week) {
						secBetween = secBetween/day;
						
						msg.replace("timeLeft", secBetween.toString());
						
						play.sendMessage("Time Left for Rank: " + rank + " is " + secBetween + " days");
						
						play.sendMessage(msg + " days");
					} else if(secBetween >= week) {//fixed indent by its self
						secBetween = secBetween/week;
						
						msg.replace("timeLeft", secBetween.toString());
						play.sendMessage(msg + " weeks");
						
					} else {
						
						msg.replace("timeLeft", secBetween.toString());
						
						play.sendMessage(msg + " seconds");
					}
					
					return;
					
				}
				
				if(timeBetween < 1) {
					
					log.info("TimeBetween Less than 1");
					
					String newConf = timeLeft;
					newConf = newConf.replace("-"+player+";"+maxVal+";"+rank, "");
					
					log.info("NewConf: " + newConf);
					
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
						
						log.info("Delayed Commands if timeBetween < 1: " + cmd2);
						
						Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
						Bukkit.dispatchCommand(console, cmd2);
						},20);
	            		
	            	}
	               	
					String command3 = "pex user " + player + " group set default ";
	               	Bukkit.dispatchCommand(console, command3);
	               	
				} else {
					
					log.info("Time between not < 1");
					
					long myTimeBetween = timeBetween;
					myTimeBetween = myTimeBetween/1000;
					myTimeBetween = myTimeBetween*20;
					
					String myTimeLeft = timeLeft;
					
					 Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
						 
						 String newConf = myTimeLeft;
						newConf = newConf.replace("-"+player+";"+maxVal+";"+rank, "");
							
						log.info("NewConf: " + newConf);
							
						plugin.getConfig().set("ExpirationTime", newConf);

							
						plugin.saveConfig();
						 
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
				        		
							log.info("Delayed Commands if timeBetween NOT < 1: " + cmd2);
			            	Bukkit.dispatchCommand(console, cmd2);
			            }
			               	
						String command3 = "pex user " + player + " group set default ";
			            Bukkit.dispatchCommand(console, command3);
			             
					 },myTimeBetween);
				}
				
			}
		}
		
	}
	
	public void testing() {
		
	}
	
}
