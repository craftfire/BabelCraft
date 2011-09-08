package com.craftfire.babelcraft.util;

import java.util.HashMap;

import org.bukkit.Server;

import com.craftfire.babelcraft.BabelCraft;

public class Variables {
    public static HashMap<String, String> playerdatabase = new HashMap<String, String>();
    public static String plugin_prefix = "§cBabel§fCraft §7> §f"; // TODO: Add option to customize in messages.yml
    public static String nextline_tag = "                 ";
    public static boolean plugintag = false;
    public static String pluginName, pluginVersion, pluginWebsite, pluginDescrption;
    public static Server server;
    public static BabelCraft plugin;
}
