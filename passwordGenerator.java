import java.sql.*;
import java.util.*;
import javax.swing.*;


public class passwordGenerator{
    private String tabel;
    private int decimalPlace;
    private String column;
    private static final String url = "jdbc:mysql://localhost:3306/Banking system";
    private static final  String username = "sqluser";
    private static final String password = "ssbhy";

    public passwordGenerator(String tabel, String column, int decimalPlace){
        this.tabel = tabel;
        this.decimalPlace = decimalPlace;
        this.column = column;
    }

    private static String generateID(int m){
        Random random = new Random();
        long id = random.nextLong((long) Math.pow(10, m));
        return String.format("%0" + m + "d", id);
    }
    
     private static boolean isIDExist(String id, String table, String column){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + table + " WHERE " + column + " = " + id);
            ResultSet resultSet =stmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            stmt.close();
            conn.close();
            return count > 0;
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "error in unique id creation!", "failed", JOptionPane.ERROR_MESSAGE);
            
        }
        return true;            
    }

    private static String generateCustomerID(int m, String table, String column){
        try {
            while (true) {
                String Id = generateID(m);
                if (!isIDExist(Id, table, column)) {
                    return Id;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error in generating id", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public String getId(){
        String ID = generateCustomerID(this.decimalPlace, this.tabel, this.column);
        return ID;
    }
}