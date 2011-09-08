/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.managers.PlayerManager;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.maxmind.geoip.LookupService;

public class Util {
	
	PlayerManager playerManager = new PlayerManager();

    int hexToInt(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }

        ch = Character.toUpperCase(ch);
        if (ch >= 'A' && ch <= 'F') {
            return ch - 'A' + 0xA;
        }

        throw new IllegalArgumentException("Not a hex character: " + ch);
    }

    String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    String bytes2hex(byte[] bytes) {
        StringBuffer r = new StringBuffer(32);
        for (int i = 0; i < bytes.length; i++) {
            String x = Integer.toHexString(bytes[i] & 0xff);
            if (x.length() < 2) {
                r.append("0");
            }
            r.append(x);
        }
        return r.toString();
    }

    public String Capitalize(String string) {
        String result = "";
        Scanner scn = new Scanner(string);
        while (scn.hasNext()) {
            String next = scn.next();
            result += Character.toString(next.charAt(0)).toUpperCase();
            for (int i = 1; i < next.length(); i++) {
                result += Character.toString(next.charAt(i));
            }
        }
        return result;
    }

    public boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public String replaceStrings(String string, Player player, String additional) {
        string = string.replaceAll("\\{IP\\}", playerManager.getIP(player));
        string = string.replaceAll("\\{PLAYER\\}", player.getName());
        string = string.replaceAll("\\{NEWPLAYER\\}", "");
        string = string.replaceAll("\\{PLUGIN\\}", Variables.pluginName);
        string = string.replaceAll("\\{VERSION\\}", Variables.pluginVersion);
        string = string.replaceAll("\\{COUNTRY\\}", "");
        
        string = string.replaceAll("&", "§");

        /// COLORS
        string = string.replaceAll("\\<BLACK\\>", "§0");
        string = string.replaceAll("\\<NAVY\\>", "§1");
        string = string.replaceAll("\\<GREEN\\>", "§2");
        string = string.replaceAll("\\<BLUE\\>", "§3");
        string = string.replaceAll("\\<RED\\>", "§4");
        string = string.replaceAll("\\<PURPLE\\>", "§5");
        string = string.replaceAll("\\<GOLD\\>", "§6");
        string = string.replaceAll("\\<LIGHTGRAY\\>", "§7");
        string = string.replaceAll("\\<GRAY\\>", "§8");
        string = string.replaceAll("\\<DARKPURPLE\\>", "§9");
        string = string.replaceAll("\\<LIGHTGREEN\\>", "§a");
        string = string.replaceAll("\\<LIGHTBLUE\\>", "§b");
        string = string.replaceAll("\\<ROSE\\>", "§c");
        string = string.replaceAll("\\<LIGHTPURPLE\\>", "§d");
        string = string.replaceAll("\\<YELLOW\\>", "§e");
        string = string.replaceAll("\\<WHITE\\>", "§f");

        /// colors
        string = string.replaceAll("\\<black\\>", "§0");
        string = string.replaceAll("\\<navy\\>", "§1");
        string = string.replaceAll("\\<green\\>", "§2");
        string = string.replaceAll("\\<blue\\>", "§3");
        string = string.replaceAll("\\<red\\>", "§4");
        string = string.replaceAll("\\<purple\\>", "§5");
        string = string.replaceAll("\\<gold\\>", "§6");
        string = string.replaceAll("\\<lightgray\\>", "§7");
        string = string.replaceAll("\\<gray\\>", "§8");
        string = string.replaceAll("\\<darkpurple\\>", "§9");
        string = string.replaceAll("\\<lightgreen\\>", "§a");
        string = string.replaceAll("\\<lightblue\\>", "§b");
        string = string.replaceAll("\\<rose\\>", "§c");
        string = string.replaceAll("\\<lightpurple\\>", "§d");
        string = string.replaceAll("\\<yellow\\>", "§e");
        string = string.replaceAll("\\<white\\>", "§f");
        return string;
    }
}
