
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

public class connector {

    private static final String url = "jdbc:mysql://dpsdb.cltxtb6ls4t4.us-east-1.rds.amazonaws.com:3306/dpsdb?user=Aesthellar&password=password";
    private static Connection con;

    //connect connects to the DPS database
    public static Connection connect() {
        try {
            con = DriverManager.getConnection(url);
            System.out.println("Connected to DPSDB");

        } catch (SQLException ex) {
            System.out.println("Unable to connect to DPSDB");
            ex.printStackTrace();
        }
        return con;
    }
    
    //makeUser creates a new user with a password and unique username
    public static void makeUser(String username, String password) {
        
        String findUsername = "SELECT username FROM user WHERE username = '" + username + "'";
        String insertUser = "INSERT INTO user (username, password) VALUES ('" + username + "', '" + password + "')";
        
        //Check if username is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(findUsername);
            while (rs.next()) {
                String name = rs.getString("username");
                if (name.equals(username)) {
                    System.out.println("Username is not unique");
                } else {
                    st.executeUpdate(insertUser);   //if username is unique then insert the new user
                }
                st.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to make a new user account");
        }
    }
    
    //Authenticate checks if the given username and password exists and correlates in the database
    public static boolean Authenticate(String username, String password) {
        
        boolean bool = false;
        
        String query = "SELECT password FROM user WHERE username = '" + username + "'";
        
        //find the user's password given username
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String dbpass = rs.getString("password");
                if (dbpass.equals(password)) {  //check if given password matches database password
                    bool = true;
                } else {
                    bool = false;
                }
            }

            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to authenticate");
        }

        return bool;
    }
    
    //Checks if player already has a token on the map using their username, the current mapID, and the session the map belongs to
    //Returns the player's tokenID
    public static int checkPlayerToken(String username, int sessionID, int mapID) {
        
        int tokenID = 10000;
        String findTokenID = "SELECT tokenID FROM mapContain WHERE mapID = " + mapID;
        
        //find list of tokens from a specific map
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(findTokenID);
            ResultSet rs2;
            
            while (rs.next()) {
                tokenID = rs.getInt("tokenID"); //Find all tokens on map
                String findUserToken = "SELECT username FROM tokenowner WHERE tokenID = " + tokenID;
                
                rs2 = st.executeQuery(findUserToken);   
                while (rs2.next()) {
                    String user = rs2.getString("username");
                    if (username.equals(user)) {    //Check if user owns a token on map
                        return tokenID; //Returns tokenID of a user
                    } else {
                        //Call makeToken with username, mapID, and size (1, 1)
                    }
                }
            } 
        } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Unable to check if new player");
                    }
        return tokenID;
    }

    //getSessionList returns an ArrayList of sessions that the user has created
    public static ArrayList<Integer> getSessionList(String username) {
        
        ArrayList<Integer> sessions = new ArrayList<Integer>();
        String query = "SELECT sessionID FROM sessionowner WHERE username = '" + username + "'";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int sessionID = rs.getInt("sessionID");
                sessions.add(sessionID);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to find sessions");
        }
        return sessions;
    }

    //makeSession makes a session using the user's username and a sessionName then returns the sessionID
    public static int makeSession(String sessionName, String username) {
        //Generate new sessionID
        Random rand = new Random();
        int num = rand.nextInt(1001);
        int sessionID = num;

        String randCheck = "SELECT sessionID FROM session WHERE sessionID = " + num;
        String insertNewSess = "INSERT INTO session (sessionID, sessionName) VALUES (" + sessionID + ", '" + sessionName + "')";
        String insertNewSessOwner = "INSERT INTO sessionowner (username, sessionID) VALUES ('" + username + "', " + sessionID + ")";
                
        //Check if new sessionID is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(randCheck);

            while (rs.next()) {
                sessionID = rs.getInt("sessionID");
            }
            if (sessionID == num) {
                System.out.println("The sessionID is not unique");
            } else {
                st.executeUpdate(insertNewSess);   //insert new session
                st.executeUpdate(insertNewSessOwner);   //insert new sessionowner
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to make a new session");
        }

        return num;
    }
    
    //editSession finds a session with the sessionID and changes the sessionName
    public static void editSession(int sessionID, String sessionName) {
        
        String query = "UPDATE session SET sessionName = '" + sessionName + "' WHERE sessionID = " + sessionID;
        
        try {
            Statement st = con.createStatement();
            st.executeQuery(query);
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to update session");
        }
    }

    //deleteSession removes a session from the database using the sessionID
    public static void deleteSession(int sessionID) {
        String query = "DELETE FROM session WHERE sessionID = " + sessionID;
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to delete session");
        }
    }

    //getMapList returns an ArrayList of mapID given the sessionID 
    public static ArrayList<String> getMapList(int sessionID) {
        
        ArrayList<String> maps = new ArrayList<String>();
        int mapID = 1001;
        String checkMapID = "SELECT mapID FROM sessionmaps WHERE sessionID = '" + sessionID + "'";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(checkMapID);
            
            //find the mapID
            while (rs.next()) {
                mapID = rs.getInt("mapID");
                st.close();
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to find sessions");
        }
        return maps;
    }

    //makeMap makes a new map with user given mapValues and relates it to a session with a sessionID then returns the mapID
    public static int makeMap(String mapValues, int sessionID) {
        //Generate new mapID
        Random rand = new Random();
        int num = rand.nextInt(1001);
        int mapID = num;

        String randCheck = "SELECT mapID FROM map WHERE mapID = " + num;
        String insertNewMap = "INSERT INTO map (mapID, mapValues) VALUES (" + mapID + ", '" + mapValues + "')";
        String insertNewSessMap = "INSERT INTO sessionowner (sessionID, mapID) VALUES ('" + sessionID + "', " + mapID + ")";

        //Check if new mapID is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(randCheck);

            while (rs.next()) {
                mapID = rs.getInt("mapID");
            }
            if (mapID == num) {
                System.out.println("The mapID is not unique");
            } else {
                st.executeUpdate(insertNewMap);  //insert new map
                st.executeUpdate(insertNewSessMap); //insert new sessionmaps
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to make a new session");
        }

        return num;
    }
    
    //getMapValues returns all mapValues of a map
    public static String getMapValues(int mapID) {
        String findMapValues = "SELECT mapValues FROM map WHERE mapID = " + mapID; 
        String mapValues = "";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(findMapValues);
            
            while (rs.next()) {
                mapValues = rs.getString("mapValues");
                return mapValues;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to get map");
        }
        return mapValues;
    }
    
    //deleteMap removes a map from the database using the mapID
    public static void deleteMap(int mapID) {
                String query = "DELETE FROM map WHERE mapID = " + mapID;
        try {
            Statement st = con.createStatement();

            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to delete map");
        }
    }
    
    //makeToken makes a new token given the player's username, size of the token, and the mapID it belongs to then returns the new tokenID
    public static int makeToken(String username, int sizeX, int sizeY, int mapID) {
        Random rand = new Random();
        int num = rand.nextInt(10000);
        int tokenID = num;
        
        String randCheck = "SELECT tokenID FROM token WHERE tokenID = " + num;
        String insertNewToken = "INSERT INTO token (tokenID, tokenSizeX, tokenSizeY) VALUES (" + tokenID + ", " + sizeX + ", " + sizeY;
        String insertNewTokenOwner = "INSERT INTO tokenowner (tokenID, username) VALUES (" + tokenID + ", " + username;
        String insertNewTokenMap = "INSERT INTO mapContain (mapID, tokenID) VALUES (" + mapID + ", " + tokenID;
        
        //Check if new tokenID is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(randCheck);
            
            while (rs.next()) {
                tokenID = rs.getInt("tokenID");
            }
            if (tokenID == num) {
                System.out.println("This tokenID is not unique");
            } else {
                st.executeUpdate(insertNewToken);
                st.executeUpdate(insertNewTokenOwner);
                st.executeUpdate(insertNewTokenMap);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to make a new token");
        }
        
        return num;
    }
    
    //deleteToken removes a token from the database using the tokenID
    public static void deleteToken(int tokenID) {
        String query = "DELETE FROM token WHERE tokenID = " + tokenID;
        try {
            Statement st = con.createStatement();
            
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to delete token");
        }
     }

    //closeCon closes the connection to the DPS database to relieve reserved resources
    public static void closeCon() {
        try {
            con.close();

        } catch (SQLException ex) {
            System.out.println("Unable to close connection to DPSDB");
        }
    }

    //Test all methods
    public static void main(String args[]) {
        //test connect
        con = connector.connect();

        String username = "Serdiann";
        String password = "fuckace";
        int sesID = 1234;
        int mapID = 60;

        //test makeUser
        //connector.makeUser(username, password);
        
        //tests Authenticate
        boolean bool = connector.Authenticate(username, password);
        System.out.println("Authenticating... " + bool);

        //tests getSessionList
        ArrayList<Integer> sessions = new ArrayList<Integer>();
        sessions = connector.getSessionList(username);
        System.out.println(username + "'s owned sessions: ");
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println("Session ID: " + sessions.get(i));
        }

        //test makeSession
        int sessionID = connector.makeSession("test", username);
        System.out.println("Your new session's ID is: " + sessionID);

        //test editSession
        //connector.editSession(sessionID, "Serdiann s00ks");
        
        //test deleteSession
        //connector.deleteSession(sesID);
       
        //test getMapList
        /*ArrayList<String> maps = new ArrayList<String>();
        maps = connector.getMapList(60);
        System.out.println(username + "Maps of this session: ");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println("Map name: " + maps.get(i));
        }*/

        //test makeMap
        /*int mapID = connector.makeMap(mapValues, sessionID);
        System.out.println("Your new session ID is: " + sessionID);*/
        
        //test getMapValues
        //
        
        //test deleteMap
        //connector.deleteMap(mapID);
        
        //test getToken
        //
        
        //test makeToken
        //
        
        //test deleteToken
        //
        
        //test closeCon
        connector.closeCon();
    }
}