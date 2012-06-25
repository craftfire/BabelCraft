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
package com.craftfire.babelcraft.listeners;

import com.google.api.translate.Language;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.craftfire.babelcraft.BabelCraft;
import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;
import com.craftfire.babelcraft.util.databases.EBean;
import com.craftfire.babelcraft.util.managers.Managers;

public class BabelCraftPlayerListener extends PlayerListener {
    Util util = new Util();
    String TempPrefix;
    private final BabelCraft plugin;

    public BabelCraftPlayerListener(BabelCraft instance) {
        this.plugin = instance;
        if (Variables.plugintag) {
            TempPrefix = Variables.plugin_prefix;
        } else {
            TempPrefix = Variables.nextline_tag;
        }
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Managers.player.checkPlayer(event.getPlayer(), true);
        Managers.player.checkIP(event.getPlayer().getName(), Managers.player.getIP(event.getPlayer()));
        event.getPlayer().sendMessage(Variables.plugin_prefix + "Your current language is §c" + Managers.player.getLanguageString(event.getPlayer()) + "§f.");
        event.getPlayer().sendMessage(TempPrefix + "To change this, use /lang <language>");
    }

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().split(" ");
        Player player = event.getPlayer();

        if (split[0].equals("/language") || split[0].equals("/lang")) {
            if (split.length == 2 || split.length == 3) {
                String parameter1 = split[1];
                if (util.isNumeric(parameter1)) {
                    int counter = 0;
                    int page = Integer.parseInt(parameter1);
                    int x1;
                    if (page == 1) {
                        x1 = 1;
                    } else {
                        x1 = 10 * page;
                    }
                    int x2 = x1 + 8;
                    player.sendMessage(Variables.plugin_prefix + "§c------------------Page " + page + "------------------");

                    for (Language l : Language.values()) {
                        if (counter >= x1 && counter <= x2) {
                            player.sendMessage(Variables.plugin_prefix + util.capitalize(l.name().toLowerCase()) + " - " + l);
                        }
                        counter++;
                    }
                } else if (parameter1.equals("search") || parameter1.equals("s")) {
                    if (split.length == 3)  {
                        int counter = 0;
                        String parameter2 = split[2];
                        player.sendMessage(Variables.plugin_prefix + "§c--------Searching results for '" + parameter2 + "'----------");
                        for (Language l : Language.values()) {
                            if (!l.name().toLowerCase().equals("auto_detect")) {
                                if (l.name().toLowerCase().startsWith(parameter2.toLowerCase()))  {
                                    player.sendMessage(TempPrefix + util.capitalize(l.name().toLowerCase()) + " - " + l);
                                    counter++;
                                } else if (l.toString().toLowerCase().startsWith(parameter2.toLowerCase())) {
                                    player.sendMessage(TempPrefix + util.capitalize(l.name().toLowerCase()) + " - " + l);
                                    counter++;
                                }
                            }
                        }
                        if (counter == 0) {
                            player.sendMessage(TempPrefix + "Could not find any languages for string '" + parameter2 + "'");
                        }
                    } else {
                        player.sendMessage(Variables.plugin_prefix + "Correct usage is: /lang search <String>");
                    }
                } else if (Managers.translation.isLanguageSupported(parameter1)) {
                        EBean eBeanClass = Managers.player.checkPlayer(event.getPlayer(), true);
                        eBeanClass.setLanguage(Managers.translation.languageName(parameter1));
                        eBeanClass.save(eBeanClass);
                        player.sendMessage(Variables.plugin_prefix + "Successfully changed your chat language to");
                        player.sendMessage(TempPrefix + "§c" + Managers.translation.languageName(parameter1) + "§f!");
                    }
                } else {
                    player.sendMessage(Variables.plugin_prefix + "Unsupported language!");
                    player.sendMessage(TempPrefix + "Use /lang <pagenumber> for a list.");
                    player.sendMessage(TempPrefix + "Or search with /lang search <string>");
                }
            } else {
                String lang = Managers.player.getLanguageString(event.getPlayer());
                player.sendMessage(Variables.plugin_prefix + "Your current language is §c" + lang + "§f.");
                player.sendMessage(TempPrefix + "To change this, use /lang <language>");
                player.sendMessage(TempPrefix + "To list the languages, use /lang <pagenumber>");
                player.sendMessage(TempPrefix + "To search, use /lang search <string>");
            }
            event.setCancelled(true);
        }

    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Language langfrom = Managers.player.getLanguage(event.getPlayer());
        if (Config.language_serverforced) {
            Language langto = Language.fromString(Config.language_default.toLowerCase());
            Managers.logging.debug("Translating from language: " + langfrom.toString());
            Managers.logging.debug("Translating to language: " + langto.toString());
            String NewMessage = Managers.translation.translate(event.getMessage(), langfrom, langto);
            event.setMessage(NewMessage);
            event.setCancelled(true);
        } else if (Config.language_playerset) {
            int tempcounter = 0;
            for (Player player : event.getRecipients()) {
                Language langto = Managers.player.getLanguage(player);
                Managers.logging.debug("Translating from language: " + langfrom.toString());
                Managers.logging.debug("Translating to language: " + langto.toString());
                String newMessage = null;
                if (langfrom.toString().equalsIgnoreCase(langto.toString())) {
                    Managers.logging.debug(event.getPlayer().getDisplayName() + "'s language is the same as " + player.getDisplayName() + "'s language, no need to translate.");
                    newMessage = event.getMessage();
                } else {
                    Managers.logging.debug(event.getPlayer().getDisplayName() + "'s language is NOT the same as " + player.getDisplayName() + "'s language, translating.");
                    newMessage = Managers.translation.translate(event.getMessage(), langfrom, langto);
                }
                String format = event.getFormat();
                Managers.logging.debug("Message format: " + format);
                format = format.replace(event.getMessage(), newMessage);
                format = format.replace("%1$s", event.getPlayer().getDisplayName());
                format = format.replace("%2$s", newMessage);
                player.sendMessage(format);
                if (tempcounter == 0) {
                    Managers.logging.info(event.getPlayer().getName() + ": " + newMessage);
                    tempcounter++;
                }
            }
            event.setCancelled(true);
        }
    }
}
