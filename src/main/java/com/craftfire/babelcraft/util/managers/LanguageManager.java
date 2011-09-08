package com.craftfire.babelcraft.util.managers;

import java.io.IOException;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.maxmind.geoip.LookupService;

public class LanguageManager {
	
	LoggingManager logging = new LoggingManager();
	
    public Language GetPlayerLanguageHash(Player player) {
        String TheLanguage = Config.playerdatabase.get(player.getName().toLowerCase());
        String[] Split = TheLanguage.split(":");
        return Language.fromString(Split[0]);
    }

    public String GetPlayerCountryHash(Player player) {
        String TheCountry = Config.playerdatabase.get(player.getName().toLowerCase());
        String[] Split = TheCountry.split(":");
        return Split[1];
    }
    
    public String Translate(String message, Language langfrom, Language langto) {
    	logging.debug("FROM: " + langfrom);
    	logging.Debug("TO: " + langto);
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

    public boolean IsLanguageSupported(String lang) {
        Language thelang = Language.fromString(lang);
        if (thelang != null) {
            return true;
        }
        return false;
    }

    public String LanguageName(String lang) {
        for (Language l : Language.values()) {
            if (lang.toLowerCase().equals(l.toString().toLowerCase())) {
                return Capitalize(l.name().toLowerCase());
            } else if (lang.toLowerCase().equals(l.name().toLowerCase())) {
                return Capitalize(l.name().toLowerCase());
            }
        }
        return lang;
    }

    public String LanguageCode(String lang) {
        for (Language l : Language.values()) {
            if (lang.toLowerCase().equals(l.toString().toLowerCase())) {
                return Capitalize(l.toString().toLowerCase());
            } else if (lang.toLowerCase().equals(l.name().toLowerCase())) {
                return Capitalize(l.toString().toLowerCase());
            }
        }
        return lang;
    }

    public Language GetLanguage(Player player, String action) {
        String LanguageCode = GetFile(player.getName().toLowerCase(), null);
        String CountryCode = GetCountryCode(GetIP(player));
        if (CountryCode.equals("--")) {
            CountryCode = "localhost";
        }
        Util.PlayerDatabase("add", player, LanguageCode, CountryCode);
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
    
    public String GetCountryCode(String IP) {
        LookupService cl = null;
        try {
            cl = new LookupService(Config.dbfile, LookupService.GEOIP_MEMORY_CACHE);
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
}
