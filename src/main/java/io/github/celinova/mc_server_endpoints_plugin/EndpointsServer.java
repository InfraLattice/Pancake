package io.github.celinova.mc_server_endpoints_plugin;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EndpointsServer {

    private final HttpServer server;
    private final Mc_server_endpoints_plugin plugin;

    private String buildStatusJson(MetricsSnapshot snapshot) {
        return String.format(
                "{\"onlinePlayers\": %d, \"maxPlayers\": %d, \"uptimeSeconds\": %d, \"startTimeMillis\": %d, \"minecraftVersion\": \"%s\", \"serverVersion\": \"%s\", \"lastUpdatedEpoch\": %d}",
                snapshot.getOnlinePlayers(),
                snapshot.getMaxPlayers(),
                snapshot.getUptimeSeconds(),
                snapshot.getStartTimeMillis(),
                snapshot.getMinecraftVersion(),
                snapshot.getServerVersion(),
                snapshot.getLastUpdatedEpoch()
        );
    }


    public EndpointsServer(Mc_server_endpoints_plugin plugin) throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.plugin = plugin;

        registerHealthEndpoint();
        registerStatusEndpoint();
        registerMetricsEndpoint();
    }


    // ========================================================================================================
    // SERVER ENDPOINTS
    // ========================================================================================================


    private void registerHealthEndpoint() {
        server.createContext("/health", exchange -> {
            try {
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    sendMethodNotAllowed(exchange);
                    return;
                }

                sendJson(exchange, 200, "{\"status\":\"ok\"}");

            } catch (Exception e) {
                sendInternalError(exchange);
            }
        });
    }


    private void registerStatusEndpoint() {
        server.createContext("/status", exchange -> {
            try {
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    sendMethodNotAllowed(exchange);
                    return;
                }

                MetricsSnapshot snapshot = plugin.getCurrentSnapshot();

                if (snapshot == null) {
                    sendInternalError(exchange);
                    return;
                }

                String response = buildStatusJson(snapshot);
                sendJson(exchange, 200, response);

            } catch (Exception e) {
                    sendInternalError(exchange);
            }
        });
    }


    // prviate method for registering metrics -> prometheus
    private void registerMetricsEndpoint() {
        server.createContext("/metrics", exchange -> {
            try {
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    sendMethodNotAllowed(exchange);
                    return;
                }

                MetricsSnapshot snapshot = plugin.getCurrentSnapshot();

                if (snapshot == null) {
                    sendInternalError(exchange);
                    return;
                }

                StringBuilder sb = new StringBuilder();

                sb.append("# HELP mc_online_players Current number of online players\n");
                sb.append("# TYPE mc_online_players gauge\n");
                sb.append("mc_online_players ").append(snapshot.getOnlinePlayers()).append("\n");

                sb.append("# HELP mc_max_players Maximum allowed player count\n");
                sb.append("# TYPE mc_max_players gauge\n");
                sb.append("mc_max_players ").append(snapshot.getMaxPlayers()).append("\n");

                sb.append("# HELP mc_uptime_seconds Current server uptime in seconds\n");
                sb.append("# TYPE mc_uptime_seconds gauge\n");
                sb.append("mc_uptime_seconds ").append(snapshot.getUptimeSeconds()).append("\n");

                sb.append("# HELP mc_start_time_millis Server start time in milliseconds since epoch\n");
                sb.append("# TYPE mc_start_time_millis gauge\n");
                sb.append("mc_start_time_millis ").append(snapshot.getStartTimeMillis()).append("\n");

                sb.append("# HELP mc_snapshot_last_updated_epoch Last time the metrics snapshot was refreshed\n");
                sb.append("# TYPE mc_snapshot_last_updated_epoch gauge\n");
                sb.append("mc_snapshot_last_updated_epoch ").append(snapshot.getLastUpdatedEpoch()).append("\n");

                sb.append("# HELP mc_entities_total Current total number of loaded entities across all worlds\n");
                sb.append("# TYPE mc_entities_total gauge\n");
                sb.append("mc_entities_total ").append(snapshot.getEntitiesTotal()).append("\n");

                sb.append("# HELP mc_entities_overworld Current number of loaded entities in the overworld\n");
                sb.append("# TYPE mc_entities_overworld gauge\n");
                sb.append("mc_entities_overworld ").append(snapshot.getEntitiesOverworld()).append("\n");

                sb.append("# HELP mc_entities_nether Current number of loaded entities in the nether\n");
                sb.append("# TYPE mc_entities_nether gauge\n");
                sb.append("mc_entities_nether ").append(snapshot.getEntitiesNether()).append("\n");

                sb.append("# HELP mc_entities_end Current number of loaded entities in the end\n");
                sb.append("# TYPE mc_entities_end gauge\n");
                sb.append("mc_entities_end ").append(snapshot.getEntitiesEnd()).append("\n");

                String response = sb.toString();
                sendPlainText(exchange, 200, response);

            } catch (Exception e) {
                sendInternalError(exchange);
            }
        });
    }


    // ========================================================================================================
    // HELPERS / ERROR HANDLING
    // ========================================================================================================


    // Set content-type json response headers
    private void sendJson(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (var os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }


    // set content-type text/plain response headers
    private void sendPlainText(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (var os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }


    // error 405
    private void sendMethodNotAllowed(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        sendJson(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
    }


    // error 500
    private void sendInternalError(com.sun.net.httpserver.HttpExchange exchange) {
        try {
            sendJson(exchange, 500, "{\"error\":\"Internal Server Error\"}");
        } catch (IOException ignored) {
        }
    }


    // server start method
    public void start() {
        server.start();
    }


    // server stop
    public void stop() {
        server.stop(0);
    }
}