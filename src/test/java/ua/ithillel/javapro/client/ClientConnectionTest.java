package ua.ithillel.javapro.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.ithillel.javapro.server.ConnectionHandler;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ClientConnectionTest {

    @Mock
    private Socket socketMock;
    @Mock
    private ConnectionHandler connectionHandlerMock;
    @Mock
    private PrintWriter outMock;
    @Mock
    private BufferedReader inMock;

    private ClientConnection clientConnection;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(socketMock.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(socketMock.getInputStream()).thenReturn(mock(InputStream.class));

        clientConnection = ClientConnection.builder()
                .socket(socketMock)
                .clientName("Client-1")
                .connectionHandler(connectionHandlerMock)
                .build();

        clientConnection.setOut(outMock);
        clientConnection.setIn(inMock);
    }


    @Test
    void onRun_shouldDisconnectClient_whenExitCommandIsReceived() throws IOException {
        when(inMock.readLine()).thenReturn("exit");
        clientConnection.run();

        verify(connectionHandlerMock, times(1)).onDisconnect(clientConnection);
    }


    @Test
    void sendMessage_shouldSendMessageToClient_whenMessageIsSent() {
        String message = "[SERVER] echo: Test";
        clientConnection.sendMessage(message);

        verify(outMock, times(1)).println(message);
        verify(outMock, times(1)).flush();
    }

}
