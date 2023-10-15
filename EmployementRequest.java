import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;

public class EmployementRequest extends JFrame implements ActionListener{
    private JLabel status, documentsL, jobPosition, EducationLevelL, describtions;
    private JButton fileUploader, requestBt;
    private JTextField statusTF, jobPositionTF, EducationLevelTF;
    private JTextArea describtionArea;
    
    public EmployementRequest(){
        setLayout(null);
        documentsL = new JLabel();
        documentsL.setBackground(Color.WHITE);
        documentsL.setOpaque(true);
        documentsL.setBounds(0, 0, 200, 200);

        fileUploader = new JButton("upload");
        fileUploader.setBounds(50, 220, 100,50);
        fileUploader.addActionListener(this);

        requestBt = new JButton("Finish");
        requestBt.setBounds(500,570,100,50);
        requestBt.addActionListener(this);

        status = new JLabel("current job", JLabel.CENTER);
        status.setForeground(Color.WHITE);
        status.setBackground(Color.DARK_GRAY);
        status.setFont(new Font("", Font.PLAIN, 22));
        status.setBounds(220, 180, 150,50);

        statusTF = new JTextField(10);
        statusTF.setFont(new Font("", Font.BOLD, 22));
        statusTF.setBounds(410, 180, 270, 50);

        jobPosition = new JLabel("Looking job", JLabel.CENTER);
        jobPosition.setBackground(Color.DARK_GRAY);
        jobPosition.setForeground(Color.WHITE);
        jobPosition.setFont(new Font("", Font.PLAIN, 22));
        jobPosition.setBounds(220, 280,150,50);

        jobPositionTF = new JTextField(20);
        jobPositionTF.setFont(new Font("", Font.BOLD, 22));
        jobPositionTF.setBounds(410, 280,270,50);

        EducationLevelL = new JLabel("Education Level", JLabel.CENTER);
        EducationLevelL.setFont(new Font("", Font.PLAIN, 22));
        EducationLevelL.setBackground(Color.DARK_GRAY);
        EducationLevelL.setForeground(Color.WHITE);
        EducationLevelL.setBounds(220,380,170,50);

        EducationLevelTF = new JTextField();
        EducationLevelTF.setFont(new Font("", Font.BOLD, 22));
        EducationLevelTF.setBounds(410, 380,270,50);

        describtions = new JLabel("Describtion", JLabel.CENTER);
        describtions.setFont(new Font("", Font.PLAIN, 22));
        describtions.setBackground(Color.DARK_GRAY);
        describtions.setForeground(Color.WHITE);
        describtions.setBounds(220, 480,150,50);

        describtionArea = new JTextArea(5, 10);
        describtionArea.setFont(new Font("", Font.PLAIN, 22));
        describtionArea.setBounds(410, 480,270,50);

        add(status);
        add(statusTF);
        add(documentsL);
        add(fileUploader);
        add(jobPosition);
        add(jobPositionTF);
        add(EducationLevelL);
        add(EducationLevelTF);
        add(describtions);
        add(describtionArea);
        add(requestBt);

        setSize(800, 700);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == fileUploader) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selected.getPath());
                documentsL.setIcon(icon);
            }
        }
    }

    public static void main(String[] args){
        new EmployementRequest();
    }
}