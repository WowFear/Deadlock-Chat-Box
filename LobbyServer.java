import java.net.*;
import java.io.*;
import java.util.*;

public class LobbyServer {
    //Making list for everyone who had just joined/will join and using it for when people leave
    static List<ClientHandler> agents = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Match server online. Waiting for agents...");

        while (true) {
            Socket clientSocket = server.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    // This is the global chat that everyone will have access to to speak with one another
    public static synchronized void broadcastGlobal(String message) {
        // Create a loop through the list of agents and send a message to each of them 
        
        for (ClientHandler agent : agents) {
            agent.sendMessage(message);
        }

 
    }

    // Sends a message to one specific agent (DM)
    public static synchronized void sendDM(String targetCallsign, String message) {
        //Look for that specific name within the list that matches what the user put in and have a dm conversation happen
        //note: This is only for that specific agent


    }

    //This one is just for the exception for when someone else may take another persons name word for word
    public static synchronized boolean callsignTaken(String callsign) {
        //This will just be a way for me to check if someone else has the same name as someone else and be able to ask them to change it. 
        
        for(ClientHandler agent : agents) {
            if (agent.callsign.equalsIgnoreCase(callsign)) {
                return true;
            }
        }
        
        return false;

    }
}