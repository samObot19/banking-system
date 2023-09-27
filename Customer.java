import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.Vector;
import java.util.Date;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Locale;
import java.sql.*;

public class Customer extends JFrame implements ActionListener{
    private JLabel FirstNameL, MiddleNameL, LastNameL, BirthDate, phoneNumberL, EmailL, JobTitleL, CityL, Kebele, woredaL, photo, sexLabel, 
    passportLabel, DriverLicenseL, NationalIdL, titleBarL, NationalityL, countryL, selectionOfAcountType, InitialAmountL;
    private JTextField FirstNameTF, MiddleNameTF, LastNameTF, phoneNumberTF, EmailTF, JobTitleTF, CityTF, woredaTF, BirthDayTF, KebeleTF,
    passportTF, DriverLicenseTF, NationalIdTF;
    private JFormattedTextField BirthDateTF;
    private JButton upload, NextB, backButton, NextB2, backButton2, FinishB, goToMain;
    private JCheckBox male, female;
    private ButtonGroup Gender;
    private JPanel panel, sexHolderPanel, addressPanel, identificationPanel;
    private ImageIcon defaultIcon = new ImageIcon("photo.jpg");
    private JComboBox<String> Nationality, liveCountry, cityList;
    private static JComboBox<String> accountTypeList;
    private final int lengthF = 850;
    private final int widthF = 775;
    private final int widthP = 800;
    private final int hightP = 300;
    private String email, phoneNumber;
    private final static String userName = "sqluser";
    private final static String Password = "ssbhy";
    private final static String url = "jdbc:mysql://localhost:3306/Banking system";
    private String customerPassword;
    private static JFileChooser fileChooser;
    //private SpinnerDateModel model;
    private JSpinner birthDateSpinner;
    private static JSpinner intialAmount;
    private static String selected;

    private String First_name, Middle_name, Last_name, Phone_number, Email;
    private ImageIcon Icon = new ImageIcon("banker2.png");

