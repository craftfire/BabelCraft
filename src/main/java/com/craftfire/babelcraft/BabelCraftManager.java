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
	
    public String translate(String message, Player player) {
        Language lang;
        if (Config.language_serverforced) {
            lang = Language.fromString(Config.language_default);
        } else {
            lang = playerManager.getLanguage(player);
        }
        return  translation.translate(message, Language.AUTO_DETECT, lang);
    }
    
    public String getLanguage(Player player) {
        return playerManager.getLanguageString(player);
    }

    public String getLanguageCode(Player player) {
        return translation.languageCode(playerManager.getLanguageString(player));
    }

    public String getCountry(Player player) {
        return translation.getCountryName(playerManager.getIP(player));
    }

    public String getCountryCode(Player player) {
        return playerManager.getCountryCode(player);
    }
}
