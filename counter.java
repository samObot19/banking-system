import java.sql.*;
import java.util.Vector;


public class counter{
    private String tabelName;
    private String columnName;
    private long conditions;

    private static final String url = "jdbc:mysql://localhost:3306/Banking system";
    private static final String userName = "sqluser";
    private static final String password = "ssbhy";
    public counter(String tabelName, String columnName, long conditions){
        this.tabelName = tabelName;
        this.columnName = columnName;
        this.conditions = conditions;
     }

    public int getCountingItems(){
        String query = "SELECT COUNT(*) FROM " + this.tabelName + " WHERE " + columnName + " = ?";

        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, this.conditions);
            ResultSet set = stmt.executeQuery();
            set.next();
            int num = set.getInt(1);
            return num;
        }catch(SQLException e){
            System.out.println(e);
        }
        return 0;
    }

    public Vector<String> getItems(String column){
        Vector<String> v = new Vector<>();
        String query = "SELECT " + column + " FROM " + this.tabelName + " WHERE " + this.columnName + " = ?";
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, this.conditions);
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                v.add(set.getString(1));
            }
            return v;
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args){
        counter cnt = new counter("account", "Customer_ID", 50);
        Vector<String> v = cnt.getItems("AccountNumber");

        for(int i = 0; i < v.size(); i++){
              System.out.println(v.get(i));
        }
    }
}