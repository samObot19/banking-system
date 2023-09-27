import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class add_account extends JFrame implements ActionListener{
    private JLabel  accountTypeL, intialBalanceL;
    private JSpinner amount;
    private JComboBox<String> accountTypeSelector;
    private JButton createAccount;
    private String AccountType;
    private String accountNumber;
    private long CustomerID;
    private double intialBalance;
    private static final String url = "jdbc:mysql://localhost:3306/banking system";
    private static final String password = "ssbhy";
    private static final String userName = "sqluser";

    public add_account(String accountNumber, long CustomerId){
        this.CustomerID = CustomerId;
        this.accountNumber = accountNumber;
        accountTypeL = new JLabel("Account Type:", JLabel.CENTER);
        accountTypeL.setBackground(Color.DARK_GRAY);
        accountTypeL.setForeground(Color.WHITE);
        accountTypeL.setFont(new Font("Mv Boli", Font.BOLD, 20));
        accountTypeL.setBounds(20, 150, 200, 40);

        intialBalanceL = new JLabel("Intial amount:", JLabel.CENTER);
        intialBalanceL.setBackground(Color.DARK_GRAY);
        intialBalanceL.setFont(new Font("Mv Boli", Font.BOLD, 20));
        intialBalanceL.setForeground(Color.WHITE);
        intialBalanceL.setBounds(20, 220, 200, 40);

        double minValue = 100;
        double maxValue = 1000;
        double steps = 100;
        double currentValue = 100;
        SpinnerNumberModel model = new SpinnerNumberModel(currentValue, minValue, maxValue, steps);
        amount = new JSpinner(model);
        amount.setBounds(200, 220, 250, 40);
        
        String [] accountTypeChoise = {"saving", "checking", "cd", "moneyMarket", "Loan"};
        accountTypeSelector = new JComboBox<>(accountTypeChoise);
        accountTypeSelector.setBounds(200, 150, 250, 40);

        createAccount = new JButton("Create Account");
        createAccount.setBounds(250, 290, 150, 35);
        createAccount.addActionListener(this);
        setLayout(null);
        add(intialBalanceL);
        add(accountTypeL);
        add(accountTypeSelector);
        add(amount);
        add(createAccount);
        
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
           if (e.getSource() == createAccount) {
              String accType = (String)accountTypeSelector.getSelectedItem();
              double ammount  = (double)amount.getValue();
            try {
                addAccount(CustomerID, this.accountNumber, accType, ammount);
                JOptionPane.showMessageDialog(null, "adding account succesfully!/n Thanks!", "add account", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
           }
    }

    public void addAccount(long customerId, String accountNumber, String AccountType, double balance) throws SQLException{
        String query = "INSERT INTO account(Customer_ID, AccountNumber, AccountType, Balance) VALUES (?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, userName, password);
            PreparedStatement stmt = connection.prepareStatement(query)
        ){
           stmt.setLong(1, customerId);
           stmt.setString(2, accountNumber);
           stmt.setString(3, AccountType);
           stmt.setDouble(4, balance);
           stmt.executeUpdate();
           stmt.close();
           connection.close();
        }
    }



    public static void main(String[]args){
        new add_account("", 1000);
    }
}