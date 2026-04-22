package io.github.celinova.mc_server_endpoints_plugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.management.ManagementFactory;
import java.io.IOException;
import java.time.Instant;

public class Mc_server_endpoints_plugin extends JavaPlugin implements Listener {

  private EndpointsServer endpointsServer;
  private MetricsSnapshot currentSnapshot;

  @Override
  public void onEnable() {

    Bukkit.getPluginManager().registerEvents(this, this);
    refreshSnapshot();

    // will gather every 100 ticks, or 5 seconds
    this.getServer().getScheduler().runTaskTimer(
            this,
            this::refreshSnapshot,
            100L,
            100L
    );

    try {
      EndpointsServer server = new EndpointsServer(this);
      server.start();
    } catch (IOException e) {
      getLogger().severe("Disabling Plugin. Failed to start HTTP server: " + e.getMessage());
      getServer().getPluginManager().disablePlugin(this);
    }
  }

  private void refreshSnapshot() {
    int onlinePlayers = Bukkit.getOnlinePlayers().size();
    int maxPlayers = Bukkit.getMaxPlayers();
    int entitiesTotal = 0;
    int entitiesOverworld = 0;
    int entitiesNether = 0;
    int entitiesEnd = 0;

    long uptimeSeconds = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
    long startTimeMillis = ManagementFactory.getRuntimeMXBean().getStartTime();

    String minecraftVersion = Bukkit.getBukkitVersion();
    String serverVersion = Bukkit.getVersion();

    long lastUpdatedEpoch = Instant.now().getEpochSecond();

    for (World world : Bukkit.getWorlds()) {
      int count = world.getEntities().size();

      entitiesTotal += count;

      switch (world.getEnvironment()) {
        case NORMAL -> entitiesOverworld += count;
        case NETHER -> entitiesNether += count;
        case THE_END -> entitiesEnd += count;
      }
    }

    currentSnapshot = new MetricsSnapshot(
            onlinePlayers,
            maxPlayers,
            uptimeSeconds,
            startTimeMillis,
            minecraftVersion,
            serverVersion,
            lastUpdatedEpoch,
            entitiesTotal,
            entitiesOverworld,
            entitiesNether,
            entitiesEnd
    );


  }

  @Override
  public void onDisable() {
    if (endpointsServer != null) {
      endpointsServer.stop();
    }
  }

  public MetricsSnapshot getCurrentSnapshot() {
    return currentSnapshot;
  }
}