/**          (C) Copyright 2011 Contex <contexmoh@gmail.com>
	
This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ 
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.

**/

package com.craftfire.babelcraft.util;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;


public class Config 
{
	///////////////////////////////////////////
	//               GLOBAL
	///////////////////////////////////////////
	//
    public static org.bukkit.Server Server;
	public static String pluginname = "BabelCraft";
	public static String pluginversion = "1.1.0";
	//
	public static Logger log = Logger.getLogger("Minecraft");
	public static String players = "players.db";
	public static HashMap<String, String> playerdatabase = new HashMap<String, String>();
	
	public static String dbfile = "plugins/"+pluginname+"/GeoIP.dat"; 
	
	
	public static String plugin_prefix = "§cBabel§fCraft §7> §f";
	public static String nextline_tag = "                 ";
	public static boolean plugintag = false;
	///////////////////////////////////////////
	//               Core
	///////////////////////////////////////////
	
		///////////////////////////////////////////
		//               BabelCraft
		///////////////////////////////////////////
		
		///////////////////////////////////////////
		//               plugin
		///////////////////////////////////////////
		public static boolean plugin_debugmode,plugin_usagestats;
	
			///////////////////////////////////////////
			//               language
			///////////////////////////////////////////
			public static String language_default;
			public static boolean language_serverforced,language_playerset;
		
	  public static Configuration template = null;

	  public Config(String config, String directory, String filename) {
		  template = new Configuration(new File(directory, filename));
		  template.load();
		if(config.equals("config")) 
		{
			///////////////////////////////////////////
			//               Core
			///////////////////////////////////////////
			
				///////////////////////////////////////////
				//               BabelCraft
				///////////////////////////////////////////
				
					///////////////////////////////////////////
					//               plugin
					///////////////////////////////////////////
					plugin_debugmode = GetConfigBoolean("Core.plugin.debugmode", false);
					plugin_usagestats = GetConfigBoolean("Core.plugin.usagestats", true);
			
					///////////////////////////////////////////
					//               language
					///////////////////////////////////////////
					language_default = GetConfigString("Core.language.default", "English");
					language_serverforced = GetConfigBoolean("Core.language.serverforced", false);
					language_playerset = GetConfigBoolean("Core.language.playerset", true);
		}
		else if(config.equals("messages")) 
		{
			///////////////////////////////////////////
			//               Core
			///////////////////////////////////////////
			
				///////////////////////////////////////////
				//               BabelCraft
				///////////////////////////////////////////
				
					///////////////////////////////////////////
					//               language
					///////////////////////////////////////////
					/*Messages.BabelCraft_message_default = GetConfigString("Core.BabelCraft.language.default", "English");
					Messages.BabelCraft_message_autodetect = GetConfigString("Core.BabelCraft.language.autodetect", false);
					Messages.BabelCraft_message_usage = GetConfigString("Core.BabelCraft.language.usage", true);
					Messages.BabelCraft_message_notsupported = GetConfigString("Core.BabelCraft.language.notsupported", "English");
					Messages.BabelCraft_message_nottranslated = GetConfigString("Core.BabelCraft.language.mottranslated", false);
					Messages.BabelCraft_message_savesuccess = GetConfigString("Core.BabelCraft.language.savesuccess", true);
					Messages.BabelCraft_message_savefailure = GetConfigString("Core.BabelCraft.language.savefailuret", true);*/
		}

	  }

	  public static String GetConfigString(String key, String defaultvalue)
	  {
	    return template.getString(key, defaultvalue);
	  }
	  
	  public static boolean GetConfigBoolean(String key, boolean defaultvalue)
	  {
	    return template.getBoolean(key, defaultvalue);
	  }
	  
	  public void DeleteConfigValue(String key) 
	  {
		template.removeProperty(key);
	  }

	  public String raw(String key, String line)
	  {
	    return template.getString(key, line);
	  }

	  public void save(String key, String line) {
		  template.setProperty(key, line);
	  }
}
