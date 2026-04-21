package io.github.celinova.mc_server_endpoints_plugin;

public class MetricsSnapshot {
    private final int onlinePlayers;
    private final int maxPlayers;
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
            long lastUpdatedEpoch
    ) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.uptimeSeconds = uptimeSeconds;
        this.startTimeMillis = startTimeMillis;
        this.minecraftVersion = minecraftVersion;
        this.serverVersion = serverVersion;
        this.lastUpdatedEpoch = lastUpdatedEpoch;
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
}