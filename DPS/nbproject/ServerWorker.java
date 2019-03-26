/*
 * This class is meant to capture everything about the connection to the client;  
 * including logging in, logging out, sending group messages, and direct messages.
 * Part of the Dungeon Positioning System software created for a senior project at UNCG by
 * Phil, Liz, Spencer, Greg, and Ray.

 */
package dps_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ray
 */
public class ServerWorker extends Thread {

    private final Socket clntSock;
    private String login = null;
    private final Server server;
    private OutputStream out;

    public ServerWorker(Server server, Socket clntSock) {
        this.server = server;
        this.clntSock = clntSock;
    }

    @Override
    public void run() {
        try {
            HandleClient();
        } catch (IOException ex) {
        
        }
    }

    public String getLogin() {
        return login;
    }

    public void HandleClient() throws IOException {
        InputStream in = clntSock.getInputStream();
        this.out = clntSock.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        
        //loop to constantly grab input
        while ((line = reader.readLine()) != null) {
            
            //divide the input byte array by whitespace and get commands accordingly
            String[] tokens = line.split(" ");

            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];

                if ("quit".equalsIgnoreCase(cmd) || ("logoff".equalsIgnoreCase(cmd))) { //if logoff, then handle logoff
                    handleLogoff();
                } else if ("login".equalsIgnoreCase(cmd)) { //if login then handle login
                    HandleLogin(out, tokens);
                } else if (cmd.startsWith("#")) {   //if # is the first character, then it's a direct message.
                    DirectMsg(tokens);
                } else {                         //else it's a group chat message.
                    HandleMsgAll(tokens);   
                }
            }
        }
    }

    private void HandleLogin(OutputStream out, String tokens[]) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if (!login.equals("null")) {
                if (login.equals("DM") && password.equals("DM")
                        || (login.equals("ray") && password.equals("ray"))
                        || (login.equals("phil") && password.equals("phil"))
                        || (login.equals("spencer") && password.equals("spencer"))
                        || (login.equals("liz") && password.equals("liz"))
                        || (login.equals("greg") && password.equals("greg"))) {
                    String msg = "Welcome to the Party!\n";
                    out.write(msg.getBytes());
                    this.login = login;
                    System.out.println(login + " logged in successfully.");

                    List<ServerWorker> workerList = server.getWorkerList();
                    String onlineMsg;

                    //sending new login notification to other users
                    for (ServerWorker worker : workerList) {
                        if (!login.equals(worker.getLogin())) {
                            if (worker.getLogin() != null) {
                                onlineMsg = worker.getLogin() + " is online.\n";
                                send(onlineMsg);
                            }
                        }
                    }

                    //sending list of online people to current user
                    for (ServerWorker worker : workerList) {
                        if (!login.equals(worker.getLogin())) {
                            onlineMsg = login + " just logged in.\n";
                            worker.send(onlineMsg);
                        }
                    }
                } else {
                    String msg = "error login\n";
                    out.write(msg.getBytes());
                    System.err.println("Login failed for " + login+".");
                }
            }
        }
    }

    private void handleLogoff() throws IOException {
        String Msg = "Bye.\n";
        out.write(Msg.getBytes());
        server.removeWorker(this);
        System.out.println(getLogin()+" logged off.");
        List<ServerWorker> workerList = server.getWorkerList();

        //tell everyone logged in that you logged off.
        for (ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {

                Msg = getLogin() + " logged off.\n";
                worker.send(Msg);
            }
        }

        clntSock.close();
    }

    private void send(String Msg) throws IOException {
        if (login != null) {    //must have a login to send a message, so you can see who it came from.
            out.write(Msg.getBytes());
        }
    }

    //format "#login" "body of message"
    private void DirectMsg(String tokens[]) throws IOException {
        
        String sendTo = tokens[0].replaceFirst("#", "");    //the login of the user the direct message is intended for is tokens[0] without the #.

        //loop to build the message from every word entered.
        String body = "";
        for (int i = 1; i < tokens.length; i++) {
            body = body.concat(tokens[i] + " ");
        }

        List<ServerWorker> workerList = server.getWorkerList();

        //for everyone connected
        for (ServerWorker worker : workerList) {
            if (sendTo.equals(worker.getLogin())) {
                {
                    String outMsg = "PM from " + login + ": " + body + "\n";    
                    worker.send(outMsg);            //send the message to everyone logged in.
                }
            }
        }
    }

    //send a message to everyone in the chat by typing no special characters
    private void HandleMsgAll(String tokens[]) throws IOException {
        String body = "";
        for (int i = 0; i < tokens.length; i++) {
            body = body.concat(tokens[i] + " ");
        }

        List<ServerWorker> workerList = server.getWorkerList();

        //send text to everyone logged in.
        for (ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                {
                    String outMsg = login + " said: " + body + "\n";
                    worker.send(outMsg);
                }
            }
        }
    }

}
