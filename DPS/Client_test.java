
package client_test;


    import java.net.Socket;
    import java.net.SocketException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.util.Scanner;

/**
 *
 * @author Ray
 */
public class Client_test {

    
    public static void main(String[] args) throws IOException {
    
        String server = "152.40.35.223";    //my ip for testing
       
        byte[] data;    //the message you want to send
        int servPort = 11064;
        
        System.out.println("Connecting to Server...");
        
        //create a socket that is connected to the host
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to " + server + " on port: " + servPort);
        
        Scanner s = new Scanner(System.in); //for getting input
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        
        while(true){
            
        System.out.print(socket.getLocalAddress() + ":");
        data = s.nextLine().getBytes(); //convert input to bytes so it can be sent
        
        
        out.write(data);
        
        //recieving the same string back from the server
        int totalBytesRecieved = 0; //total bytes recieved so far
        int bytesRecieved;          //bytes recieved in last read
        
        while(totalBytesRecieved<data.length)
        {
            if((bytesRecieved = in.read(data, totalBytesRecieved, data.length - totalBytesRecieved))== -1)
                throw new SocketException("Connection closed prematurely");
         totalBytesRecieved += bytesRecieved;
        }   //loop only exits when array is full
        
        }
        ///close the socket
        //socket.close();
    }
    
}

