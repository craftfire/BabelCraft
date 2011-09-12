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
            Managers.logging.debug("IP in persistence is different than the player's IP, removing session and syncing IP's.");
            eBeanClass.setIp(IP);
            Variables.database.save(eBeanClass);
        }
	}
    
    public String getLanguageString(Player player) { return getLanguageString(player.getName()); }
    
    public String getLanguageString(String player) {
    	Managers.logging.debug("Getting " + player +"'s language from storage.");
    	if(Variables.player_languages.containsKey(player)) {
    		Managers.logging.debug("Found " + player +"'s language in hashmap with language: " 
	    	+ Variables.player_languages.get(player) + " | " 
	    	+ Managers.translation.languageName(Variables.player_languages.get(player)));
    		return Managers.translation.languageName(Variables.player_languages.get(player));
    	} else {
	    	EBean eBeanClass = checkPlayer(player, true);
	        String language = eBeanClass.getLanguage();
	        if(language != null) {
	        	Managers.logging.debug("Found " + player +"'s language in ebean with language: " 
    	    	+ Managers.translation.languageName(language));
	        	return Managers.translation.languageName(language);
	        } else {
	        	Managers.logging.debug("Could not find " + player +"'s language in storage, return default language: " 
    	    	+ Managers.translation.languageName(language));
	        	eBeanClass.setLanguage(Managers.translation.languageName(Config.language_default));
	        	Variables.database.save(eBeanClass);
	        }
    	}
    	Variables.player_languages.put(player, Managers.translation.languageName(Config.language_default));
        return Managers.translation.languageName(Config.language_default);
    }
    
    public Language getLanguage(Player player) {
    	return Managers.translation.fromString(getLanguageString(player));
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
	        	eBeanClass.setCountrycode(Managers.translation.getCountryCode(Managers.player.getIP(player)));
	        	Variables.database.save(eBeanClass);
	        }
    	}
    	Variables.player_country_codes.put(playerName, Managers.translation.getCountryCode(Managers.player.getIP(player)));
        return Managers.translation.getCountryCode(Managers.player.getIP(player));
    }
    
    public EBean checkPlayer(String player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayername(player);
            eBeanClass.setLanguage(Managers.translation.languageName(Config.language_default));
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }

    public EBean checkPlayer(Player player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player.getName()).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayer(player);
            eBeanClass.setLanguage(Managers.translation.languageName(Config.language_default));
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }
    
    public void save(EBean eBeanClass) {
        Variables.database.save(eBeanClass);
    }
    
}
