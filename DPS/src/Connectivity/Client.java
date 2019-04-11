

//client needs send and recieve
//server needs to bounce

package Connectivity;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import database.*;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import GUI.PlayerSessionController;

/**
 *
 *
 * @author Ray
 */
public class Client {

    private String serverName;
    private final int servPort;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedIn;
    private final ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private final ArrayList<MessageListener> userMessageListeners = new ArrayList<>();
    private Socket socket;
    private int sessionCode;

    /**
     * Constructor for the client object
     * @param servPort Server port you want to use. Default 11064.
     * @param sessionCode Session code you want to connect to
     * @throws IOException
     */
    public Client(int servPort, int sessionCode) throws IOException {
        this.sessionCode = sessionCode;
        Connection dbcon = connector.connect();
        this.serverName = getHostIP();
        this.servPort = servPort;

        //overriding the abstract methods in the interface
        this.addUserStatusListener(new UserStatusListener() {

            @Override
            public void online(String login) {
                System.out.println("ONLINE: " + login);
            }

            @Override
            public void offline(String login) {
                System.out.println("OFFLINE: " + login);
            }

        });

        //overriding the abstract method in the interface
        this.addMessageListener(new MessageListener() {
            @Override
            public String onMessage(String fromLogin, String msg) {
                String outMsg = fromLogin + ": " + msg;
                System.out.println(outMsg);
                PlayerSessionController.onMessage(outMsg);
                return outMsg;
            }
        });
    }

    //starts the client connection process and logs in with a username
    public boolean start(String username) throws IOException {
        if (this.connect()) {
            System.out.println("Connected to host.");
            if (this.login(username)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Login failed.");
            }
        } else {
            System.out.println("Connection failed.");
            return false;
        }
        return true;
    }

    //for logging in with a string username
    private boolean login(String login) throws IOException {
        String cmd = "login " + login + " " + sessionCode + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Server said: " + response);

        //this just matches the output from the server
        if ("welcome to the party!".equalsIgnoreCase(response)) {
            startMsgReader();

            return true;
        } else {
            return false;
        }
    }

    public void logoff() throws IOException {
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());
    }

    private boolean connect() throws IOException {
        try {
            this.socket = new Socket(serverName, servPort);
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;

        } catch (IOException e) {
            System.out.println("Could not connect client to host.\n");
        }
        return false;
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }

    public void removeUserStatusListener(UserStatusListener listener) {
        userStatusListeners.remove(listener);
    }

    public void addMessageListener(MessageListener listener) {
        userMessageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        userMessageListeners.remove(listener);
    }

    private void startMsgReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    readMsgLoop();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();

    }

    //loop constantly reading input from server
    private void readMsgLoop() throws IOException {
        
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    } else if ("offline".equalsIgnoreCase(cmd)) {
                        handleOffline(tokens);
                    } else if (cmd.length()>100){
                        handleRecieveMap(tokens);
                    } else if("tokenmove".equalsIgnoreCase(cmd)){
                        handleRecieveTokenMove(tokens);
                    }else {
                        handleMessage(tokens);
                    }
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    //for printing who is logged in 
    private void handleOnline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.online(login);
        }
    }

    //for printing logoffs
    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }

    //call this method for sending strings to server
    public void sendMsg(String Msg) throws IOException {
        String cmd = Msg + "\n";
        serverOut.write(cmd.getBytes());
    }

    //method for displaying messages recieved
    private void handleMessage(String[] tokens) throws IOException {
        String cmd = "";
        String login = tokens[0];
        for (int i = 2; i < tokens.length; i++) {
            cmd = cmd.concat(tokens[i] + " ");
        }

        for (MessageListener listener : userMessageListeners) {
            listener.onMessage(login, cmd);
        }
    }

    public OutputStream getOutStream() {
        return this.serverOut;
    }

    public InputStream getInStream() {
        return this.serverIn;
    }

    //gets the IP address of the server from the database
    private String getHostIP(){
        Connection dbcon = connector.connect();
        return connector.getIP(this.sessionCode);
    }

    private void handleSendMap(String mapStr) throws IOException {
        this.sendMsg(mapStr);
    }
    
    //probably don't need this method. We will see.
    private void handleRecieveMap(String tokens[]) {
       String mapStr = tokens[1];
        //drawMap(mapStr);
    }
    
    private void handleSendTokenMove(int tokenID, String tokenInfo) throws IOException {
        this.sendMsg("tokenmove "+tokenInfo+" "+tokenInfo);
    }
    
    //probably don't need this method. We will see.
    private void handleRecieveTokenMove(String tokens[]){
       
             for (MessageListener listener : userMessageListeners) {
            //"tokenmove "+tokenID+" "+xPos+" "+yPos+"\n";
            int tokenID = Integer.parseInt(tokens[1]);
            String tokenInfo = tokens[2] + tokens[3];
           
            //moveToken.method
        }
    

    
    
}
