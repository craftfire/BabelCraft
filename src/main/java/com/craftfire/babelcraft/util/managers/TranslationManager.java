/*
 * This file is part of BabelCraft Legacy.
 *
 * Copyright (c) 2011-2012, CraftFire <http://www.craftfire.com/>
 * BabelCraft Legacy is licensed under the GNU Lesser General Public License.
 *
 * BabelCraft Legacy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BabelCraft Legacy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftfire.babelcraft.util.managers;

import java.io.IOException;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.maxmind.geoip.LookupService;

import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;

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
            if (lang.toLowerCase().equalsIgnoreCase(l.toString().toLowerCase())) {
                return util.capitalize(l.name().toLowerCase());
            } else if (lang.toLowerCase().equalsIgnoreCase(l.name().toLowerCase())) {
                return util.capitalize(l.name().toLowerCase());
            }
        }
        return lang;
    }

    public Language fromString(String lang) {
        for (Language l : Language.values()) {
            if (lang.toLowerCase().equalsIgnoreCase(l.toString().toLowerCase())) {
                return l;
            } else if (lang.toLowerCase().equalsIgnoreCase(l.name().toLowerCase())) {
                return l;
            }
        }
        return null;
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
