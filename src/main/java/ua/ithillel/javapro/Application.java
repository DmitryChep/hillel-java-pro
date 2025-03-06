package ua.ithillel.javapro;

import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.exception.ExceptionHandler;
import ua.ithillel.javapro.server.Server;

import java.io.IOException;

@Slf4j
public class Application {
    public static void main(String[] args)  {
        log.info("Starting application...");

        try (Server server = new Server(8080, 100)) {
            server.startServer();
        } catch (IOException e) {
            log.error( "Error startup server:  {}", e.getMessage());
            throw new ExceptionHandler("Error startup server:",e.getCause());
        }

        log.info("Application finished.");
    }
}
