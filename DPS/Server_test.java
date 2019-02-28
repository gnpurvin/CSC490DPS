
package server_test;

/**
 *
 * @author Ray
 */

import java.net.*;
import java.io.*;

public class Server_test {

    public static final int buff_size = 32; //recieve buffer
    
    
    /**
     * TCP echo server test
     */
    public static void main(String[] args) throws IOException{
       
        int servPort = 2020;   //random port number
        
        //create a server socket to accept client connect requests
        ServerSocket servSock = new ServerSocket(servPort);
        
        int recvMsgSize; //size of recieved message
        
        byte[] recieveBuf = new byte[buff_size]; //recieve buffer
        
        
        while(true){    //run forever
            Socket clntSock = servSock.accept();    //get client connection
            
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client at " + clientAddress);
            
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            
            //recieve until client kills connection, by typing -1
            while((recvMsgSize = in.read(recieveBuf)) != -1){
                out.write(recieveBuf, 0, recvMsgSize);
            }
            
            clntSock.close();
            
                    }
        
    }
    
}
