package com.craftfire.babelcraft.listeners;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.craftfire.babelcraft.BabelCraft;

public class BabelCraftServerListener extends ServerListener
{
  private BabelCraft plugin;

  public BabelCraftServerListener(BabelCraft plugin)
  {
    this.plugin = plugin;
  }

  public void onPluginEnable(PluginEnableEvent event) 
  {
    Plugin plugin = event.getPlugin();
    String name = plugin.getDescription().getName();

    if (name.equals("HeroChat")) 
    	this.plugin.zHeroChat = true;
  }
  
}