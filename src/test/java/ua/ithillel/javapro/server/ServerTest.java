package ua.ithillel.javapro.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.ithillel.javapro.client.ClientConnection;
import ua.ithillel.javapro.exception.ExceptionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ServerTest {
    @Mock
    private ServerSocket serverMock;
    @Mock
    private Socket clientMock;
    @Mock
    private ClientConnection clientConnectionMock;
    private Server server;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(serverMock.accept()).thenReturn(clientMock);

        server = new Server(8080, 100) {
            @Override
            public void startServer() {
                server.onConnect(clientConnectionMock);
                server.onDisconnect(clientConnectionMock);
                server.onMessage(clientConnectionMock, "Test");
            }
        };

        when(clientConnectionMock.getSocket()).thenReturn(clientMock);
        when(clientConnectionMock.getClientName()).thenReturn("Client-1");
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    void ServerInitialization_shouldInitializeServer_whenPortAndMaxClientsAreValid() {
        assertNotNull(server.getServerSocket());
        assertNotNull(server.getClientPool());
    }

    @Test
    void ServerInitialization_shouldThrowExceptionHandler_whenPortIsInvalid() {
        assertThrows(ExceptionHandler.class, () -> new Server(0, 100));
    }

    @Test
    void ServerInitialization_shouldThrowExceptionHandler_whenMaxClientIsInvalid() {
        assertThrows(ExceptionHandler.class, () -> new Server(8080, 0));
    }

    @Test
    void onConnect_shouldAddClientToActiveConnections_whenConnectionIsEstablished() {
        server.onConnect(clientConnectionMock);
        assertTrue(server.getActiveConnections().contains(clientConnectionMock));
    }

    @Test
    void onDisconnect_shouldRemoveClientFromActiveConnections_whenClientDisconnects() {
        server.onConnect(clientConnectionMock);
        server.onDisconnect(clientConnectionMock);
        assertFalse(server.getActiveConnections().contains(clientConnectionMock));
    }

    @Test
    void onMessage_shouldAddClientToActiveConnections_whenConnectionIsEstablished() {
        server.startServer();
        String expectedMessage = "[SERVER] echo: Test";

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(clientConnectionMock).sendMessage(messageCaptor.capture());

        assertEquals(expectedMessage, messageCaptor.getValue());
    }
}
