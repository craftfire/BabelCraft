package com.craftfire.babelcraft.util.managers;

import java.io.IOException;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.maxmind.geoip.LookupService;

public class TranslationManager {
	
	LoggingManager logging = new LoggingManager();
	PlayerManager playerManager = new PlayerManager();
	Util util = new Util();
	
    public Language getPlayerLanguageHash(Player player) {
        String TheLanguage = Config.playerdatabase.get(player.getName().toLowerCase());
        String[] Split = TheLanguage.split(":");
        return Language.fromString(Split[0]);
    }

    public String getPlayerCountryHash(Player player) {
        String TheCountry = Config.playerdatabase.get(player.getName().toLowerCase());
        String[] Split = TheCountry.split(":");
        return Split[1];
    }
    
    public String translate(String message, Language langfrom, Language langto) {
    	logging.debug("FROM: " + langfrom);
    	logging.debug("TO: " + langto);
        Translate.setHttpReferrer("http://www.craftfire.com/");
        String translated = null;
        try {
            translated = Translate.execute(message, langfrom, langto);
        } catch (Exception e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
        }
        return translated;
    }

    public boolean isLanguageSupported(String lang) {
        Language thelang = Language.fromString(lang);
        if (thelang != null) {
            return true;
        }
        return false;
    }

    public String languageName(String lang) {
        for (Language l : Language.values()) {
            if (lang.toLowerCase().equals(l.toString().toLowerCase())) {
                return util.Capitalize(l.name().toLowerCase());
            } else if (lang.toLowerCase().equals(l.name().toLowerCase())) {
                return util.Capitalize(l.name().toLowerCase());
            }
        }
        return lang;
    }

    public String languageCode(String lang) {
        for (Language l : Language.values()) {
            if (lang.toLowerCase().equals(l.toString().toLowerCase())) {
                return util.Capitalize(l.toString().toLowerCase());
            } else if (lang.toLowerCase().equals(l.name().toLowerCase())) {
                return util.Capitalize(l.toString().toLowerCase());
            }
        }
        return lang;
    }

    public Language getLanguage(Player player, String action) {
        String LanguageCode = playerManager.getLanguage(player);
        String CountryCode = getCountryCode(playerManager.getIP(player));
        if (CountryCode.equals("--")) {
            CountryCode = "localhost";
        }
        Util.playerDatabase("add", player, LanguageCode, CountryCode);
        Language lang = Language.fromString(LanguageCode);
        if (lang != null) {
            return lang;
        } else {
            if (action.equals("to")) {
                lang = Language.ENGLISH;
            } else if (action.equals("from")) {
                lang = Language.AUTO_DETECT;
            }
        }
        return lang;
    }
    
    public String getCountryCode(String IP) {
        LookupService cl = null;
        try {
            cl = new LookupService(Variables.dbfile, LookupService.GEOIP_MEMORY_CACHE);
        } catch (IOException e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
        }

        String CountryCode = cl.getCountry(IP).getCode();

        cl.close();
        if (CountryCode.equals("--")) {
            CountryCode = "localhost";
        }
        return CountryCode;
    }
    
    public String getCountryName(String IP) {
        LookupService cl = null;
        try {
            cl = new LookupService(Variables.dbfile, LookupService.GEOIP_MEMORY_CACHE);
        } catch (IOException e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
        }

        String CountryName = cl.getCountry(IP).getName();

        cl.close();
        return CountryName;
    }
}
