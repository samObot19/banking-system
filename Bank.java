import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Bank extends JFrame implements ActionListener{
    private JPanel AdminPanel, EmployeePanel, CustomerPanel,A1, A2, E1, E2, C1, C2;
    private JTabbedPane TabbedInfo;
    private JLabel nameAdmin, nameEmployee, nameCustomer, adminId, EmployeeId, customerId;
    private JTextField nameAdminTF, nameEmployeeTF, nameCustomerTF;
    private JPasswordField adminTdTF, EmployeeIdTF, CustomerIdTF;
    private JButton adminRegister, adminLogin, employeeRegister, employeeLogin, customerRegister, customerLogin;
    private static final String url = "jdbc:mysql://localhost:3306/banking system";
    private static final String username = "sqluser";
    private static final String password = "ssbhy";

    public Bank(){
        // adding the main tab
        TabbedInfo = new JTabbedPane();
        TabbedInfo.setBackground(Color.DARK_GRAY);
        TabbedInfo.setForeground(Color.WHITE);
        TabbedInfo.setOpaque(true);
        //creating customer panel
        CustomerPanel = new JPanel(new GridLayout(3, 1, 0, 50));
        CustomerPanel.setBackground(Color.DARK_GRAY);
        //adding the components in to customer page pannel
        C1 = new JPanel(new GridLayout(1, 2, 10, 0));
        C1.setBackground(Color.DARK_GRAY);
        nameCustomer= new JLabel("user name ", JLabel.CENTER);
        nameCustomer.setFont(new Font("Mv Boli", Font.BOLD, 20 ));
        nameCustomer.setForeground(Color.WHITE);

        nameCustomerTF = new JTextField(10);
        nameCustomerTF.setFont(new Font("", Font.BOLD, 20));
        nameCustomerTF.setForeground(Color.BLACK);

        C1.add(nameCustomer);
        C1.add(nameCustomerTF);
        
        C2 = new JPanel(new GridLayout(1, 2, 10, 0));
        C2.setBackground(Color.DARK_GRAY);
        customerId = new JLabel("Password ", JLabel.CENTER);
        customerId.setFont(new Font("Mv Boli", Font.BOLD, 20));
        customerId.setForeground(Color.WHITE);


        CustomerIdTF = new JPasswordField(10);
        CustomerIdTF.setForeground(Color.BLACK);
        CustomerIdTF.setFont(new Font("", Font.BOLD, 25));

        C2.add(customerId);
        C2.add(CustomerIdTF);
        
        
        customerRegister = new JButton("Register");
        customerRegister.setBounds(350, 450, 100, 40);
        customerRegister.addActionListener(this);

        customerLogin = new JButton("Log in");
        customerLogin.setBounds(520, 450, 100, 40);
        customerLogin.addActionListener(this);


        CustomerPanel.add(C1);
        CustomerPanel.add(C2);
        CustomerPanel.setBounds(75, 200, 550, 250);

        JPanel CustomerHolder = new JPanel();
        CustomerHolder.setLayout(null);
        CustomerHolder.setBackground(Color.DARK_GRAY);
        CustomerHolder.add(CustomerPanel);
        CustomerHolder.add(customerRegister);
        CustomerHolder.add(customerLogin);
       

        EmployeePanel = new JPanel(new GridLayout(3, 1, 0, 50));
        EmployeePanel.setBackground(Color.DARK_GRAY);
        //adding the components in to customer page pannel
        E1 = new JPanel(new GridLayout(1, 2, 10, 0));
        E1.setBackground(Color.DARK_GRAY);
         
        nameEmployee = new JLabel("Employee name", JLabel.CENTER);
        nameEmployee.setFont(new Font("Mv Boli", Font.BOLD, 20 ));
        nameEmployee.setForeground(Color.WHITE);

        nameEmployeeTF = new JTextField(10);
        nameEmployeeTF.setFont(new Font("", Font.BOLD, 20));
        nameEmployeeTF.setForeground(Color.BLACK);

        E1.add(nameEmployee);
        E1.add(nameEmployeeTF);
        
        E2 = new JPanel(new GridLayout(1, 2, 10, 0));
        E2.setBackground(Color.DARK_GRAY);
        EmployeeId = new JLabel("Employee pin ", JLabel.CENTER);
        EmployeeId.setFont(new Font("Mv Boli", Font.BOLD, 20));
        EmployeeId.setForeground(Color.WHITE);


        EmployeeIdTF = new JPasswordField(10);
        EmployeeIdTF.setForeground(Color.BLACK);
        EmployeeIdTF.setFont(new Font("", Font.BOLD, 20));

        E2.add(EmployeeId);
        E2.add(EmployeeIdTF);
        
        
        employeeRegister = new JButton("Register");
        employeeRegister.setBounds(350, 450, 100, 40);
        employeeRegister.addActionListener(this);

        employeeLogin = new JButton("Log in");
        employeeLogin.setBounds(520, 450, 100, 40);
        employeeLogin.addActionListener(this);


        EmployeePanel.add(E1);
        EmployeePanel.add(E2);
        EmployeePanel.setBounds(75, 200, 550, 250);

        JPanel EmployeeHolder = new JPanel();
        EmployeeHolder.setLayout(null);
        EmployeeHolder.setBackground(Color.DARK_GRAY);
        EmployeeHolder.add(EmployeePanel);
        EmployeeHolder.add(employeeRegister);
        EmployeeHolder.add(employeeLogin);

        //ADMIN

        AdminPanel = new JPanel(new GridLayout(3, 1, 0, 50));
        AdminPanel.setBackground(Color.DARK_GRAY);
        //adding the components in to customer page pannel
        A1 = new JPanel(new GridLayout(1, 2, 10, 0));
        A1.setBackground(Color.DARK_GRAY);
         
        nameAdmin = new JLabel("Administer name", JLabel.CENTER);
        nameAdmin.setFont(new Font("Mv Boli", Font.BOLD, 20 ));
        nameAdmin.setForeground(Color.WHITE);

        nameAdminTF = new JTextField(10);
        nameAdminTF.setFont(new Font("", Font.BOLD, 20));
        nameAdminTF.setForeground(Color.BLACK);

        A1.add(nameAdmin);
        A1.add(nameAdminTF);
        
        A2 = new JPanel(new GridLayout(1, 2, 10, 0));
        A2.setBackground(Color.DARK_GRAY);
        adminId = new JLabel("Administer Password", JLabel.CENTER);
        adminId.setFont(new Font("Mv Boli", Font.BOLD, 20));
        adminId.setForeground(Color.WHITE);


        adminTdTF = new JPasswordField(10);
        adminTdTF.setForeground(Color.BLACK);
        adminTdTF.setFont(new Font("", Font.BOLD, 20));

        A2.add(adminId);
        A2.add(adminTdTF);
        
        
        adminRegister= new JButton("Register");
        adminRegister.setBounds(350, 450, 100, 40);
        adminLogin = new JButton("Log in");
        adminLogin.setBounds(520, 450, 100, 40);


        AdminPanel.add(A1);
        AdminPanel.add(A2);
        AdminPanel.setBounds(75, 200, 550, 250);

        JPanel  AdminHolder = new JPanel();
        AdminHolder.setLayout(null);
        AdminHolder.setBackground(Color.DARK_GRAY);
        AdminHolder.add(AdminPanel);
        AdminHolder.add(adminRegister);
        AdminHolder.add(adminLogin);


        
        TabbedInfo.addTab("Customer", CustomerHolder);
        TabbedInfo.addTab("Employee", EmployeeHolder);
        TabbedInfo.addTab("Admin", AdminHolder);

        
        getContentPane().add(TabbedInfo, BorderLayout.CENTER);
        getContentPane().setBackground(Color.darkGray);
        setLocation(350, 70);
        setSize(700, 600);
        setTitle("Bank page");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e){
           if(e.getSource() == customerRegister){
               new Customer();
               dispose();
           }
           if (e.getSource() == customerLogin) {
               char[] Password = CustomerIdTF.getPassword();
               String userPassword = new String(Password);
               String  name = nameCustomerTF.getText();
               if (isCustomerExist(name, userPassword)) {
                new CustomerLoginPage(nameCustomerTF.getText(), userPassword);
                dispose();
               }else{
                JOptionPane.showMessageDialog(null, "your username or password is incorrect\n please login!", "customer info", JOptionPane.ERROR_MESSAGE);
               }
               String sql = "SELECT COUNT(*) FROM customer WHERE FirstName = ? AND password = ?";

           }
    }

    private boolean isCustomerExist(String userName, String Password){
        String sql = "SELECT COUNT(*) FROM customer WHERE FirstName = ? AND password = ?";
        int count = 0;
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password)){
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, userName);
                stmt.setString(2, Password);
                ResultSet result = stmt.executeQuery();
                result.next();
                count = result.getInt(1);
                stmt.close();
                connection.close();
        }catch(SQLException d){
               System.out.println(d);
        }
        return count > 0;   
    }

    public static void main(String[] args){
        new Bank();
    }

}