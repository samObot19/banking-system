import java.awt.Component;
import java.sql.*;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

public class Utilitys{
    private String tableName;
    private String column;
    private String Password;
    private static final String url = "jdbc:mysql://localhost:3306/banking system";
    private static final String username = "sqluser";
    private static final String password = "ssbhy";

    public Utilitys(String tableName, String column, String Password){
            this.tableName = tableName;
            this.column = column;
            this.Password = Password;
    }

    public Utilitys(String Password){
        this.tableName = "account";
        this.column = "Balance";
        this.Password = Password;
    }

    public Utilitys(){
        this.tableName = "";
        this.Password = "";
        this.column = "";
    }

    public void copyPanel(JPanel sourcePanel, JPanel panel){
          Component [] components = sourcePanel.getComponents();

          for(Component comp : components){
            panel.add(comp);
          }
    }

    public Vector<String> getAccounts(long customerId){
        Vector<String> v = new Vector<>();
        String query = "SELECT AccountNumber FROM account WHERE Customer_ID = ?";
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, customerId);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                v.add(rst.getString(1));
            }
        }catch(SQLException E){
            E.printStackTrace();
        }
        return v;
    }

    public void populateBox(JComboBox<String> comboBox){
        Locale[] locales = Locale.getAvailableLocales();

        Set<String> currencyCodes = new HashSet<>();

        for(Locale loc: locales){
            try {
                Currency C = Currency.getInstance(loc);
                currencyCodes.add(C.getCurrencyCode());
            } catch (Exception e) {
                // i ignore this exception 
            }
        }

        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(currencyCodes.toArray(new String[0]));
        comboBox.setModel(comboModel);
    }

    public float getMaxValue(){
        try(Connection connect = DriverManager.getConnection(url, username, password)){
            String query = "SELECT " + this.column + " FROM " + this.tableName + "WHERE AccountNumber = ?";
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, this.Password);
            ResultSet amountExcuter = stmt.executeQuery();
            amountExcuter.next();
            float ammount = amountExcuter.getFloat(1);
            stmt.close();
            connect.close();
            return ammount;
        }catch(SQLException s){
            s.getStackTrace();
        }
        return -1;
    }

    public double getBalance(String AccountNumber){
         String query = "SELECT Balance FROM account WHERE AccountNumber = ?";
         try(Connection cnt = DriverManager.getConnection(url, username, password)){
            PreparedStatement stmt = cnt.prepareStatement(query);
            stmt.setString(1, AccountNumber);
            ResultSet rst = stmt.executeQuery();
            rst.next();
            return rst.getDouble(1);
         }catch(SQLException sqlException){
            sqlException.printStackTrace();
         }
         return -1;
    }

    public String getStatus(String AccountNumber){
        String query = "SELECT Status FROM account WHERE AccountNumber = ?";
         try(Connection cnt = DriverManager.getConnection(url, username, password)){
            PreparedStatement stmt = cnt.prepareStatement(query);
            stmt.setString(1, AccountNumber);
            ResultSet rst = stmt.executeQuery();
            rst.next();
            return rst.getString(1);
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
         return null;
    }

    public String getFullName(String customerPassword){
        String query = "SELECT FirstName, MiddleName, LastName FROM customer WHERE password = ?";
         try(Connection cnt = DriverManager.getConnection(url, username, password)){
            PreparedStatement stmt = cnt.prepareStatement(query);
            stmt.setString(1, customerPassword);
            ResultSet rst = stmt.executeQuery();
            rst.next();
            return rst.getString(1) + "  " + 
            rst.getString(2) + "  " + rst.getString(3);
         }catch(SQLException sqlException){
            sqlException.printStackTrace();
         }
         return null;
    }

    public Vector<String> getFullCustomerInformation(String customerPassword){
        Vector<String> v = new Vector<>();
        String query = "SELECT FirstName, MiddleName, LastName, Email, phoneNumber, Nationality FROM customer WHERE password = ?";
        try(Connection connect = DriverManager.getConnection(url, username, password)){
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, customerPassword);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            v.add(resultSet.getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3));
            v.add(resultSet.getString(4));
            v.add(resultSet.getString(5));
            v.add(resultSet.getString(6));
            stmt.close();
            connect.close();
            return v;
        }catch(SQLException ex){
            v.add(ex.toString());
        }
        return v;
    }

    public String Transfer(String intiator, String reciever, double Amount){
        if(!passwordGenerator.isIDExist(reciever, "account", "AccountNumber") || reciever == null) return "incorrect  account number please try again";
        if (intiator == reciever) {
            return "it,s rediculus transfering many to in between the same account fuck you!!";
        }
        try(Connection cont = DriverManager.getConnection(url, username, password)){

            cont.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            cont.setAutoCommit(false);

            double intiatorBalance = readBalance(intiator, cont);
            double recieverBalance = readBalance(reciever, cont);

            if (intiatorBalance >= Amount && intiatorBalance > 0) {
                double updatedIntiatorBalance = intiatorBalance - Amount;
                double updatedRecieverBalance = recieverBalance + Amount;
                updateBalance(reciever, updatedRecieverBalance, cont);
                updateBalance(intiator, updatedIntiatorBalance, cont);
                cont.commit();
                return ("transfered succsesfuly\t" + Amount + " ETB\n Your current balance is " + getBalance(intiator));
            }else{
                return("you have not enough balance");
            }
        }catch(SQLException sqlex){
             return "Transaction unsuccessfull";
        }
    }

    public void updateBalance(String accountNumber, double Amount, Connection cnt){
           String query = "UPDATE account SET Balance = ? WHERE AccountNumber = ?";
           try(PreparedStatement stmt = cnt.prepareStatement(query)){
                stmt.setDouble(1, Amount);
                stmt.setString(2, accountNumber);
                stmt.executeUpdate();
           }catch(SQLException e){
                e.printStackTrace();
           }
    }

    private float readBalance(String accountNumber, Connection cnt){
            String query = "SELECT Balance FROM account WHERE AccountNumber = ?";
            try{
                PreparedStatement stmt = cnt.prepareStatement(query);
                stmt.setString(1, accountNumber);
                ResultSet rst = stmt.executeQuery();
                rst.next();
                return rst.getFloat(1);
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return -1;
    }

    public void storeTransaction(String intiatorAccountNumber, String reciverAccNumber, float ammount, String TransactionType){

    }

    public void getID(String tableName, String IDcolumnName, String passwordKey, String passwordColumnName){
        String query = "SELECT  " + IDcolumnName + " FROM " + tableName + " WHERE " + passwordColumnName + " = ?";
        try {
            Connection cont = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = cont.prepareStatement(query);
            stmt.setString(1, passwordKey);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    
}