    Customer(){
        setIconImage(Icon.getImage());
        setLayout(null);
        setTitle("customer Form");
        //title bar
        titleBarL = new JLabel("REGISTER FORM", JLabel.LEFT);
        titleBarL.setFont(new Font("Mv Boli", Font.BOLD, 45));
        titleBarL.setBounds(279, 0, 400, 200);
        titleBarL.setForeground(Color.white);
        //photo label;
        photo = new JLabel();
        photo.setText("Photo");
        photo.setHorizontalTextPosition(JLabel.CENTER);
        photo.setIcon(defaultIcon);
        photo.setIconTextGap(-25);
        photo.setVerticalTextPosition(JLabel.TOP);
        photo.setBackground(Color.BLACK);
        photo.setOpaque(true);
        photo.setBounds(0, 0, 200, 200);
        //upload button
        upload = new JButton("upload");
        upload.addActionListener(this);
        upload.setBounds(60, 225, 100, 40);

        NextB = new JButton("Next");
        NextB.addActionListener(this);
        NextB.setBounds(700, 650, 100, 40);

        goToMain = new JButton("Back");
        goToMain.addActionListener(this);
        goToMain.setBounds(400, 650, 100, 40);

        NextB2 = new JButton("Next");
        NextB2.addActionListener(this);
        NextB2.setBounds(700, 650, 100, 40);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBounds(400, 650, 100, 40);

        backButton2 = new JButton("Back");
        backButton2.addActionListener(this);
        backButton2.setBounds(400, 650, 100, 40);

        FinishB = new JButton("Finish");
        //FinishB.addActionListener(new Final_buttton_handdler());
        FinishB.setBounds(700, 650, 100, 40);
        FinishB.addActionListener(new Final_buttton_handdler());
            //basic info panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        
        panel.setBounds(0, 300, widthP, hightP);
        // creating informatons label and text fields
        FirstNameL = new JLabel("First Name", JLabel.CENTER);
        FirstNameL.setForeground(Color.white);
        FirstNameL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        MiddleNameL = new JLabel("Middle Name", JLabel.CENTER);
        MiddleNameL.setForeground(Color.white);
        MiddleNameL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        LastNameL = new JLabel("LastName", JLabel.CENTER);
        LastNameL.setForeground(Color.white);
        LastNameL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        phoneNumberL = new JLabel("phone Number", JLabel.CENTER);
        phoneNumberL.setForeground(Color.white);
        phoneNumberL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        EmailL = new JLabel("Email Address", JLabel.CENTER);
        EmailL.setForeground(Color.white);
        EmailL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        sexLabel = new JLabel("Gender", JLabel.CENTER);
        sexLabel.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        sexLabel.setForeground(Color.white);
        // text fields
        FirstNameTF = new JTextField(30);
        FirstNameTF.setFont(new Font("", Font.PLAIN, 16));
        MiddleNameTF = new JTextField(30);
        MiddleNameTF.setFont(new Font("", Font.PLAIN, 16));
        LastNameTF = new JTextField(30);
        LastNameTF.setFont(new Font("", Font.PLAIN, 16));
        phoneNumberTF = new JTextField(30);
        phoneNumberTF.setFont(new Font("", Font.PLAIN, 16));
        EmailTF = new JTextField(30);
        EmailTF.setFont(new Font("", Font.PLAIN, 16));
        // gender chooser
        male = new JCheckBox("Male");
        female = new JCheckBox("Female");
        Gender = new ButtonGroup();
        // adding Rbutton to gender button group
        Gender.add(male);
        Gender.add(female);
        male.addActionListener(this);
        female.addActionListener(this);
        //adding this groupTow in one panel
        sexHolderPanel = new JPanel(new BorderLayout());
        sexHolderPanel.setBackground(Color.DARK_GRAY);;
        sexHolderPanel.add(male, BorderLayout.EAST);
        sexHolderPanel.add(female, BorderLayout.WEST);
        // addding all labels and text fields to the panel

        panel.add(FirstNameL);
        panel.add(FirstNameTF);
        panel.add(MiddleNameL);
        panel.add(MiddleNameTF);
        panel.add(LastNameL);
        panel.add(LastNameTF);
        panel.add(sexLabel);
        panel.add(sexHolderPanel);
        panel.add(phoneNumberL);
        panel.add(phoneNumberTF);
        panel.add(EmailL);
        panel.add(EmailTF);

        addressPanel = new JPanel();
        addressPanel.setBackground(Color.DARK_GRAY);
        addressPanel.setBounds(0, 300, widthP, hightP);
        addressPanel.setLayout(new GridLayout(6, 2, 10, 10));

        BirthDate = new JLabel("Birth Date", JLabel.CENTER);
        BirthDate.setForeground(Color.WHITE);
        BirthDate.setFont(new Font("", Font.TRUETYPE_FONT, 20));

        CityL = new JLabel("City", JLabel.CENTER);
        CityL.setForeground(Color.WHITE);
        CityL.setFont(new Font("", Font.TRUETYPE_FONT, 20));

        woredaL = new JLabel("Woreda", JLabel.CENTER);
        woredaL.setForeground(Color.white);
        woredaL.setFont(new Font("", Font.TRUETYPE_FONT, 20));

        Kebele = new JLabel("Kebele", JLabel.CENTER);
        Kebele.setForeground(Color.WHITE);;
        Kebele.setFont(new Font("", Font.TRUETYPE_FONT, 20));

        JobTitleL = new JLabel("Job Title", JLabel.CENTER);
        JobTitleL.setForeground(Color.white);
        JobTitleL.setFont(new Font("", Font.TRUETYPE_FONT, 20));

        DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        DateFormatter birthDateFormatter = new DateFormatter(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.set(1980, 1, 1);
        Date minValue = calendar.getTime();
        calendar.set(2050, 12, 31);
        Date upto = calendar.getTime();
        int steps = Calendar.DAY_OF_MONTH;
        Date currentValue = new Date();
        SpinnerDateModel dmodel = new SpinnerDateModel(currentValue, minValue, upto, steps);
        birthDateSpinner = new JSpinner(dmodel);
        JSpinner.DateEditor dEditor = new  JSpinner.DateEditor(birthDateSpinner, "dd/mm/yy");
        birthDateSpinner.setEditor(dEditor);
        birthDateSpinner.setFont(new Font("", Font.BOLD, 30));

        String [] countries = Locale.getISOCountries();
        String [] countryName = new String[countries.length];

        for(int i = 0; i < countries.length; i++){
            Locale locale = new Locale("", countries[i]);
            countryName[i] = locale.getDisplayCountry();
        }

        BirthDateTF = new JFormattedTextField(birthDateFormatter);
        BirthDateTF.setColumns(10);
        BirthDateTF.setValue(0);
        BirthDateTF.setFont(new Font("", Font.PLAIN, 18));
        BirthDateTF.setForeground(Color.BLUE);
        BirthDateTF.setBackground(Color.GRAY);

        DefaultComboBoxModel<String> cmodel = new DefaultComboBoxModel<>(countryName);
        liveCountry = new JComboBox<>(cmodel);
        liveCountry.setFont(new Font("", Font.BOLD, 26));
        CityTF = new JTextField(20);
        CityTF.setFont(new Font("", Font.PLAIN, 18));

        countryL = new JLabel("country", JLabel.CENTER);
        countryL.setFont(new Font("", Font.PLAIN, 18));
        countryL.setForeground(Color.WHITE);
        woredaTF = new JTextField(20);
        woredaTF.setFont(new Font("", Font.PLAIN, 18));

        KebeleTF = new JTextField(20);
        KebeleTF.setFont(new Font("", Font.PLAIN, 18));

        JobTitleTF = new JTextField(20);
        JobTitleTF.setFont(new Font("", Font.PLAIN, 18));

        addressPanel.add(BirthDate);
        addressPanel.add(birthDateSpinner);
        addressPanel.add(countryL);
        addressPanel.add(liveCountry);
        addressPanel.add(CityL);
        addressPanel.add(CityTF);
        addressPanel.add(woredaL);
        addressPanel.add(woredaTF);
        addressPanel.add(Kebele);
        addressPanel.add(KebeleTF);
        addressPanel.add(JobTitleL);
        addressPanel.add(JobTitleTF);

        identificationPanel = new JPanel(new GridLayout(6, 2, 10, 20));
        identificationPanel.setBackground(Color.DARK_GRAY);
        identificationPanel.setBounds(0, 300, widthP, hightP);

        selectionOfAcountType = new JLabel("Account Type", JLabel.CENTER);
        selectionOfAcountType.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        selectionOfAcountType.setForeground(Color.WHITE);

        String[] lst = {"saving", "checking", "CD", "money market", "Loan"};
        accountTypeList = new JComboBox(lst);
        float min = 100;
        float max = 100000;
        float increment = 20;
        SpinnerNumberModel mod = new SpinnerNumberModel(min, min, max, increment);
        intialAmount = new JSpinner(mod);
        NationalityL = new JLabel("Nationality", JLabel.CENTER);
        NationalityL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        NationalityL.setForeground(Color.WHITE);
        InitialAmountL = new JLabel("Intial Amount", JLabel.CENTER);
        InitialAmountL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        InitialAmountL.setForeground(Color.WHITE);
        NationalIdL = new JLabel("National ID(SSN)", JLabel.CENTER);
        NationalIdL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        NationalIdL.setForeground(Color.WHITE);
        passportLabel = new JLabel("Passport Number", JLabel.CENTER);
        passportLabel.setForeground(Color.WHITE);
        passportLabel.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        DriverLicenseL = new JLabel("Driver License Number", JLabel.CENTER);
        DriverLicenseL.setFont(new Font("", Font.TRUETYPE_FONT, 20));
        DriverLicenseL.setForeground(Color.WHITE);

        

        DefaultComboBoxModel<String> countryModel = new DefaultComboBoxModel<>(countryName);
        Nationality = new JComboBox<>(countryModel);
        Nationality.setFont(new Font("", Font.PLAIN, 30));

        passportTF = new JTextField(20);
        DriverLicenseTF = new JTextField(20);
        NationalIdTF = new JTextField(steps);
        NationalIdTF.setFont(new Font("", Font.PLAIN, 18));
        passportTF.setFont(new Font("", Font.PLAIN, 18));
        DriverLicenseTF.setFont(new Font("", Font.PLAIN, 18));

        identificationPanel.add(NationalityL);
        identificationPanel.add(Nationality);
        identificationPanel.add(NationalIdL);
        identificationPanel.add(NationalIdTF);
        identificationPanel.add(passportLabel);
        identificationPanel.add(passportTF);
        identificationPanel.add(DriverLicenseL);
        identificationPanel.add(DriverLicenseTF);
        identificationPanel.add(selectionOfAcountType);
        identificationPanel.add(accountTypeList);
        identificationPanel.add(InitialAmountL);
        identificationPanel.add(intialAmount);

        add(panel);
        add(photo);
        add(NextB);
        add(goToMain);
        add(upload);
        add(titleBarL);
        getContentPane().setBackground(Color.DARK_GRAY);       
        setSize(lengthF, widthF);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == upload){
            fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selected.getPath());
                photo.setIcon(icon);
            }
        }
        if(e.getSource() == NextB){
            email = EmailTF.getText();
            phoneNumber = phoneNumberTF.getText();
            
            if (isValidEmail(email) && isValidPhoneNumber(phoneNumber)) {
                getContentPane().remove(panel);
                getContentPane().add(NextB2);
                getContentPane().remove(NextB);
                getContentPane().remove(goToMain);         
                add(addressPanel);
                add(backButton);
                revalidate();
                repaint();    
            }else{
            JOptionPane.showMessageDialog(null, "please enter the valid email or phone", "ERROR", JOptionPane.ERROR_MESSAGE);
            }        
        }

        if(e.getSource() == backButton){
            getContentPane().remove(addressPanel);
            getContentPane().remove(backButton);
            getContentPane().remove(NextB2);
            add(panel);
            add(NextB);
            add(goToMain);
            revalidate();
            repaint();
        }

        if (e.getSource() == goToMain) {
            new Bank();
            dispose();
        }

        if(e.getSource() == NextB2){
            getContentPane().remove(addressPanel);
            getContentPane().remove(NextB2);
            getContentPane().remove(backButton);
            //FinishB.addActionListener(new Final_buttton_handdler());
            add(identificationPanel);
            add(FinishB);
            add(backButton2);
            revalidate();
            repaint();
        }

        if(e.getSource() == backButton2){
            getContentPane().remove(identificationPanel);
            getContentPane().remove(FinishB);
            getContentPane().remove(backButton2);
            add(addressPanel);
            add(NextB2);
            add(backButton);
            revalidate();
            repaint();
        }

        if(e.getSource() == male || e.getSource() == female){
            if (male.isSelected()) {
                selected = male.getText();
            }else{
                selected = female.getText();
            }
        }   
    }

    private boolean isValidEmail(String EMAIL){

        String emailRegx = "^[A-Za-z0-9+_.]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegx);
        Matcher matcher = pattern.matcher(EMAIL);
        return matcher.matches();

    }
    private boolean isValidPhoneNumber(String phone){
        String pattern = "^\\+?[1-9]\\d{1,14}$";
        return phone.matches(pattern);
    }


    public class Final_buttton_handdler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            First_name = FirstNameTF.getText();
            Middle_name = MiddleNameTF.getText();
            Last_name = LastNameTF.getText();
            Phone_number = phoneNumberTF.getText();
            Email = EmailTF.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, userName, Password);
                String Query = "INSERT INTO Customer(FirstName, MiddleName, LastName, Gender, phoneNumber, Email, photo, birth_date, Nationality, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                String accountCreaterQuery = "INSERT INTO account (Customer_ID, AccountNumber, AccountType, Balance) VALUES(?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmt2 = connection.prepareStatement(accountCreaterQuery);

                //geting info to the photo file 
                File selectedPhotoFile = fileChooser.getSelectedFile();  //uploading file from th desktop
                FileInputStream fis = new FileInputStream(selectedPhotoFile);
                byte[] photoInByte = new byte[fis.available()];
                fis.read(photoInByte);  //changing the photo pixel int byte
                Date birthdate = (Date) birthDateSpinner.getValue();
                java.sql.Date sqldate = new java.sql.Date(birthdate.getTime());
                String nation = (String) Nationality.getSelectedItem();
                //generating unique password for
                passwordGenerator custPassword = new passwordGenerator("customer", "password", 7);
                customerPassword = custPassword.getId(); 
                //inserting to the database
                stmt.setString(1, First_name);
                stmt.setString(2, Middle_name);
                stmt.setString(3, Last_name);
                stmt.setString(4, selected);
                stmt.setString(5, Phone_number);
                stmt.setString(6, Email);
                stmt.setBytes(7, photoInByte);
                stmt.setDate(8, sqldate);
                stmt.setString(9, nation);
                stmt.setString(10, customerPassword);
                stmt.executeUpdate();

                ResultSet customerID = stmt.getGeneratedKeys();
                long cId = 0;
                if(customerID.next()){
                    cId = customerID.getLong(1);
                }

                passwordGenerator accNumber = new passwordGenerator("account", "AccountNumber", 12);
                stmt2.setLong(1, cId);
                stmt2.setString(2, accNumber.getId());
                stmt2.setString(3, (String) accountTypeList.getSelectedItem());
                stmt2.setDouble(4, (Double) intialAmount.getValue());
                stmt2.executeUpdate();
                stmt2.close();

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "registered!", "valid", JOptionPane.INFORMATION_MESSAGE);
                }
                stmt.close();
                connection.close();
            }catch(SQLIntegrityConstraintViolationException d){
                JOptionPane.showMessageDialog(null, "registered!\n user name: " + FirstNameTF.getText() + "\npassword: " + customerPassword, "valid", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception m) {
                JOptionPane.showMessageDialog(null, m, "error in connection", JOptionPane.ERROR_MESSAGE);
            }           
        }
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new Customer();
            }
        });
    }
}