package io.github.celinova.mc_server_endpoints_plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Mc_server_endpoints_plugin extends JavaPlugin implements Listener {

  private EndpointsServer endpointsServer;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

    try {
      EndpointsServer server = new EndpointsServer();
      server.start();
    } catch (IOException e) {
      getLogger().severe("Disabling Plugin. Failed to start HTTP server: " + e.getMessage());
      getServer().getPluginManager().disablePlugin(this);
    }
  }

  @Override
  public void onDisable() {
    if (endpointsServer != null) {
      endpointsServer.stop();
    }
  }
}