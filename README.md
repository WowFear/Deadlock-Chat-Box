# Deadlock Chat Lobby

A multi-client chat server built in Java, themed around Deadlock.

## How to Run

**Compile:**
javac LobbyServer.java ClientHandler.java AgentClient.java

**Start the server:**
java LobbyServer

**Connect a client:**
java AgentClient

## Commands
/dm [callsign] [message] — send a private message to a specific agent

## Notes
- Max 6 agents can connect at once
- Chat history is saved to chathistory.txt