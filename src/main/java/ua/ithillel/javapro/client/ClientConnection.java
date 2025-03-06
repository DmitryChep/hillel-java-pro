package ua.ithillel.javapro.client;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.server.ConnectionHandler;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

@Data
@Builder
@Slf4j
public class ClientConnection implements Runnable {
    private final Socket socket;
    private final String clientName;
    private LocalDateTime startTime;
    private final ConnectionHandler connectionHandler;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            this.in = new BufferedReader(new InputStreamReader(inputStream));
            this.out = new PrintWriter(outputStream, true);

            out.println("Hello, " + clientName + "! You are connected to the server.");
            out.println("Enter \"exit\" to quit.");
            out.flush();

            connectionHandler.onConnect(this);

            while (socket.isConnected()) {
                String message = in.readLine();
                if (message.trim().equalsIgnoreCase("exit")) {
                    log.info("Client {} sent 'exit' command", clientName);
                    break;
                }
                connectionHandler.onMessage(this, message);
            }
        } catch (IOException e) {
            log.error("Error handling client {}", clientName, e);
        } finally {
            connectionHandler.onDisconnect(this);

            // Close the streams and the socket
            try {
                this.in.close();
                this.out.close();
                this.socket.close();
            } catch (IOException e) {
                log.error("Error closing client connection {}", clientName, e);
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }
}
