package com.craftfire.babelcraft.util;

import java.util.HashMap;

import org.bukkit.Server;

import com.avaje.ebean.EbeanServer;
import com.craftfire.babelcraft.BabelCraft;
import com.craftfire.babelcraft.util.managers.LoggingManager;
import com.craftfire.babelcraft.util.managers.PlayerManager;
import com.craftfire.babelcraft.util.managers.TranslationManager;

public class Variables {
    public static HashMap<String, String> player_languages = new HashMap<String, String>();
    public static HashMap<String, String> player_country_codes = new HashMap<String, String>();
    public static String plugin_prefix = "§cBabel§fCraft §7> §f"; // TODO: Add option to customize in messages.yml
    public static String nextline_tag = "                 ";
    public static boolean plugintag = false;
    public static String pluginName, pluginVersion, pluginWebsite, pluginDescrption;
    public static Server server;
    public static BabelCraft plugin;
	public static EbeanServer database;
    public static String dbfile = plugin.getDataFolder() + "/data/GeoIP.dat";
	public static PlayerManager playerManager = new PlayerManager();
	public static LoggingManager logging = new LoggingManager();
	public static TranslationManager translation = new TranslationManager();
}
