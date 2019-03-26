/*
 * Server object
 */
package dps_server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server extends Thread {

    private final int serverPort;
    
    //array list of serverWorkers, for multiple user connections
    private ArrayList<ServerWorker> workerList = new ArrayList<>();

    public Server(int port) {
        this.serverPort = port;
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    @Override
    public void run() {
        try {
            ServerSocket servSock = new ServerSocket(serverPort);
            while (true) {
                Socket clntSock = servSock.accept();
                OutputStream out = clntSock.getOutputStream();
                System.out.println("Connected to " + clntSock.getInetAddress());
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

}
