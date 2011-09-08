/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.util;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public class Messages {
    ///////////////////////////////////////////
    //               language
    ///////////////////////////////////////////
    public static String message_default, message_autodetect, message_usage, message_notsupported, message_nottranslated, message_savesuccess, message_savefailure;

    public static void SendMessage(String type, Player player, PlayerLoginEvent event) {
        if (type.equals("message_default")) {
            player.sendMessage(Util.replaceStrings(message_default, player, null));
        } else if (type.equals("message_autodetect")) {
            player.sendMessage(Util.replaceStrings(message_autodetect, player, null));
        } else if (type.equals("message_usage")) {
            player.sendMessage(Util.replaceStrings(message_usage, player, null));
        } else if (type.equals("message_notsupported")) {
            player.sendMessage(Util.replaceStrings(message_notsupported, player, null));
        } else if (type.equals("message_nottranslated")) {
            player.sendMessage(Util.replaceStrings(message_nottranslated, player, null));
        } else if (type.equals("message_savesuccess")) {
            player.sendMessage(Util.replaceStrings(message_savesuccess, player, null));
        } else if (type.equals("message_savefailure")) {
            player.sendMessage(Util.replaceStrings(message_savefailure, player, null));
        }
    }
}
