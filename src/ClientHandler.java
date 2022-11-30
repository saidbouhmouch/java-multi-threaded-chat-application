import java.io.*;
import java.util.*;
import java.net.*;


// ClientHandler class
class ClientHandler implements Runnable
{
    private String _name;
    final DataInputStream _received;
    final DataOutputStream _reply;
    Socket _socket;
    boolean _isloggedin;
     
    // constructor
    public ClientHandler(Socket socket, String name,
                            DataInputStream received, DataOutputStream reply) {
        this._received = received;
        this._reply = reply;
        this._name = name;
        this._socket = socket;
        this._isloggedin=true;
    }
 
    @Override
    public void run() {
 
        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = _received.readUTF();
                 
                if(received.equals("logout")){
                    this._isloggedin=false;
                    this._socket.close();
                    break;
                }
                 
                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();
 
                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler client : Server.clientsList){
                    // if the recipient is found, write on its
                    // output stream
                    if (client._name.equals(recipient) && client._isloggedin){
                        client._reply.writeUTF("new msg from["+this._name+"] : "+MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
             
        }
        try
        {
            System.out.println("closing resources...");
            // closing resources
            this._received.close();
            this._reply.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}