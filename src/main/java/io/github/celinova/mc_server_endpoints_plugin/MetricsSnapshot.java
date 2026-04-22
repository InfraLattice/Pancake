package io.github.celinova.mc_server_endpoints_plugin;

public class MetricsSnapshot {
    private final int onlinePlayers;
    private final int maxPlayers;
    private final int entitiesTotal;
    private final int entitiesOverworld;
    private final int entitiesNether;
    private final int entitiesEnd;
    private final long uptimeSeconds;
    private final long startTimeMillis;
    private final String minecraftVersion;
    private final String serverVersion;
    private final long lastUpdatedEpoch;

    public MetricsSnapshot(
            int onlinePlayers,
            int maxPlayers,
            long uptimeSeconds,
            long startTimeMillis,
            String minecraftVersion,
            String serverVersion,
            long lastUpdatedEpoch,
            int entitiesTotal,
            int entitiesOverworld,
            int entitiesNether,
            int entitiesEnd
    ) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.uptimeSeconds = uptimeSeconds;
        this.startTimeMillis = startTimeMillis;
        this.minecraftVersion = minecraftVersion;
        this.serverVersion = serverVersion;
        this.lastUpdatedEpoch = lastUpdatedEpoch;
        this.entitiesTotal = entitiesTotal;
        this.entitiesOverworld = entitiesOverworld;
        this.entitiesNether = entitiesNether;
        this.entitiesEnd = entitiesEnd;
    }


    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public long getUptimeSeconds() {
        return uptimeSeconds;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public long getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public int getEntitiesTotal() {
        return entitiesTotal;
    }

    public int getEntitiesOverworld() {
        return entitiesOverworld;
    }

    public int getEntitiesNether() {
        return entitiesNether;
    }

    public int getEntitiesEnd() {
        return entitiesEnd;
    }
}