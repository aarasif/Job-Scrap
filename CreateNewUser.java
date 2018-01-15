import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class CreateNewUser extends JFrame implements ActionListener
{
	private JLabel labelWelcome,name_tag,password_tag,email_tag, type_tag;
	private JTextField name, email, password;
	private JRadioButton type1,type2;
	private ButtonGroup bg;
	private JPanel panel;
	private JButton button,back;
	private String s;
	public CreateNewUser()
	{
		super("Admin Home Window");
		
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		labelWelcome = new JLabel("Create Account");
		labelWelcome.setBounds(20, 20, 400, 50);
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 40));
		panel.add(labelWelcome);

		name_tag = new JLabel("User Name	: ");
		name_tag.setBounds(20, 130, 150, 25);
		name_tag.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(name_tag);

		name = new JTextField();
		name.setBounds(300,130,400,50);
		panel.add(name);
		
		password_tag = new JLabel("Password	: ");
		password_tag.setBounds(20, 190, 150, 25);
		password_tag.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(password_tag);
		
		password = new JTextField();
		password.setBounds(300,190,400,50);
		panel.add(password);

		email_tag = new JLabel("Email	: ");
		email_tag.setBounds(20, 250, 150, 25);
		email_tag.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(email_tag);

		email = new JTextField();
		email.setBounds(300,250,400,50);
		panel.add(email);

		type_tag = new JLabel("User Type	: ");
		type_tag.setBounds(20, 310, 150, 25);
		type_tag.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(type_tag);

		type1 = new JRadioButton("Normal user");
		type1.setBounds(300,310,150,50);
		panel.add(type1);
		
		type2 = new JRadioButton("Admin user");
		type2.setBounds(550,310,150,50);
		panel.add(type2);

		bg = new ButtonGroup(); 
		bg.add(type1);
		bg.add(type2);
		
		button = new JButton("Create Account");
		button.setBackground(Color.WHITE);
		button.setBounds(550,390,150,60);
		button.addActionListener(this);
		panel.add(button);

		back = new JButton("Back");
		back.setBackground(Color.WHITE);
		back.setBounds(820,590,150,60);
		back.addActionListener(this);
		panel.add(back);
		
		this.add(panel);
		}

		public void actionPerformed(ActionEvent ae)
		{
			String elementText = ae.getActionCommand();
			if(type1.isSelected())
			{
				s="normal";
			}
			else if(type2.isSelected())
			{
				s="admin";
			}
			
			if(elementText.equals(button.getText()))
			{
				insertIntoDB();

				Login obj = new Login();
				this.setVisible(false);
				obj.setVisible(true);
			}
			else if(elementText.equals(back.getText())){
				Login lo = new Login();
				this.setVisible(false);
				lo.setVisible(true);
			}
			
		}

		public void insertIntoDB()
    {
        

        String query = "INSERT INTO user VALUES ('0','"+s+"','"+name.getText()+"','"+password.getText()+"','"+email.getText()+"');";
        
        System.out.println(query);
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder", "root", "");
            Statement stm = con.createStatement();
            stm.execute(query);
            stm.close();
            con.close();
                    
        }
        catch(Exception ex)
        {
            System.out.println("Exception : " +ex.getMessage());
        }
    }
}