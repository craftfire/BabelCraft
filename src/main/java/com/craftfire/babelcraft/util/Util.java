/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.util;

import java.util.Scanner;

import org.bukkit.entity.Player;

public class Util {
	
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

    public String capitalize(String string) {
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
        string = string.replaceAll("\\{IP\\}", Variables.playerManager.getIP(player));
        string = string.replaceAll("\\{PLAYER\\}", player.getName());
        string = string.replaceAll("\\{NEWPLAYER\\}", "");
        string = string.replaceAll("\\{PLUGIN\\}", Variables.pluginName);
        string = string.replaceAll("\\{VERSION\\}", Variables.pluginVersion);
        string = string.replaceAll("\\{COUNTRY\\}", "");

        return string;
    }
}
