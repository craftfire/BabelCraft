package com.craftfire.babelcraft;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Variables;
import com.google.api.translate.Language;

public class BabelCraftManager {
	
    public String translate(String message, Player player) {
        Language lang;
        if (Config.language_serverforced) {
            lang = Language.fromString(Config.language_default);
        } else {
            lang = Variables.playerManager.getLanguage(player);
        }
        return  Variables.translation.translate(message, Language.AUTO_DETECT, lang);
    }
    
    public String getLanguage(Player player) {
        return Variables.playerManager.getLanguageString(player);
    }

    public String getLanguageCode(Player player) {
        return Variables.translation.languageCode(Variables.playerManager.getLanguageString(player));
    }

    public String getCountry(Player player) {
        return Variables.translation.getCountryName(Variables.playerManager.getIP(player));
    }

    public String getCountryCode(Player player) {
        return Variables.playerManager.getCountryCode(player);
    }
}
