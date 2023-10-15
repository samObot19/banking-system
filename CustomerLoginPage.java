import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.sql.*;
import java.text.Normalizer;

public class CustomerLoginPage extends JFrame implements ActionListener{
    
    private JPanel infoPanel, AccountTabPanel, withDrawPanel, DepositPanel, TransferPanel;
    private JLabel photoLabel, name, userNameL, accountNumber, accountNumberHolderL, balanceL, balanceHolderL, statusL, statusHolderL,noificationL
    , withdrawBaner, withdrawL, currencyL, DepositAmountL, DepositCurrency, NationalityL, phoneNumberL, EmailL, recieverAccountNumberLabel, transferAmountLabel, currencyOfTransferLabel;
    private JTextField withdrowTextField, recieverAccountNumber;
    private JMenuBar menubar;
    private JMenu menus, Accounts;
    private JMenuItem logOut, addAccount, EmployementRequestMenu;
    private static final String url = "jdbc:mysql://localhost:3306/banking system";
    private static final String username = "sqluser";
    private static final String password = "ssbhy";
    private JSpinner amount, depositAmount, TransferAmount;
    private JButton conform, conformDepositButton, conformTransfer;
    private JTabbedPane tabs;
    private JComboBox<String> currency_codes, depositCurrency, TransferCurrency;
    private static final float minValue = 100;
    private static final float currentValue = 100;
    private static final float steps = 100;
    private static final float maxValue = 1000000;
    private String customerUserName, customerPassword, AccountNumber;
    private float minWithdrawAmount = 0;
    private float currentAmount = 0;
    private float incrementSine = 20;
    
