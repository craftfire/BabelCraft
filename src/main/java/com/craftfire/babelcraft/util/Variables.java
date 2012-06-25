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

import java.util.HashMap;

import com.avaje.ebean.EbeanServer;

import org.bukkit.Server;

import com.craftfire.babelcraft.BabelCraft;

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
    public static String dbfile = "/plugins/" + pluginName + "/data/GeoIP.dat";
}
