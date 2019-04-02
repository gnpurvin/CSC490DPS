
package Connectivity;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author Ray
 */
public class Client {

    private final String serverName;
    private final int servPort;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedIn;
    private final ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private final ArrayList<MessageListener> userMessageListeners = new ArrayList<>();
    private Socket socket;

    public Client(String serverName, int servPort) throws IOException {
        this.serverName = serverName;
        this.servPort = servPort;

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

        this.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msg) {
                System.out.println(fromLogin + ": " + msg);
            }
        });
    }

    public void start(String username, String password) throws IOException {
        if (this.connect()) {
            System.out.println("Connected to host.");
            if (this.login(username, password)) {
                System.out.println("Login successful.");

            } else {
                System.out.println("Login failed.");
            }
        } else {
            System.out.println("Connection failed.");
        }
    }

    //for logging in with a string username and password
    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
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

    //loop reading input from server
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
                    } else {
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
    private void sendMsg(String Msg) throws IOException {
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

}
