import java.net.*;
import java.io.*;
import java.util.Scanner;

public class AgentClient {
    public static void main(String[] args) throws IOException {
        //Be able to connect to the server using a Socket

        Socket socket = new Socket("localhost", 8080);

        //Put in the buffer and print

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);
       
        //I also want to make some sort of thread to listen in for any incoming messages within the server
         
            Thread listener = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println(">> Apotheosis has been achieved.");
            }
        });
        listener.start();

        
        //Give a place for the agent to input using Scanner 

        while (scanner.hasNextLine()){
            String input = scanner.nextLine();
            out.println(input);
        }
        socket.close();
        
        //IF the user puts in a message with smt like 'dm'then it'll be a message sent striaght to that agent and that agent only
        
        //If no dm just a global message 
        
    }
}