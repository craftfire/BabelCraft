/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft.util.databases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;
import com.craftfire.babelcraft.util.Variables;
import com.craftfire.babelcraft.util.managers.LoggingManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Entity()
@Table(name = "babelcraft_users")
public class EBean {
	
	LoggingManager logging = new LoggingManager();

    public static EBean checkPlayer(String player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayername(player);
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }

    public static EBean checkPlayer(Player player, boolean save) {
        EBean eBeanClass = Variables.database.find(EBean.class).where().ieq("playername", player.getName()).findUnique();
        if (eBeanClass == null) {
            eBeanClass = new EBean();
            eBeanClass.setPlayer(player);
            if (save) { save(eBeanClass); }
        }
        return eBeanClass;
    }

    public static void save(EBean eBeanClass) {
        Variables.database.save(eBeanClass);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String playername;
    private String ip;
    private String language;
    private String countrycode;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String ply) {
        this.playername = ply;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(playername);
    }

    public void setPlayer(Player player) {
        this.playername = player.getName();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

}
