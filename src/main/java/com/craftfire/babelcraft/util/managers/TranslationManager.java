package com.craftfire.babelcraft.util.managers;

import java.io.IOException;

import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.maxmind.geoip.LookupService;

public class TranslationManager {
	
	LoggingManager logging = new LoggingManager();
	PlayerManager playerManager = new PlayerManager();
	Util util = new Util();
    
    public String translate(String message, Language langfrom, Language langto) {
    	logging.debug("FROM: " + langfrom);
    	logging.debug("TO: " + langto);
        Translate.setHttpReferrer("http://www.craftfire.com/");
        String translated = null;
        try {
            translated = Translate.execute(message, langfrom, langto);
        } catch (Exception e) {
        	logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
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
                return util.capitalize(l.name().toLowerCase());
            } else if (lang.toLowerCase().equals(l.name().toLowerCase())) {
                return util.capitalize(l.name().toLowerCase());
            }
        }
        return lang;
    }

    public String languageCode(String lang) {
        for (Language l : Language.values()) {
            if (lang.equalsIgnoreCase(l.toString())) {
                return util.capitalize(l.toString().toLowerCase());
            } else if (lang.equalsIgnoreCase(l.name())) {
                return util.capitalize(l.toString().toLowerCase());
            }
        }
        return lang;
    }
    
    public String getCountryCode(String IP) {
        LookupService cl = null;
        try {
            cl = new LookupService(Variables.dbfile, LookupService.GEOIP_MEMORY_CACHE);
        } catch (IOException e) {
        	logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
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
        	logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
        }

        String CountryName = cl.getCountry(IP).getName();

        cl.close();
        return CountryName;
    }
}
