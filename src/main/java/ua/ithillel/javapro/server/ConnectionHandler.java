package ua.ithillel.javapro.server;

import ua.ithillel.javapro.client.ClientConnection;

public interface ConnectionHandler {
    void onConnect(ClientConnection clientConnection);
    void onDisconnect(ClientConnection clientConnection);
    void onMessage(ClientConnection clientConnection, String message);
}