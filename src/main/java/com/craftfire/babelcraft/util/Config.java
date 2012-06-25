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

import java.io.File;

import org.bukkit.util.config.Configuration;

public class Config {
    public static boolean plugin_debugmode, plugin_usagestats, plugin_logging;
    public static String plugin_language_commands, plugin_language_messages, plugin_logformat;
    public static String language_default;
    public static boolean language_serverforced, language_playerset;

    public Configuration template = null;

    public Config(String config, String directory, String filename) {
        template = new Configuration(new File(directory, filename));
        template.load();
        if (config.equals("config")) {
            plugin_language_commands = GetConfigString("plugin.language.commands", "English");
            plugin_language_messages = GetConfigString("plugin.language.messages", "English");
            plugin_debugmode = GetConfigBoolean("plugin.debugmode", false);
            plugin_usagestats = GetConfigBoolean("plugin.usagestats", true);
            plugin_logging = GetConfigBoolean("plugin.logging", true);
            plugin_logformat = GetConfigString("plugin.logformat", "yyyy-MM-dd");

            language_default = GetConfigString("plugin.language.default", "English");
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

    public String GetConfigString(String key, String defaultvalue) {
        return template.getString(key, defaultvalue);
    }

    public boolean GetConfigBoolean(String key, boolean defaultvalue) {
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
