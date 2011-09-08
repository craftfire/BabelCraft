package com.craftfire.babelcraft;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.google.api.translate.Language;

public class BabelCraftManager {
	
	Util util = new Util();
	
    public String Translate(String message, Player player) {
        Language lang;
        if (Config.language_serverforced) {
            lang = Language.fromString(Config.language_default);
        } else {
            boolean isin = util.PlayerDatabase("check", player, null, null);
            if (isin) {
                lang = util.GetPlayerLanguageHash(player);
            } else {
                lang = util.GetLanguage(player, "to");
            }
        }
        return  util.Translate(message, Language.AUTO_DETECT, lang);
    }
    
    public String getLanguage(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        Language lang;
        if (isin) {
            lang = util.GetPlayerLanguageHash(player);
        } else {
            lang = util.GetLanguage(player, "from");
        }
        return util.LanguageName("" + lang).toLowerCase();
    }

    public String getLanguageCode(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        Language lang;
        if (isin) {
            lang = util.GetPlayerLanguageHash(player);
        } else {
            lang = util.GetLanguage(player, "from");
        }
        return util.LanguageCode("" + lang).toLowerCase();
    }

    public String getCountry(Player player) {
        String CountryName = util.GetCountryName(Util.GetIP(player));
        return  CountryName.toLowerCase();
    }

    public String getCountryCode(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        String CountryCode;
        if (isin) {
            CountryCode = util.GetPlayerCountryHash(player);
        } else {
            CountryCode = util.GetCountryCode(Util.GetIP(player));
        }
        return  CountryCode.toLowerCase();
    }
}
