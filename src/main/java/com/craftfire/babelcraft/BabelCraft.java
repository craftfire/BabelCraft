/**
(C) Copyright 2011 CraftFire <dev@craftfire.com>
Contex <contex@craftfire.com>, Wulfspider <wulfspider@craftfire.com>

This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/
or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
**/

package com.craftfire.babelcraft;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.craftfire.babelcraft.listeners.BabelCraftPlayerListener;
import com.craftfire.babelcraft.listeners.BabelCraftServerListener;
import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Util;
import com.craftfire.babelcraft.util.Variables;
import com.craftfire.babelcraft.util.databases.EBean;
import com.craftfire.babelcraft.util.managers.CraftFireManager;
import com.craftfire.babelcraft.util.managers.LoggingManager;

public class BabelCraft extends JavaPlugin {
    private final BabelCraftPlayerListener playerListener = new BabelCraftPlayerListener(this);
    private final BabelCraftServerListener serverListener = new BabelCraftServerListener(this);
    public Logger log = Logger.getLogger("Minecraft");
    Util util = new Util();
    LoggingManager logging = new LoggingManager();
    CraftFireManager craftFire = new CraftFireManager();
    PluginDescriptionFile pluginFile = getDescription();

    public void onDisable() {
        
    }

    public void onEnable() {
    	Variables.server = getServer();
    	Variables.pluginName = getDescription().getName();
    	Variables.pluginVersion = getDescription().getVersion();
        Variables.pluginWebsite = getDescription().getWebsite();
        Variables.pluginDescrption = getDescription().getDescription();
        Variables.plugin = this;
        
        Variables.database = getDatabase();
        
        if (Config.plugin_usagestats) {
            Plugin[] plugins = Variables.server.getPluginManager().getPlugins();
            int counter = 0;
            String Plugins = "";
            while (plugins.length > counter) {
                Plugins += plugins[counter].getDescription().getName() + "&_&" + plugins[counter].getDescription().getVersion();
                if (plugins.length != (counter + 1)) {
                    Plugins += "*_*";
                }
                counter++;
            }

            String online = "" + getServer().getOnlinePlayers().length;
            String max = "" + getServer().getMaxPlayers();

            try {
            	craftFire.postInfo(getServer().getName(), getServer().getVersion(), Variables.pluginVersion, System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"), System.getProperty("java.version"), "", "", Plugins, online, max, Variables.server.getPort());
            } catch (IOException e1) {
                logging.debug("Could not send data to main server.");
            }
        }

        String fileName = "basic.yml";
        String configFile = "config";
        File f = new File(getDataFolder() + "/config/" + fileName);
        if (!f.exists()) {
            logging.info(fileName + " could not be found in " + getDataFolder() + "/config/! Creating " + fileName + "!");
            defaultFile(fileName, "config");
        }
        new Config(configFile, getDataFolder() + "/config/", fileName);
        
        loadYml("messages", getClass().getProtectionDomain().getCodeSource());
        loadYml("commands", getClass().getProtectionDomain().getCodeSource());
        setupDatabase();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Event.Priority.Low, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, this.serverListener, Event.Priority.Low, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this.playerListener, Event.Priority.High, this);

        logging.info(Variables.pluginVersion + " is enabled");
        logging.debug("Debug is ENABLED, get ready for some heavy spam");
        logging.info("developed by CraftFire <dev@craftfire.com>");
    }
    
    private void setupDatabase() {
        try {
            getDatabase().find(EBean.class).findRowCount();
        } catch (PersistenceException ex) {
            logging.info("Installing persistence database for " + Variables.pluginName + " due to first time usage");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(EBean.class);
        return list;
    }
    
    private void defaultFile(String name, String folder) {
        File actual = new File(getDataFolder() + "/" + folder + "/", name);
        File direc = new File(getDataFolder() + "/" + folder + "/", "");
        if (!direc.exists()) {
            if (direc.mkdir()) {
                logging.debug("Sucesfully created directory: "+direc);
            }
        }
        if (!actual.exists()) {
          java.io.InputStream input = getClass().getResourceAsStream("/files/" + folder + "/" + name);
          if (input != null) {
              java.io.FileOutputStream output = null;
            try {
              output = new java.io.FileOutputStream(actual);
              byte[] buf = new byte[8192];
              int length = 0;

              while ((length = input.read(buf)) > 0) {
                output.write(buf, 0, length);
              }

              System.out.println("[" + Variables.pluginName + "] Written default setup for " + name);
            } catch (Exception e) {
              logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
            } finally {
              try {
                if (input != null) {
                    input.close();
                }
              } catch (Exception e) {
                  logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
              }
              try {
                if (output != null) {
                  output.close();
                }
              } catch (Exception e) {
                  logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
              }
            }
          }
        }
    }
    
    void loadYml(String type, CodeSource src) {
        String language = "English";
        File languagesAll = new File(getDataFolder() + "/translations");
        if(!languagesAll.exists()) {
            if (languagesAll.mkdir()) {
                logging.debug("Sucesfully created directory: " + languagesAll);
            }
        }
        boolean set = false;
        File[] directories;
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };

        if (src != null) {
            try {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                ZipEntry ze = null;

                while ((ze = zip.getNextEntry()) != null) {
                    String directory = ze.getName();
                    if (directory.startsWith("files/translations/") && directory.endsWith(".yml") == false)  {
                        directory = directory.replace("files/translations/", "");
                        directory = directory.replace("/", "");
                        if (directory.equals("") == false) {
                            logging.debug("Directory: "+directory);
                            File f = new File(getDataFolder() + "/translations/" + directory + "/" + type + ".yml");
                            if (!f.exists()) {
                                logging.info(type + ".yml" + " could not be found in plugins/" + Variables.pluginName + "/translations/" + directory + "/! Creating " + type + ".yml");
                                defaultFile(type + ".yml","translations/" + directory + "");
                            }
                            if (type.equals("commands") && (Config.plugin_language_commands).equalsIgnoreCase(directory))  {
                                set = true;
                                language = directory;
                            } else if (type.equals("messages") && (Config.plugin_language_messages).equalsIgnoreCase(directory))  {
                                set = true;
                                language = directory;
                            }
                        }
                    }
                }
                zip.close();
            } catch (IOException e) {
                logging.stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
            }
        }

        directories = languagesAll.listFiles(fileFilter);
        if (directories.length > 0) { logging.debug("Found " + directories.length + " directories for " + type); }
        else { logging.error("Error! Could not find any directories for " + type); }
        if (!set) {
            for (int z=0; z<directories.length; z++) {
                if (type.equalsIgnoreCase("commands") && Config.plugin_language_commands.equalsIgnoreCase(directories[z].getName()))  {
                    set = true;
                    language = directories[z].getName();
                } else if (type.equalsIgnoreCase("messages") && Config.plugin_language_messages.equalsIgnoreCase(directories[z].getName()))  {
                    set = true;
                    language = directories[z].getName();
                }
            }
        }
        if (!set && type.equalsIgnoreCase("commands")) { logging.info("Could not find translation files for " + Config.plugin_language_commands + ", defaulting to " + language); }
        else if (!set && type.equalsIgnoreCase("messages")) { logging.info("Could not find translation files for " + Config.plugin_language_messages + ", defaulting to " + language); }
        else if (type.equalsIgnoreCase("commands")) { logging.info(type + " language set to " + Config.plugin_language_commands); }
        else if (type.equalsIgnoreCase("messages")) { logging.info(type + " language set to " + Config.plugin_language_messages); }
        new Config(type, getDataFolder() + "/translations/" + language + "/", type + ".yml");
    }
}
