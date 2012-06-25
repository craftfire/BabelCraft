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

import java.util.Scanner;

import org.bukkit.entity.Player;

import com.craftfire.babelcraft.util.managers.Managers;

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
        string = string.replaceAll("\\{IP\\}", Managers.player.getIP(player));
        string = string.replaceAll("\\{PLAYER\\}", player.getName());
        string = string.replaceAll("\\{NEWPLAYER\\}", "");
        string = string.replaceAll("\\{PLUGIN\\}", Variables.pluginName);
        string = string.replaceAll("\\{VERSION\\}", Variables.pluginVersion);
        string = string.replaceAll("\\{COUNTRY\\}", "");

        return string;
    }
}
