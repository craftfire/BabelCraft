/**          (C) Copyright 2011 Contex <contexmoh@gmail.com>
	
This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ 
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.

**/
package com.craftfire.babelcraft.util;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public class Messages
{
///////////////////////////////////////////
//  Core
///////////////////////////////////////////	

	///////////////////////////////////////////
	//               BabelCraft
	///////////////////////////////////////////
	
		///////////////////////////////////////////
		//               language
		///////////////////////////////////////////

		public static String BabelCraft_message_default,BabelCraft_message_autodetect,BabelCraft_message_usage,BabelCraft_message_notsupported,BabelCraft_message_nottranslated,BabelCraft_message_savesuccess,BabelCraft_message_savefailure;
			
			
	public static void SendMessage(String type,Player player,PlayerLoginEvent event)
	{
		if(type.equals("BabelCraft_message_default")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_default,player,null));
		}
		else if(type.equals("BabelCraft_message_autodetect")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_autodetect,player,null));
		}
		else if(type.equals("BabelCraft_message_usage")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_usage,player,null));
		}
		else if(type.equals("BabelCraft_message_notsupported")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_notsupported,player,null));
		}
		else if(type.equals("BabelCraft_message_nottranslated")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_nottranslated,player,null));
		}
		else if(type.equals("BabelCraft_message_savesuccess")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_savesuccess,player,null));
		}
		else if(type.equals("BabelCraft_message_savefailure")) 
		{
			player.sendMessage(Util.replaceStrings(BabelCraft_message_savefailure,player,null));
		}
	}
}