
import java.sql.*;

public class connector {

    private static final String url = "jdbc:mysql://dpsdb.cltxtb6ls4t4.us-east-1.rds.amazonaws.com:3306/dpsdb?user=Aesthellar&password=password";

    private static Connection con;

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

    public static void main(String args[]) {
        con = connector.connect();
        try {
            con.close();

        } catch (SQLException ex) {
            System.out.println("Unable to close connection to DPSDB");
        }
    }
}
