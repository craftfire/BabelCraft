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
        player.sendMessage(Util.replaceStrings(message.text, player, null));
    }
}
