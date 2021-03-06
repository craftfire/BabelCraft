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
package com.craftfire.babelcraft.util.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.plugin.Plugin;

import com.craftfire.babelcraft.BabelCraft;
import com.craftfire.babelcraft.util.Config;
import com.craftfire.babelcraft.util.Variables;

public class LoggingManager {
    BabelCraft plugin = Variables.plugin;
    String pluginName = Variables.pluginName;
    String logFolder = plugin.getDataFolder() + "/logs/";

    public static enum Type {
        error, debug, info, warning, servere;
    }

    /**
     * Prints debug messages if enabled to console and file.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void debug(String line) {
        if (Config.plugin_debugmode) {
            plugin.log.info("[" + pluginName + "] " + line);
            ToFile(Type.debug, "[" + pluginName + "] " + line, logFolder);
        }
    }

    /**
     * Print to console with info level.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void info(String line) {
        plugin.log.info("[" + pluginName + "] " + line);
    }

    /**
     * Print to console with severe level.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void severe(String line) {
        plugin.log.severe("[" + pluginName + "] " + line);
    }

    /**
     * Print to console with warning level.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void warning(String line) {
        plugin.log.warning("[" + pluginName + "] " + line);
    }

    /**
     * Prints out a nice advanced warning into the console.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void advancedWarning(String line) {
        plugin.log.warning("[" + pluginName + "]" + System.getProperty("line.separator")
        + "|-----------------------------------------------------------------------------|" + System.getProperty("line.separator")
        + "|--------------------------------AUTHDB WARNING-------------------------------|" + System.getProperty("line.separator")
        + "|-----------------------------------------------------------------------------|" + System.getProperty("line.separator")
        + "| " + line.toUpperCase() + System.getProperty("line.separator")
        + "|-----------------------------------------------------------------------------|");
    }

    /**
     * Prints out a plain warning into the console.
     *
     * @param line is the line to be printed.
     * @param pluginName is the prefix of the messages, for example [pluginName] line.
     */
    public void plainWarning(String line) {
        plugin.log.warning("[" + pluginName + "] " + line);
    }

    public void stackTrace(StackTraceElement[] stack, String function, int linenumber, String classname, String file) {
        advancedWarning("StackTrace Error");
        plainWarning("Class name: " + classname);
        plainWarning("File name: " + file);
        plainWarning("Function name: " + function);
        plainWarning("Error line: " + linenumber);
        if (Config.plugin_logging) {
            DateFormat LogFormat = new SimpleDateFormat(Config.plugin_logformat);
            Date date = new Date();
            plainWarning("Check log file: " + plugin.getDataFolder() + "\\logs\\error\\" + LogFormat.format(date) + "-error.log");
        } else {
            plainWarning("Enable logging in the config to get more information about the error.");
        }

        logError("--------------------------- STACKTRACE ERROR ---------------------------");
        logError("Class name: " + classname);
        logError("File name: " + file);
        logError("Function name: " + function);
        logError("Error line: " + linenumber);
        logError("BabelCraft version: " + Variables.pluginVersion);
        Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
        int counter = 0;
        StringBuffer pluginsList = new StringBuffer();
        while (plugins.length > counter) {
            pluginsList.append(plugins[counter].getDescription().getName() + " " + plugins[counter].getDescription().getVersion() + ", ");
            counter++;
        }
        logError("Plugins: " + pluginsList.toString());
        logError("--------------------------- STACKTRACE START ---------------------------");
        for (int i = 0; i < stack.length; i++) {
            logError(stack[i].toString());
        }
        logError("---------------------------- STACKTRACE END ----------------------------");
    }

    public void error(String error) {
        plainWarning(error);
        logError(error);
    }

    public void logError(String error) {
        ToFile(Type.error, "[" + pluginName + "] " + error, logFolder);
    }

    private void ToFile(Type type, String line, String logFolder) {
        if (Config.plugin_logging) {
            File data = new File(logFolder, "");
            if (!data.exists()) {
                if (data.mkdir()) {
                    debug("Created missing directory: " + logFolder);
                }
            }
            data = new File(logFolder + type.toString() + "/", "");
            if (!data.exists()) {
                if (data.mkdir()) {
                   debug("Created missing directory: " + logFolder + type.toString());
                }
            }
            DateFormat LogFormat = new SimpleDateFormat(Config.plugin_logformat);
            Date date = new Date();
            data = new File(logFolder + type.toString() + "/" + LogFormat.format(date) + "-" + type.toString() + ".log");
            if (!data.exists()) {
                try {
                    data.createNewFile();
                } catch (IOException e) {
                    stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
                }
            }
            FileWriter Writer;
            try {
                DateFormat StringFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date TheDate = new Date();
                Writer = new FileWriter(logFolder + type.toString() + "/" + LogFormat.format(date) + "-" + type.toString() + ".log", true);
                BufferedWriter Out = new BufferedWriter(Writer);
                Out.write(StringFormat.format(TheDate) + " - " + line + System.getProperty("line.separator"));
                Out.close();
            } catch (IOException e) {
                stackTrace(e.getStackTrace(), Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getStackTrace()[1].getLineNumber(), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getFileName());
            }
        }
    }
}
