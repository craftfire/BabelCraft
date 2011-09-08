/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.util;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class Config {
    ///////////////////////////////////////////
    //               GLOBAL
    ///////////////////////////////////////////
    public static org.bukkit.Server Server;
    public static String pluginname = "BabelCraft"; // TODO: pull plugin name from plugin.yml
    public static String pluginversion = "1.1.0"; // TODO: pull plugin version from plugin.yml

    public static Logger log = Logger.getLogger("Minecraft");
    public static String players = "players.db";  // TODO: Move to eBeans persistence
    public static HashMap<String, String> playerdatabase = new HashMap<String, String>();

    public static String dbfile = "plugins/" + pluginname + "/data/GeoIP.dat"; // TODO: Create variable for data folder?

    public static String plugin_prefix = "§cBabel§fCraft §7> §f"; // TODO: Add option to customize in messages.yml
    public static String nextline_tag = "                 ";
    public static boolean plugintag = false;

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
        if (config.equals("config")) {
            ///////////////////////////////////////////
            //               plugin
            ///////////////////////////////////////////
            plugin_debugmode = GetConfigBoolean("plugin.debugmode", false);
            plugin_usagestats = GetConfigBoolean("plugin.usagestats", true);

            ///////////////////////////////////////////
            //               language
            ///////////////////////////////////////////
            language_default = GetConfigString("language.default", "English");
            language_serverforced = GetConfigBoolean("language.serverforced", false);
            language_playerset = GetConfigBoolean("language.playerset", true);
        } else if (config.equals("messages")) {
            ///////////////////////////////////////////
            //               language
            ///////////////////////////////////////////
            /* Messages.message_default = GetConfigString("language.default", "English");
            Messages.message_autodetect = GetConfigString("language.autodetect", false);
            Messages.message_usage = GetConfigString("language.usage", true);
            Messages.message_notsupported = GetConfigString("language.notsupported", "English");
            Messages.message_nottranslated = GetConfigString("language.mottranslated", false);
            Messages.message_savesuccess = GetConfigString("language.savesuccess", true);
            Messages.message_savefailure = GetConfigString("language.savefailuret", true); */
        }
    }

    public static String GetConfigString(String key, String defaultvalue) {
        return template.getString(key, defaultvalue);
    }

    public static boolean GetConfigBoolean(String key, boolean defaultvalue) {
        return template.getBoolean(key, defaultvalue);
    }

    public void DeleteConfigValue(String key) {
        template.removeProperty(key);
    }

    public String raw(String key, String line) {
        return template.getString(key, line);
    }

    public void save(String key, String line) {
        template.setProperty(key, line);
    }
}