    public CustomerLoginPage(String customerUserName, String customerPassword){
       this.customerPassword = customerPassword;
       this.customerUserName = customerUserName;
       
       menubar = new JMenuBar();
       setJMenuBar(menubar);
       menus = new JMenu("menu");
       menubar.add(menus);
       Accounts = new JMenu("Accounts");

       Vector<String> accounts = (new Utilitys()).getAccounts(getCustomerId(customerPassword));
       this.AccountNumber = accounts.get(0);
        
       int m = 1;
       for(String i : accounts){
            JMenuItem item = new JMenuItem("Account " + m);
            item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    AccountNumber = i;
                    double maxVal = (new Utilitys()).getBalance(AccountNumber);
                    SpinnerNumberModel newWithdrawModel = new SpinnerNumberModel(currentAmount, minWithdrawAmount, maxVal, incrementSine);
                    amount.setModel(newWithdrawModel);
                    TransferAmount.setModel(newWithdrawModel);
                    balanceHolderL.setText(Double.toString((new Utilitys()).getBalance(i)) + " ETB");
                    statusHolderL.setText((new Utilitys()).getStatus(AccountNumber));
                    accountNumberHolderL.setText(i);
                }
            });
            Accounts.add(item);
            m++;
        }

       menubar.add(Accounts);

       logOut = new JMenuItem("Log out");
       logOut.addActionListener(this);
       addAccount = new JMenuItem("Add account");
       addAccount.addActionListener(this);
       EmployementRequestMenu = new JMenuItem("Employment request");
       EmployementRequestMenu.addActionListener(this);
       
       menus.add(addAccount);
       menus.add(logOut);
       menus.add(EmployementRequestMenu);
       
       tabs = new JTabbedPane();
       tabs.setBackground(Color.DARK_GRAY);
       tabs.setForeground(Color.WHITE);
       tabs.setFont(new Font("", Font.BOLD, 15));
       tabs.setOpaque(true);

        //preparing photo label

        photoLabel = new JLabel();
        photoLabel.setBounds(10, 15, 220, 220);
        photoLabel.setBackground(Color.GRAY);
        photoLabel.setOpaque(true);

        infoPanel = new JPanel(new GridLayout(4, 2));
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setBounds(250, 15, 500, 400);
        //preparing labels to add into infoPanel

        name = new JLabel("user name :", JLabel.CENTER);
        name.setBackground(Color.DARK_GRAY);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Mv Boli", Font.PLAIN, 20));

        Utilitys ut = new Utilitys();
        Vector<String> customerInfo = ut.getFullCustomerInformation(customerPassword);


        userNameL = new JLabel("userName:       " + customerInfo.get(0) ,JLabel.LEFT);
        userNameL.setBackground(Color.DARK_GRAY);
        userNameL.setForeground(Color.WHITE);
        userNameL.setFont(new Font("", Font.PLAIN, 25));
        userNameL.setBounds(300, 70, 450, 50);

        phoneNumberL = new JLabel("Phone:          " + customerInfo.get(2), JLabel.LEFT);
        phoneNumberL.setBackground(Color.DARK_GRAY);
        phoneNumberL.setForeground(Color.WHITE);
        phoneNumberL.setFont(new Font("", Font.PLAIN, 22));
        phoneNumberL.setBounds(300, 170, 450, 50);

        EmailL = new JLabel("Email:         " + customerInfo.get(1), JLabel.LEFT);
        EmailL.setBackground(Color.darkGray);
        EmailL.setForeground(Color.WHITE);
        EmailL.setFont(new Font("", Font.PLAIN, 22));
        EmailL.setBounds(300, 270, 450, 50);

        NationalityL = new JLabel("Nationality:      " + customerInfo.get(3), JLabel.LEFT);
        NationalityL.setBackground(Color.DARK_GRAY);
        NationalityL.setForeground(Color.WHITE);
        NationalityL.setFont(new Font("", Font.PLAIN, 22));
        NationalityL.setBounds(300, 370, 450, 50);
        
        accountNumber = new JLabel("Account number: ", JLabel.LEFT);
        accountNumber.setBackground(Color.DARK_GRAY);
        accountNumber.setForeground(Color.WHITE);
        accountNumber.setFont(new Font("", Font.PLAIN, 22));
        accountNumber.setBounds(300, 470, 200, 50);
        
        accountNumberHolderL = new JLabel(this.AccountNumber, JLabel.LEFT);
        accountNumberHolderL.setBackground(Color.DARK_GRAY);
        accountNumberHolderL.setForeground(Color.WHITE);
        accountNumberHolderL.setFont(new Font("", Font.BOLD, 20));
        accountNumberHolderL.setBounds(520, 470, 200, 50);
        
        balanceL = new JLabel("Balance: ", JLabel.CENTER);
        balanceL.setBackground(Color.DARK_GRAY);
        balanceL.setForeground(Color.WHITE);
        balanceL.setFont(new Font("Mv Boli", Font.PLAIN, 20));
        
        balanceHolderL = new JLabel("", JLabel.LEFT);
        balanceHolderL.setBackground(Color.darkGray);
        balanceHolderL.setForeground(Color.WHITE);
        balanceHolderL.setFont(new Font("", Font.BOLD, 22));
        
        statusL = new JLabel("status: ", JLabel.CENTER);
        statusL.setBackground(Color.DARK_GRAY);
        statusL.setForeground(Color.WHITE);
        statusL.setFont(new Font("Mv Boli", Font.PLAIN, 20));

        statusHolderL = new JLabel("", JLabel.LEFT);
        statusHolderL.setBackground(Color.DARK_GRAY);
        statusHolderL.setForeground(Color.WHITE);
        statusHolderL.setFont(new Font("", Font.BOLD, 25));
        // adding to the info panel
        infoPanel.add(name);
        infoPanel.add(userNameL);
        infoPanel.add(accountNumber);
        infoPanel.add(accountNumberHolderL);
        infoPanel.add(balanceL);
        infoPanel.add(balanceHolderL);
        infoPanel.add(statusL);
        infoPanel.add(statusHolderL);

        noificationL = new JLabel("Customer information", JLabel.LEFT);
        noificationL.setBackground(Color.DARK_GRAY);
        noificationL.setForeground(Color.WHITE);
        noificationL.setFont(new Font("Mv Boli", Font.BOLD, 30));
        noificationL.setBounds(340, 10, 370, 25);

        AccountTabPanel = new JPanel();
        AccountTabPanel.setBackground(Color.DARK_GRAY);
        AccountTabPanel.setLayout(null);

        AccountTabPanel.add(userNameL);
        AccountTabPanel.add(EmailL);
        AccountTabPanel.add(NationalityL);
        AccountTabPanel.add(phoneNumberL);
        AccountTabPanel.add(accountNumber);
        AccountTabPanel.add(accountNumberHolderL);
        //AccountTabPanel.add(infoPanel);
        AccountTabPanel.add(photoLabel);
        AccountTabPanel.add(noificationL);
        

        withdrawBaner = new JLabel("Withdraw", JLabel.CENTER);
        withdrawBaner.setFont(new Font("Mv Boli", Font.BOLD, 100));
        withdrawBaner.setBounds(16, 10, 500, 100);
        withdrawBaner.setForeground(Color.WHITE);

        withdrawL = new JLabel("Amount", JLabel.CENTER);
        withdrawL.setFont(new Font("Mv Boli", Font.PLAIN, 20));
        withdrawL.setForeground(Color.white);
        withdrawL.setBackground(Color.DARK_GRAY);
        withdrawL.setOpaque(true);
        withdrawL.setBounds(200, 250, 100, 50);

        
        double maxWithdrawValue = (new Utilitys()).getBalance(this.AccountNumber);
        SpinnerNumberModel nModel = new SpinnerNumberModel(currentAmount, minWithdrawAmount, maxWithdrawValue, incrementSine);
        amount = new JSpinner(nModel);
        amount.setFont(new Font("", Font.PLAIN, 30));
        amount.setBounds(320, 250, 250, 50);


        currencyL = new JLabel("Currency", JLabel.CENTER);
        currencyL.setFont(new Font("Mv Boli", Font.PLAIN, 20));
        currencyL.setBackground(Color.DARK_GRAY);
        currencyL.setForeground(Color.WHITE);
        currencyL.setOpaque(true);
        currencyL.setBounds(200, 350, 100, 50);

        currency_codes = new JComboBox<>();
        populateBox(currency_codes);
        currency_codes.setSelectedItem("ETB");
        currency_codes.setBounds(320, 350, 250, 50);
        currency_codes.setFont(new Font("", Font.BOLD, 20));

        withDrawPanel = new JPanel();
        withDrawPanel.setLayout(null);
        withDrawPanel.setBackground(Color.DARK_GRAY);

        conform = new JButton("OK");
        conform.setBounds(410, 440, 70, 35);
        conform.addActionListener(this);
        //withDrawPanel.add(withdrawBaner);
        withDrawPanel.add(conform);
        withDrawPanel.add(withdrawL);
        withDrawPanel.add(amount);
        withDrawPanel.add(currencyL);
        withDrawPanel.add(currency_codes);
        
        DepositPanel = new JPanel();
        DepositPanel.setBackground(Color.darkGray);
        DepositPanel.setLayout(null);

        DepositAmountL = new JLabel("Amount", JLabel.CENTER);
        DepositAmountL.setBackground(Color.DARK_GRAY);
        DepositAmountL.setForeground(Color.WHITE);
        DepositAmountL.setFont(new Font("", Font.PLAIN, 23));
        DepositAmountL.setBounds(160, 270, 90, 50);

        SpinnerNumberModel mod = new SpinnerNumberModel(currentValue, minValue, maxValue, steps);
        depositAmount = new JSpinner(mod);
        depositAmount.setBounds(290, 270, 300, 50);
        depositAmount.setFont(new Font("", Font.BOLD, 25));

        DepositCurrency = new JLabel("currency", JLabel.CENTER);
        DepositCurrency.setBackground(Color.DARK_GRAY);
        DepositCurrency.setForeground(Color.WHITE);
        DepositCurrency.setFont(new Font("", Font.PLAIN, 22));
        DepositCurrency.setBounds(160, 350, 90, 50);
        
        depositCurrency = new JComboBox<>();
        populateBox(depositCurrency);
        depositCurrency.setSelectedItem("ETB");
        depositCurrency.setFont(new Font("", Font.BOLD, 25));
        depositCurrency.setBounds(290, 350, 300, 50);

        conformDepositButton = new JButton("OK");
        conformDepositButton.setBounds(390, 450, 75, 37);
        
        DepositPanel.add(DepositAmountL);
        DepositPanel.add(depositAmount);
        DepositPanel.add(DepositCurrency);
        DepositPanel.add(depositCurrency);
        DepositPanel.add(conformDepositButton);


        //transfer panel ui

        TransferPanel = new JPanel();
        TransferPanel.setLayout(null);
        TransferPanel.setBackground(Color.DARK_GRAY);

        recieverAccountNumberLabel = new JLabel("Account No", JLabel.LEFT);
        recieverAccountNumberLabel.setForeground(Color.WHITE);
        recieverAccountNumberLabel.setBackground(Color.DARK_GRAY);
        recieverAccountNumberLabel.setFont(new Font("Mv Boli", Font.PLAIN, 23));
        recieverAccountNumberLabel.setBounds(150, 200, 150, 40);

        recieverAccountNumber = new JTextField(20);
        recieverAccountNumber.setFont(new Font("", Font.BOLD, 23));
        recieverAccountNumber.setBounds(300, 200, 300, 50);

        transferAmountLabel = new JLabel("Amount", JLabel.LEFT);
        transferAmountLabel.setBackground(Color.DARK_GRAY);
        transferAmountLabel.setForeground(Color.WHITE);
        transferAmountLabel.setFont(new Font("Mv Boli", Font.BOLD, 23));
        transferAmountLabel.setBounds(150, 300, 150, 40);

        SpinnerNumberModel transferNumModel = new SpinnerNumberModel(currentAmount, minWithdrawAmount, maxWithdrawValue, incrementSine);
        TransferAmount = new JSpinner(transferNumModel);
        TransferAmount.setFont(new Font("", Font.BOLD, 27));
        TransferAmount.setBounds(300, 300,300, 50);

        currencyOfTransferLabel = new JLabel("currency", JLabel.LEFT);
        currencyOfTransferLabel.setForeground(Color.WHITE);
        currencyOfTransferLabel.setFont(new Font("Mv Boli", Font.PLAIN, 23));
        currencyOfTransferLabel.setBounds(150, 400, 150, 40);

        TransferCurrency = new JComboBox<>();
        populateBox(TransferCurrency);
        TransferCurrency.setSelectedItem("ETB");
        TransferCurrency.setFont(new Font("", Font.BOLD, 23));
        TransferCurrency.setBounds(300, 400,300, 50);

        conformTransfer = new JButton("OK");
        conformTransfer.setBounds(420, 500, 80, 50);
        conformTransfer.addActionListener(this);

        TransferPanel.add(recieverAccountNumberLabel);
        TransferPanel.add(recieverAccountNumber);
        TransferPanel.add(transferAmountLabel);
        TransferPanel.add(TransferAmount);
        TransferPanel.add(currencyOfTransferLabel);
        TransferPanel.add(TransferCurrency);
        TransferPanel.add(conformTransfer);
        

        tabs.addTab("Customer Page", AccountTabPanel);
        tabs.addTab("Withdraw", withDrawPanel);
        tabs.addTab("Deposit", DepositPanel);
        tabs.add("Transfer", TransferPanel);

        add(tabs);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(350, 20);
        getContentPane().setBackground(Color.DARK_GRAY);
        setSize(800, 750);
        setPhoto(photoLabel);
    }

    private void populateBox(JComboBox<String> comboBox){
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

    public void actionPerformed(ActionEvent e){
           if(e.getSource() == logOut){
              new Bank();
              dispose();
           }
           if(e.getSource() == addAccount){
                passwordGenerator ids = new passwordGenerator("account", "AccountNumber", 12);
                String accNumber = ids.getId();
                new add_account(accNumber, getCustomerId(this.customerPassword));
           }
           if (e.getSource() == conform) {
            
           }

           if(e.getSource() == conformTransfer){
                String reciever = recieverAccountNumber.getText();
                
                double r = Double.parseDouble(reciever);
                double a = Double.parseDouble(this.AccountNumber);
                //the above cast is to identify the user is try to transfer many to its own acount 

                Utilitys utls = new Utilitys();
                double amount = (double)(TransferAmount.getValue());
                
                if(r != a){
                    JOptionPane.showMessageDialog(null, utls.Transfer(this.AccountNumber, reciever, amount), "Transaction", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "You can't transfer money in the same account", "Transaction fault", JOptionPane.ERROR_MESSAGE);
                }
            }

           if (e.getSource() == EmployementRequestMenu){
                EventQueue.invokeLater(new Runnable(){
                    public void run() {
                        new EmployementRequest();
                    }
                });
           }
    }

    private void setPhoto(JLabel lab){
       try(Connection connection = DriverManager.getConnection(url, username, password)){
           String qu = "SELECT photo FROM customer WHERE password = ?";
           try(PreparedStatement stmt = connection.prepareStatement(qu)){
                 //stmt.setString(1, customerUserName);
                stmt.setString(1, customerPassword);
             try(ResultSet result = stmt.executeQuery()){
                //if(!result.next()) JOptionPane.showMessageDialog(null,customerPassword + "Not found", "error", JOptionPane.ERROR_MESSAGE);
                if(result.next()){
                    //JOptionPane.showMessageDialog(null,customerPassword + "found", "error", JOptionPane.ERROR_MESSAGE);
                    byte[] photodata = result.getBlob("photo").getBytes(1, (int)result.getBlob("photo").length());
                    InputStream inputStream = new ByteArrayInputStream(photodata);
                    BufferedImage image = ImageIO.read(inputStream);
                    ImageIcon icon = new ImageIcon(image);
                    lab.setIcon(icon);
                    stmt.close();
                    connection.close();
                }
             }catch(SQLException | IOException E){
                System.out.println(E);
             }
           }
       }catch(Exception e){
             System.out.println(e);
       }
    }
    

    private long getCustomerId(String password){
        String query = "SELECT Customer_ID FROM customer WHERE password = ?";
        try(Connection connection = DriverManager.getConnection(url, username, this.password);
            PreparedStatement stmt = connection.prepareStatement(query)){
                 stmt.setString(1, password);
                     try(ResultSet result = stmt.executeQuery()){
                        if(result.next()){
                            return result.getLong(1);
                        }
                     }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    
}