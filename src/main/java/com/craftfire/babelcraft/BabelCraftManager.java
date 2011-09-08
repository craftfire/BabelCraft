package com.craftfire.babelcraft;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.managers.PlayerManager;
import com.craftfire.babelcraft.util.managers.TranslationManager;
import com.google.api.translate.Language;

public class BabelCraftManager {
	
	Util util = new Util();
	TranslationManager translation = new TranslationManager();
	PlayerManager playerManager = new PlayerManager();
	
    public String Translate(String message, Player player) {
        Language lang;
        if (Config.language_serverforced) {
            lang = Language.fromString(Config.language_default);
        } else {
            boolean isin = util.PlayerDatabase("check", player, null, null);
            if (isin) {
                lang = translation.GetPlayerLanguageHash(player);
            } else {
                lang = translation.GetLanguage(player, "to");
            }
        }
        return  translation.Translate(message, Language.AUTO_DETECT, lang);
    }
    
    public String getLanguage(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        Language lang;
        if (isin) {
            lang = translation.GetPlayerLanguageHash(player);
        } else {
            lang = translation.GetLanguage(player, "from");
        }
        return util.LanguageName("" + lang).toLowerCase();
    }

    public String getLanguageCode(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        Language lang;
        if (isin) {
            lang = translation.GetPlayerLanguageHash(player);
        } else {
            lang = translation.GetLanguage(player, "from");
        }
        return util.LanguageCode("" + lang).toLowerCase();
    }

    public String getCountry(Player player) {
        String CountryName = translation.GetCountryName(playerManager.getIP(player));
        return  CountryName.toLowerCase();
    }

    public String getCountryCode(Player player) {
        boolean isin = util.PlayerDatabase("check", player, null, null);
        String CountryCode;
        if (isin) {
            CountryCode = translation.GetPlayerCountryHash(player);
        } else {
            CountryCode = translation.GetCountryCode(playerManager.getIP(player));
        }
        return  CountryCode.toLowerCase();
    }
}
