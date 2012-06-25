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
package com.craftfire.babelcraft.util;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public class Messages {
    Util util = new Util();

    public static String message_default_lang, message_autodetect, message_usage;
    public static String message_notsupported, message_nottranslated, message_savesuccess;
    public static String message_savefailure;

    public enum Message {
        default_lang (message_default_lang),
        autodetect (message_autodetect),
        usage (message_usage),
        notsupported (message_notsupported),
        nottranslated (message_nottranslated),
        savesuccess (message_savesuccess),
        savefailure (message_savefailure);

        public String text;

        Message (String text) {
            this.text = text;
        }
    }

    public void SendMessage(final Message message, Player player, PlayerLoginEvent event) {
        player.sendMessage(util.replaceStrings(message.text, player, null));
    }
}
