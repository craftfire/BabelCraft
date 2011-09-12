package com.craftfire.babelcraft.util.managers;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;
import com.craftfire.babelcraft.util.databases.EBean;
import com.google.api.translate.Language;

public class PlayerManager {
	Util util = new Util();
	
    public String getIP(Player player) {
        if(player.getAddress().getAddress().toString().substring(1) != null) {
        	return player.getAddress().getAddress().toString().substring(1);
        }
        return "Unknown";
    }
    
    public void checkIP(String player, String IP) {
        EBean eBeanClass = checkPlayer(player, true);
        if (eBeanClass.getIp() == null || eBeanClass.getIp().equals(IP) == false) {
            Variables.logging.debug("IP in persistence is different than the player's IP, removing session and syncing IP's.");
            eBeanClass.setIp(IP);
            Variables.database.save(eBeanClass);
        }
	}
    
    public String getLanguageString(Player player) { return getLanguageString(player.getName()); }
    
    public String getLanguageString(String player) {
    	if(Variables.player_languages.containsKey(player)) {
    		return Variables.translation.languageName(Variables.player_languages.get(player));
    	} else {
	    	EBean eBeanClass = checkPlayer(player, true);
	        String language = eBeanClass.getLanguage();
	        if(language != null) {
	        	return Variables.translation.languageName(language);
	        } else {
	        	eBeanClass.setLanguage(Variables.translation.languageName(Config.language_default));
	        	Variables.database.save(eBeanClass);
	        }
    	}
    	Variables.player_languages.put(player, Variables.translation.languageName(Config.language_default));
        return Variables.translation.languageName(Config.language_default);
    }
    
    public String getCountryCode(Player player) {
    	String playerName = player.getName();
    	if(Variables.player_country_codes.containsKey(playerName)) {
    		return Variables.player_country_codes.get(playerName);
    	} else {
	    	EBean eBeanClass = checkPlayer(player, true);
	        String countryCode = eBeanClass.getCountrycode();
	        if(countryCode != null) {
	        	return countryCode;
	        } else {
	        	eBeanClass.setCountrycode(Variables.translation.getCountryCode(Variables.playerManager.getIP(player)));
	        	Variables.database.save(eBeanClass);
	        }
    	}
    	Variables.player_country_codes.put(playerName, Variables.translation.getCountryCode(Variables.playerManager.getIP(player)));
        return Variables.translation.getCountryCode(Variables.playerManager.getIP(player));
    }
    
    public Language getLanguage(Player player) {
    	return Language.fromString(getLanguageString(player));
    }
    
    public EBean checkPlayer(String player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayername(player);
            eBeanClass.setLanguage(Variables.translation.languageName(Config.language_default));
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }

    public EBean checkPlayer(Player player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player.getName()).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayer(player);
            eBeanClass.setLanguage(Variables.translation.languageName(Config.language_default));
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }
    
    public void save(EBean eBeanClass) {
        Variables.database.save(eBeanClass);
    }
    
}
