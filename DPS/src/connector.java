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
            if (rs.next()) {
                System.out.println("Username is not unique");
            } else {
                st.executeUpdate(insertUser);   //if username is unique then insert the new user
            }
            st.close();

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
            if (rs.next()) {
                String pass = rs.getString("password");
                if (pass.equals(password)) 
                {
                bool = true;
                } else {
                bool = false;
                }
            } else {
                bool = false;
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
                        //Call makeToken with username, size (1, 1), mapID, and tokenValues
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

            if (rs.next()) {
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

    //editSession updates the sessionName of a sessionID
    public static void editSession(int sessionID, String sessionName) {

        String query = "UPDATE session SET sessionName = '" + sessionName + "' WHERE sessionID = " + sessionID;

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
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
    public static ArrayList<Integer> getMapList(int sessionID) {

        ArrayList<Integer> maps = new ArrayList<Integer>();
        int mapID = 1001;
        String checkMapID = "SELECT mapID FROM sessionmaps WHERE sessionID = '" + sessionID + "'";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(checkMapID);

            //find the mapID
            while (rs.next()) {
                mapID = rs.getInt("mapID");
                maps.add(mapID);
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
        String insertNewSessMap = "INSERT INTO sessionmaps (sessionID, mapID) VALUES ('" + sessionID + "', " + mapID + ")";

        //Check if new mapID is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(randCheck);

            if(rs.next()) {
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

    //getMapValues returns mapValues of a map
    public static String getMapValues(int mapID) {
        String findMapValues = "SELECT mapValues FROM map WHERE mapID = " + mapID;
        String mapValues = "";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(findMapValues);

            if (rs.next()) {
                mapValues = rs.getString("mapValues");
                return mapValues;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to get map vales");
        }
        return mapValues;
    }

    //editMapValues updates the mapValues of a given mapID
    public static void editMapValues(int mapID, String mapValues) {
        String query = "UPDATE map SET mapValues = '" + mapValues + "' WHERE mapID = " + mapID;

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to update map");
        }
    }

    //deleteMap removes a map from the database using the mapID
    public static void deleteMap(int mapID) {
        String query = "DELETE FROM map WHERE mapID = " + mapID;
        try {
            Statement st = con.createStatement();

            st.executeUpdate(query);
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to delete map");
        }
    }

    //makeToken makes a new token given the player's username, size of the token, and the mapID it belongs to then returns the new tokenID
    public static int makeToken(String username, int sizeX, int sizeY, int mapID, String tokenValues) {
        Random rand = new Random();
        int num = rand.nextInt(10000);
        int tokenID = num;

        String randCheck = "SELECT tokenID FROM token WHERE tokenID = " + num;
        String insertNewToken = "INSERT INTO token (tokenID, tokenSizeX, tokenSizeY, tokenValues) VALUES (" + tokenID + ", " + sizeX + ", " + sizeY + ", '" + tokenValues + "')";
        String insertNewTokenOwner = "INSERT INTO tokenowner (tokenID, username) VALUES (" + tokenID + ", '" + username + "')";
        String insertNewTokenMap = "INSERT INTO mapContain (mapID, tokenID) VALUES (" + mapID + ", " + tokenID + ")";

        //Check if new tokenID is unique
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(randCheck);

            if (rs.next()) {
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

    //getTokenValues returns all tokenValues of a token
    public static String getTokenValues(int tokenID) {
        String findTokenValues = "SELECT tokenValues FROM token WHERE tokenID = " + tokenID;
        String tokenValues = "";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(findTokenValues);

            while (rs.next()) {
                tokenValues = rs.getString("tokenValues");
                return tokenValues;
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to get token values");
        }
        return tokenValues;
    }

    //editTokenValues updates the tokenValues of a given tokenID
    public static void editTokenValues(int tokenID, String tokenValues) {
        String query = "UPDATE token SET tokenValues = '" + tokenValues + "' WHERE tokenID = " + tokenID;

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to update token");
        }
    }

    //deleteToken removes a token from the database using the tokenID
    public static void deleteToken(int tokenID) {
        String query = "DELETE FROM token WHERE tokenID = " + tokenID;
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
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
        //con = connector.connect();

        //test makeUser
        //connector.makeUser("Aesthellar", "letmein");

        //tests Authenticate
        //boolean bool = connector.Authenticate("Aesthellar", "letmein");
        //System.out.println("Authenticating... " + bool);
        
        //test makeSession
        //int sessionID = connector.makeSession("test", "Aesthellar");
        //System.out.println("Your new session's ID is: " + sessionID);
        
        //tests getSessionList
        /*ArrayList<Integer> sessions = new ArrayList<Integer>();
        sessions = connector.getSessionList("Aesthellar");
        System.out.println("Aesthellar's owned sessions: ");
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println("Session ID: " + sessions.get(i));
        }*/
        
        //test editSession
        //connector.editSession(49, "Tester");
        
        //test deleteSession
        //connector.deleteSession(49);
        
        //test makeMap
        //int mapID = connector.makeMap("mapstuff", 849);
        //System.out.println("Your new map ID is: " + mapID);
        
        //test getMapList
        /*ArrayList<Integer> maps = new ArrayList<Integer>();
        maps = connector.getMapList(849);
        System.out.println("Maps of session 849: ");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println("MapID: " + maps.get(i));
        }*/
        
        //test getMapValues
        /*String mapValues = connector.getMapValues(24);
        System.out.println("MapValues: " + mapValues);*/
        
        //test editMapValues
        //connector.editMapValues(24, "newmapstuff0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        
        //test deleteMap
        //connector.deleteMap(24);
        
        //test makeToken
        //int tokenID = connector.makeToken("Aesthellar", 1, 1, 594, "tokenstuff");
        //System.out.println("Your new token ID is: " + tokenID);
        
        //test getTokenValues
        //String tokenValues = connector.getTokenValues(20);
        //System.out.println(tokenValues);
        
        //test editTokenValues
        //String tokenValues = "map values 00000000000";
        //connector.editTokenValues(20, tokenValues);
        
        //test deleteToken
        //connector.deleteToken(117);
        
        //test closeCon
        //connector.closeCon();
    }
}
