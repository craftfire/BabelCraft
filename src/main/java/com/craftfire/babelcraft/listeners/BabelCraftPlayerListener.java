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

import com.google.api.translate.Language;

public class BabelCraftPlayerListener extends PlayerListener {
    private final BabelCraft plugin;
    String TempPrefix;
    public BabelCraftPlayerListener(BabelCraft instance) {
        this.plugin = instance;
        if (Config.plugintag) {
            TempPrefix = Config.plugin_prefix;
        } else {
            TempPrefix = Config.nextline_tag;
        }
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Util.PlayerDatabase("check", event.getPlayer(), null, null)) {
            Language lang = Util.GetPlayerLanguageHash(event.getPlayer());
            event.getPlayer().sendMessage(Config.plugin_prefix+"Your current language is §c" + Util.LanguageName("" + lang) + "§f.");
            event.getPlayer().sendMessage(TempPrefix + "To change this, use /lang <language>");
        } else {
            if (!Util.ToFile("check", event.getPlayer().getName().toLowerCase(), null)) {
                String CountryCode = Util.GetCountryCode(Util.GetIP(event.getPlayer()));
                if (CountryCode.equals("--")) {
                    CountryCode = "localhost";
                }
                Util.ToFile("write", event.getPlayer().getName().toLowerCase() + ":" + Util.LanguageCode(Config.language_default).toLowerCase(), null);
                Util.PlayerDatabase("add", event.getPlayer(), Config.language_default, CountryCode);
                event.getPlayer().sendMessage(Config.plugin_prefix + "Your language has been set to §c" + Util.LanguageName(Config.language_default) + "§f");
                event.getPlayer().sendMessage(TempPrefix + "To change this, use /lang <language>");
            } else {
                Language lang = Util.GetLanguage(event.getPlayer(),null);
                event.getPlayer().sendMessage(Config.plugin_prefix + "Your current language is §c" + Util.LanguageName("" + lang) + "§f.");
                event.getPlayer().sendMessage(TempPrefix + "To change this, use /lang <language>");
            }
        }
        // else {
        //     BabelCraft.ToFile("remove", event.getPlayer().getName().toLowerCase(),null);
        // }
    }

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().split(" ");
        Player player = event.getPlayer();

        if (split[0].equals("/language") || split[0].equals("/lang")) {
            if (split.length == 2 || split.length == 3) {
                String parameter1 = split[1];
                if (Util.isNumeric(parameter1)) {
                    int counter = 0;
                    int page = Integer.parseInt(parameter1);
                    int x1;
                    if (page == 1) {
                        x1 = 1;
                    } else {
                        x1 = 10 * page;
                    }
                    int x2 = x1 + 8;
                    player.sendMessage(Config.plugin_prefix + "§c------------------Page " + page + "------------------");

                    for (Language l : Language.values()) {
                        if (counter >= x1 && counter <= x2) {
                            player.sendMessage(Config.plugin_prefix + Util.Capitalize(l.name().toLowerCase()) + " - " + l);
                        }
                        counter++;
                    }
                } else if (parameter1.equals("search") || parameter1.equals("s")) {
                    if (split.length == 3)  {
                        int counter = 0;
                        String parameter2 = split[2];
                        player.sendMessage(Config.plugin_prefix + "§c--------Searching results for '" + parameter2 + "'----------");
                        for (Language l : Language.values()) {
                            if (!l.name().toLowerCase().equals("auto_detect")) {
                                if (l.name().toLowerCase().startsWith(parameter2.toLowerCase()))  {
                                    player.sendMessage(TempPrefix + Util.Capitalize(l.name().toLowerCase()) + " - " + l);
                                    counter++;
                                } else if (l.toString().toLowerCase().startsWith(parameter2.toLowerCase())) {
                                    player.sendMessage(TempPrefix + Util.Capitalize(l.name().toLowerCase()) + " - " + l);
                                    counter++;
                                }
                            }
                        }
                        if (counter == 0) {
                            player.sendMessage(TempPrefix + "Could not find any languages for string '" + parameter2 + "'");
                        }
                    } else {
                        player.sendMessage(Config.plugin_prefix + "Correct usage is: /lang search <String>");
                    }
                } else if (Util.IsLanguageSupported(parameter1)) {
                    if (!Util.ToFile("check", event.getPlayer().getName().toLowerCase(), null)) {
                        Util.ToFile("write", event.getPlayer().getName().toLowerCase() + ":" + parameter1, null);
                        String CountryCode = Util.GetCountryCode(Util.GetIP(event.getPlayer()));
                        if (CountryCode.equals("--")) {
                            CountryCode = "localhost";
                        }
                        Util.PlayerDatabase("add", event.getPlayer(), parameter1, CountryCode);
                    } else {
                        String CountryCode = Util.GetCountryCode(Util.GetIP(event.getPlayer()));
                        if (CountryCode.equals("--")) {
                            CountryCode = "localhost";
                        }
                        Util.PlayerDatabase("add", event.getPlayer(), parameter1, CountryCode);
                        Util.ToFile("change", event.getPlayer().getName().toLowerCase(),parameter1);
                        player.sendMessage(Config.plugin_prefix + "Successfully changed your chat language to");
                        player.sendMessage(TempPrefix + "§c" + Util.LanguageName(parameter1) + "§f!");
                    }
                } else {
                    player.sendMessage(Config.plugin_prefix + "Unsupported language!");
                    player.sendMessage(TempPrefix + "Use /lang <pagenumber> for a list.");
                    player.sendMessage(TempPrefix + "Or search with /lang search <string>");
                }
            } else {
                boolean isin = Util.PlayerDatabase("check", event.getPlayer(),null, null);
                Language lang;
                if (isin) {
                    lang = Util.GetPlayerLanguageHash(event.getPlayer());
                } else {
                    lang = Util.GetLanguage(event.getPlayer(), "to");
                }

                player.sendMessage(Config.plugin_prefix + "Your current language is §c" + Util.LanguageName("" + lang) + "§f.");
                player.sendMessage(TempPrefix + "To change this, use /lang <language>");
                player.sendMessage(TempPrefix + "To list the languages, use /lang <pagenumber>");
                player.sendMessage(TempPrefix + "To search, use /lang search <string>");
            }
            event.setCancelled(true);
        }
    }

    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.isCancelled()) {
            boolean isin = Util.PlayerDatabase("check", event.getPlayer(), null, null);
            Language langfrom;
            if (isin) {
                langfrom = Util.GetPlayerLanguageHash(event.getPlayer());
            } else {
                langfrom = Util.GetLanguage(event.getPlayer(), "from");
            }
            // int counter = 0;
            if (Config.language_serverforced) {
                Language langto = Language.fromString(Config.language_default.toLowerCase());
                String NewMessage = Util.Translate(event.getMessage(), langfrom,langto);
                /* if (counter == 0 && NewMessage.equals(event.getMessage())) {
                    event.getPlayer().sendMessage(Config.plugin_prefix + "The message below could not be translated.");
                    counter++;
                } */
                // Config.Server.broadcastMessage(event.getPlayer().getName() + ": " + NewMessage);
                event.setMessage(NewMessage);
                // Config.log.info(event.getPlayer().getName() + ": " + NewMessage);
            } else if (Config.language_playerset) {
                int tempcounter = 0;
                /* if (this.plugin.zHeroChat) {
                    HashMap<String, String> languages = new HashMap<String, String>();
                    JavaPlugin plugin = (JavaPlugin) Config.Server.getPluginManager().getPlugin("HeroChat");
                    HeroChat herochat = (HeroChat) plugin;
                    ChannelManager channelManager = herochat.getChannelManager();
                    Channel channel = channelManager.getActiveChannel(event.getPlayer().getName());
                    List<String> players = channel.getPlayers();
                    for (String theplayer : players) {
                        for (Player player : Config.Server.getOnlinePlayers()) {
                            if (player.getName().equals(theplayer)) {
                                boolean isin2 = Util.PlayerDatabase("check", player, null, null);
                                Language langto;
                                if (isin2) {
                                    langto = Util.GetPlayerLanguageHash(player);
                                } else {
                                    langto = Util.GetLanguage(player,"to");
                                }
                                String NewMessage = null;
                                if (langfrom.equals(langto)) {
                                    NewMessage = event.getMessage();
                                    channel.sendMessage(event.getPlayer().getName(), NewMessage, channel.getMsgFormat(), players, true, true);
                                } else {
                                    NewMessage = Util.Translate(event.getMessage(), langfrom,langto);
                                    channel.sendMessage(event.getPlayer().getName(), NewMessage, channel.getMsgFormat(), players, true, true);
                                }
                            }
                        }
                    }
                } else { */
                for (Player player : Config.Server.getOnlinePlayers()) {
                    boolean isin2 = Util.PlayerDatabase("check", player, null, null);
                    Language langto;
                    if (isin2) {
                        langto = Util.GetPlayerLanguageHash(player);
                    } else {
                        langto = Util.GetLanguage(player, "to");
                    }
                    String NewMessage = null;
                    if (langfrom.equals(langto)) {
                        NewMessage = event.getMessage();
                        player.sendMessage(event.getPlayer().getName() + ": " + NewMessage);
                    } else {
                        NewMessage = Util.Translate(event.getMessage(), langfrom,langto);
                        player.sendMessage(event.getPlayer().getName() + ": " + NewMessage);
                    }
                    if (tempcounter == 0) {
                        Config.log.info(event.getPlayer().getName() + ": " + NewMessage);
                        tempcounter++;
                    }
                    /* if (counter == 0 && NewMessage.equals(event.getMessage()) && player != event.getPlayer()) {
                        event.getPlayer().sendMessage(Config.plugin_prefix+"The message below could not be translated.");
                        counter++;
                    } */
                }
            }
        }
        event.setCancelled(true);
    }
}
