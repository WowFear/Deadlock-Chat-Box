import java.net.*;
import java.nio.LongBuffer;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    public String callsign;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //Set up the two functions for read and write (BufferedReader and then PrintWritter)

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        

            //Give the agent a callsign(their name) and then I could do a check to see if someone did the same name
            //could be confusing if there are two same names and then could get into issues
            //Now, if the callsign is taken by someone else just ask the user to change it out

            out.println("What will the Patrons call you today...?");
            callsign = in.readLine();
            
            
            while(LobbyServer.callsignTaken(callsign)) {
                out.println("Sorry Pal, names taken... Wanna give it another go?");
                callsign = in.readLine();
            }

            //Add them into the server
            synchronized (LobbyServer.agents) {
                LobbyServer.agents.add(this);
            }


            //Give them access to the chat and the history to read
            //I had also mentioend I wanted to give everyone a heads up about who had joined in.

            LobbyServer.broadcastGlobal(">> The Patron gladly welcomes " + callsign + " to the ritual >:)");
        

            //I want there to be two different functions
            //1. For mesasages with dms
            //2. Messaages for the global chat to read

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/dm")) {
                        //Work on the dm fix 
                } else {
                    LobbyServer.broadcastGlobal("[" + callsign + "]: " + message);
                }
            }
        

        } catch (IOException e) {
            //Making an exception if someone had just suddenly disapeared
        } finally {
            //kick them out and let everyone know who got kicked out
           
            synchronized (LobbyServer.agents){
                LobbyServer.agents.remove(this);
            }
        LobbyServer.broadcastGlobal(">> " + callsign + "Has left the ritual");
        try {socket.close(); } 
        catch (IOException e) {}
        }
    }

    // Sends a message directly to this agent
    public void sendMessage(String message) {
        out.println(message);
    }
}