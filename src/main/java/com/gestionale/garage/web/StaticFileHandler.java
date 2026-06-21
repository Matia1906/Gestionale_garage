package com.gestionale.garage.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticFileHandler implements HttpHandler {

    private final Path indexPath;

    public StaticFileHandler(Path indexPath) {
        this.indexPath = indexPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String path = exchange.getRequestURI().getPath();
            if (!"/".equals(path) && !"/index.html".equals(path)) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }

            if (!Files.exists(indexPath)) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }

            byte[] body = Files.readAllBytes(indexPath);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, body.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(body);
            }
        } finally {
            exchange.close();
        }
    }
}
