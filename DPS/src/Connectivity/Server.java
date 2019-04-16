
package Connectivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import database.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;

/**
 *
 * @author sotirod
 */
public class Server extends Thread{
/*
 * Server object
 */

    private final int serverPort;
    private String IP;
    private int sessionCode;
    
    //array list of serverWorkers, for multiple user connections
    private ArrayList<ServerWorker> workerList = new ArrayList<>();
    
    /*
    *
    *@param port this is the port on the computer you will be connecting through. default 11064.
    *@param sessionCode The session code you wish to host.
    */
    public Server(int port, int sessionCode) {
        this.serverPort = port;
        this.sessionCode = sessionCode;
        
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    @Override
    public void run() {
        try {
            ServerSocket servSock = new ServerSocket(serverPort);
            InetAddress ip;
            String hostname;
            try {
                ip = InetAddress.getLocalHost();
                hostname = ip.getHostName();
                this.IP = hostname;
            } catch (UnknownHostException e) { 
                e.printStackTrace();
            }
            this.setIPinDB();
            while (true) {
                Socket clntSock = servSock.accept();
                System.out.println("Connected to " + clntSock.getInetAddress());
                OutputStream out = clntSock.getOutputStream();
                ServerWorker worker = new ServerWorker(this, clntSock);
                workerList.add(worker);
                worker.start();
            }
        } catch (IOException e) {
        }
    }

    public void removeWorker(ServerWorker worker) {
        this.workerList.remove(worker);
    }
    
    public void addWorker(ServerWorker worker){
        this.workerList.add(worker);
    }

    public String getIP(){
    return this.IP;
}

    //this matchess the IP of the server to the session code in the database
     public void setIPinDB() {
        Connection con = connector.connect();
        connector.setIP(this.sessionCode, this.IP);
    }
    
}

