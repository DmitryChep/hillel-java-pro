package ua.ithillel.javapro.server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.client.ClientConnection;
import ua.ithillel.javapro.exception.ExceptionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class Server implements ConnectionHandler, AutoCloseable {

    private final List<ClientConnection> activeConnections = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger clientCount = new AtomicInteger(0);
    private final ServerSocket serverSocket;
    private final ExecutorService clientPool; // Thread pool to handle client connections

    public Server(int port, int maxClients) throws IOException {
        if (port == 0 || port < 0) {
            log.error("Invalid port number");
            throw new ExceptionHandler("Invalid port number");
        }
        if (maxClients < 1 ) {
            log.error("MaxClients must be greater than 1");
            throw new ExceptionHandler("MaxClients must be greater than 1");
        }
            this.serverSocket = new ServerSocket(port);
        // Create a thread pool with a core pool size of 10, maxClients as maximum pool size, and a blocking queue of 1000 tasks
        this.clientPool = new ThreadPoolExecutor(
                10, maxClients, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
                new ThreadPoolExecutor.CallerRunsPolicy());  // used to run task in the main thread if the pool is full
        log.info("Server initialized on port {}", port);
    }

    public void startServer() {
        log.info("Server started");

        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();

                String clientName = "client-" + clientCount.incrementAndGet();
                log.debug("[SERVER] Client {} successfully connected", clientName);

                Runnable clientConnection = ClientConnection.builder()
                        .socket(clientSocket)
                        .clientName(clientName)
                        .startTime(LocalDateTime.now())
                        .connectionHandler(this)
                        .build();

                // Submit client connection task to the thread pool
                clientPool.submit(clientConnection);
            } catch (IOException e) {
                log.error("Error accepting client connection: {}", e.getMessage(), e);
                throw new ExceptionHandler("Error accepting client connection", e);
            }
        }
    }


    @Override
    public void onConnect(ClientConnection clientConnection) {
        activeConnections.add(clientConnection);
        log.info("[SERVER] Client {} connected successfully", clientConnection.getClientName());
        log.debug("Connection details: {}", clientConnection);
    }


    @Override
    public void onDisconnect(ClientConnection clientConnection) {
        activeConnections.remove(clientConnection);
        log.info("[SERVER] Client {} disconnected successfully", clientConnection.getClientName());
        log.debug("Closed connection details: {}", clientConnection);
    }


    @Override
    public void onMessage(ClientConnection clientConnection, String message) {
        log.info("[SERVER] Message received from client {}: {}", clientConnection.getClientName(), message);
        clientConnection.sendMessage("[SERVER] echo: " + message);
    }


    @Override
    public void close() {
        log.info("[SERVER] Closing server");
        try {
            serverSocket.close();
            clientPool.shutdownNow();
        } catch (IOException e) {
            log.error("Error closing server resources: {}", e.getMessage(), e);
        }
    }
}

