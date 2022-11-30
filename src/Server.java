
// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java
 
import java.io.*;
import java.util.*;
import java.net.*;
 
// Server class
public class Server
{
 
    // Vector to store active clients
    static Vector<ClientHandler> clientsList = new Vector<>();
     
    // counter for clients
    static int clientNumber = 0;
 
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        ServerSocket serverSocket = new ServerSocket(1234);
         
        Socket socket;
         
        // running infinite loop for getting
        // client request
        while (true)
        {
            // Accept the incoming request
            socket = serverSocket.accept();
 
            System.out.println("New client request received : " + socket);
             
            // obtain input and output streams
            DataInputStream received = new DataInputStream(socket.getInputStream());
            DataOutputStream reply = new DataOutputStream(socket.getOutputStream());
             
            System.out.println("Creating a new handler for this client...");
 
            // Create a new handler object for handling this request.
            ClientHandler client = new ClientHandler(socket,"client" + clientNumber, received, reply);
 
            // Create a new Thread with this object.
            Thread t = new Thread(client);
             
            System.out.println("Adding this client to active client list");
 
            // add this client to active clients list
            clientsList.add(client);
 
            // start the thread.
            t.start();
 
            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            clientNumber++;
 
        }
    }
}