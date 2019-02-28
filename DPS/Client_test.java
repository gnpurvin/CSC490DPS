
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
    
        String server = "152.40.35.202";    //my ip for testing
        byte[] data;    //the message you want to send
        int servPort = 2020; //7 is the default port for echoing data back
        
        System.out.println("Connecting to Server...");
        
        //create a socket that is connected to the host
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to Server");
        
        Scanner s = new Scanner(System.in); //for getting input
        System.out.println("Enter a message to be sent then press enter: ");
        data = s.nextLine().getBytes(); //convert input to bytes so it can be sent
        
        System.out.println("Sending string...");
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
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
        
        //print the string back
        System.out.println("Recieved: " + new String(data));
        
        ///close the socket
        socket.close();
    }
    
}
