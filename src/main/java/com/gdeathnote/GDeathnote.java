package com.gdeathnote;

import com.gdeathnote.commands.GDeathnoteCommand;
import com.gdeathnote.listeners.BookListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class GDeathnote extends JavaPlugin {

    public static Configuration configuration;
    public static Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void onEnable() {
        configuration = this.getConfig();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BookListeners(), this);
        getCommand("gdeathnote").setExecutor(new GDeathnoteCommand());
        getLogger().info("Plugin is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled.");
    }
}
