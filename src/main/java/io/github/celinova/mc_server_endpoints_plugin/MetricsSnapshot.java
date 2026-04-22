package io.github.celinova.mc_server_endpoints_plugin;

public class MetricsSnapshot {
    private final int onlinePlayers;
    private final int maxPlayers;
    private final int entitiesTotal;
    private final int entitiesOverworld;
    private final int entitiesNether;
    private final int entitiesEnd;
    private final int loadedChunksTotal;
    private final int loadedChunksOverworld;
    private final int loadedChunksNether;
    private final int loadedChunksEnd;
    private final long uptimeSeconds;
    private final long startTimeMillis;
    private final String minecraftVersion;
    private final String serverVersion;
    private final long lastUpdatedEpoch;
    private double tps1m;
    private double tps5m;
    private double tps15m;

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
            int entitiesEnd,
            int loadedChunksTotal,
            int loadedChunksOverworld,
            int loadedChunksNether,
            int loadedChunksEnd,
            double tps1m,
            double tps5m,
            double tps15m
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
        this.loadedChunksTotal = loadedChunksTotal;
        this.loadedChunksOverworld = loadedChunksOverworld;
        this.loadedChunksNether = loadedChunksNether;
        this.loadedChunksEnd = loadedChunksEnd;
        this.tps1m = tps1m;
        this.tps5m = tps5m;
        this.tps15m = tps15m;

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

    public int getLoadedChunksTotal() {
        return loadedChunksTotal;
    }

    public int getLoadedChunksOverworld() {
        return loadedChunksOverworld;
    }

    public int getLoadedChunksNether() {
        return loadedChunksNether;
    }

    public int getLoadedChunksEnd() {
        return loadedChunksEnd;
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

    public double getTps1m() {
        return tps1m;
    }

    public double getTps5m() {
        return tps5m;
    }

    public double getTps15m() {
        return tps15m;
    }
}