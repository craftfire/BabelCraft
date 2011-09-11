/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.listeners;

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
import com.craftfire.babelcraft.util.managers.LoggingManager;
import com.craftfire.babelcraft.util.managers.PlayerManager;
import com.craftfire.babelcraft.util.managers.TranslationManager;

import com.google.api.translate.Language;

public class BabelCraftPlayerListener extends PlayerListener {
	TranslationManager translation = new TranslationManager();
	PlayerManager playerManager = new PlayerManager();
	Util util = new Util();
	LoggingManager logging = new LoggingManager();
    String TempPrefix;
    
    public BabelCraftPlayerListener(BabelCraft instance) {
        if (Variables.plugintag) {
            TempPrefix = Variables.plugin_prefix;
        } else {
            TempPrefix = Variables.nextline_tag;
        }
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
    	playerManager.checkIP(event.getPlayer().getName(), playerManager.getIP(event.getPlayer()));
        event.getPlayer().sendMessage(Variables.plugin_prefix + "Your current language is §c" + playerManager.getLanguageString(event.getPlayer()) + "§f.");
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
                } else if (translation.isLanguageSupported(parameter1)) {
                		EBean eBeanClass = playerManager.checkPlayer(event.getPlayer(), true);
                		eBeanClass.setLanguage(translation.languageName(parameter1));
                		eBeanClass.save(eBeanClass);
                        player.sendMessage(Variables.plugin_prefix + "Successfully changed your chat language to");
                        player.sendMessage(TempPrefix + "§c" + translation.languageName(parameter1) + "§f!");
                    }
                } else {
                    player.sendMessage(Variables.plugin_prefix + "Unsupported language!");
                    player.sendMessage(TempPrefix + "Use /lang <pagenumber> for a list.");
                    player.sendMessage(TempPrefix + "Or search with /lang search <string>");
                }
            } else {
                String lang = playerManager.getLanguageString(event.getPlayer());
                player.sendMessage(Variables.plugin_prefix + "Your current language is §c" + lang + "§f.");
                player.sendMessage(TempPrefix + "To change this, use /lang <language>");
                player.sendMessage(TempPrefix + "To list the languages, use /lang <pagenumber>");
                player.sendMessage(TempPrefix + "To search, use /lang search <string>");
            }
            event.setCancelled(true);
        }
    
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) { return; }
        Language langfrom = playerManager.getLanguage(event.getPlayer());
        if (Config.language_serverforced) {
            Language langto = Language.fromString(Config.language_default.toLowerCase());
            String NewMessage = translation.translate(event.getMessage(), langfrom, langto);
            event.setMessage(NewMessage);
        } else if (Config.language_playerset) {
            int tempcounter = 0;
            for (Player player : Variables.server.getOnlinePlayers()) {
                Language langto = playerManager.getLanguage(player);
                String NewMessage = null;
                if (langfrom.equals(langto)) {
                    NewMessage = event.getMessage();
                    player.sendMessage(event.getPlayer().getName() + ": " + NewMessage);
                } else {
                    NewMessage = translation.translate(event.getMessage(), langfrom, langto);
                    player.sendMessage(event.getPlayer().getName() + ": " + NewMessage);
                }
                if (tempcounter == 0) {
                	logging.info(event.getPlayer().getName() + ": " + NewMessage);
                    tempcounter++;
                }
                /* if (counter == 0 && NewMessage.equals(event.getMessage()) && player != event.getPlayer()) {
                    event.getPlayer().sendMessage(Config.plugin_prefix+"The message below could not be translated.");
                    counter++;
                } */
            }
        }
    }
}
