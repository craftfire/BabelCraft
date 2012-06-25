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
package com.craftfire.babelcraft;

import com.google.api.translate.Language;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.managers.Managers;

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
