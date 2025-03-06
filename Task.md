__Server:__

* The server must be able to accept incoming connections from clients in an unlimited quantity. In other words, more than one client can connect to the server.
* After a successful client connection, the server stores information about the client in the "active connections" list, including the following data:
  * A randomly generated name with a unique template, e.g. client-1, **client-2**, client-N, connection time, and client socket.
* After a successful client connection, the server logs that the new client has connected.
  * For example: __[SERVER] Client-1__ successfully connected.
* The server supports a set of special commands from the client:
  * __exit__ - Disconnects the client from the connection. At this moment, the client who sent this command to the server will be disconnected from the server and removed from the active connections list.

__Client:__

* The client must be able to connect to the server.
* The client must be able to send special commands to the server.