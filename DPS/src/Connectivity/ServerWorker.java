   /*
 * This class is meant to capture everything about the connection to the client;  
 * including logging in, logging out, sending group messages, and direct messages.
 * Part of the Dungeon Positioning System software created for a senior project at UNCG by
 * Phil, Liz, Spencer, Greg, and Ray.
 */
package Connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import qualityoflife.*;

/**
 *
 * @author sotirod
 */
public class ServerWorker extends Thread{
    
 
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
                } else if("map".equalsIgnoreCase(cmd)){     //if map then send map
                    HandleMap(out, tokens);        
                } else if("move".equalsIgnoreCase(cmd)){    //if token move then handle move
                    HandleTokenMove(out, tokens);
                } else if ("roll".equalsIgnoreCase(cmd)){
                    HandleRollDice(out, tokens);
                }else if (cmd.startsWith("#")) {   //if # is the first character, then it's a direct message.
                    DirectMsg(tokens);
                } else {                         //else it's a group chat message.
                    HandleMsgAll(tokens);   
                }
            }
        }
    }

    //login method with strings for user and pass
    public void Login(String user, String password) throws IOException{
        
            if (!user.equals("null")) {
                //to be replaced by queries to the DB later
                if (user.equals("DM") && password.equals("DM")
                        || (user.equals("ray") && password.equals("ray"))
                        || (user.equals("phil") && password.equals("phil"))
                        || (user.equals("spencer") && password.equals("spencer"))
                        || (user.equals("liz") && password.equals("liz"))
                        || (user.equals("greg") && password.equals("greg"))) {
                    String msg = "Welcome to the Party!\n";
                    out.write(msg.getBytes());
                    this.login = user;
                    System.out.println(this.login + " logged in successfully.");

                    List<ServerWorker> workerList = server.getWorkerList();
                    String onlineMsg;

                    //sending new login notification to other users
                    for (ServerWorker worker : workerList) {
                        if (!user.equals(worker.getLogin())) {
                            if (worker.getLogin() != null) {
                                onlineMsg = worker.getLogin() + " is online.\n";
                                send(onlineMsg);
                            }
                        }
                    }

                    //sending list of online people to current user
                    for (ServerWorker worker : workerList) {
                        if (!user.equals(worker.getLogin())) {
                            onlineMsg = user + " just logged in.\n";
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
    
    private void HandleLogin(OutputStream out, String tokens[]) throws IOException {
        if (tokens.length == 3) {
            String user = tokens[1];
            String password = tokens[2];
            Login(user, password);

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
     for (String token : tokens) {
         body = body.concat(token + " ");
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

    //TO-DO add logic for sending map
    private void HandleMap(OutputStream out, String tokens[]) {
        
         }

    //TO-DO add logic for sending moved token
    private void HandleTokenMove(OutputStream out, String[] tokens) {
        
    }

    public void RollDice(String diceType, String diceNum){
        //call gregs function
    }
    
   public void HandleRollDice(OutputStream out, String tokens[]) throws IOException{
          String rollString = tokens[1];
     
       DiceRoller d = new DiceRoller(rollString);
       String msg = d.roll();
       
        List<ServerWorker> workerList = server.getWorkerList();
   
        String outMsg = login+" "+msg+"\n";
        this.send(outMsg);
        
        //send text to everyone logged in.
        for (ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                {
                    outMsg = login +" " + msg + "\n";
                    worker.send(outMsg);
                }
            }
        }
   }
   
}
