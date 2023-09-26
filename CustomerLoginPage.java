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
import java.sql.*;

public class CustomerLoginPage extends JFrame implements ActionListener{
    
    private JPanel infoAndPhotoPanel, infoPanel, AccountTabPanel, withDrawPanel;
    private JLabel photoLabel, name, userNameL, accountNumber, accountNumberHolderL, balanceL, balanceHolderL, statusL, statusHolderL,noificationL
    , withdrawBaner, withdrawL, currencyL;
    private JTextArea notificatioArea;
    private JTextField withdrowTextField, commentTextField;
    private String customerUserName, customerPassword;
    private static final String url = "jdbc:mysql://localhost:3306/banking system";
    private static final String username = "sqluser";
    private static final String password = "ssbhy";
    private JSpinner amount;
    private JButton conform;
    private JTabbedPane tabs;
    private JComboBox<String> currency_codes;
    private static final int minValue = 100;
    private static final int currentValue = 100;
    private static final int steps = 100;
    private static final int maxValue = 1000000;
    public CustomerLoginPage(String customerUserName, String customerPassword){
        this.customerPassword = customerPassword;
        this.customerUserName = customerUserName;

        tabs = new JTabbedPane();
        tabs.setBackground(Color.DARK_GRAY);
        tabs.setForeground(Color.WHITE);
        tabs.setFont(new Font("", Font.BOLD, 15));
        tabs.setOpaque(true);

        //preparing photo label

        photoLabel = new JLabel();
        photoLabel.setBounds(10, 15, 250, 250);
        photoLabel.setBackground(Color.GRAY);
        photoLabel.setOpaque(true);

        infoPanel = new JPanel(new GridLayout(4, 2));
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setBounds(0,280, 400, 400);
        //preparing labels to add into infoPanel

        name = new JLabel("user name :", JLabel.CENTER);
        name.setBackground(Color.DARK_GRAY);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Mv Boli", Font.PLAIN, 20));

        userNameL = new JLabel("",JLabel.LEFT);
        userNameL.setBackground(Color.DARK_GRAY);
        userNameL.setForeground(Color.WHITE);
        userNameL.setFont(new Font("Mv Boli", Font.PLAIN, 22 ));
        userNameL.setText(customerUserName);
        
        accountNumber = new JLabel("Account number: ", JLabel.CENTER);
        accountNumber.setBackground(Color.DARK_GRAY);
        accountNumber.setForeground(Color.WHITE);
        accountNumber.setFont(new Font("Mv Boli", Font.PLAIN, 20));
        
        accountNumberHolderL = new JLabel("", JLabel.CENTER);
        accountNumberHolderL.setBackground(Color.DARK_GRAY);
        accountNumberHolderL.setForeground(Color.WHITE);
        
        balanceL = new JLabel("Balance: ", JLabel.CENTER);
        balanceL.setBackground(Color.DARK_GRAY);
        balanceL.setForeground(Color.WHITE);
        balanceL.setFont(new Font("Mv Boli", Font.PLAIN, 20));
        
        balanceHolderL = new JLabel("", JLabel.CENTER);
        balanceHolderL.setBackground(Color.darkGray);
        balanceHolderL.setForeground(Color.WHITE);
        
        statusL = new JLabel("status: ", JLabel.CENTER);
        statusL.setBackground(Color.DARK_GRAY);
        statusL.setForeground(Color.WHITE);
        statusL.setFont(new Font("Mv Boli", Font.PLAIN, 20));

        statusHolderL = new JLabel("", JLabel.CENTER);
        statusHolderL.setBackground(Color.DARK_GRAY);
        statusHolderL.setForeground(Color.WHITE);
        // adding to the info panel
        infoPanel.add(name);
        infoPanel.add(userNameL);
        infoPanel.add(accountNumber);
        infoPanel.add(accountNumberHolderL);
        infoPanel.add(balanceL);
        infoPanel.add(balanceHolderL);
        infoPanel.add(statusL);
        infoPanel.add(statusHolderL);

        infoAndPhotoPanel = new JPanel();
        infoAndPhotoPanel.setBackground(Color.darkGray);
        infoAndPhotoPanel.setLayout(null);
        infoAndPhotoPanel.setBounds(0, 0, 500, 700);
        
        infoAndPhotoPanel.add(photoLabel);
        infoAndPhotoPanel.add(infoPanel);

        noificationL = new JLabel("Notifications", JLabel.CENTER);
        noificationL.setBackground(Color.DARK_GRAY);
        noificationL.setForeground(Color.WHITE);
        noificationL.setFont(new Font("Mv Boli", Font.BOLD, 25));
        noificationL.setBounds(500, 15, 200, 25);

        notificatioArea = new JTextArea();
        notificatioArea.setBounds(500, 65, 200, 200);

        AccountTabPanel = new JPanel();
        AccountTabPanel.setBackground(Color.DARK_GRAY);
        AccountTabPanel.setLayout(null);

        AccountTabPanel.add(infoAndPhotoPanel);
        AccountTabPanel.add(noificationL);
        AccountTabPanel.add(notificatioArea);

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

        SpinnerNumberModel nModel = new SpinnerNumberModel(currentValue, minValue, maxValue, steps);
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
        
      
          

        tabs.addTab("Customer Page", AccountTabPanel);
        tabs.addTab("Withdraw", withDrawPanel);

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
                    ImageIcon icon =new ImageIcon(image);
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

    public void setName(JLabel label){
        
    }

    public static void main(String [] args){
       // new CustomerLoginPage("", "");
    }
}