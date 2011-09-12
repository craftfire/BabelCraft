package com.craftfire.babelcraft;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.managers.Managers;
import com.google.api.translate.Language;

public class BabelCraftManager {
	
    public String translate(String message, Player player) {
        Language lang;
        if (Config.language_serverforced) {
            lang = Language.fromString(Config.language_default);
        } else {
            lang = Managers.player.getLanguage(player);
        }
        return  Managers.translation.translate(message, Language.AUTO_DETECT, lang);
    }
    
    public String getLanguage(Player player) {
        return Managers.player.getLanguageString(player);
    }

    public String getLanguageCode(Player player) {
        return Managers.translation.languageCode(Managers.player.getLanguageString(player));
    }

    public String getCountry(Player player) {
        return Managers.translation.getCountryName(Managers.player.getIP(player));
    }

    public String getCountryCode(Player player) {
        return Managers.player.getCountryCode(player);
    }
}
