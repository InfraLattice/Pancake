package io.github.celinova.mc_server_endpoints_plugin;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.io.IOException;
public class EndpointsServer {

    private final HttpServer server;

    private String buildStatusJson(
            int onlinePlayers,
            int maxPlayers,
            long uptimeSeconds,
            long startTimeMillis,
            String minecraftVersion,
            String serverVersion
    ) {
        return String.format(
                "{\"onlinePlayers\": %d, \"maxPlayers\": %d, \"uptimeSeconds\": %d, \"startTimeMillis\": %d, \"minecraftVersion\": \"%s\", \"serverVersion\": \"%s\"}",
                onlinePlayers,
                maxPlayers,
                uptimeSeconds,
                startTimeMillis,
                minecraftVersion,
                serverVersion
        );
    }

    public EndpointsServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        registerHealthEndpoint();
        registerStatusEndpoint();
    }

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

                int onlinePlayers = Bukkit.getOnlinePlayers().size();
                int maxPlayers = Bukkit.getMaxPlayers();

                long uptimeSeconds = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
                long startTimeMillis = ManagementFactory.getRuntimeMXBean().getStartTime();

                String minecraftVersion = Bukkit.getBukkitVersion();
                String serverVersion = Bukkit.getVersion();

                String response = buildStatusJson(
                        onlinePlayers,
                        maxPlayers,
                        uptimeSeconds,
                        startTimeMillis,
                        minecraftVersion,
                        serverVersion
                );

                sendJson(exchange, 200, response);

            } catch (Exception e) {
                    sendInternalError(exchange);
            }
        });
    }

    private void sendJson(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (var os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendMethodNotAllowed(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        sendJson(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
    }

    private void sendInternalError(com.sun.net.httpserver.HttpExchange exchange) {
        try {
            sendJson(exchange, 500, "{\"error\":\"Internal Server Error\"}");
        } catch (IOException ignored) {
        }
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }
}