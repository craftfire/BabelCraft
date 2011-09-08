/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.listeners;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.craftfire.babelcraft.BabelCraft;

public class BabelCraftServerListener extends ServerListener {
    private BabelCraft plugin;

    public BabelCraftServerListener(BabelCraft plugin) {
        this.plugin = plugin;
    }

    public void onPluginEnable(PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();
        String name = plugin.getDescription().getName();

        if (name.equals("HeroChat")) {
            this.plugin.zHeroChat = true;
        }
    }
}
