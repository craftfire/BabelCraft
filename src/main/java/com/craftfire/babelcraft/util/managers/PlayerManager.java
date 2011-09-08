package com.craftfire.babelcraft.util.managers;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Variables;
import com.craftfire.babelcraft.util.databases.EBean;

public class PlayerManager {
	LoggingManager logging = new LoggingManager();
	TranslationManager translation = new TranslationManager();
    public String getIP(Player player) {
        if(player.getAddress().getAddress().toString().substring(1) != null) {
        	return player.getAddress().getAddress().toString().substring(1);
        }
        return "Unknown";
    }
    
    public void checkIP(String player, String IP) {
        EBean eBeanClass = EBean.checkPlayer(player, true);
        if (eBeanClass.getIp() == null || eBeanClass.getIp().equals(IP) == false) {
            logging.debug("IP in persistence is different than the player's IP, removing session and syncing IP's.");
            eBeanClass.setIp(IP);
            Variables.database.save(eBeanClass);
        }
	}
    
    public String getLanguage(Player player) { return getLanguage(player.getName()); }
    
    public String getLanguage(String player) {
    	if(Variables.player_languages.containsKey(player)) {
    		return translation.languageName(Variables.player_languages.get(player));
    	} else {
	    	EBean eBeanClass = EBean.checkPlayer(player, true);
	        String language = eBeanClass.getLanguage();
	        if(language != null) {
	        	return translation.languageName(language);
	        } else {
	        	eBeanClass.setLanguage(translation.languageName(Config.language_default));
	        	Variables.database.save(eBeanClass);
	        }
    	}
    	Variables.player_languages.put(player, translation.languageName(Config.language_default));
        return translation.languageName(Config.language_default);
    }
}
